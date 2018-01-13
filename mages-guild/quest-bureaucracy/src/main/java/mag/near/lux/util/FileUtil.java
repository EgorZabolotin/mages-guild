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
    public static void writeQuestsToTxtFile(List<String> body){
        PropsUtil propsUtil = new PropsUtil(ResourcesNames.OUTPUT);
        final String fileName = propsUtil.getPropertyByNmae("output.text.file.name");
        Path file = Paths.get(fileName);
        try {
            Files.write(file, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeQuestsToFile(String content, String fileName){
        PropsUtil propsUtil = new PropsUtil(ResourcesNames.OUTPUT);
        final String outputDirectory = propsUtil.getPropertyByNmae("output.files.path");
        String pathToFile = outputDirectory + fileName;
        Path file = Paths.get(pathToFile);

        try {
            Files.write(file, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
