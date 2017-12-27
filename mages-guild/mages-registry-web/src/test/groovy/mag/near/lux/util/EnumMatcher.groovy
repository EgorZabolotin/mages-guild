package mag.near.lux.util

import org.hamcrest.CustomMatcher


import java.util.function.Function
import java.util.stream.Collectors

class EnumMatcher<T extends Enum<T>> extends CustomMatcher{

    List<String> input;

    EnumMatcher(String description, Class<T> input) {
        super(description)
        this.input = Arrays.stream(input.getEnumConstants())
                .map(new Function<Object, String>()  {
            @Override
            String apply(Object param) {
                return param.toString()
            }})
            .collect(Collectors.toList())
    }

    public static <T> EnumMatcher of(Class<T> input){
        return new EnumMatcher("matches String to Enum", input)
    }

    @Override
    boolean matches(Object o) {
        return input.contains((String) o)
    }
}
