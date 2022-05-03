package com.crm.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置文件工具类
 * 
 *
 */
public class PropertiesUtil {

    /**
     * 获取配置文件
     * 
     * @param fileName
     * @return
     */
    public static Properties getProperties(String fileName) {
        InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(is, "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

}
