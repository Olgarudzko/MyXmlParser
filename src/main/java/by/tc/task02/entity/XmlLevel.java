package by.tc.task02.entity;

import java.util.ArrayList;
import java.util.List;

public class XmlLevel {

    private List<XmlLevel> children;
    private XmlLevel parent;
    private String name;

    public List<XmlLevel> getChildren() {
        return children;
    }

    private void addChild(XmlLevel child) {
        if (children == null) {
            children = new ArrayList<XmlLevel>();
        }
        children.add(child);
    }

    public XmlLevel getParent() {
        return parent;
    }

    public void setParent(XmlLevel parent) {
        parent.addChild(this);
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XmlLevel xmlLevel = (XmlLevel) o;

        if (children != null ? !children.equals(xmlLevel.children) : xmlLevel.children != null) return false;
        if (parent != null ? !parent.equals(xmlLevel.parent) : xmlLevel.parent != null) return false;
        return name != null ? name.equals(xmlLevel.name) : xmlLevel.name == null;
    }

    @Override
    public int hashCode() {
        int result = children != null ? children.hashCode() : 0;
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
