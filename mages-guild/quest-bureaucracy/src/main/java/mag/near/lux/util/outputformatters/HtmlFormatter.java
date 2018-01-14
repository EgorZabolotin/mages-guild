package mag.near.lux.util.outputformatters;

import mag.near.lux.util.tabular.TableData;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class HtmlFormatter extends AbstractTabularDataFormatter {

    private static final String XSLT_FILE_NAME = "xmlToHtml.xsl";
    private static final String FILE_EXTENSION = "html";

    public HtmlFormatter(TableData data) {
        super(data);
    }

    @Override
    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(getTable().getClass());
            Transformer transformer = TransformerFactory.newInstance().newTransformer(findXslt());
            JAXBSource xmlInput = new JAXBSource(context, getTable());
            ByteArrayOutputStream htmlOutputStream = new ByteArrayOutputStream();
            transformer.transform(xmlInput, new StreamResult(htmlOutputStream));
            return htmlOutputStream.toString("UTF-8");
        } catch (JAXBException | TransformerException | UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Source findXslt() {
        Source xslt;
        File xslFile = new File(XSLT_FILE_NAME);
        if (xslFile.canRead()) {
            // Found an external XSL file:
            xslt = new StreamSource(xslFile);
        } else {
            // Default to an internal XSL file inside the application .jar file:
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("xmlToHtml.xsl");
            xslt = new StreamSource(inputStream);
        }
        return xslt;
    }

    @Override
    public String getFileExtension() {
        return FILE_EXTENSION;
    }
}
