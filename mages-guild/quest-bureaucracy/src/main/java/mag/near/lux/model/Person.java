package mag.near.lux.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.util.UUID;

@Data

@AllArgsConstructor
public class Person {

    private UUID guid;
    private String name;
    private String surname;
    private Sex sex;
}
