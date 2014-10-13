package cnblogs.com;

/**
 * =====================================================================
 * 文件：ThreadDemo_02.java 描述：等待一个线程的结束
 * ======================================================================
 */
class ThreadDemo_02 extends Thread {
	Thread t;
	ThreadDemo_02() {
	}
	
	ThreadDemo_02(String szName, Thread t) {
		super(szName);
		this.t = t;
	}

	ThreadDemo_02(String szName) {
		super(szName);
	}

	// 重载run函数
	public void run() {
		try {
			if(t!=null){
				//等待t执行完毕
				t.join();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName());
		for (int count = 1, row = 1; row < 20; row++, count++) {
			for (int i = 0; i < count; i++) {
				System.out.print('*');
			}
			System.out.println();
		}
	}
}

class ThreadDemo_02_Test {
	public static void main(String argv[]) {
		// 产生两个同样的线程
		ThreadDemo_02 th1 = new ThreadDemo_02("th1");
		ThreadDemo_02 th2 = new ThreadDemo_02("th2", th1);

		// 我们的目的是先运行第一个线程，再运行第二个线程
		th1.start();
		
		th2.start();
	}
}