package by.tc.dao;

import by.tc.dao.implementation.XmlParser;

public final class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();

    private final XmlDao xmlDao = new XmlParser();

    private DAOFactory() {
    }

    public XmlDao getXmlDao() {
        return xmlDao;
    }

    public static DAOFactory getInstance() {
        return instance;
    }
}
