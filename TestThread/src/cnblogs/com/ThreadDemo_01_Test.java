package cnblogs.com;

/**
 * =====================================================================
 * 文件：ThreadDemo_01.java 描述：产生一个新的线程
 * ======================================================================
 */
class ThreadDemo_01 extends Thread {
	ThreadDemo_01() {
	}

	ThreadDemo_01(String szName) {
		super(szName);
	}

	// 重载run函数
	public void run() {
		for (int count = 1; count < 5; count++) {
			for (int i = 0; i < count; i++) {
				try {
					sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.print('*');
			}
			System.out.println();
		}
	}
}

class ThreadDemo_01_Test {
	public static void main(String argv[]) {
		System.out.println("main begin");
		ThreadDemo_01 th = new ThreadDemo_01();
		// 调用start()方法执行一个新的线程
		th.start();
		try {
			System.out.println("th is daemon : "+th.isDaemon());
			System.out.println("main is daemon : "+Thread.currentThread().isDaemon());
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main end");
	}
}