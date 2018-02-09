package mag.near.lux.dto.quests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="headhunter")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeadHunterXml {

    @XmlElement(name="rank")
    private String rank;
    @XmlElement(name="headhunter-name")
    private String headHunterName;
    @XmlElement(name="wanted")
    private List<WantedXml> wantedXmlList;
}
