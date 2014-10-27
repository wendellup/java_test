package proandcon_生产者消费者;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class NameList {
//	private List nameList = Collections.synchronizedList(new LinkedList());
	private List nameList = new LinkedList();

	public synchronized void add(String name) {
		nameList.add(name);
	}

	public synchronized String removeFirst() {
		if (nameList.size() > 0) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return (String) nameList.remove(0);
		} else {
			return null;
		}
	}
}

public class Test {
	public static void main(String[] args) {
		final NameList nl = new NameList();
		nl.add("aaa");
		nl.add("bbb");
		class NameDropper extends Thread {
			public void run() {
				String name = nl.removeFirst();
				System.out.println(name);
			}
		}

		Thread t1 = new NameDropper();
		Thread t2 = new NameDropper();
		t1.start();
		t2.start();
	}
}