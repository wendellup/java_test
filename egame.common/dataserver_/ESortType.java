package cn.egame.common.dataserver;


public enum ESortType {
	ASE("ase",1),
	DESC("desc",0);
	private String name;
	private int value;
	ESortType(String name,int value){
		this.name = name;
		this.value = value;
	}
}
