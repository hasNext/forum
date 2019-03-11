package com.forum.utils.propertis;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

    /**
     * 所有properites文件中的键值对缓存对象
     */
    private static Map<String, Properties> propsMap = new HashMap<>();

    /**
     * 获取文件中key对应的value值
     *
     * @param propertyKey
     * @param propertyFileName
     * @return
     */
    public static String getProperty(String propertyKey, String propertyFileName) {
        if (propsMap.containsKey(propertyFileName)) {
            return propsMap.get(propertyFileName).getProperty(propertyKey);
        }
        String value = null;
        try {
            Properties props = PropertiesLoaderUtils
                    .loadAllProperties(propertyFileName);
            value = props.getProperty(propertyKey);
            propsMap.put(propertyFileName, props);
        } catch (IOException e) {
            System.out.println(propertyFileName + "文件未找到");
            e.printStackTrace();
        }
        return value;
    }

}
