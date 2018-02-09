package mag.near.lux.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import mag.near.lux.dto.OffenderDTO;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OffenderPerson extends Person {

    private String suffix;
    private int age;
    private List<Crime> crimes;

    public OffenderPerson(UUID guid, String name, String surname, Sex sex, String suffix, int age) {
        super(guid, name, surname, sex);
        this.suffix = suffix;
        this.age = age;
    }

    public OffenderPerson(OffenderDTO offenderDTO, List<Crime> crimes){
        super(offenderDTO.getGuid(), offenderDTO.getName(), offenderDTO.getSurname(), offenderDTO.getSex());
        this.suffix = offenderDTO.getSuffix();
        this.age = offenderDTO.getAge();
        this.crimes = crimes;
    }

    public boolean isCriminal(){
        return !this.crimes.isEmpty();
    }
}
