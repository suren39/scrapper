package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Full class description here.
 * Created by Grinch on 4/11/2015.
 * util
 */
public class PatternMatcher {

    public static List<String> match(List<String> elements, String regex) {
        final List<String> matches = new ArrayList<String>();
        final Pattern pattern = Pattern.compile(regex);
        for (String string : elements) {
            final Matcher matcher = pattern.matcher(string);
            if (matcher.find()) {
                matches.add(string);
            }
        }
        return matches;
    }
}
