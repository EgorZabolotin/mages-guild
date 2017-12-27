package mag.near.lux.provider;

import com.sun.javafx.collections.MappingChange;
import mag.near.lux.model.Person;
import mag.near.lux.model.Rank;
import mag.near.lux.model.Sex;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class PersonDataProvider extends DataProvider{

    private final Map<Sex, List<String>> name;
    private final List<String> surname;


    public PersonDataProvider() {
        this.name = Collections.unmodifiableMap(
                Stream.of(
                        new AbstractMap.SimpleImmutableEntry<>(Sex.MALE, parseFile("MaleName.txt")),
                        new AbstractMap.SimpleImmutableEntry<>(Sex.FEMALE, parseFile("FemaleName.txt"))
                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );

        this.surname = Collections.unmodifiableList(parseFile("Surname.txt"));
    }

    public Person createPerson(){
        Sex sex = randomEnum(Sex.class);
        return createPerson(sex);
    }

    private Person createPerson(final Sex sex){
        return new Person(
                UUID.randomUUID(),
                randomElement(this.name.get(sex)),
                randomElement(this.surname),
                sex,
                randomEnum(Rank.class)
        );
    }
}
