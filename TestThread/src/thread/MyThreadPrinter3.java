package thread;

public class MyThreadPrinter3 implements Runnable {

	private String name;
	private Object prev;
	private Object self;

	private MyThreadPrinter3(String name, Object prev, Object self) {
		this.name = name;
		this.prev = prev;
		this.self = self;
	}

	@Override
	public void run() {
		int count = 10;
		while (count > 0) {
			synchronized (prev) {
				synchronized (self) {
					System.out.print(name);
					count--;
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					self.notify();
				}
				try {
					prev.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static void main(String[] args) throws Exception {
		Object a = new Object();
		Object b = new Object();
		Object c = new Object();
		MyThreadPrinter3 pa = new MyThreadPrinter3("A", c, a);
		MyThreadPrinter3 pb = new MyThreadPrinter3("B", a, b);
		MyThreadPrinter3 pc = new MyThreadPrinter3("C", b, c);

		new Thread(pa).start();
		Thread.sleep(10);
		new Thread(pb).start();
		Thread.sleep(10);
		new Thread(pc).start();
		Thread.sleep(10);
	}
}
