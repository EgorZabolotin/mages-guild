package mag.near.lux;

import mag.near.lux.dto.PersonDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class QuestBureaucracy {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestBureaucracy.class);
    private static final String getMagesUrl = "http://localhost:8080/mages-registry-web/mages/{limit}";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        PersonDTO[]  magesArray;

        ResponseEntity<PersonDTO[]> mages = restTemplate.getForEntity(getMagesUrl, PersonDTO[].class, "5");

        if(mages.getStatusCode().equals(HttpStatus.OK)){
            magesArray = mages.getBody();
            Stream.of(magesArray)
                    .forEach(person -> LOGGER.debug(person.toString()));

        }else{
            LOGGER.error("Error ocured while getting mages. Response is {}", mages.getStatusCode());
        }
    }
}
