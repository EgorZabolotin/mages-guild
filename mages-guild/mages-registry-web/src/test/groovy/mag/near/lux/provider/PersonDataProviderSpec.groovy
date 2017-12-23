package mag.near.lux.provider

import org.slf4j.LoggerFactory
import spock.lang.Shared
import spock.lang.Specification

class PersonDataProviderSpec extends Specification{

    @Shared
    def logger = LoggerFactory.getLogger(getClass())

    def"Call createPerson method should return filled Person"(){
        given:
        def personDataProvider = new PersonDataProvider()

        expect:
            def person = personDataProvider.createPerson()
            logger.debug("Person (${person.guid.toString()}) is ${person.name} ${person.surname}, ${person.rank}, ${person.sex}")
            Objects.nonNull(person.name)
            Objects.nonNull(person.surname)
            Objects.nonNull(person.sex)
            Objects.nonNull(person.rank)
            Objects.nonNull(person.guid)
    }
}
