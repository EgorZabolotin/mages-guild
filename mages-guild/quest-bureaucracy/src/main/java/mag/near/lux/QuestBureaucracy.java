package mag.near.lux;

import mag.near.lux.model.*;
import mag.near.lux.services.MagesService;
import mag.near.lux.services.MailService;
import mag.near.lux.services.OffenderService;
import mag.near.lux.util.FileUtil;
import mag.near.lux.util.outputformatters.XmlFormatter;
import mag.near.lux.util.tabular.TableData;
import mag.near.lux.util.tabular.TableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


        Map<Rank, List<Quest>> questByRank = new HashMap<>();
        for (Rank rank : Rank.values()) {
            if (!rank.equals(Rank.UNDEFIND)) {
                List<OffenderWithReward> offenders = offendersByRank.get(rank);
                List<MagePerson> mages = magesByRank.get(rank);
                List<Quest> quests = getQuestsForRank(offenders, mages);
                //Map<MagePerson, List<Quest>> questsByMage = quests.stream().collect(Collectors.groupingBy(Quest::getHeadHunter));
                questByRank.put(rank, quests);
            }
        }


/*        List<String> questsString = questByRank.entrySet().stream()
            .flatMap(entry -> entry.getValue().stream()
                .flatMap(value -> value.getWanted().getOffender().getCrimes().stream()
                    .map(crime -> getTableRecord(entry, value, crime))
                )
            )
            .sorted(Comparator.comparing(TableRecord::getRank).reversed().thenComparing(TableRecord::getMageName)
                    .thenComparing(TableRecord::getAvgReward).thenComparing(TableRecord::getCrimeDate).reversed())
            .peek(record -> LOGGER.debug(record.toString()))
            .map(TableRecord::toString)
            .collect(Collectors.toList()
         ) ;*/

        TableData questsTable = new TableData("quests");
        questsTable.addRow(TableRecord.getTableheader());


        List<TableRow> questsRows = questByRank.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .flatMap(value -> value.getWanted().getOffender().getCrimes().stream()
                                .map(crime -> getTableRecord(entry, value, crime))
                        )
                )
                .sorted(Comparator.comparing(TableRecord::getRank).reversed().thenComparing(TableRecord::getMageName)
                        .thenComparing(TableRecord::getAvgReward).thenComparing(TableRecord::getCrimeDate).reversed())
                .peek(record -> LOGGER.debug(record.toString()))
                .map(TableRecord::toTableRow)
                .peek(questsTable::addRow)
                .collect(Collectors.toList()
                ) ;

        XmlFormatter xmlFormatter = new XmlFormatter(questsTable);

        FileUtil.writeQuestsToFile(xmlFormatter.toString(), xmlFormatter.getFileName());
        MailService.sendMail("ezabolotin@luxoft.com", "Quests list", "See quests in attachment", "quest-bureaucracy/tmp/quests.xml");


    }

    public static TableRecord getTableRecord(Map.Entry<Rank, List<Quest>> entry, Quest value, Crime crime) {
        TableRecord tableRecord = new TableRecord();
        tableRecord.setRank(entry.getKey());
        tableRecord.setMageName(value.getHeadHunter().getName() + " " + value.getHeadHunter().getSurname());
        tableRecord.setWantedName(value.getWanted().getOffender().getSuffix() + " "
                + value.getWanted().getOffender().getName() + " " + value.getWanted().getOffender().getSurname());
        tableRecord.setWantedAge(value.getWanted().getOffender().getAge());
        tableRecord.setMinReward(value.getWanted().getMinReward());
        tableRecord.setMaxReward(value.getWanted().getMaxReward());
        tableRecord.setAvgReward((value.getWanted().getMaxReward() + value.getWanted().getMinReward()) / 2);
        tableRecord.setCrimeName(crime.getArticle());
        tableRecord.setCrimeType(crime.getType());
        tableRecord.setCrimePalce(crime.getLocation());
        tableRecord.setCrimeDate(crime.getTimestamp());
        return tableRecord;
    }

    private static List<Quest> getQuestsForRank(List<OffenderWithReward> offenders, List<MagePerson> mages) {
        List<Quest> quests = new ArrayList<>();
        int i = 0;
        for (OffenderWithReward offender : offenders) {
            if (i > mages.size() - 1) i = 0;
            quests.add(Quest.of(mages.get(i), offender));
            i++;
        }
        return quests;
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

