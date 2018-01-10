package mag.near.lux.dto;

import lombok.Data;
import lombok.NonNull;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@XmlRootElement(name="arrayOfCrime")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ArrayOfCrimeDTO {

    @XmlElement(name="crime")
    private List<CrimeDTO> crimes;

    public List<CrimeDTO> getCrimes(){
        return Optional.ofNullable(crimes).orElse(new ArrayList<CrimeDTO>());
    }
}
