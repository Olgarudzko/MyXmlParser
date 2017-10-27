package by.tc.task02.dao;

import by.tc.task02.entity.XmlLevel;

public interface XmlDao {

    XmlLevel parseXml(String source);
}
