package mag.near.lux.services;

import mag.near.lux.dto.ArrayOfCrimeDTO;
import mag.near.lux.dto.OffenderDTO;
import mag.near.lux.model.Crime;
import mag.near.lux.model.OffenderPerson;
import mag.near.lux.model.OffenderWithReward;
import mag.near.lux.model.Rank;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OffenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OffenderService.class);

    public Map<Rank, List<OffenderWithReward>> getOffendersByRank(Integer limit) {
        final PropsUtil propsUtil = new PropsUtil(ResourcesNames.END_POINTS);
        final String getOffendersURL = propsUtil.getPropertyByNmae(ResourcesNames.GET_OFFENDERS_URL);
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<OffenderDTO[]> offendersDto = restTemplate.getForEntity(getOffendersURL, OffenderDTO[].class, limit.toString());
        final  CrimeService crimeService = new CrimeService();

        if(offendersDto.getStatusCode().equals(HttpStatus.OK)){
            LOGGER.debug("Getting list of offenders from outer world portal");
            return Arrays.stream(offendersDto.getBody())
                .map(offenderDTO -> getOffenderWithHisCrimes(crimeService, offenderDTO))
                .filter(OffenderPerson::isCriminal)
                .map(OffenderWithReward::new)
                .collect(Collectors.groupingBy((OffenderWithReward offender) ->
                    Arrays.stream(Rank.values())
                        .filter(rank -> rank.getValue() >= offender.getOffender().getAge())
                        .min(Comparator.comparing(Rank::getValue))
                        .orElse(Rank.UNDEFIND)
                ));
        }else{
            LOGGER.error("Error occurred while getting mages. Response is {}", offendersDto.getStatusCode());
        }
        return null;
    }

    public OffenderPerson getOffenderWithHisCrimes(CrimeService crimeService, OffenderDTO offenderDTO) {
        List<Crime> crimes = crimeService.getCrimesForId(offenderDTO.getGuid());
        return new OffenderPerson(offenderDTO, crimes);
    }

 /*   public List<OffenderPerson> getOffendersByRank(Integer limit){
        CrimeService crimeService = new CrimeService();

        List<OffenderPerson> offenders = getOffendersDTO(limit).stream()
            .map(OffenderPerson::new)
            .peek(citizen -> citizen.setCrimes(crimeService.getCrimesForId(citizen.getGuid())))
            .filter(citizen -> !citizen.getCrimes().isEmpty())
            .collect(Collectors.toList());
        return offenders;
    }*/
}
