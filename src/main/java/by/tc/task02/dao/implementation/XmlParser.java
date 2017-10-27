package by.tc.task02.dao.implementation;

import by.tc.task02.dao.XmlDao;
import by.tc.task02.entity.XmlLevel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser implements XmlDao {

    private Pattern startOfTagPattern = Pattern.compile("<");
    private Matcher startOfTag;
    private Pattern endOfTagPattern = Pattern.compile(">");
    private Matcher endOfTag;

    public XmlLevel parseXml(String source){

        startOfTag = startOfTagPattern.matcher(source.trim());
        endOfTag = endOfTagPattern.matcher(source);

        XmlLevel topLevel = null;
        XmlLevel previousLevel=null;

        while (startOfTag.find()){

            skipFirstLine(source);

            if (source.charAt(startOfTag.start() + 1) != '/') {
                endOfTag.find();
                XmlLevel level = new XmlLevel();
                level.setName(source.substring(startOfTag.start() + 1, endOfTag.start()).trim());
                if (previousLevel != null) {
                    level.setParent(previousLevel);
                } else{
                    topLevel = level;
                }
                previousLevel = level;
            } else {
                if (source.charAt(endOfTag.start() + 1) == '<'){
                    previousLevel=previousLevel.getParent();
                    endOfTag.find();
                } else {
                    XmlLevel level = new XmlLevel();
                    level.setName(source.substring(endOfTag.start()+1, startOfTag.start()).trim());
                    level.setParent(previousLevel);
                    previousLevel=previousLevel.getParent();
                    endOfTag.find();
                }
            }
        }


        return topLevel;
    }

    private void skipFirstLine(String source) {
        if (source.charAt(startOfTag.start() + 1) == '?') {
            endOfTag.find();
            startOfTag.find();
        }
    }
}
