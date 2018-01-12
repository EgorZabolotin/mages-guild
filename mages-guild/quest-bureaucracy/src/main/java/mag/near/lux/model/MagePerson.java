package mag.near.lux.model;

import lombok.*;
import mag.near.lux.dto.PersonDTO;

import java.util.Optional;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Value
public class MagePerson extends Person {

    public MagePerson(PersonDTO personDTO){
        super(personDTO.getGuid(), personDTO.getName(), personDTO.getSurname(), personDTO.getSex());
        this.rank = personDTO.getRank();
    }

    public MagePerson(UUID guid, String name, String surname, Sex sex, Rank rank) {
        super(guid, name, surname, sex);
        this.rank = rank;
    }

    private Rank rank;
}
