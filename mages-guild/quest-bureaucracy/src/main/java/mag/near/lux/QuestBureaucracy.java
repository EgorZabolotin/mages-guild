package mag.near.lux;

import mag.near.lux.dto.ArrayOfCrimeDTO;
import mag.near.lux.dto.PersonDTO;
import mag.near.lux.model.MagePerson;
import mag.near.lux.model.OffenderPerson;
import mag.near.lux.services.MagesService;
import mag.near.lux.services.OffenderService;
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
import java.util.stream.Stream;

public class QuestBureaucracy {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestBureaucracy.class);

    public static void main(String[] args) {
        MagesService magesService = new MagesService();
        OffenderService offenderService = new OffenderService();

        List<MagePerson> mages = magesService.getMages(100);
        List<OffenderPerson> offenders = offenderService.getOffenders(100000);

    }
}

