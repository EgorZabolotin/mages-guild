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

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.AbstractMap.SimpleImmutableEntry;

public class QuestBureaucracy {

    public static void main(String[] args) {
        final MagesService magesService = new MagesService();
        final OffenderService offenderService = new OffenderService();

        final Map<Rank, List<MagePerson>> magesByRank = magesService.getMagesByRank(20);
        final Map<Rank, List<OffenderWithReward>> offendersByRank = offenderService.getOffendersByRank(50);

        final Map<Rank, List<QuestWithList>> questByRank = Arrays.stream(Rank.values())
            .filter(Rank::isDefined)
            .map(rank -> {
                final List<OffenderWithReward> offenders = offendersByRank.get(rank);
                final List<MagePerson> mages = magesByRank.get(rank);
                final List<QuestWithList> quests = getQuestsWithListForRank(offenders, mages);
                return new SimpleImmutableEntry<>(rank, quests);
            })
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        final List<HeadHunterXml> headHunters = questByRank.entrySet().stream()
            .flatMap(entry -> entry.getValue().stream().map(QuestBureaucracy::getHedHanterXml))
            .collect(Collectors.toList());

        final QuestXmlFormatter questsXmlFormatter = new QuestXmlFormatter(new QuestsXml(headHunters, "agregated-quests"));

        FileUtil.writeQuestsToFile(questsXmlFormatter);
        MailService.sendMail("ezabolotin@luxoft.com", "Quests list", "See quests in attachment", "quest-bureaucracy/tmp/agregated-quests.xml");


    }

    private static WantedXml getWantedXml(OffenderWithReward offenderWithReward) {

        final String wantedName = offenderWithReward.getOffender().getSuffix() + " "
            + offenderWithReward.getOffender().getName() + " "
            + offenderWithReward.getOffender().getSurname();
        final String age = String.valueOf(offenderWithReward.getOffender().getAge());
        final String minReward = ((Integer) offenderWithReward.getMinReward()).toString();
        final String maxReward = ((Integer) offenderWithReward.getMaxReward()).toString();
        final String avgReward = ((Double) ((offenderWithReward.getMaxReward() + offenderWithReward.getMinReward()) / 2.)).toString();
        final List<CrimeXml> crimeXmls = offenderWithReward.getOffender().getCrimes().stream()
            .map(QuestBureaucracy::getCrimeXml)
            .collect(Collectors.toList());
        return new WantedXml(wantedName, age, minReward, maxReward, avgReward, crimeXmls);
    }

    private static CrimeXml getCrimeXml(Crime crime) {
        return new CrimeXml(crime.getType().getTypeName()
            , crime.getLocation()
            , crime.getTimestamp().format(DateTimeFormatter.ISO_DATE)
            , crime.getArticle()
            , crime.getPunishment());
    }

    private static HeadHunterXml getHedHanterXml(final QuestWithList questWithList) {
        final String headHunterName = questWithList.getHeadHunter().getName()
            + " " + questWithList.getHeadHunter().getSurname();
        final List<WantedXml> wantedXmls = questWithList.getWantedList().stream()
            .map(QuestBureaucracy::getWantedXml)
            .collect(Collectors.toList());
        return new HeadHunterXml(headHunterName, questWithList.getHeadHunter().getRank().toString(), wantedXmls);
    }

/*    private static List<QuestWithList> getQuestsWithListForRank1(List<OffenderWithReward> offenders, List<MagePerson> mages) {
        List<QuestWithList> quests = mages.stream().map(QuestWithList::of)
            .collect(Collectors.toList());
        int i = 0;
        for (OffenderWithReward offender : offenders) {
            if (i > mages.size() - 1) i = 0;
            quests.get(i).addWanted(offender);
            i++;
        }
        return quests;
    }*/


    /**
     * Distribute offenders among mages.
     *
     * @param offenders
     * @param mages
     * @return
     */
    private static List<QuestWithList> getQuestsWithListForRank(List<OffenderWithReward> offenders, List<MagePerson> mages) {
        final int offendersPerMage = offenders.size() / mages.size();
        final int addtionalOffenders = offenders.size() % mages.size();

        final List<QuestWithList> quests = new ArrayList<>(mages.size());
        for (int i = 0; i < mages.size(); i++) {
            final int offendersPermageCorrected = i < addtionalOffenders ? offendersPerMage + 1 : offendersPerMage;
            quests.add(new QuestWithList(
                mages.get(i),
                offenders.subList(i * offendersPermageCorrected, (i + 1) * offendersPermageCorrected)
            ));
        }
        return quests;
    }


}

