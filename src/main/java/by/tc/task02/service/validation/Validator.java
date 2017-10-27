package by.tc.task02.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String TAG_OPENED = "<[^\\/?][^>]+>";
    private static final String TAG_CLOSED = "<[\\/][^>]+>";
    private static final int TAG_NAME = 0;
    private static final String ONE_LEVEL_NOT_ENOUGH = "XML should consist of at least two levels";
    private static final String TAG_NOT_FOUND = "Matching opening tag not found: ";
    private static final String WRONG_MAIN_TAG = "Wrong use of the main tag";
    private static final String FORBIDDEN_SEQUENCE = "Forbidden sequence: ";

    public static boolean isXmlValid(String source) {

        Pattern openTagPattern = Pattern.compile(TAG_OPENED);
        Pattern closeTagPattern = Pattern.compile(TAG_CLOSED);
        Matcher openTag = openTagPattern.matcher(source);
        Matcher closeTag = closeTagPattern.matcher(source);

        List<String> tags = new ArrayList<String>();
        while (openTag.find()) {
            String found = openTag.group();
            String tag = found.substring(1, found.length() - 1).trim()
                    .split("\\s")[TAG_NAME].intern();
            tags.add(tag);
        }

        if (!(tags.size()>1)){
            System.out.println(ONE_LEVEL_NOT_ENOUGH);
            return false;
        }

        int closed = 0;
        String lastCloseTag=null;
        while (closeTag.find()) {
            String found = closeTag.group();
            lastCloseTag = found.substring(2, found.length() - 1).trim();
            if (!tags.contains(lastCloseTag)) {
                System.out.println(TAG_NOT_FOUND + found);
                return false;
            }
            closed++;
        }

       if(!isRootTagValid(tags, lastCloseTag)){
            System.out.println(WRONG_MAIN_TAG);
            return false;
        };

        return !hasEmptyTags(source) && tags.size() == closed;
    }

    private static boolean isRootTagValid(List<String> tags, String rootTag){

        if (!tags.get(0).equals(rootTag)){
            return false;
        }

        int mainTagOccurence = 0;
        for (String tag: tags){
            if (tag.equals(rootTag)){
                mainTagOccurence++;
            }
        }

        return mainTagOccurence==1;
    }


    private static boolean hasEmptyTags(String source){

        Pattern openCloseTagPattern = Pattern.compile(TAG_OPENED+TAG_CLOSED);
        Matcher openCloseTag = openCloseTagPattern.matcher(source);

        if (openCloseTag.find()){
            String found = openCloseTag.group().trim();
            System.out.println(FORBIDDEN_SEQUENCE + found);
            return true;
        }

        return false;
    }
}
