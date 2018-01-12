package mag.near.lux.services;

import mag.near.lux.dto.ArrayOfCrimeDTO;
import mag.near.lux.dto.OffenderDTO;
import mag.near.lux.model.OffenderPerson;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OffenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OffenderService.class);

    private List<OffenderDTO> getOffendersDTO(Integer limit) {
        PropsUtil propsUtil = new PropsUtil(ResourcesNames.END_POINTS);
        String getOffendersURL = propsUtil.getPropertyByNmae(ResourcesNames.GET_OFFENDERS_URL);
        RestTemplate restTemplate = new RestTemplate();
        List<OffenderDTO> offendersDTO = null;

        ResponseEntity<OffenderDTO[]> offenders = restTemplate.getForEntity(getOffendersURL, OffenderDTO[].class, limit.toString());
        if(offenders.getStatusCode().equals(HttpStatus.OK)){
            LOGGER.debug("Getting list of offenders from outer world portal");
            offendersDTO = Arrays.stream(offenders.getBody())
                    //.peek(offender -> LOGGER.debug(offender.toString()))
                    .collect(Collectors.toList());
        }else{
            LOGGER.error("Error occurred while getting mages. Response is {}", offenders.getStatusCode());
        }
        return offendersDTO;
    }

    public List<OffenderPerson> getOffenders(Integer limit){
        CrimeService crimeService = new CrimeService();

        List<OffenderPerson> offenders = getOffendersDTO(limit).stream()
            .map(OffenderPerson::new)
            .peek(citizen -> citizen.setCrimes(crimeService.getCrimesForId(citizen.getGuid())))
            .filter(citizen -> !citizen.getCrimes().isEmpty())
            .collect(Collectors.toList());
        return offenders;
    }
}
