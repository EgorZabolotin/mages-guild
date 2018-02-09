package mag.near.lux.services;

import mag.near.lux.dto.PersonDTO;
import mag.near.lux.model.MagePerson;
import mag.near.lux.model.Rank;
import mag.near.lux.util.PropsUtil;
import mag.near.lux.util.ResourcesNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MagesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MagesService.class);

    public Map<Rank, List<MagePerson>> getMagesByRank(Integer limit) {
        final RestTemplate restTemplate = new RestTemplate();
        final PropsUtil propsUtil = new PropsUtil(ResourcesNames.END_POINTS);
        final String getMagesUrl = propsUtil.getPropertyByNmae(ResourcesNames.GET_MAGES_URL);

        ResponseEntity<PersonDTO[]> mages = restTemplate.getForEntity(getMagesUrl, PersonDTO[].class, limit.toString());

        if (mages.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.debug("Getting list of mages from mages registry");
            final PersonDTO[] magesArray = mages.getBody();
            return Stream.of(magesArray)
                .map(MagePerson::new)
                .collect(Collectors.groupingBy(MagePerson::getRank));
        } else {
            LOGGER.error("Error occurred while getting mages. Response is {}", mages.getStatusCode());
            return null;
        }
    }
}
