package cnblogs.com;

/* ==================================================================================
 * 文件：ThreadDemo07.java
 * 描述：生产者－－消费者
 * 注：其中的一些注释是我根据自己的理解加注的
 * ==================================================================================
 */

// 共享的数据对象
class ShareData03k {
	private char c;

	//表示可写入的标示
	private boolean writable = true;
	
	public synchronized void setShareChar(char c) {
		if(!writable){
			try {
				//等待消费者线程消费
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.c = c;
		writable = false;
		notifyAll();
	}

	public synchronized char getShareChar() {
		if(writable){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		writable = true;
		notifyAll();
		return this.c;
	}
}

// 生产者线程
class Producer extends Thread {

	private ShareData03k s;

	Producer(ShareData03k s) {
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
class Consumer extends Thread {

	private ShareData03k s;

	Consumer(ShareData03k s) {
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

class ThreadDemo_07_Test {
	public static void main(String argv[]) {
		ShareData03k s = new ShareData03k();
		Consumer c = new Consumer(s);
		Producer p = new Producer(s);
		Producer p2 = new Producer(s);
		c.start();
		p.start();
		p2.start();
//		try {
//			c.join();
//			p.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
}