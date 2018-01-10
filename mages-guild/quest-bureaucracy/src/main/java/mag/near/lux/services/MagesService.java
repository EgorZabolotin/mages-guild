package mag.near.lux.services;

import mag.near.lux.dto.PersonDTO;
import mag.near.lux.model.MagePerson;
import mag.near.lux.util.PropsUtil;
import mag.near.lux.util.ResourcesNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MagesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MagesService.class);

    public List<PersonDTO> getMagesDTO(Integer limit){
        RestTemplate restTemplate = new RestTemplate();
        PersonDTO[]  magesArray;
        List<PersonDTO> magesDTO = null;
        PropsUtil propsUtil = new PropsUtil(ResourcesNames.END_POINTS);
        String getMagesUrl = propsUtil.getPropertyByNmae(ResourcesNames.GET_MAGES_URL);

        ResponseEntity<PersonDTO[]> mages = restTemplate.getForEntity(getMagesUrl, PersonDTO[].class, limit.toString());

        if(mages.getStatusCode().equals(HttpStatus.OK)){
            LOGGER.debug("Getting list of mages from mages registry");
            magesArray = mages.getBody();
            magesDTO = Stream.of(magesArray)
                    .peek(person -> LOGGER.debug(person.toString()))
                    .collect(Collectors.toList());


        }else{
            LOGGER.error("Error occurred while getting mages. Response is {}", mages.getStatusCode());
        }
        return magesDTO;
    }

    public List<MagePerson> getMages(Integer limit){
         List<PersonDTO> magesDTO =  getMagesDTO(limit);
         List<MagePerson> magePersons = magesDTO.stream()
                 .map(MagePerson::new)
                 .collect(Collectors.toList());
         return magePersons;
    }

}
