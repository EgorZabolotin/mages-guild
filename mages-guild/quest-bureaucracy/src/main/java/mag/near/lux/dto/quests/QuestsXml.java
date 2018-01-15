package mag.near.lux.dto.quests;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="quests")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@Data
public class QuestsXml{

    @XmlElement(name="headhunter")
    private List<HeadHunterXml> headHunterXmlList;
    @XmlAttribute(name = "identifier")
    private final String identifier;

    public QuestsXml(){
        this("unknown");
    }

    public QuestsXml(String identifier){
        this.identifier = identifier;
    }

    public void addHeadHunter(HeadHunterXml headHunterXml){
        headHunterXmlList.add(headHunterXml);
    }
}
