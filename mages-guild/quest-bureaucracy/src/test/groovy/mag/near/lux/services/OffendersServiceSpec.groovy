package mag.near.lux.services

import mag.near.lux.BaseSpec
import mag.near.lux.model.OffenderPerson
import spock.lang.Shared

class OffendersServiceSpec extends BaseSpec{

    @Shared
    OffenderService offenderService = new OffenderService()

    def "getMageCitizens method should return a list of OffenderPerson"() {
        when:
        List<OffenderPerson> offenders = offenderService.getOffenders(100)
        then:
        offenders.each { offender ->
            assert offender.class == OffenderPerson.class
            assert offender.guid ==~ expectedGuid
            assert offender.name ==~ expectedName
            assert offender.surname ==~ expectedName
            assert !offender.crimes.empty
        }
    }
}