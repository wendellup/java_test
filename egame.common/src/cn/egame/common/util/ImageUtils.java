package cn.egame.common.util;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.log4j.Logger;

import cn.egame.common.exception.ErrorCodeBase;
import cn.egame.common.exception.ExceptionCommonBase;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtils {
    private static Logger logger = Logger.getLogger(ImageUtils.class);

    public static final MediaTracker tracker = new MediaTracker(new Component() {
        private static final long serialVersionUID = 1234162663955668507L;
    });

    public static void toFile(BufferedImage bufferedImage, String filename,ImageTypeInfo imageTypeInfo) throws java.io.IOException {
        OutputStream outputStream = new FileOutputStream(new File(filename));
        try {
            ImageIO.write(bufferedImage, imageTypeInfo.getImageType(), outputStream);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(bufferedImage);
            jep.setQuality(0.4f, true);
            encoder.encode(bufferedImage, jep);

        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    public static BufferedImage scaled(Image inputImage, int width, int height, int zoomMaxSourcePixels, boolean fixRate, ImageTypeInfo imageTypeInfo)
            throws ExceptionCommonBase {
        return make(inputImage, width, height, zoomMaxSourcePixels, fixRate, false, imageTypeInfo);
    }

    private static BufferedImage make(Image inputImage, int width, int height, int zoomMaxSourcePixels, boolean fixRate, boolean thumbnail,
            ImageTypeInfo imageTypeInfo) throws ExceptionCommonBase {
        waitForImage(inputImage);
        int imageWidth = inputImage.getWidth(null);
        if (imageWidth < 1)
            throw new ExceptionCommonBase(ErrorCodeBase.ParameterError, "image's width");

        int imageHeight = inputImage.getHeight(null);
        if (imageHeight < 1)
            throw new ExceptionCommonBase(ErrorCodeBase.ParameterError, "image's height");

        int convasWidth = width;
        int convasHeight = height;
        double scaleW = (double) imageWidth / (double) width;
        double scaleY = (double) imageHeight / (double) height;

        if (zoomMaxSourcePixels > 0 && (imageWidth < zoomMaxSourcePixels || imageHeight < zoomMaxSourcePixels)) {
            // no zoom
            if (scaleW < scaleY) {
                height = (int) ((double) height * (double) imageWidth / (double) width);
                width = imageWidth;
            } else {
                width = (int) ((double) width * (double) imageHeight / (double) height);
                height = imageHeight;
            }
            convasWidth = width;
            convasHeight = height;
        }
        if (fixRate || thumbnail) {
            scaleW = (double) imageWidth / (double) width;
            scaleY = (double) imageHeight / (double) height;
            if (scaleW >= 0 && scaleY >= 0) {
                if (thumbnail) {
                    if (scaleW < scaleY) {
                        height = (int) ((double) imageHeight / scaleW);
                    } else {
                        width = (int) ((double) imageWidth / scaleY);
                    }
                } else {
                    if (scaleW > scaleY) {
                        height = (int) ((double) imageHeight / scaleW);
                    } else {
                        width = (int) ((double) imageWidth / scaleY);
                    }
                }
            }
        }
        
        Image outputImage = inputImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = null;
        if (thumbnail) {
            bufferedImage = new BufferedImage(convasWidth, convasHeight, imageTypeInfo.getImageTypeRGB());

            if (convasWidth != width)
                bufferedImage.getGraphics().drawImage(outputImage, (convasWidth - width) / 2, 0, null);
            else
                bufferedImage.getGraphics().drawImage(outputImage, 0, (convasHeight - height) / 2, null);
        } else {
            bufferedImage = new BufferedImage(width, height, imageTypeInfo.getImageTypeRGB());
            bufferedImage.getGraphics().drawImage(outputImage, 0, 0, null);
        }
        return bufferedImage;
    }

    public static void thumbnail(String source, String dest, int width, int height, int zoomMaxSourcePixels) throws IOException {
        Image inputImage = ImageIO.read(new File(source));
        ImageTypeInfo imageTypeInfo =getImageTypeInfo(source);
        BufferedImage bufferedImage = make(inputImage, width, height, zoomMaxSourcePixels, true, true, imageTypeInfo);
        toFile(bufferedImage, dest,imageTypeInfo);
    }

    public static InputStream thumbnailToEfs(String source, int width, int height, int zoomMaxSourcePixels) throws IOException {
        Image inputImage = ImageIO.read(new File(source));
        if(inputImage.getWidth(null) <= width && inputImage.getHeight(null) <= height){
            return new FileInputStream(new File(source));
        }
        ImageTypeInfo imageTypeInfo =getImageTypeInfo(source);
        BufferedImage bufferedImage = make(inputImage, width, height, zoomMaxSourcePixels, true, true, imageTypeInfo);
        return getImageStream(bufferedImage, imageTypeInfo);
    }

    public static void thumbnail(String source, String dest, int width, int zoomMaxSourcePixels) throws IOException {
        thumbnail(source, dest, width, width, zoomMaxSourcePixels);
    }

    public static void scaled(String source, String dest, int width, int height, int zoomMaxSourcePixels) throws IOException {
        // byte[] bytes = Utils.fileToBytes(source);
        Image inputImage = ImageIO.read(new File(source));
        ImageTypeInfo imageTypeInfo =getImageTypeInfo(source);
        BufferedImage bufferedImage = scaled(inputImage, width, height, zoomMaxSourcePixels, true, imageTypeInfo);
        toFile(bufferedImage, dest,imageTypeInfo);
    }

    public static InputStream scaledToEfs(String source, int width, int height, int zoomMaxSourcePixels) throws IOException {
        // byte[] bytes = Utils.fileToBytes(source);
        Image inputImage = ImageIO.read(new File(source));
        if(inputImage.getWidth(null) <= width && inputImage.getHeight(null) <= height){
            return new FileInputStream(new File(source));
        }
        ImageTypeInfo imageTypeInfo =getImageTypeInfo(source);
        BufferedImage bufferedImage = scaled(inputImage, width, height, zoomMaxSourcePixels, true, imageTypeInfo);
        return getImageStream(bufferedImage, imageTypeInfo);
    }

    public static InputStream getImageStream(BufferedImage bi, ImageTypeInfo imageTypeInfo) {
        InputStream is = null;
        bi.flush();
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imOut;
        try {
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bi, imageTypeInfo.getImageType(), imOut);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(bi);
            jep.setQuality(0.4f, true);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bs, jep);
            encoder.encode(bi);
        } catch (Exception ef) {
            logger.error("", ef);
        }
        is = new ByteArrayInputStream(bs.toByteArray());
        return is;
    }

    private static void waitForImage(Image image) {
        try {
            tracker.addImage(image, 0);
            tracker.waitForID(0);
            tracker.removeImage(image, 0);
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }

    public static String getFormatInFile(File f) {
        return getFormatName(f);
    }

    public static String getFormatName(Object o) {
        ImageInputStream iis = null;
        try {
            iis = ImageIO.createImageInputStream(o);
            if (iis == null) {
                return null;
            }
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                return null;
            }
            ImageReader reader = iter.next();

            return reader.getFormatName();
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            try {
                if (iis != null){
                    iis.close();
                    
                }
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        return null;
    }

    private static ImageTypeInfo getImageTypeInfo(String source) {
        ImageTypeInfo info = new ImageTypeInfo();
        if ("png".equalsIgnoreCase(ImageUtils.getFormatInFile(new File(source)))) {
            info.setImageType("png");
            info.setImageTypeRGB(BufferedImage.TYPE_INT_ARGB);
        }
        return info;
    }
}
