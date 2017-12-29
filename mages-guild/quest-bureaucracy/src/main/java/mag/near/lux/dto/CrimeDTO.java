package mag.near.lux.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="crime")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class CrimeDTO {

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
