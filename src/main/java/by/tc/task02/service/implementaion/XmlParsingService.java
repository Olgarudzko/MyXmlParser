package by.tc.task02.service.implementaion;

import by.tc.task02.dao.DAOFactory;
import by.tc.task02.dao.XmlDao;
import by.tc.task02.entity.XmlLevel;
import by.tc.task02.service.ParsingService;
import by.tc.task02.service.validation.Validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class XmlParsingService implements ParsingService {

    private static final String NULL_SOURCE = "Source is null";
    private static final String CHARSET = "UTF-8";
    private static final String FILE_NOT_FOUND = "File not found";
    private static final String ERROR_READING_FILE = "Error reading file";
    private static final String SPACES_BETWEEN_TAGS = ">(\\s)+<";
    private static final String TAGS_WITHOUT_SPACES = "><";

    public XmlLevel parse(String source) {
        String xml = readXmlFromFile(getClass(), source);

        if (!Validator.isXmlValid(xml)) {
            return null;
        }

        DAOFactory factory = DAOFactory.getInstance();
        XmlDao xmlDAO = factory.getXmlDao();

        XmlLevel resultXml = xmlDAO.parseXml(xml);
        return resultXml;
    }

    public static String readXmlFromFile(Class context, String name) {

        String xml = "";
        FileInputStream catalogueReader = null;

        try {
            URL source = context.getClassLoader().getResource(name);
            if (source == null) {
                System.out.println(NULL_SOURCE);
                return null;
            }
            catalogueReader = new FileInputStream(new File(source.getFile()));
            byte[] content = new byte[catalogueReader.available()];
            catalogueReader.read(content);
            String parsed = new String(content, CHARSET);
            xml = parsed.replaceAll(SPACES_BETWEEN_TAGS, TAGS_WITHOUT_SPACES);

        } catch (FileNotFoundException e) {
            System.out.println(FILE_NOT_FOUND);
            return null;
        } catch (IOException e) {
            System.out.println(ERROR_READING_FILE);
            return null;
        }

        return xml;
    }
}
