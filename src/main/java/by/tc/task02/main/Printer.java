package by.tc.task02.main;

import by.tc.task02.entity.XmlLevel;

public class Printer {

    public static final String XML_IS_NULL = "XML is not valid";
    public static final String XML_LEVEL_FORMATTER = "______________________________________________";

    public static void print(XmlLevel xml) {
        if (xml != null) {
            char[] stars = new char[xml.getName().length() + 6];
            for (int i = 0; i < stars.length; i++) {
                stars[i] = '*';
            }
            System.out.println(stars);
            System.out.println("*  " + xml.getName().toUpperCase() + "  *");
            System.out.println(stars);

            for (XmlLevel level : xml.getChildren()) {
                printOtherLevels(level, 1);
            }
            System.out.println("\n");
        } else {
            System.out.println(XML_IS_NULL);
        }
    }

    private static void printOtherLevels(XmlLevel xml, int levelNumber) {
        if (xml.getChildren() != null) {
            System.out.println(xml.getName().toUpperCase());
            if (xml.getChildren().size() > 1) {
                System.out.println(XML_LEVEL_FORMATTER);
            }

            for (XmlLevel level : xml.getChildren()) {
                printOtherLevels(level, levelNumber);
            }

            if (xml.getChildren().size() > 1) {
                System.out.println(XML_LEVEL_FORMATTER);
                System.out.println();
            }
        } else {
            System.out.println(" --> " + xml.getName());
        }
    }

}
