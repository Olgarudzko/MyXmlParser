package by.tc.entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class XmlBean {

    private String beanName;
    private LinkedHashMap<String, String> beanFields;

    public XmlBean() {
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public LinkedHashMap<String, String> getBeanFields() {
        return beanFields;
    }

    public void setBeanFields(LinkedHashMap<String, String> beanFields) {
        this.beanFields = beanFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XmlBean xmlBean = (XmlBean) o;

        if (beanName != null ? !beanName.equals(xmlBean.beanName) : xmlBean.beanName != null) return false;
        return beanFields != null ? beanFields.equals(xmlBean.beanFields) : xmlBean.beanFields == null;
    }

    @Override
    public int hashCode() {
        int result = beanName != null ? beanName.hashCode() : 0;
        result = 31 * result + (beanFields != null ? beanFields.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder beans=new StringBuilder();
        for (Map.Entry<String, String> entry: beanFields.entrySet()){
            beans.append(entry.getKey()).append("->").append(entry.getValue()).append("; ");
        }
        return "XmlBean{" +
                "beanName='" + beanName + '\'' +
                ", beanFields=[" + beans.toString() +
                "]}";
    }
}
