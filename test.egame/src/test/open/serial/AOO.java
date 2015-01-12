package test.open.serial;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

public class AOO 
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String aooStr;

	public String getAooStr() {
		
		return aooStr;
//		return (String) this.get(0);
		
	}

	public void setAooStr(String aooStr) {
		this.aooStr = aooStr;
	}

	@Override
	public String toString() {
		return "AOO [aooStr=" + aooStr + "]";
	}
	
	public void fun1(){
		System.out.println("fun1 in Aoo");
	}
	
	private void writeObject(ObjectOutputStream s) throws IOException {
		// Write out the threshold, loadfactor, and any hidden stuff
		System.out.println("AOO writeObject begin...");
		this.aooStr = aooStr+"xxxx";
		s.defaultWriteObject();
		System.out.println("AOO writeObject end...");
		
	}
	
}
