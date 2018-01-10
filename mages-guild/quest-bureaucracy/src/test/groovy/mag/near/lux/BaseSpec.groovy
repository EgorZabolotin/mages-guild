package mag.near.lux

import org.slf4j.LoggerFactory
import spock.lang.Shared
import spock.lang.Specification

import java.util.regex.Pattern

class BaseSpec extends Specification{

    @Shared
    private static final LOGGER = LoggerFactory.getLogger(getClass().class)

    @Shared
    Pattern expectedGuid = ~/[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}/

    @Shared
    Pattern expectedName = ~/^[А-Я]?[а-я](-|\s)?[А-Я]?[а-я]*$/
}
