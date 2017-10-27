package by.tc.dao;

import by.tc.entity.XmlLevel;

public interface XmlDao {

    XmlLevel parseXml(String source);
}
