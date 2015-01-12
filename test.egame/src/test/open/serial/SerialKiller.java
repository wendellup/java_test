package test.open.serial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

class Super implements Serializable {
	// HashSet要放置在父类中会百分百机率出现
	// 放置到子类中就不一定会出现问题了
	final Set set = new HashSet();
}

class Sub extends Super {
	private int id;

	public Sub(int id) {
		this.id = id;
		set.add(this);
	}

	public int hashCode() {
		return id;
	}

	public boolean equals(Object o) {
		return (o instanceof Sub) && (id == ((Sub) o).id);
	}
}

public class SerialKiller {
	public static void main(String[] args) throws Exception {
		Sub sb = new Sub(888);
		System.out.println(sb.set.contains(sb));// true
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		new ObjectOutputStream(bos).writeObject(sb);
		ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
		sb = (Sub) new ObjectInputStream(bin).readObject();
		System.out.println(sb.set.contains(sb));// false
	}
}