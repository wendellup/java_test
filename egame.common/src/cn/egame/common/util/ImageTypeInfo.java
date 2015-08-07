package cn.egame.common.util;

import java.awt.image.BufferedImage;

public class ImageTypeInfo {
    private String imageType="jpg";
    private int imageTypeRGB=BufferedImage.TYPE_INT_RGB;

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public int getImageTypeRGB() {
        return imageTypeRGB;
    }

    public void setImageTypeRGB(int imageTypeRGB) {
        this.imageTypeRGB = imageTypeRGB;
    }
}
