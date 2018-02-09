package mag.near.lux.services

import mag.near.lux.BaseSpec
import mag.near.lux.model.Crime
import mag.near.lux.model.OffenderPerson
import org.slf4j.LoggerFactory
import spock.lang.Shared

class CrimeServiceSpec extends BaseSpec{

    private static final LOGGER = LoggerFactory.getLogger(CrimeServiceSpec.class)
    @Shared
    CrimeService crimeService = new CrimeService()
    @Shared
    OffenderService offenderService = new OffenderService()

    def"getCrimesForId method should return a list of Crimes"(){
        expect:
        List<OffenderPerson> offenders = offenderService.getOffendersByRank(100)
        offenders.each {offender ->
            List<Crime> crimes = crimeService.getCrimesForId(offender.getGuid())
            crimes.each {crime ->
                LOGGER.debug("From test: ${crime.toString()}")
            }
        }
    }


}
