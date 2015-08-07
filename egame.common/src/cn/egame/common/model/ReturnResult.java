package cn.egame.common.model;

import java.io.Serializable;

public class ReturnResult implements Serializable   {
	private int code;
	private String text="success";
	private Object ext;
	public int getCode() {
		return code;
	}
	public Object getExt() {
		return ext;
	}
	public String getText() {
		return text;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setExt(Object ext) {
		this.ext = ext;
	}
	public void setText(String text) {
		this.text = text;
	}
}
