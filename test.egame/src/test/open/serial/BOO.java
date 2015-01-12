package test.open.serial;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BOO extends AOO implements Serializable {
	private static final long serialVersionUID = -1833525307672154518L;
	private String booStr;

	public String getBooStr() {
		return booStr;
	}

	public void setBooStr(String booStr) {
		this.booStr = booStr;
	}

	@Override
	public String toString() {
		return "BOO [booStr=" + booStr + ", "+super.getAooStr()+"]";
	}


	
	private void writeObject(ObjectOutputStream s) throws IOException {
		System.out.println("BOO writeObject begin...");
		this.booStr = booStr + "!!!";
		s.defaultWriteObject();
		System.out.println("BOO writeObject end ...");
	}
	
	public void fun1(){
		super.fun1();
		System.out.println("fun1 in BOO");;
	}
}
