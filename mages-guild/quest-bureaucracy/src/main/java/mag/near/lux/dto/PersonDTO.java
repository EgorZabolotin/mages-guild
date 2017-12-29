package mag.near.lux.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO implements Serializable {

    @NonNull
    private UUID guid;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private String sex;

    @NonNull
    private String rank;
}
