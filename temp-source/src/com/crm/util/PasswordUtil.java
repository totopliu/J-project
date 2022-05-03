package com.crm.util;

import java.util.Random;

/**
 * 
 * @ClassName: PasswordUtil
 * @Description: 生成随机密码工具类
 * @date 2017年4月26日 上午11:04:38
 * @version 1.0
 */
public class PasswordUtil {

    /**
     *
     * @Title: genRandomNum @Description: 生成指定位数的密码包含大小写字母、数字 @param @param
     * pwd_len @param @return 设定文件 @return String 返回类型 @throws
     */
    public static String genRandomNum(int len) {
        String str = ""; // 密码
        char[] bigNum = new char[26];
        char[] smallNum = new char[26];
        int[] num = new int[10];
        for (int i = 65; i <= 90; i++) { // 生成大写字母表,对照ASIC表
            bigNum[i - 65] = (char) i;
        }
        for (int i = 97; i <= 122; i++) { // 生成小写字母表
            smallNum[i - 97] = (char) i;
        }
        for (int i = 0; i <= 9; i++) { // 生成数字表
            num[i] = i;
        }
        Random random = new Random();
        // 需要先随机生成len长度中，大写字母的个数，小写字母的个数以及数字的个数，且保证每个个数都不能为0
        int big_len = random.nextInt(len - 2) + 1; // random.nextInt(len)表示生成[0,len)整数,表示生成[1,len-1)整数;
        int small_len = random.nextInt(len - big_len - 1) + 1;
        int num_len = len - big_len - small_len;
        // 每一位生成对应的密码
        for (int i = 0; i < big_len; i++) {
            str += bigNum[random.nextInt(26)];
        }
        for (int i = 0; i < small_len; i++) {
            str += smallNum[random.nextInt(26)];
        }
        for (int i = 0; i < num_len; i++) {
            str += num[random.nextInt(10)];
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(genRandomNum(6));
    }

}
