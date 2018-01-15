package mag.near.lux.model;

import lombok.Data;
import mag.near.lux.util.tabular.CellData;
import mag.near.lux.util.tabular.TableRow;

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

    public TableRow toTableRow(){
        TableRow tableRow = new TableRow(false);
        tableRow.addCell(new CellData(rank.toString()));
        tableRow.addCell(new CellData(mageName));
        tableRow.addCell(new CellData(wantedName));
        tableRow.addCell(new CellData(((Integer)wantedAge).toString()));
        tableRow.addCell(new CellData(((Integer)minReward).toString()));
        tableRow.addCell(new CellData(((Integer)maxReward).toString()));
        tableRow.addCell(new CellData(((Integer)avgReward).toString()));
        tableRow.addCell(new CellData(((Integer)totalReward).toString()));
        tableRow.addCell(new CellData(crimeName));
        tableRow.addCell(new CellData(crimePalce));
        tableRow.addCell(new CellData(crimeDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        tableRow.addCell(new CellData(crimeType.getTypeName()));
        return tableRow;
    }

    public static final TableRow getTableheader(){
        TableRow tableHeader = new TableRow(true);
        tableHeader.addCell(new CellData("Rank"));
        tableHeader.addCell(new CellData("HeadHunter"));
        tableHeader.addCell(new CellData("Wanted name"));
        tableHeader.addCell(new CellData("Wanted age"));
        tableHeader.addCell(new CellData("Min reward"));
        tableHeader.addCell(new CellData("Max reward"));
        tableHeader.addCell(new CellData("Avg reward"));
        tableHeader.addCell(new CellData("Total reward"));
        tableHeader.addCell(new CellData("Article"));
        tableHeader.addCell(new CellData("Crime location"));
        tableHeader.addCell(new CellData("Crime date"));
        tableHeader.addCell(new CellData("Crime type"));
        return tableHeader;
    }
}
