package mag.near.lux.services

import mag.near.lux.BaseSpec
import mag.near.lux.dto.PersonDTO
import mag.near.lux.model.MagePerson
import spock.lang.Shared

import java.util.regex.Pattern

class MagesServicespec extends  BaseSpec{

    @Shared
    MagesService magesService = new  MagesService()

    def"getMessagesDTO should return a list of MagesDTO"(){

        when:
        List<PersonDTO> magesList = magesService.getMagesDTO(5)
        then:
        magesList.each {mage ->
            mage.getClass() == PersonDTO.getClass()
            mage.guid ==~ expectedGuid
            mage.name ==~ expectedName
            mage.surname ==~ expectedName
        }

    }

    def"getMages should return a list of Mages"(){
        when:
        List<MagePerson> magesList= magesService.getMages(100)
        then:
        magesList.each {mage ->
            mage.getClass() == MagePerson.getClass()
            mage.guid ==~ expectedGuid
            mage.name ==~ expectedName
            mage.surname ==~ expectedName
        }

    }
}
