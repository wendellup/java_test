package thread.lock2;

public class ObjThread extends Thread {

	LockTestClass lock;

	int i = 0;

	public ObjThread(LockTestClass lock, int i) {

		this.lock = lock;

		this.i = i;

	}

	public void run() {

		// 无锁方法

		// lock.noSynMethod(this.getId(),this);

		// 类锁方法2，采用static synchronized increment的方式
		lock.increament2(i);
		
		// 类锁方法，采用static synchronized increment的方式
		LockTestClass.increament(i);
		
		
		
		
		// 对象锁方法1，采用synchronized(this)的方式
//		lock.synInMethod(i);

		// 对象锁方法2，采用synchronized synOnMethod的方式

//		 lock.synOnMethod(i);

		// 私有锁方法，采用synchronized(object)的方式

//		 lock.synMethodWithObj(i);


	}
	
	public static void main(String[] args) {
		LockTestClass lock1 = new LockTestClass();
		System.out.println(lock1);
		LockTestClass lock2 = new LockTestClass();
		System.out.println(lock2);
		new ObjThread(lock1, 1).start();
		new ObjThread(lock2, 2).start();
	}

}
