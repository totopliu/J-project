package com.crm.util;

import java.math.BigDecimal;

public class CurrencyPointUtil {

    /**
     * 计算点值
     * 
     * @param currency
     * @param digits
     * @param bid
     * @param fixed
     * @param relbid
     * @return
     */
    public static Double mathCurrencyPoint(String currency, Integer digits, Double bid, Double fixed, Double relbid) {
        BigDecimal onehtbd = new BigDecimal(100000);
        BigDecimal digitsbd = new BigDecimal(Math.pow(10, digits));
        if ("USD".equals(currency.substring(currency.length() - 3))) {
            return BigDecimal.TEN.doubleValue();
        } else if (fixed != null && new BigDecimal(fixed).compareTo(BigDecimal.ZERO) == 1) {
            return fixed;
        } else if ("USD".equals(currency.substring(0, 3))) {
            BigDecimal rebidbd = new BigDecimal(1 / bid);
            return onehtbd.divide(digitsbd).multiply(rebidbd).doubleValue();
        } else {
            BigDecimal bidbd = new BigDecimal(bid);
            BigDecimal relbidbd = new BigDecimal(relbid);
            return onehtbd.divide(digitsbd).multiply(relbidbd.divide(bidbd, 5, BigDecimal.ROUND_HALF_UP)).doubleValue();
        }
    }

}
