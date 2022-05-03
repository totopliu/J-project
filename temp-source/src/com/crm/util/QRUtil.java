package com.crm.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;

public class QRUtil {

    private static final String CODE = "utf-8";

    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * 生成RQ二维码
     * 
     * @param str
     *            内容
     * @param height
     *            高度（px）
     */
    public static void getRQ(String str, Integer height, HttpServletResponse response) {
        if (height == null || height < 100) {
            height = 200;
        }
        try {
            // 文字编码
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, CODE);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, height, height, hints);
            // Code128Writer writer = new Code128Writer();
            // BitMatrix m = writer.encode(str, BarcodeFormat.QR_CODE, height,
            // height, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, "png", response.getOutputStream());
            // return toBufferedImage(bitMatrix);
            // 输出方式
            // 网页
            ImageIO.write(toBufferedImage(bitMatrix), "png", response.getOutputStream());
            // 文件
            // ImageIO.write(image, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成一维码（128）
     */
    public static void getBarcode(String str, Integer width, Integer height, File file) {
        if (width == null || width < 200) {
            width = 200;
        }
        if (height == null || height < 50) {
            height = 50;
        }
        try {
            // 文字编码
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, CODE);
            // BitMatrix bitMatrix = new MultiFormatWriter().encode(str,
            // BarcodeFormat.CODE_128, width, height, hints);
            Code128Writer writer = new Code128Writer();
            BitMatrix m = writer.encode(str, BarcodeFormat.CODE_128, width, height, hints);
            MatrixToImageWriter.writeToFile(m, "png", file);
            // ImageIO.write(toBufferedImage(bitMatrix), "png",
            // response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转换成图片
     * 
     * @param matrix
     * @return
     */
    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    /**
     * 解码(二维、一维均可)
     */
    public static String read(File file) {
        BufferedImage image;
        try {
            if (file == null || file.exists() == false) {
                throw new Exception(" File not found:" + file.getPath());
            }
            image = ImageIO.read(file);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result;
            // 解码设置编码方式为：utf-8，
            Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        long t1 = System.currentTimeMillis();
        File file = new File("D://条形码.png");
        // QRUtil.getBarcodeWriteFile("1020NS0DO02TV01EXCB", null, null, file);
        // File file1 = new File("D://二维码.png");
        // QRUtil.getRQWriteFile("1020NS0DO02TV01EXCB", 400, file1);
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);
        System.out.println("-----成生成功----");
        System.out.println();

        String s = QRUtil.read(file);

        System.out.println("-----解析成功----");
        System.out.println(s);
    }

}