package mag.near.lux.services

import mag.near.lux.BaseSpec
import mag.near.lux.dto.PersonDTO

import java.util.regex.Pattern

class MagesServicespec extends  BaseSpec{

    def"getMessagesDTO should return a list of mages"(){

        when:
        List<PersonDTO> magesList = new MagesService().getMagesDTO(5)
        then:
        magesList.each {mage -> mage.guid ==~ expectedGuid
            mage.name ==~ expectedName
            mage.surname ==~ expectedName
            }

    }
}
