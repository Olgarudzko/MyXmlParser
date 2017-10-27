package by.tc.task02.main;

import by.tc.task02.entity.XmlLevel;
import by.tc.task02.service.ParsingFactory;
import by.tc.task02.service.ParsingService;

public class Runner {
    public static void main(String[] args) {

        ParsingFactory factory = ParsingFactory.getInstance();
        ParsingService service = factory.getParsingService();

        XmlLevel example1 = service.parse("task02.xml");
        Printer.print(example1);

        XmlLevel example2 = service.parse("task02-2.xml");
        Printer.print(example2);
    }
}
