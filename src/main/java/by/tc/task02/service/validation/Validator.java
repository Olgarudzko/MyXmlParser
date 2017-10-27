package by.tc.task02.service.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String TAG_OPENED = "<[^\\/?][^>]+>";
    private static final String TAG_CLOSED = "<[\\/][^>]+>";
    private static final int TAG_NAME = 0;

    public static boolean isXmlValid(String source) {

        Pattern openTagPattern = Pattern.compile(TAG_OPENED);
        Pattern closeTagPattern = Pattern.compile(TAG_CLOSED);
        Matcher openTag = openTagPattern.matcher(source);
        Matcher closeTag = closeTagPattern.matcher(source);

        List<String> openTags = new ArrayList<String>();
        while (openTag.find()) {
            String found = openTag.group();
            String tag = found.substring(1, found.length() - 1).trim().split("\\s")[TAG_NAME].intern();
            openTags.add(tag);
        }

        int closed = 0;
        while (closeTag.find()) {
            String found = closeTag.group();
            String tag = found.substring(2, found.length() - 1).trim();
            if (!openTags.contains(tag)) {
                return false;
            }
            closed++;
        }

        return openTags.size() == closed;
    }
}
