package com.warner.lcs.common.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQL {

    private static Map<String, String> sqlMap = new HashMap<>();

    private static void load(String fileName) throws Exception {
       SAXBuilder parser = new SAXBuilder();
       Document document = parser.build(SQL.class.getClassLoader().getResourceAsStream(fileName+".xml"));
        Element root = document.getRootElement();
        List<Element> list = root.getChildren();
        for(Element element: list)
        {
            sqlMap.put(fileName +"_"+ element.getName(), element.getText());
        }
    }

    public static String get(String zpackage, String name) throws Exception {

        String key = zpackage + "_" +name;
        String sql = sqlMap.get(key);

        if(sql == null || sql.isEmpty()) {
            load(zpackage);
        }

        return sqlMap.get(key);
    }

}
