package mag.near.lux.services;

import mag.near.lux.dto.ArrayOfCrimeDTO;
import mag.near.lux.model.Crime;
import mag.near.lux.util.PropsUtil;
import mag.near.lux.util.ResourcesNames;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CrimeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MagesService.class);

    private ArrayOfCrimeDTO getArrayOfCrimeDTO(UUID uuid) {
        PropsUtil propsUtil = new PropsUtil(ResourcesNames.END_POINTS);
        String getCrimeByIdUrl = propsUtil.getPropertyByNmae(ResourcesNames.GET_CRIME_BI_ID);
        RestTemplate restTemplate = new RestTemplate();
        ArrayOfCrimeDTO arrayOfCrimeDTO = null;

        ResponseEntity<String> crimes = restTemplate.getForEntity(getCrimeByIdUrl, String.class, uuid.toString());
        if (crimes.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.debug("Getting list of crimes from Outer World Portal for id: {}", uuid);
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(ArrayOfCrimeDTO.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                arrayOfCrimeDTO = (ArrayOfCrimeDTO) unmarshaller.unmarshal(
                        new StreamSource(new ByteArrayInputStream(crimes.getBody().getBytes(StandardCharsets.ISO_8859_1))));
                arrayOfCrimeDTO.getCrimes().stream()
                        .forEach(crime -> LOGGER.debug(crime.toString()));
            } catch (JAXBException e) {
                LOGGER.error(e.toString());
            }
        } else {
            LOGGER.error("Error occurred while getting crimes. Response code is {}", crimes.getStatusCode());
        }
        return arrayOfCrimeDTO;
    }

    public List<Crime> getCrimesForId(UUID uuid) {
        ArrayOfCrimeDTO arrayOfCrimeDTO = getArrayOfCrimeDTO(uuid);

        return arrayOfCrimeDTO.getCrimes().stream()
                .map(Crime::new)
                .collect(Collectors.toList());
    }

}
