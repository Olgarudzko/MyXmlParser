package by.tc.entity;

import java.util.List;
import java.util.LinkedHashMap;

public class EntireXml {

    private LinkedHashMap<String, Integer> xmlStructure;
    private List <XmlBean> xmlBeans;

    public EntireXml() {
    }

    public LinkedHashMap<String, Integer> getXmlStructure() {
        return xmlStructure;
    }

    public void setXmlStructure(LinkedHashMap<String, Integer> xmlStructure) {
        this.xmlStructure = xmlStructure;
    }

    public List<XmlBean> getXmlBeans() {
        return xmlBeans;
    }

    public void setXmlBeans(List<XmlBean> xmlBeans) {
        this.xmlBeans = xmlBeans;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntireXml entireXml = (EntireXml) o;

        if (xmlStructure != null ? !xmlStructure.equals(entireXml.xmlStructure) : entireXml.xmlStructure != null)
            return false;
        return xmlBeans != null ? xmlBeans.equals(entireXml.xmlBeans) : entireXml.xmlBeans == null;
    }

    @Override
    public int hashCode() {
        int result = xmlStructure != null ? xmlStructure.hashCode() : 0;
        result = 31 * result + (xmlBeans != null ? xmlBeans.hashCode() : 0);
        return result;
    }
}
