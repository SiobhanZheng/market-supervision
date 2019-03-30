package com.siobhan.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by siobhan.zheng on 2019/3/27
 */
public class VerifyCodeUtils {
    // 验证码组成
    private static final String VERIFY_CODES = "0123456789";
    // 验证码长度
    private static final int VERIFY_SIZE = 4;
    // 图片宽度
    private static final int IMG_WIDTH = 113;
    // 图片高度
    private static final int IMG_HEIGHT = 43;

    private static final Random random = new Random();

    /**
     * 在“0123456789”中随机生成一个长度为4的验证码
     * @return
     * @author zheng
     */
    public static String generateCodes(){
        StringBuilder verifyCode = new StringBuilder(VERIFY_SIZE);
        for (int i = 0; i < VERIFY_SIZE; i++) {
            verifyCode.append(VERIFY_CODES.charAt(random.nextInt(VERIFY_CODES.length() - 1)));
        }
        return verifyCode.toString();
    }

    /**
     * 生成指定验证码的图片，并以流的形式返回到客户端
     * @param os
     * @param code
     * @throws IOException
     */
    public static void outputImage(OutputStream os, String code) throws IOException {
        {
            int verifySize = code.length();
            BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
            Random rand = new Random();
            Graphics2D g2 = image.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color[] colors = new Color[5];
            Color[] colorSpaces = new Color[]{Color.WHITE, Color.CYAN,
                    Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                    Color.PINK, Color.YELLOW};
            float[] fractions = new float[colors.length];
            for (int i = 0; i < colors.length; i++) {
                colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
                fractions[i] = rand.nextFloat();
            }
            Arrays.sort(fractions);

            g2.setColor(Color.GRAY);// 设置边框色
            g2.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);

            Color c = getRandColor(200, 250);
            g2.setColor(c);// 设置背景色
            g2.fillRect(0, 2, IMG_WIDTH, IMG_HEIGHT - 4);

            //绘制干扰线
            Random random = new Random();
            g2.setColor(getRandColor(160, 200));// 设置线条的颜色
            for (int i = 0; i < 20; i++) {
                int x = random.nextInt(IMG_WIDTH - 1);
                int y = random.nextInt(IMG_HEIGHT - 1);
                int xl = random.nextInt(6) + 1;
                int yl = random.nextInt(12) + 1;
                g2.drawLine(x, y, x + xl + 40, y + yl + 20);
            }

            // 添加噪点
            float yawpRate = 0.05f;// 噪声率
            int area = (int) (yawpRate * IMG_WIDTH * IMG_HEIGHT);
            for (int i = 0; i < area; i++) {
                int x = random.nextInt(IMG_WIDTH);
                int y = random.nextInt(IMG_HEIGHT);
                int rgb = getRandomIntColor();
                image.setRGB(x, y, rgb);
            }

            shear(g2, IMG_WIDTH, IMG_HEIGHT, c);// 使图片扭曲

            g2.setColor(getRandColor(100, 160));
            int fontSize = IMG_HEIGHT - 4;
            Font font = new Font("Algerian", Font.ITALIC, fontSize);
            g2.setFont(font);
            char[] chars = code.toCharArray();
            for (int i = 0; i < verifySize; i++) {
                AffineTransform affine = new AffineTransform();
                affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (IMG_WIDTH / verifySize) * i + fontSize / 2, IMG_HEIGHT / 2);
                g2.setTransform(affine);
                g2.drawChars(chars, i, 1, ((IMG_WIDTH - 10) / verifySize) * i + 5, IMG_HEIGHT / 2 + fontSize / 2 - 10);
            }

            g2.dispose();
            ImageIO.write(image, "jpg", os);
        }
    }

    private static Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private static void shearX(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }
}
