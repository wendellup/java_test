package cnblogs.com;

/* ==================================================================================
 * 文件：ThreadDemo07.java
 * 描述：生产者－－消费者
 * 注：其中的一些注释是我根据自己的理解加注的
 * ==================================================================================
 */

// 共享的数据对象
class ShareData03kx {
	private char c;

	public void setShareChar(char c) {
		this.c = c;
	}

	public char getShareChar() {
		return this.c;
	}
}

// 生产者线程
class Producerx extends Thread {

	private ShareData03kx s;

	Producerx(ShareData03kx s) {
		this.s = s;
	}

	public void run() {
		for (char ch = 'A'; ch <= 'Z'; ch++) {
			try {
				Thread.sleep((int) Math.random() * 4000);
			} catch (InterruptedException e) {
			}

			// 生产
			s.setShareChar(ch);
			System.out.println(ch + " producer by producer.");
		}
	}
}

// 消费者线程
class Consumerx extends Thread {

	private ShareData03kx s;

	Consumerx(ShareData03kx s) {
		this.s = s;
	}

	public void run() {
		char ch;

		do {
			try {
				Thread.sleep((int) Math.random() * 4000);
//				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			// 消费
			ch = s.getShareChar();
			System.out.println(ch + " consumer by consumer.");
		} while (ch != 'Z');
	}
}

class CopyOfThreadDemo_07_Test {
	public static void main(String argv[]) {
		ShareData03k s = new ShareData03k();
		new Consumer(s).start();
		new Producer(s).start();
	}
}