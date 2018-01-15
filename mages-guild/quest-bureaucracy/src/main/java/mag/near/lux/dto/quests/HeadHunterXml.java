package mag.near.lux.dto.quests;

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
public class HeadHunterXml {

    @XmlElement(name="rank")
    String rank;
    @XmlElement(name="headhunter-name")
    String headHunterName;
    @XmlElement(name="wanted")
    List<WantedXml> wantedXmlList;

    public HeadHunterXml(){
        wantedXmlList = new ArrayList<>();
    }

    public HeadHunterXml(String rank, String name){
        this.rank = rank;
        this.headHunterName = name;
        wantedXmlList = new ArrayList<>();
    }

    public void addWanted(WantedXml wantedXml){
        wantedXmlList.add(wantedXml);

    }
}
