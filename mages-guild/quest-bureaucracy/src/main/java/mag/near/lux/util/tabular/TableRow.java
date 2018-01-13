package mag.near.lux.util.tabular;

import lombok.Value;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@Value
public class TableRow {

    private final boolean header;
    private final List<CellData> cells = new ArrayList<>();

    public TableRow(boolean header){
        this.header = header;
    }

    @XmlAttribute
    public boolean isHeader(){
        return header;
    }

    @XmlElement(name = "cell")
    public List<CellData> getCells() {
        return cells;
    }

    public void addCell(CellData cell) {
        this.cells.add(cell);
    }
}
