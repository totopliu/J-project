package com.crm.util;

import java.math.BigDecimal;

/**
 * 返佣工具类
 * 
 */
public class RebateUtil {

    public static double getFixedRebate(BigDecimal volumeBigDecimal, Double rebateFixed) {
        double rebate = MathUtil.roundBigDecimalSetHalfupToDouble(2,
                volumeBigDecimal.multiply(MathUtil.translateToBigDecimal(rebateFixed)));
        if (rebate < 0) {
            rebate = BigDecimal.ZERO.doubleValue();
        }
        return rebate;
    }

    public static double getPointRebate(double currencypoint, BigDecimal volumeBigDecimal, Double rebatePoint) {
        double rebate = MathUtil.roundBigDecimalSetHalfupToDouble(2, MathUtil.translateToBigDecimal(currencypoint)
                .multiply(volumeBigDecimal).multiply(MathUtil.translateToBigDecimal(rebatePoint)));
        if (rebate < 0) {
            rebate = BigDecimal.ZERO.doubleValue();
        }
        return rebate;
    }

}
