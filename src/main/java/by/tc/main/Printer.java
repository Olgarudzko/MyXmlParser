package by.tc.main;

import by.tc.entity.EntireXml;
import by.tc.entity.XmlBean;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Printer {

    public static final String SPACE_FOR_EVEN_LEVEL = "%evenreserved%";
    public static final String SPACE_FOR_ODD_LEVEL = "%oddsreserved%";

    public static void printData(EntireXml xml) {

        StringBuilder readyXml = new StringBuilder();
        readyXml.append(SPACE_FOR_EVEN_LEVEL);
        Pattern evenPattern = Pattern.compile(SPACE_FOR_EVEN_LEVEL);
        Pattern oddPattern = Pattern.compile(SPACE_FOR_ODD_LEVEL);
        Matcher matcher;
        int levelNumber=0;
        for (Map.Entry<String, Integer> field : xml.getXmlStructure().entrySet()) {
            StringBuilder copyXml = new StringBuilder(readyXml);
            matcher = (levelNumber % 2 == 0 ) ? evenPattern.matcher(copyXml) : oddPattern.matcher(copyXml);

            while (matcher.find()) {
                Matcher newmatcher = (levelNumber % 2 == 0 ) ? evenPattern.matcher(readyXml) : oddPattern.matcher(readyXml);
                newmatcher.find();
                StringBuilder leftPadding = new StringBuilder();
                for (int i=1; i<levelNumber; i++){
                    leftPadding.append("   ");
                }
                StringBuilder replacer = (levelNumber != 0) ?
                        new StringBuilder(leftPadding.toString()+"["+field.getKey().toUpperCase() + "]\n")
                        : new StringBuilder("______"+field.getKey().toUpperCase() + "______\n\n");

                for (int i = 0; i < field.getValue(); i++) {
                    replacer.append((levelNumber%2==0) ? SPACE_FOR_ODD_LEVEL : SPACE_FOR_EVEN_LEVEL);
                }
                replacer.append("\n");

                readyXml.replace(newmatcher.start(), newmatcher.end(), replacer.toString());
            }
            levelNumber++;
        }
        StringBuilder copyXml = new StringBuilder(readyXml);
        matcher = (xml.getXmlStructure().size() % 2 == 0) ? evenPattern.matcher(copyXml) : oddPattern.matcher(copyXml);
        int beanNumber=0;
        while (matcher.find()) {
            Matcher newmatcher = (xml.getXmlStructure().size() % 2 == 0) ? evenPattern.matcher(readyXml) : oddPattern.matcher(readyXml);
            newmatcher.find();
            readyXml.replace(newmatcher.start(), newmatcher.end(), stringBean(xml.getXmlBeans().get(beanNumber)));
            beanNumber++;
        }
        System.out.println(readyXml.toString());
    }


    private static String stringBean(XmlBean bean) {
        StringBuilder beanString = new StringBuilder();
        beanString.append("\n---").append(bean.getBeanName().toUpperCase().concat("---\n"));
        for (Map.Entry<String, String> field : bean.getBeanFields().entrySet()) {
            beanString.append(field.getKey().concat(": ")
                    .concat(field.getValue()).concat("\n"));
        }
        return beanString.toString();
    }
}
