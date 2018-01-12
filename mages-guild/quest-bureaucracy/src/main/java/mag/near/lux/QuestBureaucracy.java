package mag.near.lux;

import mag.near.lux.dto.ArrayOfCrimeDTO;
import mag.near.lux.dto.PersonDTO;
import mag.near.lux.model.*;
import mag.near.lux.services.CrimeService;
import mag.near.lux.services.MagesService;
import mag.near.lux.services.MailService;
import mag.near.lux.services.OffenderService;
import mag.near.lux.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


        List<String> questsString = questByRank.entrySet().stream()
            .flatMap(entry -> entry.getValue().stream().flatMap(value ->
                    value.getWanted().getOffender().getCrimes().stream().map(crime -> {
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
                    })
            ))
            .sorted(Comparator.comparing(TableRecord::getRank).reversed().thenComparing(TableRecord::getMageName)
                    .thenComparing(TableRecord::getAvgReward).thenComparing(TableRecord::getCrimeDate).reversed())
            .peek(record -> LOGGER.debug(record.toString()))
            .map(TableRecord::toString)
            .collect(Collectors.toList()
         ) ;


        FileUtil.writeQuestsToFile(questsString);
        //MailService.sendMail("ezabolotin@luxoft.com", "Quests list", );


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
}

