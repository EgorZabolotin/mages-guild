package mag.near.lux.model;

import lombok.Data;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


@Data
public class TableRecord {
    Rank rank;
    String mageName;
    String wantedName;
    int wantedAge;
    int minReward;
    int maxReward;
    int avgReward;
    int totalReward;
    String crimeName;
    String crimePalce;
    ZonedDateTime crimeDate;
    CrimeType crimeType;

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HeadHunter: ")
                .append(rank).append("\t")
                .append(mageName).append("\t");
        stringBuilder.append("Wanted: ")
                .append(wantedName).append("\t")
                .append("Age: ").append(wantedAge).append("\t")
                .append("Reward: ").append(avgReward).append("\t")
                .append("Crime tipe: ").append(crimeType.getTypeName()).append("\t")
                .append("Article: ").append(crimeName).append("\t")
                .append("Date: ").append(crimeDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).append("\t")
                .append("place: ").append(crimePalce)
        ;
        return stringBuilder.toString();
    }
}
