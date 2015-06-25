package test.com.entity;

import java.io.Serializable;

public class AOO implements Serializable{
	
	private static final long serialVersionUID = 5016226658286034271L;
	
	private int id;
	private String name;
	private String desc;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public AOO(int id, String name, String desc) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
	}
	
	
}
