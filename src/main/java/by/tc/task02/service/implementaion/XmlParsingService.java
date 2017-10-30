package by.tc.task02.service.implementaion;

import by.tc.task02.dao.DAOFactory;
import by.tc.task02.dao.XmlDao;
import by.tc.task02.entity.XmlLevel;
import by.tc.task02.service.ParsingService;
import by.tc.task02.service.validation.Validator;
import by.tc.task02.service.validation.XmlValidationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class XmlParsingService implements ParsingService {

    private static final String NULL_SOURCE = "Source is null";
    private static final String CHARSET = "UTF-8";
    private static final String SPACES_BETWEEN_TAGS = ">(\\s)+<";
    private static final String TAGS_WITHOUT_SPACES = "><";

    public XmlLevel parse(String source) {

        try {
            String xml = readXmlFromFile(getClass(), source);
            Validator.isXmlValid(xml);
            DAOFactory factory = DAOFactory.getInstance();
            XmlDao xmlDAO = factory.getXmlDao();
            return xmlDAO.parseXml(xml);

        } catch (IOException e) {
            System.out.println(e.getMessage());

        } catch (XmlValidationException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private static String readXmlFromFile(Class context, String name) throws IOException, XmlValidationException {

        String xml = "";
        FileInputStream catalogueReader = null;
        URL source = context.getClassLoader().getResource(name);

        if (source == null) {
            throw new XmlValidationException(NULL_SOURCE);
        }

        catalogueReader = new FileInputStream(new File(source.getFile()));
        byte[] content = new byte[catalogueReader.available()];
        catalogueReader.read(content);
        String parsed = new String(content, CHARSET);
        xml = parsed.replaceAll(SPACES_BETWEEN_TAGS, TAGS_WITHOUT_SPACES);

        return xml;
    }
}
