package by.tc.task02.main;

import by.tc.task02.entity.XmlLevel;

public class Printer {

    private static final String XML_IS_NULL = "XML is not valid";
    public static final int FRAME_PADDING = 6;
    public static final int FIRST_LEVEL_PADDING = 5;

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

    private static void printXmlName(XmlLevel xml){
        char[] stars = new char[xml.getName().length() + FRAME_PADDING];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = '*';
        }
        System.out.println(stars);
        System.out.println("*  " + xml.getName().toUpperCase() + "  *");
        System.out.println(stars);
    }

    private static void printOtherLevels(XmlLevel xml, int levelNumber) {

        System.out.print(definePaddingForLevel(levelNumber));

        if (xml.getChildren() != null) {

            System.out.println(xml.getName().toUpperCase());

            if (xml.getChildren().size() > 1) {
                System.out.println();
            }

            for (XmlLevel level : xml.getChildren()) {
                printOtherLevels(level, levelNumber+1);
            }

            if (xml.getChildren().size() > 1) {
                System.out.println();
            }
        } else {
            System.out.println(" --> " + xml.getName());
        }
    }

    private static char [] definePaddingForLevel(int levelNumber){
        char[] padding = new char[levelNumber* FIRST_LEVEL_PADDING];
        for (int i = 0; i<padding.length; i++){
            padding[i]=' ';
        }
        return padding;
    }
}
