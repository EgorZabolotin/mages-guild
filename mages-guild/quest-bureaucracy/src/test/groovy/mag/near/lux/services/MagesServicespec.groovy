package mag.near.lux.services

import mag.near.lux.BaseSpec
import mag.near.lux.dto.PersonDTO
import mag.near.lux.model.MagePerson
import spock.lang.Shared

import java.util.regex.Pattern

class MagesServicespec extends  BaseSpec{

    @Shared
    MagesService magesService = new  MagesService()

    def"getMages should return a list of Mages"(){
        when:
        List<MagePerson> magesList= magesService.getMages(100)
        then:
        magesList.each {mage ->
            assert mage.getClass() == MagePerson.class
            assert mage.guid ==~ expectedGuid
            assert mage.name ==~ expectedName
            assert mage.surname ==~ expectedName
        }

    }
}
