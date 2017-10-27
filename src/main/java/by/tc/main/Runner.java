package by.tc.main;

import by.tc.entity.XmlLevel;
import by.tc.service.ParsingFactory;
import by.tc.service.ParsingService;

public class Runner {
    public static void main(String[] args) {

        ParsingFactory factory = ParsingFactory.getInstance();
        ParsingService service = factory.getParsingService();

        XmlLevel example1 = service.parse("example.xml");
        Printer.print(example1);


        XmlLevel example2 = service.parse("example2.xml");
        Printer.print(example2);
    }
}
