package mag.near.lux.services;

import mag.near.lux.dto.ArrayOfCrimeDTO;
import mag.near.lux.dto.CrimeTypeDTO;
import mag.near.lux.model.Crime;
import mag.near.lux.model.CrimeType;
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
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CrimeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrimeService.class);
    private final PropsUtil propsUtil = new PropsUtil(ResourcesNames.END_POINTS);

    public List<Crime> getCrimesForId(UUID uuid) {
        final Map<String, CrimeType> crimeTypesByCrimeTypeName = getCrimeTypesByCrimeTypeName();

        return getArrayOfCrimeDTO(uuid).getCrimes().stream()
            .map(crimeDTO -> {
                CrimeType crimeType = new CrimeType(crimeDTO.getType()
                    , (crimeTypesByCrimeTypeName.get(crimeDTO.getType())).getMin()
                    , (crimeTypesByCrimeTypeName.get(crimeDTO.getType())).getMax());
                return new Crime(crimeDTO, crimeType);
            })
            .collect(Collectors.toList());
    }

    private Map<String, CrimeType> getCrimeTypesByCrimeTypeName() {
        final String getCrimeTypesUrl = propsUtil.getPropertyByNmae(ResourcesNames.GET_CRIME_TYPES);
        final ResponseEntity<CrimeTypeDTO[]> crimeTypesResponse
            = new RestTemplate().getForEntity(getCrimeTypesUrl, CrimeTypeDTO[].class);

        if (crimeTypesResponse.getStatusCode().equals(HttpStatus.OK)) {
            return Arrays.stream(crimeTypesResponse.getBody())
                .map(CrimeType::new)
                .collect(Collectors.toMap(CrimeType::getTypeName, crimeType -> crimeType));
        } else {
            LOGGER.error("Error occurred while getting crime types. Response is {}", crimeTypesResponse.getStatusCode());
        }
        return null;
    }

    private ArrayOfCrimeDTO getArrayOfCrimeDTO(UUID uuid) {
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
                /*arrayOfCrimeDTO.getCrimes().stream()
                        .forEach(crime -> LOGGER.debug(crime.toString()));*/
            } catch (JAXBException e) {
                LOGGER.error(e.toString());
            }
        } else {
            LOGGER.error("Error occurred while getting crimes. Response code is {}", crimes.getStatusCode());
        }
        return arrayOfCrimeDTO;
    }

}
