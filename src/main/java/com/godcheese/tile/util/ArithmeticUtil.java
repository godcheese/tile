package com.godcheese.tile.util;

import java.math.BigDecimal;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-03-12
 */
public class ArithmeticUtil {

    private static final int DEFAULT_SCALE = 2;
    private static final int DEFAULT_ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

    /**
     * 保留小数点后面几位小数，不四舍五入，如：保留2位小数：2.354 => 2.35
     *
     * @param value
     * @param scale
     * @return
     */
    public static double roundDown(double value, int scale, boolean hasZero) {
        return roundDown(value, scale);
    }

    /**
     * 保留小数点后面几位小数，不四舍五入，如：保留2位小数：2.354 => 2.35
     *
     * @param value
     * @param scale
     * @return
     */
    public static double roundDown(double value, int scale) {
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 保留小数点后面几位小数，不四舍五入，如：保留2位小数：2.354 => 2.35
     *
     * @param value
     * @param scale
     * @return
     */
    public static double roundDown(String value, int scale) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 四舍五入，5进1，如：保留1位小数：2.354 => 2.4
     *
     * @param value
     * @param scale
     * @return
     */
    public static double roundHafUp(String value, int scale) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入，5进1，如：保留1位小数：2.354 => 2.4
     *
     * @param value
     * @param scale
     * @return
     */
    public static double roundHafUp(double value, int scale) {
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入，5也略去，如：保留1位小数：2.354 => 2.3
     *
     * @param value
     * @param scale
     * @return
     */
    public static double roundHalfDown(String value, int scale) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * 提供精确的加法运算
     *
     * @param addend
     * @param augend
     * @return
     */
    public static double add(double addend, double augend) {
        BigDecimal bigDecimal1 = BigDecimal.valueOf(addend);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(augend);
        return bigDecimal1.add(bigDecimal2).doubleValue();
    }

    /**
     * 提供精确的减法运算
     *
     * @param minuend
     * @param subtrahend
     * @return
     */
    public static double subtract(double minuend, double subtrahend) {
        BigDecimal bigDecimal1 = BigDecimal.valueOf(minuend);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(subtrahend);
        return bigDecimal1.subtract(bigDecimal2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param multiplier
     * @param multiplicand
     * @return
     */
    public static double multiply(double multiplier, double multiplicand) {
        BigDecimal bigDecimal1 = BigDecimal.valueOf(multiplier);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(multiplicand);
        return bigDecimal1.multiply(bigDecimal2).doubleValue();
    }

    /**
     * 提供相对精确的除法运算
     *
     * @param dividend
     * @param divisor
     * @param scale
     * @param roundingMode
     * @return
     */
    public static double divide(double dividend, double divisor, int scale, int roundingMode) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal bigDecimal1 = BigDecimal.valueOf(dividend);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(divisor);
        return bigDecimal1.divide(bigDecimal2, scale, roundingMode).doubleValue();
    }

    /**
     * 提供相对精确的除法运算，默认四舍五入保留2位小数
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public static double divide(double dividend, double divisor) {
        BigDecimal bigDecimal1 = BigDecimal.valueOf(dividend);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(divisor);
        return bigDecimal1.divide(bigDecimal2, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE).doubleValue();
    }
}
