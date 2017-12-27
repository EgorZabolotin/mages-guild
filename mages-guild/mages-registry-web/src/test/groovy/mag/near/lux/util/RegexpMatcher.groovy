package mag.near.lux.util

import org.hamcrest.CustomMatcher

import java.util.regex.Pattern

class RegexpMatcher extends  CustomMatcher<String>{

    Pattern pattern
    RegexpMatcher(String description, Pattern pattern) {
        super(description)
        this.pattern = pattern
    }

    public static RegexpMatcher of(Pattern pattern){
        return  new RegexpMatcher("mach to regexp", pattern)
    }

    @Override
    boolean matches(Object o) {
        String input = (String) o
        return pattern.matcher(input).matches()
    }
}
