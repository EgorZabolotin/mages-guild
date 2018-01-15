package mag.near.lux.dto.quests;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="wanted")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Data
public class WantedXml {
    @XmlElement(name="wanted-name")
    String name;

    @XmlElement(name="wanted-age")
    String age;

    @XmlElement(name="min-reward")
    String minReward;

    @XmlElement(name="max-reward")
    String maxReward;

    @XmlElement(name="avg-reward")
    String avgReward;

    @XmlElement(name="crime")
    List<CrimeXml> crimes;

    public WantedXml(String name, String age, String minReward, String maxReward, String avgReward) {
        this.name = name;
        this.age = age;
        this.minReward = minReward;
        this.maxReward = maxReward;
        this.avgReward = avgReward;
        this.crimes = new ArrayList<>();
    }


    public void addCrime(CrimeXml crimeXml){
        crimes.add(crimeXml);
    }
}
