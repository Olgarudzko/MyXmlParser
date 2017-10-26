package by.tc.dao.implementation;

import by.tc.dao.XmlDao;
import by.tc.entity.EntireXml;
import by.tc.entity.XmlBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser implements XmlDao {

    public EntireXml parseXml(String source) {
        String xml = readXmlFromFile(getClass(), "example.xml");
        source = xml;
        EntireXml result = new EntireXml();
        List<XmlBean> beans = new ArrayList<XmlBean>();
        LinkedHashMap<String, Integer> structure = new LinkedHashMap<String, Integer>();
        Pattern startOfTagPattern = Pattern.compile("<");
        Matcher startOfTag = startOfTagPattern.matcher(source.trim());
        String lastKey = "";
        Pattern endOfTagPattern = Pattern.compile(">");
        Matcher endOfTag = endOfTagPattern.matcher(source);
        while (startOfTag.find()) {
            if (source.charAt(startOfTag.start() + 1) == '?'){
                endOfTag.find();
                startOfTag.find();
            }
            if (source.charAt(startOfTag.start() + 1) != '/') {
                endOfTag.find();
                String word = source.substring(startOfTag.start() + 1, endOfTag.start()).intern();
                if (source.charAt(endOfTag.start() + 1) == '<') {
                    if (structure.containsKey(word)) {
                        structure.put(word, structure.get(word) + 1);
                    } else {
                        structure.put(word, 1);
                    }
                    lastKey = word;
                } else {
                    XmlBean bean = new XmlBean();
                    bean.setBeanName(lastKey);
                    LinkedHashMap<String, String> beanFields = new LinkedHashMap<String, String>();
                    String currentWord = word;
                    while (!currentWord.contains("/")) {
                        startOfTag.find();
                        String value = source.substring(endOfTag.start()+1, startOfTag.start()).intern();
                        beanFields.put(currentWord, value);
                        endOfTag.find();
                        if (source.substring(startOfTag.start()+1, endOfTag.start()).intern().equals("/"+lastKey)){
                            break;
                        }
                        startOfTag.find();
                        endOfTag.find();
                        String tag = source.substring(startOfTag.start()+1, endOfTag.start()).intern();
                        currentWord = tag;
                    }
                    bean.setBeanFields(beanFields);
                    beans.add(bean);
                }
            } else {
                endOfTag.find();
                if (endOfTag.start() == source.length() - 1) {
                    String tag = "";
                    int counter = 0;
                    Iterator <Map.Entry<String, Integer>> iterator = structure.entrySet().iterator();
                    while (iterator.hasNext()){
                        Map.Entry<String, Integer> entry = iterator.next();
                        if (counter>0){
                            structure.put(tag, entry.getValue());
                        }
                        tag = entry.getKey();
                        counter++;
                        if (counter==structure.size()){
                            iterator.remove();
                        }
                    }

                    result.setXmlStructure(structure);
                    result.setXmlBeans(beans);
                    return result;
                }
                startOfTag.find();
            }
        }
        return null;
    }

    public static String readXmlFromFile(Class context, String name) {
        String xml="";
        FileInputStream catalogueReader = null;
        try {
            URL source = context.getClassLoader().getResource(name);
            if (source == null) {
                System.out.println("Null file");
                return null;
            }
            catalogueReader = new FileInputStream(new File(source.getFile()));
            byte[] content = new byte[catalogueReader.available()];
            catalogueReader.read(content);
            String parsed = new String(content, "UTF-8");
            String [] text = parsed.split("\\s+");
            for (String line: text){
                xml=xml.concat(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Not found");
            return null;
        } catch (IOException e) {
            System.out.println("ERROR READING");
            return null;
        } finally {
            if (catalogueReader != null) {
                try {
                    catalogueReader.close();
                } catch (IOException e) {
                    System.out.println("ERROR_STREAM_CLOSING" + e.getLocalizedMessage());
                }
            }
        }
        return xml;
    }
}
