package by.tc.dao.implementation;

import by.tc.dao.XmlDao;
import by.tc.entity.EntireXml;
import by.tc.entity.XmlBean;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser implements XmlDao {

    EntireXml result;
    List<XmlBean> beans;
    LinkedHashMap<String, Integer> structure;
    String lastKey;
    Pattern startOfTagPattern = Pattern.compile("<");
    Matcher startOfTag;
    Pattern endOfTagPattern = Pattern.compile(">");
    Matcher endOfTag;

    public EntireXml parseXml(String source) {

        result = new EntireXml();
        beans = new ArrayList<XmlBean>();
        structure = new LinkedHashMap<String, Integer>();
        startOfTag = startOfTagPattern.matcher(source.trim());
        lastKey = "";
        endOfTag = endOfTagPattern.matcher(source);

        while (startOfTag.find()) {

            skipFirstLine(source);

            if (source.charAt(startOfTag.start() + 1) != '/') {

                endOfTag.find();
                String fullTag = source.substring(startOfTag.start() + 1, endOfTag.start());
                String word = fullTag.split("\\s+")[0].intern();

                if (source.charAt(endOfTag.start() + 1) == '<') {

                    addOrUpdateLevel(word);
                    lastKey = word;

                } else {
                    XmlBean bean = parseBean(source, word);
                    beans.add(bean);
                }
            } else {
                endOfTag.find();
                if (endOfTag.start() == source.length() - 1) {

                    defineChildrenAmount();

                    result.setXmlStructure(structure);
                    result.setXmlBeans(beans);
                    return result;
                }
                startOfTag.find();
            }
        }
        return null;
    }


    private void skipFirstLine(String source) {
        if (source.charAt(startOfTag.start() + 1) == '?') {
            endOfTag.find();
            startOfTag.find();
        }
    }


    private void addOrUpdateLevel(String word) {
        if (structure.containsKey(word)) {
            structure.put(word, structure.get(word) + 1);
        } else {
            structure.put(word, 1);
        }
    }


    private XmlBean parseBean(String source, String word){

        XmlBean bean = new XmlBean();
        bean.setBeanName(lastKey);
        LinkedHashMap<String, String> beanFields = new LinkedHashMap<String, String>();
        String currentWord = word;

        while (!currentWord.contains("/")) {

            startOfTag.find();
            String fullvalue = source.substring(endOfTag.start() + 1, startOfTag.start());
            String value = fullvalue.split("\\s+")[0].intern();
            beanFields.put(currentWord, value);
            endOfTag.find();

            String fullSubstring = source.substring(startOfTag.start() + 1, endOfTag.start());
            if (fullSubstring.split("\\s+")[0].intern().equals("/" + lastKey)) {
                break;
            }

            startOfTag.find();
            endOfTag.find();
            String tag = source.substring(startOfTag.start() + 1, endOfTag.start()).intern();
            currentWord = tag;

        }

        bean.setBeanFields(beanFields);
        return bean;
    }


    private void defineChildrenAmount(){
        String tag = "";
        int counter = 0;
        Iterator<Map.Entry<String, Integer>> iterator = structure.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (counter > 0) {
                structure.put(tag, entry.getValue());
            }
            tag = entry.getKey();
            counter++;
            if (counter == structure.size()) {
                iterator.remove();
            }
        }
    }
}
