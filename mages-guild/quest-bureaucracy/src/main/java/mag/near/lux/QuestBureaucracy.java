package mag.near.lux;

import mag.near.lux.dto.ArrayOfCrimeDTO;
import mag.near.lux.dto.PersonDTO;
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
import java.util.stream.Stream;

public class QuestBureaucracy {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestBureaucracy.class);
    private static final String getMagesUrl = "http://localhost:8080/mages-registry-web/mages/{limit}";
    private static final String getCrimesById = "http://localhost:9090/mage-ministry/criminal/judgement/35c238f4-ee42-4ee4-9062-c1b4b7d27474";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        PersonDTO[]  magesArray;

        ResponseEntity<PersonDTO[]> mages = restTemplate.getForEntity(getMagesUrl, PersonDTO[].class, "5");

        if(mages.getStatusCode().equals(HttpStatus.OK)){
            magesArray = mages.getBody();
            Stream.of(magesArray)
                    .forEach(person -> LOGGER.debug(person.toString()));

        }else{
            LOGGER.error("Error occurred while getting mages. Response is {}", mages.getStatusCode());
        }

        ResponseEntity<String> crimes = restTemplate.getForEntity(getCrimesById, String.class);
        if(crimes.getStatusCode().equals(HttpStatus.OK)) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(ArrayOfCrimeDTO.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                ArrayOfCrimeDTO arrayOfCrimeDTO = (ArrayOfCrimeDTO) unmarshaller.unmarshal(
                        new StreamSource(new ByteArrayInputStream(crimes.getBody().getBytes(StandardCharsets.ISO_8859_1))));
                arrayOfCrimeDTO.getCrimes().stream()
                        .forEach(crime -> LOGGER.debug(crime.toString()));
            } catch (JAXBException e) {
                LOGGER.error(e.toString());
            }
        }
        else{
            LOGGER.error("Error occurred while getting crimes. Response code is {}", crimes.getStatusCode());
        }

    }
}
