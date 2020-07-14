package com.godcheese.tile.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class ImageUtil {

    public static VerifyCodeImage createVerifyCodeImage(int width, int height, java.awt.Color backgroundColor, String randomString, java.awt.Color stringColor, boolean yawp, int interLine, long expireIn) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.createGraphics();
        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, width, height);

        //字体大小为图片高度的80%
        int fontSiz = (int) (height * 0.8);
        int stringX = width / randomString.length() / randomString.length();
        int stringY;
        graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSiz));

        for (int i = 0; i < randomString.length(); i++) {
            String stringCode = String.valueOf(randomString.charAt(i));
            graphics.setColor(stringColor);
            stringY = (int) ((Math.random() * 0.3 + 0.6) * height);
            graphics.drawString(stringCode, stringX, stringY);
            stringX += (width / randomString.length()) * (Math.random() * 0.3 + 0.8) + i + Math.random();
        }
        if (interLine > 0) {
            int x = RandomUtil.nextInt(4), y = 0;
            int x1 = width - RandomUtil.nextInt(4), y1 = 0;
            for (int i = 0; i < interLine; i++) {
                graphics.setColor(ColorUtil.getRandomRGBColor());
                y = RandomUtil.nextInt(height - RandomUtil.nextInt(4));
                y1 = RandomUtil.nextInt((height - RandomUtil.nextInt(4)));
                graphics.drawLine(x, y, x1, y1);
            }
        }
        if (yawp) {
            yawp(bufferedImage, width, height);
        }
        graphics.dispose();
        return new VerifyCodeImage(randomString, bufferedImage, expireIn);
    }

    public static VerifyCodeImage createVerifyCodeImage(int width, int height, java.awt.Color backgroundColor, String randomString, java.awt.Color stringColor, File fontFile, boolean yawp, int interLine, long expireIn) throws IOException, FontFormatException {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.createGraphics();
        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, width, height);

        //字体大小为图片高度的80%
        int fontSize = (int) (height * 0.8);
        int stringX = width / randomString.length() / randomString.length();
        int stringY;

        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        graphics.setFont(font.deriveFont((float) fontSize));
//        graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSiz));

        for (int i = 0; i < randomString.length(); i++) {
            String stringCode = String.valueOf(randomString.charAt(i));
            graphics.setColor(stringColor);
            stringY = (int) ((Math.random() * 0.3 + 0.6) * height);
            graphics.drawString(stringCode, stringX, stringY);
            stringX += (width / randomString.length()) * (Math.random() * 0.3 + 0.8) + i + Math.random();
        }
        if (interLine > 0) {
            int x = RandomUtil.nextInt(4), y = 0;
            int x1 = width - RandomUtil.nextInt(4), y1 = 0;
            for (int i = 0; i < interLine; i++) {
                graphics.setColor(ColorUtil.getRandomRGBColor());
                y = RandomUtil.nextInt(height - RandomUtil.nextInt(4));
                y1 = RandomUtil.nextInt((height - RandomUtil.nextInt(4)));
                graphics.drawLine(x, y, x1, y1);
            }
        }
        if (yawp) {
            yawp(bufferedImage, width, height);
        }
        graphics.dispose();
        return new VerifyCodeImage(randomString, bufferedImage, expireIn);
    }

    public static VerifyCodeImage createVerifyCodeImage(int width, int height, java.awt.Color backgroundColor, String randomString, java.awt.Color stringColor, InputStream fontInputStream, boolean yawp, int interLine, long expireIn) throws IOException, FontFormatException {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.createGraphics();
        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, width, height);

        //字体大小为图片高度的80%
        int fontSize = (int) (height * 0.8);
        int stringX = width / randomString.length() / randomString.length();
        int stringY;

        Font font = Font.createFont(Font.TRUETYPE_FONT, fontInputStream);
        graphics.setFont(font.deriveFont((float) fontSize));
//        graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSiz));

        for (int i = 0; i < randomString.length(); i++) {
            String stringCode = String.valueOf(randomString.charAt(i));
            graphics.setColor(stringColor);
            stringY = (int) ((Math.random() * 0.3 + 0.6) * height);
            graphics.drawString(stringCode, stringX, stringY);
            stringX += (width / randomString.length()) * (Math.random() * 0.3 + 0.8) + i + Math.random();
        }
        if (interLine > 0) {
            int x = RandomUtil.nextInt(4), y = 0;
            int x1 = width - RandomUtil.nextInt(4), y1 = 0;
            for (int i = 0; i < interLine; i++) {
                graphics.setColor(ColorUtil.getRandomRGBColor());
                y = RandomUtil.nextInt(height - RandomUtil.nextInt(4));
                y1 = RandomUtil.nextInt((height - RandomUtil.nextInt(4)));
                graphics.drawLine(x, y, x1, y1);
            }
        }
        if (yawp) {
            yawp(bufferedImage, width, height);
        }
        graphics.dispose();
        return new VerifyCodeImage(randomString, bufferedImage, expireIn);
    }

    public static void yawp(BufferedImage bufferedImage, int width, int height) {
        Random random = new Random();
        // 噪声率
        float yawpRate = 0.05f;
        // 噪点数量
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int xxx = random.nextInt(width);
            int yyy = random.nextInt(height);
            int rgb = ColorUtil.getRandomRGBColor().getRGB();
            bufferedImage.setRGB(xxx, yyy, rgb);
        }
    }

    public static class VerifyCodeImage {

        private String verifyCode;

        private BufferedImage bufferedImage;

        private long expireIn;

        private boolean isExpire;

        private LocalDateTime expireTime;

        public VerifyCodeImage(String verifyCode, BufferedImage bufferedImage, long expireIn) {
            this.verifyCode = verifyCode;
            this.bufferedImage = bufferedImage;
            this.expireIn = expireIn;
            this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
        }

        public VerifyCodeImage() {
        }

        public String getVerifyCode() {
            return verifyCode;
        }

        public void setVerifyCode(String validateCode) {
            this.verifyCode = verifyCode;
        }

        public BufferedImage getBufferedImage() {
            return bufferedImage;
        }

        public void setBufferedImage(BufferedImage bufferedImage) {
            this.bufferedImage = bufferedImage;
        }

        public long getExpireIn() {
            return expireIn;
        }

        public void setExpireIn(long expireIn) {
            this.expireIn = expireIn;
            this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
        }

        public boolean isExpire() {
            return LocalDateTime.now().isAfter(expireTime);
        }
    }
}
