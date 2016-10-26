package io.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import cn.egame.common.util.Utils;

public class CopyOfWaterMark {
	public static final String MARK_TEXT = "广告";
	public static final String FONT_NAME = "宋体";
	public static final int FONT_STYLE = Font.PLAIN;	//普通
	public static final int FONT_SIZE = 12;			//文字大小
	public static final Color FONT_COLOR = Color.white;//文字颜色
	
	public static final int X = 10;  //文字坐标
	public static final int Y = 10;
	
	public static float ALPHA = 1F; //文字水印透明度 
	
	public static final String LOGO = "logo.gif";	//图片形式的水印
	
	public static void main(String[] args) {
		File image = new File("E:\\pic\\push\\test\\1.jpg");
//		System.out.println(Utils.getFileName("E:\\pic\\push\\1.jpg"));
//		System.out.println(Utils.getFileExtName("E:\\pic\\push\\1.jpg"));
		new CopyOfWaterMark().watermark("E:\\pic\\push\\test\\1467096954468.png");
		
	}
	
	
	public String watermark(String picPath) {
		File image = new File(picPath);
		String logoFileName = "wm_"+Utils.getFileName(picPath); //定义目标文件输出的名称
		//获取文件所在的文件夹路径
		String picPathDir = picPath.substring(0, picPath.length()-Utils.getFileName(picPath).length());
		String targetFilePath = picPathDir+logoFileName;
		OutputStream os = null;
		
		try {
			//1 创建图片缓存对象
			Image image2 = ImageIO.read(image);	//解码原图
			int width = image2.getWidth(null);	//获取原图的宽度
			int height = image2.getHeight(null);//获取原图的高度
			BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);	//图像颜色的设置
			
			//2 创建Java绘图工具对象
			Graphics2D g = bufferedImage.createGraphics();
			
			// http://www.cnblogs.com/syxchina/archive/2011/06/21/2197207.html
			// ----------  增加下面的代码使得背景透明  -----------------
			bufferedImage = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
			g.dispose(); 
			g = bufferedImage.createGraphics(); 
			// ----------  背景透明代码结束  ----------------- 
			
			//3 使用绘图工具对象将原图绘制到缓存图片对象
			g.drawImage(image2, 0, 0, width, height, null);
			
			//4 使用绘图工具对象将水印（文字/图片）绘制到缓存图片
			g.setFont(new Font(FONT_NAME,FONT_STYLE,FONT_SIZE));
			g.setColor(FONT_COLOR);
			
			//获取文字水印的宽度和高度值
			int width1 = FONT_SIZE*getTextLength(MARK_TEXT);//文字水印宽度
			int height1= FONT_SIZE;							//文字水印高度
			
			//计算原图和文字水印的宽度和高度之差
			int widthDiff = width - width1;		//宽度之差
			int heightDiff= height - height1;	//高度之差
			
			int x = X;	//横坐标
			int y = Y;	//纵坐标
			
			//保证文字水印在右下方显示
			if(x > widthDiff){
				x = widthDiff;	
			}
			if(y > heightDiff){
				y = heightDiff;	
			}
			
			//水印透明度的设置
//			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
			//绘制文字
//			g.drawString(MARK_TEXT, x, y + FONT_SIZE);
			g.drawString(MARK_TEXT, width-width1-x, height-y);
			//释放工具
			g.dispose();
			
			//创建文件输出流，指向最终的目标文件
			os = new FileOutputStream(targetFilePath);	
			
//			//5 创建图像文件编码工具类
//			JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
//			
//			//6 使用图像编码工具类，输出缓存图像到目标文件
//			en.encode(bufferedImage);
			ImageIO.write(bufferedImage, "png", os);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(os!=null){
				try {
					os.close();	//关闭流
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return targetFilePath;
	}

	//文本长度的处理：文字水印的中英文字符的宽度转换
	public int getTextLength(String text){
		int length = text.length();
		for(int i=0;i<text.length();i++){
			String s = String.valueOf(text.charAt(i));
			if(s.getBytes().length>1){	//中文字符
				length++;
			}
		}
		length = length%2 == 0?length/2:length/2+1;  //中文和英文字符的转换
		return length;
	}
}
