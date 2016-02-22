package app;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimpleSwingHelloWorld {

	/**
	 * 
	 * 
	 * @Title: main
	 * 
	 * @Description: 程序的入口
	 * 
	 * @param @param args 设定文件
	 * 
	 * @return void 返回类型
	 * 
	 * @throws
	 * 
	 * @author MrJing
	 * 
	 * @date 2012-8-2 下午11:57:36
	 */
	public static void main(String[] args) {
		JFrame myFrame = new mySimpleSwing();
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setVisible(true);

	}
}

/**
 * 
 * 
 * @ClassName: mySimpleSwing
 * 
 * @Description: 界面类
 * 
 * @author MrJing
 * 
 * @date 2012-8-2 下午11:58:20
 * 
 * 
 */
class mySimpleSwing extends JFrame {
	public mySimpleSwing() {
		setTitle("窗口标题");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		add(new myPanel());
	}

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 300;
}

class myPanel extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("hello world!", 100, 150);
	}
}
