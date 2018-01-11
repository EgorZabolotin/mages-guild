package mag.near.lux.model;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;
import mag.near.lux.dto.CrimeDTO;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Value
@AllArgsConstructor
@ToString
public class Crime {

    public Crime(CrimeDTO crimeDTO, CrimeType crimeType){
        this.type = crimeType;
        this.location = crimeDTO.getLocation();
        this.timestamp = ZonedDateTime.parse(crimeDTO.getTimestamp(), DateTimeFormatter.ISO_DATE_TIME);
        this.article = crimeDTO.getArticle();
        this.punishment = crimeDTO.getPunishment();
    }

    private CrimeType type;

    private String location;

    private ZonedDateTime timestamp;

    private String article;

    private String punishment;
}
