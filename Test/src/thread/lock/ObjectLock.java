package thread.lock;

public class ObjectLock {

	public static void main(String[] args) {

//		System.out.println("start time = " + System.currentTimeMillis() + "ms");


		for (int i = 0; i < 2; i++) {
			LockTestClass test = new LockTestClass();

			Thread thread = new ObjThread(test, i);

			thread.start();

		}
		

	}

}
