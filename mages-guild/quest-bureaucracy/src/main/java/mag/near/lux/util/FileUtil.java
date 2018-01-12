package mag.near.lux.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtil {
    public static void writeQuestsToFile(List<String> body){
        PropsUtil propsUtil = new PropsUtil(ResourcesNames.OUTPUT);
        final String fileName = propsUtil.getPropertyByNmae("output.text.file.name");
        Path file = Paths.get(fileName);
        try {
            Files.write(file, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
