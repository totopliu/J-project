package com.crm.util;

import java.util.Properties;

public class MsgUtil {

    public static String getSessionLgMsg(String key) {
        String lg = SessionUtil.getLg();
        Properties prop = PropertiesUtil.getProperties("i18n/" + lg + ".properties");
        return prop.getProperty(key);
    }

    public static String getLgMsg(String key, String lg) {
        Properties prop = PropertiesUtil.getProperties("i18n/" + lg + ".properties");
        return prop.getProperty(key);
    }

}
