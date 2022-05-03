package com.crm.util;

import java.util.Properties;

import com.crm.platform.entity.pub.GoldSokectDTO;
import com.crm.platform.entity.pub.UserResetPassSocketDTO;
import com.crm.platform.entity.pub.UserSocketDTO;

public class Mt4PropertiesUtil {

    public static UserSocketDTO getEmptyUserSocketForA() {
        Properties prop = PropertiesUtil.getProperties("mt4.properties");
        UserSocketDTO userSocketDTO = new UserSocketDTO();
        userSocketDTO.setIp(prop.getProperty("mt4.ip"));
        userSocketDTO.setLeve(Integer.parseInt(prop.getProperty("mt4.leve")));
        userSocketDTO.setLgmanage(Integer.parseInt(prop.getProperty("mt4a.lgmanage")));
        userSocketDTO.setLgpword(prop.getProperty("mt4a.lgpword"));
        userSocketDTO.setFunction(1);
        userSocketDTO.setLogin(Integer.parseInt(prop.getProperty("mt4a.login")));
        return userSocketDTO;
    }

    public static UserSocketDTO getEmptyUserSocketForB() {
        Properties prop = PropertiesUtil.getProperties("mt4.properties");
        UserSocketDTO userSocketDTO = new UserSocketDTO();
        userSocketDTO.setIp(prop.getProperty("mt4.ip"));
        userSocketDTO.setLeve(Integer.parseInt(prop.getProperty("mt4.leve")));
        userSocketDTO.setLgmanage(Integer.parseInt(prop.getProperty("mt4.lgmanage")));
        userSocketDTO.setLgpword(prop.getProperty("mt4.lgpword"));
        userSocketDTO.setFunction(1);
        userSocketDTO.setLogin(Integer.parseInt(prop.getProperty("mt4.login")));
        return userSocketDTO;
    }

    public static GoldSokectDTO getConfigGoldSoketDTO() {
        GoldSokectDTO goldSokectDTO = new GoldSokectDTO();
        Properties prop = PropertiesUtil.getProperties("mt4.properties");
        goldSokectDTO.setIp(prop.getProperty("mt4.ip"));
        goldSokectDTO.setLgmanage(Integer.parseInt(prop.getProperty("mt4.lgmanage")));
        goldSokectDTO.setLgpword(prop.getProperty("mt4.lgpword"));
        goldSokectDTO.setFunction(2);
        return goldSokectDTO;
    }

    public static UserResetPassSocketDTO getEmptyUserResetPassSocketForA() {
        Properties prop = PropertiesUtil.getProperties("mt4.properties");
        UserResetPassSocketDTO user = new UserResetPassSocketDTO();
        user.setIp(prop.getProperty("mt4.ip"));
        user.setLgmanage(Integer.parseInt(prop.getProperty("mt4a.lgmanage")));
        user.setLgpword(prop.getProperty("mt4a.lgpword"));
        user.setFunction(3);
        return user;
    }

    public static UserResetPassSocketDTO getEmptyUserResetPassSocketForB() {
        Properties prop = PropertiesUtil.getProperties("mt4.properties");
        UserResetPassSocketDTO user = new UserResetPassSocketDTO();
        user.setIp(prop.getProperty("mt4.ip"));
        user.setLgmanage(Integer.parseInt(prop.getProperty("mt4.lgmanage")));
        user.setLgpword(prop.getProperty("mt4.lgpword"));
        user.setFunction(3);
        return user;
    }

}
