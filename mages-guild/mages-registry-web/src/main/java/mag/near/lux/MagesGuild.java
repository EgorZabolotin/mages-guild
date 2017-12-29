package mag.near.lux;

import mag.near.lux.model.Person;
import mag.near.lux.provider.PersonDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class MagesGuild {

    @Autowired
    PersonDataProvider personDataProvider;

    private static final Logger LOGGER = LoggerFactory.getLogger(MagesGuild.class);

    @RequestMapping(value = "/mages/{limit}", method = RequestMethod.GET, produces = "application/json;charset=UTF8")
    @ResponseBody
    List<Person> getListOfMages(@PathVariable("limit") String limit){
        LOGGER.debug("User requested mages list with limit: {}", limit);
        return Stream.generate(personDataProvider::createPerson)
                .limit(Integer.parseInt(limit))
                .collect(Collectors.toList());
    }
}
