package com.crm.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.pub.UserResetPassSocketDTO;

/**
 * 
 * @ClassName: SocketUtil
 * @Description: 调用MT4接口Socket工具类
 * @date 2017年4月26日 上午9:39:02
 * @version 1.0
 */
public class SocketUtil {

    public static String connectMt4(String paramJsonStr) {
        Socket socket = null;
        PrintWriter write = null;
        BufferedReader in = null;
        String response = new String();
        try {
            Properties prop = PropertiesUtil.getProperties("mt4.properties");
            String server = prop.getProperty("mt4.server");
            int port = Integer.valueOf(prop.getProperty("mt4.port"));
            socket = new Socket(server, port);
            InputStream is = new ByteArrayInputStream(paramJsonStr.getBytes("utf-8"));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String readline = br.readLine();
            write = new PrintWriter(socket.getOutputStream());
            write.println(readline);
            write.flush();
            response = in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            response = "-100";
            return response;
        } finally {
            write.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public static void main(String[] args) {
        UserResetPassSocketDTO user = Mt4PropertiesUtil.getEmptyUserResetPassSocketForB();
        user.setType(0);
        user.setLogin(100271);
        user.setPassword("LNUZBYhpr1");
        String paramJsonStr = JSONObject.toJSONString(user);
        System.out.println(connectMt4(paramJsonStr));
    }
}
