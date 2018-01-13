package mag.near.lux.util.tabular;


import lombok.Value;

import javax.xml.bind.annotation.XmlValue;


@Value
public class CellData {
    @XmlValue
    private final String text;
}
