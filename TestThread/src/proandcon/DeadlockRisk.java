package proandcon;

class Resource {
	public Resource(int value) {
		this.value = value;
	}
	public int value;
}
public class DeadlockRisk {
	

	private Resource resourceA;
	private Resource resourceB;
	
	DeadlockRisk(Resource resourceA, Resource resourceB){
		this.resourceA = resourceA;
		this.resourceB = resourceB;
	}

	public int read() {
		synchronized (resourceA) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (resourceB) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(resourceB.value + resourceA.value);
				return resourceB.value + resourceA.value;
			}
		}
	}

	public void write(int a, int b) {
		synchronized (resourceB) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			synchronized (resourceA) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(resourceA.value);
				System.out.println(resourceB.value);
				resourceA.value = a;
				resourceB.value = b;
//			}
		}
	}
	
	public static void main(String[] args) {
		final Resource resourceA = new Resource(10);
		final Resource resourceB = new Resource(20);
		
		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				new DeadlockRisk(resourceA,resourceB).read();
			}
		};
		
		Runnable r2 = new Runnable() {
			
			@Override
			public void run() {
				new DeadlockRisk(resourceA,resourceB).write(1, 2);
			}
		};
		
		new Thread(r1).start();
		new Thread(r2).start();
	}
}