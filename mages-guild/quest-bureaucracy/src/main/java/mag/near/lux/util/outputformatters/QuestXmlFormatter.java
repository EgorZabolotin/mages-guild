package mag.near.lux.util.outputformatters;

import mag.near.lux.dto.quests.QuestsXml;

import javax.xml.bind.JAXB;
import java.io.ByteArrayOutputStream;

public class QuestXmlFormatter extends AbstractQuestsDataFormatter{


    private static final String FILE_EXTENSION = "xml";


    public QuestXmlFormatter(QuestsXml quests) {super(quests);}

    @Override
    public String toString() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JAXB.marshal(getQuestsXml(), outputStream);
        return outputStream.toString();
    }

    @Override
    public String getFileExtension() {
        return FILE_EXTENSION;
    }
}
