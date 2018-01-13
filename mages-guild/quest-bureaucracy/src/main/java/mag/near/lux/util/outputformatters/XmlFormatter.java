package mag.near.lux.util.outputformatters;

import mag.near.lux.util.tabular.TableData;

import javax.xml.bind.JAXB;
import java.io.ByteArrayOutputStream;

public class XmlFormatter extends AbstractTabularDataFormatter {

    private static final String FILE_EXTENSION = "xml";

    public XmlFormatter(TableData data) {
        super(data);
    }

    @Override
    public String toString() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JAXB.marshal(getTable(), outputStream);
        return outputStream.toString();
    }

    @Override
    public String getFileExtension() {
        return FILE_EXTENSION;
    }
}