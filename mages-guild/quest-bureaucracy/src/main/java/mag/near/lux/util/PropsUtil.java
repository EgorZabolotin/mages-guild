package mag.near.lux.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropsUtil {

    private final String resourceName;

    public PropsUtil(String resourceName) {
        this.resourceName = resourceName;
    }

    public Properties getProps(String resourceName) {
        final Properties props = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            props.load(is);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public String getPropertyByNmae(String name){
        Properties props = getProps(resourceName);
        return props.getProperty(name);
    }
}
