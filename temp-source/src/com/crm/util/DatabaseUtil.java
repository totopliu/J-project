package com.crm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class DatabaseUtil {

    public static void backup() {
        Properties prop = PropertiesUtil.getProperties("backup.properties");
        InputStream in = null;
        InputStreamReader xx = null;
        BufferedReader br = null;
        OutputStreamWriter writer = null;
        FileOutputStream fout = null;
        try {
            Runtime rt = Runtime.getRuntime();
            Process child = rt.exec(prop.getProperty("exec"));
            in = child.getInputStream();
            xx = new InputStreamReader(in, "utf-8");
            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            br = new BufferedReader(xx);
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();
            File file = new File("/database-backup");
            if (!file.exists()) {
                file.mkdirs();
            }
            fout = new FileOutputStream("/database-backup/" + CommonUtil.fromDateY() + ".sql");
            writer = new OutputStreamWriter(fout, "utf-8");
            writer.write(outStr);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (xx != null) {
                try {
                    xx.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        try {
            backup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
