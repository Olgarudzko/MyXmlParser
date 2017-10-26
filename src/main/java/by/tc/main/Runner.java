package by.tc.main;

import by.tc.dao.implementation.XmlParser;

public class Runner {
    public static void main(String[] args) {

        XmlParser parser = new XmlParser();

        Printer.printData(parser.parseXml(""));

    }
}
