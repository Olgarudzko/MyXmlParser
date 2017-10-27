package by.tc.task02.service.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean isXmlValid (String source){
        Pattern openTagPattern = Pattern.compile("<[^\\/?].+>");
        Pattern closeTagPattern = Pattern.compile("<[\\/].+>");
        Matcher openTag = openTagPattern.matcher(source);
        Matcher closeTag = closeTagPattern.matcher(source);
        int opened = 0;
        int closed = 0;
//        List<String> tags = new ArrayList<String>();
        while (openTag.find()){
//            String found = openTag.group();
//            String tag = found.substring(1, found.length()-2);
//            tags.add(tag);
            opened++;
        }
//        Collections.reverse(tags);
        while (closeTag.find()){
//            String found = closeTag.group();
//            String tag = found.substring(2, found.length()-2);
//            if (!tags.get(closed).equals(tag)){
//                return false;
//            }
            closed++;
        }
        return opened==closed;
    }
}
