package mag.near.lux.model;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
@AllArgsConstructor
public class Person implements Serializable {

    @NonNull
    private final UUID guid;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private Sex sex;

    @NonNull
    private Rank rank;
}
