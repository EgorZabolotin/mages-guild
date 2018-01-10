package mag.near.lux.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class OffenderDTO {

    private UUID uuid;
    private String name;
    private String surname;
    private String suffix;
    private String sex;
    private int age;
}
