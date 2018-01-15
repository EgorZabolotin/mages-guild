package mag.near.lux;

import mag.near.lux.dto.quests.CrimeXml;
import mag.near.lux.dto.quests.HeadHunterXml;
import mag.near.lux.dto.quests.QuestsXml;
import mag.near.lux.dto.quests.WantedXml;
import mag.near.lux.model.*;
import mag.near.lux.services.MagesService;
import mag.near.lux.services.MailService;
import mag.near.lux.services.OffenderService;
import mag.near.lux.util.FileUtil;
import mag.near.lux.util.outputformatters.QuestXmlFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class QuestBureaucracy {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestBureaucracy.class);

    public static void main(String[] args) {
        MagesService magesService = new MagesService();
        OffenderService offenderService = new OffenderService();


        Map<Rank, List<MagePerson>> magesByRank = magesService.getMages(20).stream()
                .collect(Collectors.groupingBy(MagePerson::getRank));

        Map<Rank, List<OffenderWithReward>> offendersByRank =
                offenderService.getOffenders(50).stream()
                        .map(OffenderWithReward::new)
                        .collect(Collectors.groupingBy((OffenderWithReward offender) ->
                                Arrays.stream(Rank.values())
                                        .filter(rank -> rank.getValue() >= offender.getOffender().getAge()).min(Comparator.comparing(Rank::getValue)).orElse(Rank.UNDEFIND)

                        ));


        Map<Rank, List<QuestWithList>> questByRank = new HashMap<>();
        for (Rank rank : Rank.values()) {
            if (!rank.equals(Rank.UNDEFIND)) {
                List<OffenderWithReward> offenders = offendersByRank.get(rank);
                List<MagePerson> mages = magesByRank.get(rank);
                List<QuestWithList> quests = getQuestsWithListForRank(offenders, mages);
                questByRank.put(rank, quests);
            }
        }

        List<HeadHunterXml> headHunters = new ArrayList<>();

        headHunters = questByRank.entrySet().stream()
            .flatMap(entry -> entry.getValue().stream()
                .map(questWithList -> {
                    HeadHunterXml headHunterXml = getHedHanterXmlWithEmptyWanteds(questWithList);
                    questWithList.getWantedList().forEach(offenderWithReward -> {
                        WantedXml wantedXml = getWantedXml(offenderWithReward);
                        headHunterXml.addWanted(wantedXml);
                    });
                    return headHunterXml;
                })
            )
        .collect(Collectors.toList());


        QuestsXml questsXml  = new QuestsXml(headHunters, "agregated-quests");
        QuestXmlFormatter questsXmlFormatter = new QuestXmlFormatter(questsXml);

        FileUtil.writeQuestsToFile(questsXmlFormatter.toString(), questsXmlFormatter.getFileName());
        MailService.sendMail("ezabolotin@luxoft.com", "Quests list", "See quests in attachment", "quest-bureaucracy/tmp/agregated-quests.xml");


    }

    public static WantedXml getWantedXml(OffenderWithReward offenderWithReward) {
        WantedXml wantedXml = getWantedXmlEmptyCrimes(offenderWithReward);
        offenderWithReward.getOffender().getCrimes()
                .forEach(crime -> wantedXml.addCrime(getCrimeXml(crime)));
        return  wantedXml;
    }

    public static WantedXml getWantedXmlEmptyCrimes(OffenderWithReward offenderWithReward) {
        String wantedName = offenderWithReward.getOffender().getSuffix() + " "
                + offenderWithReward.getOffender().getName() + " "
                + offenderWithReward.getOffender().getSurname();
        String avgReward = ((Double)((offenderWithReward.getMaxReward() + offenderWithReward.getMinReward())/2.)).toString();
        String age = String.valueOf(offenderWithReward.getOffender().getAge());
        String minReward = ((Integer) offenderWithReward.getMinReward()).toString();
        String maxReward = ((Integer) offenderWithReward.getMaxReward()).toString();
        return new WantedXml(wantedName, age, minReward, maxReward, avgReward);
    }

    public static CrimeXml getCrimeXml(Crime crime) {
        CrimeXml crimeXml = new CrimeXml(crime.getType().getTypeName()
            , crime.getLocation()
            , crime.getTimestamp().format(DateTimeFormatter.ISO_DATE)
            , crime.getArticle()
            , crime.getPunishment());
        return crimeXml;
    }

    public static HeadHunterXml getHedHanterXmlWithEmptyWanteds(QuestWithList questWithList) {
        HeadHunterXml headHunterXml = new HeadHunterXml();
        String headHunterName = questWithList.getHeadHunter().getName()
                + " " + questWithList.getHeadHunter().getSurname();
        headHunterXml.setHeadHunterName(headHunterName);
        headHunterXml.setRank(questWithList.getHeadHunter().getRank().toString());
        return headHunterXml;
    }


    public static TableRecord getTableRecord(MagePerson headHunter, OffenderWithReward wanted, Crime crime) {
        TableRecord tableRecord = new TableRecord();
        tableRecord.setRank(headHunter.getRank());
        tableRecord.setMageName(headHunter.getName() + " " + headHunter.getSurname());
        tableRecord.setWantedName(wanted.getOffender().getSuffix() + " "
                + wanted.getOffender().getName() + " " + wanted.getOffender().getSurname());
        tableRecord.setWantedAge(wanted.getOffender().getAge());
        tableRecord.setMinReward(wanted.getMinReward());
        tableRecord.setMaxReward(wanted.getMaxReward());
        tableRecord.setAvgReward((wanted.getMaxReward() + wanted.getMinReward()) / 2);
        tableRecord.setCrimeName(crime.getArticle());
        tableRecord.setCrimeType(crime.getType());
        tableRecord.setCrimePalce(crime.getLocation());
        tableRecord.setCrimeDate(crime.getTimestamp());
        return tableRecord;
    }

    private static List<QuestWithList> getQuestsWithListForRank(List<OffenderWithReward> offenders, List<MagePerson> mages){
        List<QuestWithList> quests = mages.stream().map(QuestWithList::of)
                .collect(Collectors.toList());
        int i = 0;
        for (OffenderWithReward offender : offenders) {
            if (i > mages.size() - 1) i = 0;
            quests.get(i).addWanted(offender);
            i++;
        }
        return quests;
    }
}

