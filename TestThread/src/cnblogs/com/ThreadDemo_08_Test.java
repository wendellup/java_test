package cnblogs.com;

/* ==================================================================================
 * 文件：ThreadDemo08.java
 * 描述：生产者－－消费者
 * 注：其中的一些注释是我根据自己的理解加注的
 * ==================================================================================
 */

class ShareData08 {

	private char c;
	// 通知变量
	private boolean writeable = true;

	// -------------------------------------------------------------------------
	// 需要注意的是：在调用wait()方法时，需要把它放到一个同步段里，否则将会出现
	// "java.lang.IllegalMonitorStateException: current thread not owner"的异常。
	// -------------------------------------------------------------------------
	public synchronized void setShareChar(char c) {
		if (!writeable) {
			try {
				// 未消费等待
				wait();
			} catch (InterruptedException e) {
			}
		}

		this.c = c;
		// 标记已经生产
		writeable = false;
		// 通知消费者已经生产，可以消费
		notify();
	}

	public synchronized char getShareChar() {
		if (writeable) {
			try {
				// 未生产等待
				wait();
			} catch (InterruptedException e) {
			}
		}
		// 标记已经消费
		writeable = true;
		// 通知需要生产
		notify();
		return this.c;
	}
}

// 生产者线程
class Producer08 extends Thread {

	private ShareData08 s;

	Producer08(ShareData08 s) {
		this.s = s;
	}

	public void run() {
		for (char ch = 'A'; ch <= 'Z'; ch++) {
			try {
				Thread.sleep((int) Math.random() * 400);
			} catch (InterruptedException e) {
			}

			s.setShareChar(ch);
			System.out.println(ch + " producer by producer.");
		}
	}
}

// 消费者线程
class Consumer08 extends Thread {

	private ShareData08 s;

	Consumer08(ShareData08 s) {
		this.s = s;
	}

	public void run() {
		char ch;

		do {
			try {
				Thread.sleep((int) Math.random() * 400);
			} catch (InterruptedException e) {
			}

			ch = s.getShareChar();
			System.out.println(ch + " consumer by consumer.**");
		} while (ch != 'Z');
	}
}

class ThreadDemo_08_Test {
	public static void main(String argv[]) {
		ShareData08 s = new ShareData08();
		new Consumer08(s).start();
		new Producer08(s).start();
	}
}