package mag.near.lux.provider;

import java.io.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class DataProvider {

    protected List<String> parseFile(final String fileName){
        try (final BufferedReader reader = openFile(fileName)) {

            return reader.lines().collect(Collectors.toList());
        } catch (final IOException ex){
            throw new UncheckedIOException(String.format("Unable to read file %s", fileName), ex);
        }
    }

    private BufferedReader openFile(final String fileName){
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private ThreadLocalRandom random(){
        return ThreadLocalRandom.current();
    }

    protected <T> T randomElement(final List<T> list){
        return list.get(random().nextInt(list.size()));
    }

    protected <T extends Enum<T>> T randomEnum(final Class<T> enumClass){
        if(enumClass.isEnum()){
            final T[] enumConstants = enumClass.getEnumConstants();
            return enumConstants[random().nextInt(enumConstants.length)];
        } else {
            throw new IllegalArgumentException("Argument should be enum-class");
        }
    }
}
