package by.tc.service.implementaion;

import by.tc.dao.DAOFactory;
import by.tc.dao.XmlDao;
import by.tc.entity.XmlLevel;
import by.tc.service.ParsingService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class XmlParsingService implements ParsingService {

    public static final String NULL_SOURCE = "Source is null";
    public static final String CHARSET = "UTF-8";
    public static final String FILE_NOT_FOUND = "File not found";
    public static final String ERROR_READING_FILE = "Error reading file";
    public static final String SPACES_BETWEEN_TAGS = ">(\\s)+<";
    public static final String TAGS_WITHOUT_SPACES = "><";

    public XmlLevel parse(String source) {
        String xml = readXmlFromFile(getClass(), source);
        DAOFactory factory = DAOFactory.getInstance();
        XmlDao applianceDAO = factory.getXmlDao();

        XmlLevel resultXml = applianceDAO.parseXml(xml);
        return resultXml;
    }

    public static String readXmlFromFile(Class context, String name) {

        String xml="";
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
            xml=parsed.replaceAll(SPACES_BETWEEN_TAGS, TAGS_WITHOUT_SPACES);

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
