package by.tc.task02.main;

import by.tc.task02.entity.XmlLevel;

public class Printer {

    private static final String XML_IS_NULL = "XML is not valid";
    private static final String XML_LEVEL_FORMATTER = "_______________________________________________________________";

    public static void print(XmlLevel xml) {
        if (xml != null) {

            printXmlName(xml);

            for (XmlLevel level : xml.getChildren()) {
                printOtherLevels(level, 1);
            }
            System.out.println("\n");

        } else {
            System.out.println(XML_IS_NULL);
        }
    }

    private static void printOtherLevels(XmlLevel xml, int levelNumber) {

        char[] padding = new char[levelNumber*5];
        for (int i = 0; i<padding.length; i++){
            padding[i]=' ';
        }

        if (xml.getChildren() != null) {
            System.out.print(padding);
            System.out.println(xml.getName().toUpperCase());
            if (xml.getChildren().size() > 1) {
                System.out.println(XML_LEVEL_FORMATTER);
            }

            for (XmlLevel level : xml.getChildren()) {
                printOtherLevels(level, levelNumber+1);
            }

            if (xml.getChildren().size() > 1) {
                System.out.println(XML_LEVEL_FORMATTER);
            }
        } else {
            System.out.print(padding);
            System.out.println(" --> " + xml.getName());
        }
    }

    private static void printXmlName(XmlLevel xml){
        char[] stars = new char[xml.getName().length() + 6];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = '*';
        }
        System.out.println(stars);
        System.out.println("*  " + xml.getName().toUpperCase() + "  *");
        System.out.println(stars);
    }
}
