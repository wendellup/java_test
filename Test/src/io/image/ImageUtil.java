package io.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {

//	public static void main(String[] args) {
//		File file = new File("E:\\pic\\p1854890895.jpg");
//		changeImge(file);
//	}

	/**
	 * * 转换图片 * *
	 */
	public static void changeImge(File img) {
		try {
			Image image = ImageIO.read(img);
			int srcH = image.getHeight(null);
			int srcW = image.getWidth(null);
			BufferedImage bufferedImage = new BufferedImage(srcW, srcH,
					BufferedImage.TYPE_3BYTE_BGR);
			bufferedImage.getGraphics()
					.drawImage(image, 0, 0, srcW, srcH, null);
			bufferedImage = new ColorConvertOp(
					ColorSpace.getInstance(ColorSpace.CS_GRAY), null).filter(
					bufferedImage, null);
			FileOutputStream fos = new FileOutputStream(img);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			encoder.encode(bufferedImage);
			fos.close();
			// System.out.println("转换成功...");
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("图片转换出错！", e);
		}
	}

	public final static void pressImage(String pressImg, String targetImg,
			int x, int y) {
		try {
			// 目标文件
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);

			// 水印文件
			File _filebiao = new File(pressImg);
			Image src_biao = ImageIO.read(_filebiao);
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
//			g.drawImage(src_biao, (wideth - wideth_biao) / 2,
//					(height - height_biao) / 2, wideth_biao, height_biao, null);
			g.drawImage(src_biao, x,
					y, wideth_biao, height_biao, null);
			// 水印文件结束
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印文字水印图片
	 * 
	 * @param pressText
	 *            --文字
	 * @param targetImg
	 *            -- 目标图片
	 * @param fontName
	 *            -- 字体名
	 * @param fontStyle
	 *            -- 字体样式
	 * @param color
	 *            -- 字体颜色
	 * @param fontSize
	 *            -- 字体大小
	 * @param x
	 *            -- 偏移量
	 * @param y
	 */

	public static void pressText(String pressText, String targetImg,
			String fontName, int fontStyle, int color, int fontSize, int x,
			int y) {
		try {
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);

			g.setColor(Color.WHITE);
			g.setFont(new Font(fontName, fontStyle, fontSize));
//			g.setFont(new Font("Courier", ,SIZE_MEDIUM));

//			g.drawString(pressText, wideth - fontSize - x, height - fontSize
//					/ 2 - y);
			g.drawString(pressText, x, y);
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) {
//		pressText("广告", "C:\\Users\\yuchao\\Desktop\\1472723412247.jpg", "PLAIN",0, 0,6,  0, 0);
		pressImage("E:\\pic\\1.png",          "E:\\pic\\9f8c5e89jw1ecwmmv69mhj20pz0hs75l.jpg", 0, 0);
    }

}
