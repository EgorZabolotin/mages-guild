package mag.near.lux.services

import mag.near.lux.BaseSpec
import mag.near.lux.dto.OffenderDTO
import mag.near.lux.model.OffenderPerson
import spock.lang.Shared

class OffendersServiceSpec extends BaseSpec{

    @Shared
    OffenderService offenderService = new OffenderService()

    def "getOffenders method should return a list of OffenderPerson"() {
        when:
        List<OffenderPerson> offenders = offenderService.getOffenders(100)
        then:
        offenders.each { offender ->
            assert offender.getClass() == OffenderPerson.class
            assert offender.getGuid() ==~ expectedGuid
            assert offender.getName() ==~ expectedName
            assert offender.getSurname() ==~ expectedName
        }
    }
}