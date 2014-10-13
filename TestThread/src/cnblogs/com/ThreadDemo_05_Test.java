package cnblogs.com;

/**
 * =============================================================================
 * 文件：ThreadDemo_04.java 描述：多线程不同步的原因
 * =============================================================================
 */
// 共享一个静态数据对象
class ShareData05 {
	public static String szData = "";
}

class ThreadDemo_05 extends Thread {

	private ShareData05 oShare;

	ThreadDemo_05() {
	}

	ThreadDemo_05(String szName, ShareData05 oShare) {
		super(szName);
		this.oShare = oShare;
	}

	public void run() {
		// 为了更清楚地看到不正确的结果，这里放一个大的循环
		for (int i = 0; i < 50; i++) {
			if (this.getName().equals("Thread1")) {
				synchronized (oShare) {
					oShare.szData = "这是第 1 个线程";
				}
					// 为了演示产生的问题，这里设置一次睡眠
					try {
						Thread.sleep((int) Math.random() * 100);
					} catch (InterruptedException e) {
					}
					// 输出结果
					System.out.println(this.getName() + ":" + oShare.szData);
			} else if (this.getName().equals("Thread2")) {
				synchronized (oShare) {
					oShare.szData = "这是第 2个线程";
				}
					// 为了演示产生的问题，这里设置一次睡眠
					try {
						Thread.sleep((int) Math.random() * 100);
					} catch (InterruptedException e) {
					}
					// 输出结果
					System.out.println(this.getName() + ":" + oShare.szData);
			}
		}
	}
}

public class ThreadDemo_05_Test {
	public static void main(String argv[]) {
		 ShareData05 oShare = new ShareData05();
		 ThreadDemo_05 th1 = new ThreadDemo_05("Thread1",oShare);
		 ThreadDemo_05 th2 = new ThreadDemo_05("Thread2",oShare);
		
		 th1.start();
		 th2.start();
	}
}