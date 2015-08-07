package cn.egame.common.operation;

public class ResultHealth {
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	int code = 0;
	String result = "";
	String name = "";

	public String toXml() {
		// TODO :gaolang 解决字符的问题 
		return "<status name=\"" + name + "\" code=\"" + code + "\">" + result
				+ "</status>";
	}
}
