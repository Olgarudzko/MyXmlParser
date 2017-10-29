package by.tc.task02.dao.implementation;

import by.tc.task02.dao.XmlDao;
import by.tc.task02.entity.XmlLevel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser implements XmlDao {

    private static final int NEXT_SYMBOL = 1;
    private final Pattern startOfTagPattern = Pattern.compile("<");
    private Matcher startOfTag;
    private final Pattern endOfTagPattern = Pattern.compile(">");
    private Matcher endOfTag;

    public XmlLevel parseXml(String source) {

        startOfTag = startOfTagPattern.matcher(source.trim());
        endOfTag = endOfTagPattern.matcher(source);

        XmlLevel topLevel = null;
        XmlLevel previousLevel = null;

        while (startOfTag.find()) {

            skipFirstLine(source);

            if (source.charAt(startOfTag.start() + NEXT_SYMBOL) != '/') {

                endOfTag.find();
                XmlLevel level = new XmlLevel();
                level.setName(source.substring(startOfTag.start() + NEXT_SYMBOL, endOfTag.start()).trim());
                if (previousLevel != null) {
                    level.setParent(previousLevel);
                } else {
                    topLevel = level;
                }
                previousLevel = level;

            } else {

                if (source.charAt(endOfTag.start() + NEXT_SYMBOL) == '<') {
                    previousLevel = previousLevel.getParent();
                    endOfTag.find();

                } else {
                    //action only for values
                    defineBottomLevel(source, previousLevel);
                    previousLevel = previousLevel.getParent();
                    endOfTag.find();
                }

            }
        }


        return topLevel;
    }

    private void skipFirstLine(String source) {
        if (source.charAt(startOfTag.start() + NEXT_SYMBOL) == '?') {
            endOfTag.find();
            startOfTag.find();
        }
    }

    private void defineBottomLevel(String source, XmlLevel previousLevel){
        XmlLevel level = new XmlLevel();
        level.setName(source.substring(endOfTag.start() + NEXT_SYMBOL, startOfTag.start()).trim());
        level.setParent(previousLevel);
    }
}
