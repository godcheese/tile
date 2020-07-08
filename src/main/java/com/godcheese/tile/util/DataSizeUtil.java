package com.godcheese.tile.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class DataSizeUtil {

    private static final long KILO_BYTE = 1024;
    private static final long KILO_BYTE2 = KILO_BYTE / 10;

    public static PrettySize prettySize(long mByte, long kiloByte) {
        PrettySize prettySize = new DataSizeUtil().new PrettySize();
        double mod = 0;
        SizeEnum defaultSizeEnum = SizeEnum.BYTE;
        for (SizeEnum sizeEnum : SizeEnum.values()) {
            if (sizeEnum.exponent > 0 && mByte >= kiloByte / 10) {
                mod = mByte / Math.pow(kiloByte, sizeEnum.exponent);
                if (mod < kiloByte) {
                    defaultSizeEnum = sizeEnum;
                    break;
                }
            } else {

                mod = Long.valueOf(mByte).doubleValue();
            }
        }
        prettySize.setPrettySize(mod);
        prettySize.setUnit(defaultSizeEnum.unit);
        prettySize.setName(defaultSizeEnum.name);
        return prettySize;
    }

    public static PrettySize prettySize(long mByte) {
        return prettySize(mByte, KILO_BYTE);
    }

    public static String pretty(long mByte, long kiloByte) {
        String res;
        int scale = 2;
        PrettySize prettySize = DataSizeUtil.prettySize(mByte, kiloByte);

        BigDecimal bigDecimal = new BigDecimal(String.valueOf(prettySize.getPrettySize()));
        String str = bigDecimal.setScale(scale, RoundingMode.HALF_UP).toString();
        int index;

        while ((index = str.lastIndexOf('0')) == str.length() - 1) {
            str = str.substring(0, index);
        }

        if ((index = str.lastIndexOf('.')) == str.length() - 1) {
            str = str.substring(0, index);
        }
        res = str + prettySize.getUnit();

        return res;
    }

    public static String pretty(long mByte) {
        return pretty(mByte, KILO_BYTE);
    }

    public static double format(long mByte, SizeEnum sizeEnum, long kiloByte) {
        double mod;
        if (sizeEnum.exponent > 0) {
            mod = mByte / Math.pow(kiloByte, sizeEnum.exponent);
        } else {
            mod = mByte;
        }
        return mod;
    }

    public static double format(long mByte, SizeEnum sizeEnum) {
        return format(mByte, sizeEnum, 1000);
    }

    public enum SizeEnum {

        /**
         * bit
         */

        /**
         * Byte 字节
         */
        BYTE("Byte", "B", 0),

        /**
         * KiloByte
         */
        KILO_BYTE("KiloByte", "KB", 1),

        /**
         * MegaByte
         */
        MEGA_BYTE("MegaByte", "MB", 2),

        /**
         * GigaByte
         */
        GIGA_BYTE("GigaByte", "GB", 3),

        /**
         * TeraByte
         */
        TERA_BYTE("TeraByte", "TB", 4),

        /**
         * PetaByte
         */
        PETA_BYTE("PetaByte", "PB", 5),

        /**
         * ExaByte
         */
        EXA_BYTE("ExaByte", "EB", 6),

        /**
         * ZettaByte
         */
        ZETTA_BYTE("ZettaByte", "ZB", 7),

        /**
         * YottaByte
         */
        YOTTA_BYTE("YottaByte", "YB", 8),

        /**
         * NonaByte
         */
        NONA_BYTE("NonaByte", "NB", 9),

        /**
         * DoggaByte
         */
        DOGGA_BYTE("DoggaByte", "DB", 10);

        private String name;
        private String unit;
        private int exponent;

        SizeEnum(String name, String unit, int exponent) {
            this.name = name;
            this.unit = unit;
            this.exponent = exponent;
        }

    }

    public class PrettySize {

        private long mByte;
        private double prettySize;
        private String name;
        private String unit;

        public long getmByte() {
            return mByte;
        }

        public void setmByte(long mByte) {
            this.mByte = mByte;
        }

        public double getPrettySize() {
            return prettySize;
        }

        public void setPrettySize(double prettySize) {
            this.prettySize = prettySize;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
