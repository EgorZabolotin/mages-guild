package mag.near.lux.dto.quests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="crime")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CrimeXml {

    @XmlElement(name="type")
    private String type;

    @XmlElement(name="location")
    private String location;

    @XmlElement(name="timestamp")
    private String timestamp;

    @XmlElement(name="article")
    private String article;

    @XmlElement(name="punishment")
    private String punishment;

}
