package test.open.serial;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ResultContentExt extends HashMap<String, Object> implements
		Serializable {
	private static final long serialVersionUID = 628377769976336650L;

	public ResultContentExt() {
	}

	private int testInt;

	public int getTestInt() {
		return testInt;
	}

	public void setTestInt(int testInt) {
		this.put("testInt", testInt);
//		this.testInt = testInt;
	}

	public List<String> getRef_hot_status() {
		return (List<String>) this.get("ref_hot_status");
	}


	@Override
	public String toString() {
		return "ResultContentExt [ref_hot_status=" + this.getRef_hot_status()
				+ "]" + "ref_new_status=" + this.getRef_new_status()
				+ this.getTestInt();

	}

	public void setRef_hot_status(List<String> ref_hot_status) {
		if (ref_hot_status != null) {
			this.put("ref_hot_status", ref_hot_status);
		} else {
			this.put("ref_hot_status", new LinkedList<Map<String, Integer>>());
		}
	}

	public void setRef_new_status(List ref_new_status) {
		if (ref_new_status != null) {
			this.put("ref_new_status", ref_new_status);
		} else {
			this.put("ref_new_status", new LinkedList<Map<String, Integer>>());
		}
	}

	public List<String> getRef_new_status() {
		return (List<String>) this.get("ref_new_status");
	}

	public void writeObject(ObjectOutputStream s) throws IOException {
		System.out.println("ResultContentExt begin...");

		s.defaultWriteObject();
		System.out.println("ResultContentExt end");
		
	}
}
