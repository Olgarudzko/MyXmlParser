package by.tc.main;

import by.tc.entity.EntireXml;
import by.tc.entity.XmlBean;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Printer {

    public static final String RESERVED_FOR_EVEN_LEVEL_TAG = "%evenreserved%";
    public static final String RESERVED_FOR_ODD_LEVEL_TAG = "%oddsreserved%";
    public static final String BEAN_START_FORMATTING = "\n---";
    public static final String BEAN_END_FORMATTING = "---\n";
    public static final String FIRST_LINE_FORMATTING = "______";
    public static final String PADDING_START = "   ";

    private static StringBuilder readyXml;
    private static Pattern evenPattern = Pattern.compile(RESERVED_FOR_EVEN_LEVEL_TAG);
    private static Pattern oddPattern = Pattern.compile(RESERVED_FOR_ODD_LEVEL_TAG);
    private static Matcher matcher;

    public static void printXml(EntireXml xml) {

        readyXml = new StringBuilder();
        readyXml.append(RESERVED_FOR_EVEN_LEVEL_TAG);
        int levelNumber = 0;
        for (Map.Entry<String, Integer> field : xml.getXmlStructure().entrySet()) {

            StringBuilder copyXml = new StringBuilder(readyXml);
            matcher = (levelNumber % 2 == 0) ? evenPattern.matcher(copyXml) : oddPattern.matcher(copyXml);

            while (matcher.find()) {
                fillReservedWithTags(levelNumber, field);
            }

            levelNumber++;
        }
        insertBeansIntoStructure(xml);
        System.out.println(readyXml.toString());
    }


    private static void fillReservedWithTags(int levelNumber, Map.Entry<String, Integer> field) {

        Matcher reservedPlace = (levelNumber % 2 == 0) ?
                evenPattern.matcher(readyXml) : oddPattern.matcher(readyXml);
        reservedPlace.find();

        StringBuilder leftPadding = new StringBuilder();
        for (int i = 1; i < levelNumber; i++) {
            leftPadding.append(PADDING_START);
        }

        StringBuilder replacer = (levelNumber != 0) ?
                new StringBuilder(leftPadding.toString() + "[" + field.getKey().toUpperCase() + "]\n")
                : new StringBuilder(FIRST_LINE_FORMATTING
                .concat(field.getKey().toUpperCase()).concat(FIRST_LINE_FORMATTING).concat("\n\n"));

        for (int i = 0; i < field.getValue(); i++) {
            replacer.append((levelNumber % 2 == 0) ? RESERVED_FOR_ODD_LEVEL_TAG : RESERVED_FOR_EVEN_LEVEL_TAG);
        }
        replacer.append('\n');

        readyXml.replace(reservedPlace.start(), reservedPlace.end(), replacer.toString());
    }


    private static void insertBeansIntoStructure(EntireXml xml) {

        StringBuilder copyXml = new StringBuilder(readyXml);
        matcher = (xml.getXmlStructure().size() % 2 == 0) ?
                evenPattern.matcher(copyXml) : oddPattern.matcher(copyXml);

        int beanNumber = 0;
        while (matcher.find()) {
            Matcher newmatcher = (xml.getXmlStructure().size() % 2 == 0) ?
                    evenPattern.matcher(readyXml) : oddPattern.matcher(readyXml);
            newmatcher.find();
            readyXml.replace(newmatcher.start(), newmatcher.end(),
                    formatBean(xml.getXmlBeans().get(beanNumber)));
            beanNumber++;
        }

    }


    private static String formatBean(XmlBean bean) {

        StringBuilder beanString = new StringBuilder();
        beanString.append(BEAN_START_FORMATTING).
                append(bean.getBeanName().toUpperCase().concat(BEAN_END_FORMATTING));
        for (Map.Entry<String, String> field : bean.getBeanFields().entrySet()) {
            beanString.append(field.getKey().concat(": ")
                    .concat(field.getValue()).concat("\n"));
        }

        return beanString.toString();
    }
}
