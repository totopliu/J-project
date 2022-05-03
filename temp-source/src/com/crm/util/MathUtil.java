package com.crm.util;

import java.math.BigDecimal;

/**
 * 计算工具类
 * 
 *
 */
public class MathUtil {

    /**
     * Double四舍五入成String
     * 
     * @param digits
     *            小数位数
     * @param value
     *            被四舍五入的值
     * @return 返回String
     */
    public static String roundDoubleSetHalfUpToStr(int digits, double value) {
        BigDecimal valDecimal = new BigDecimal(new Double(value).toString());
        return valDecimal.setScale(digits, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * Double截取成String
     * 
     * @param digits
     *            小数位数
     * @param value
     *            原始值
     * @return
     */
    public static String roundDoubleSetDownStr(int digits, double value) {
        BigDecimal valDecimal = translateToBigDecimal(value);
        return valDecimal.setScale(digits, BigDecimal.ROUND_DOWN).toString();
    }

    /**
     * Double四舍五入成double
     * 
     * @param digits
     *            小数位数
     * @param value
     *            被四舍五入的值
     * @return 返回Double
     */
    public static double roundDoubleSetHalfUpToDouble(int digits, double value) {
        BigDecimal valDecimal = new BigDecimal(new Double(value).toString());
        return valDecimal.setScale(digits, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * Double截取成double
     * 
     * @param digits
     *            小数位数
     * @param value
     *            原始值
     * @return
     */
    public static double roundDoubleSetDownToDouble(int digits, double value) {
        BigDecimal valDecimal = translateToBigDecimal(value);
        return valDecimal.setScale(digits, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * BigDecimal四舍五入成double
     * 
     * @param digits
     *            小数位数
     * @param bigDecimal
     *            被四舍五入的值
     * @return
     */
    public static double roundBigDecimalSetHalfupToDouble(int digits, BigDecimal bigDecimal) {
        return bigDecimal.setScale(digits, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * BigDecimal四舍五入成int
     * 
     * @param digits
     * @param bigDecimal
     * @return
     */
    public static int roundBigDecimalSetHalfupToInt(int digits, BigDecimal bigDecimal) {
        return bigDecimal.setScale(digits, BigDecimal.ROUND_HALF_UP).intValue();
    }

    /**
     * 创建BigDecimal
     * 
     * @param value
     * @return
     */
    public static BigDecimal translateToBigDecimal(double value) {
        return new BigDecimal(new Double(value).toString());
    }

    public static int compare(Double d, Double d1) {
        return translateToBigDecimal(d).compareTo(translateToBigDecimal(d1));
    }

    public static Double subtract(Double d, Double d1) {
        return translateToBigDecimal(d).subtract(translateToBigDecimal(d1)).doubleValue();
    }

    public static Double add(Double d, Double d1) {
        return translateToBigDecimal(d).add(translateToBigDecimal(d1)).doubleValue();
    }

    public static Double multiply(Double d, Double d1) {
        return translateToBigDecimal(d).multiply(translateToBigDecimal(d1)).doubleValue();
    }
}
