package mag.near.lux.dto;

import lombok.*;
import mag.near.lux.model.Rank;
import mag.near.lux.model.Sex;

import java.io.Serializable;
import java.util.UUID;

@Value
@AllArgsConstructor

public class PersonDTO{

    @NonNull
    private UUID guid;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private Sex sex;

    @NonNull
    private Rank rank;
}
