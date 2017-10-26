package by.tc.dao;

import by.tc.entity.EntireXml;

public interface XmlDao {

    EntireXml parseXml(String source);
}
