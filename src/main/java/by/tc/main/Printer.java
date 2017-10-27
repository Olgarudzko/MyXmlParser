package by.tc.main;

import by.tc.entity.XmlLevel;

public class Printer {

    public static void print (XmlLevel xml){
        if (xml==null){
            System.out.println("Null");
        }
        System.out.println(xml.getName());
        if (xml.getChildren()!=null) {
            for (XmlLevel level : xml.getChildren()) {
                print(level);
            }
        }
    }
}
