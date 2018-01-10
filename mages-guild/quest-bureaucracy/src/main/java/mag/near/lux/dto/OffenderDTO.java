package mag.near.lux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mag.near.lux.model.Sex;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OffenderDTO {

    private UUID guid;
    private String name;
    private String surname;
    private String suffix;
    private Sex sex;
    private int age;
}
