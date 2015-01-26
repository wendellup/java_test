package thread.lock2;

public class LockTestClass {

	// 用于类锁计数

//	private static int i = 0;

	// 私有锁

	private Object object = new Object();
	

	/**
	 * 
	 * 
	 * 
	 * 无锁方法
	 * 
	 * 
	 * 
	 * @param threadID
	 * 
	 * @param thread
	 */

	/**
	 * 
	 * 对象锁方法1
	 */

	public synchronized void synOnMethod(int i) {

		System.out.println("synOnMethod begins. i = " + i + ", time = "

		+ System.currentTimeMillis() + "ms");

		try {

			Thread.sleep(2000L);

		} catch (InterruptedException e) {

			e.printStackTrace();

		}

		System.out.println("synOnMethod ends. i = " + i);

	}

	/**
	 * 
	 * 对象锁方法2,采用synchronized (this)来加锁
	 */

	public void synInMethod(int i) {

		synchronized (this) {

			System.out.println("synInMethod begins. i = " + i + ", time = "

			+ System.currentTimeMillis() + "ms");

			try {

				Thread.sleep(1000L);

			} catch (InterruptedException e) {

				e.printStackTrace();

			}

			System.out.println("synInMethod ends. i = " + i);

		}
	}

	/**
	 * 
	 * 对象锁方法3
	 */

	public void synMethodWithObj(int i) {

		synchronized (object) {

			System.out.println("synMethodWithObj begins. i = " + i + ", time = "

			+ System.currentTimeMillis() + "ms");

			try {

				Thread.sleep(1000L);

			} catch (InterruptedException e) {

				e.printStackTrace();

			}

			System.out.println("synMethodWithObj ends. i = " + i);

		}

	}

	/**
	 * 
	 * 类锁
	 */
	public static synchronized void increament(int i) {

		System.out.println("class synchronized. i = " + i + ", time = "
		+ System.currentTimeMillis() + "ms");
//		i++;
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		System.out.println("class synchronized ends. i = " + i);

	}
	
	/**
	 * 
	 * 类锁2
	 */
	public synchronized void increament2(int i) {
		synchronized (LockTestClass.class) {
			System.out.println("class synchronized2. i = " + i + ", time = "
					+ System.currentTimeMillis() + "ms");
			try {
				Thread.sleep(10L);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			System.out.println("class synchronized2 ends. i = " + i);
		}
	}
}
