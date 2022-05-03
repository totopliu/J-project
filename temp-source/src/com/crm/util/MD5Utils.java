package com.crm.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static String md5(String inputStr) {
        String pwd = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] s = digest.digest(inputStr.getBytes("UTF-8"));
            pwd = toHex(s);
            return pwd;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String toHex(byte hash[]) {
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;
        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10)
                buf.append("0");
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        System.out.println(md5("zhao[].asdf"));
    }
}