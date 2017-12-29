package mag.near.lux.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="arrayOfCrime")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ArrayOfCrimeDTO {
    @XmlElement(name="crime")
    private List<CrimeDTO> crimes;
}
