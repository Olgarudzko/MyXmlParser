package by.tc.main;

import by.tc.entity.EntireXml;
import by.tc.service.ParsingFactory;
import by.tc.service.ParsingService;

public class Runner {
    public static void main(String[] args) {

        ParsingFactory factory = ParsingFactory.getInstance();
        ParsingService service = factory.getParsingService();

        EntireXml example1 = service.parse("example.xml");
        Printer.printXml(example1);

//        EntireXml example2 = service.parse("example2.xml");
//        Printer.printXml(example2);

    }
}
