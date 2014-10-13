package cnblogs.com;

/**
 * =====================================================================
 * 文件：ThreadDemo_03.java 描述：等待一个线程的结束的两种方法
 * ======================================================================
 */
class ThreadDemo_03 extends Thread {
	ThreadDemo_03() {
	}

	ThreadDemo_03(String szName) {
		super(szName);
	}

	// 重载run函数
	public void run() {
		for (int count = 1, row = 1; row < 10; row++, count++) {
			for (int i = 0; i < count; i++) {
				System.out.print('*');
			}
			System.out.println();
		}
	}
}

class ThreadDemo_03_Test {
	public static void main(String argv[]) {
		ThreadDemo_03_Test test = new ThreadDemo_03_Test();
		test.Method1();
		// test.Method2();
	}

	// 第一种方法：不断查询第一个线程是否已经终止，如果没有，则让主线程睡眠一直到它终止为止
	// 即：while/isAlive/sleep
	public void Method1() {
		ThreadDemo_03 th1 = new ThreadDemo_03();
		ThreadDemo_03 th2 = new ThreadDemo_03();
		// 执行第一个线程
		th1.start();
		// 不断查询第一个线程的状态
		while (th1.isAlive()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		// 第一个线程终止，运行第二个线程
		th2.start();
	}

	// 第二种方法：join()
	public void Method2() {
		ThreadDemo_03 th1 = new ThreadDemo_03();
		ThreadDemo_03 th2 = new ThreadDemo_03();
		// 执行第一个线程
		th1.start();
		try {
			th1.join();
		} catch (InterruptedException e) {
		}
		// 执行第二个线程
		th2.start();
	}
}