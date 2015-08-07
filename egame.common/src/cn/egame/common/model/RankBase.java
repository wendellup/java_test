package cn.egame.common.model;

public class RankBase<T1, T2, T3> {
	public T1 getId() {
		return id;
	}

	public void setId(T1 id) {
		this.id = id;
	}

	public T2 getSort() {
		return sort;
	}

	public void setSort(T2 sort) {
		this.sort = sort;
	}

	public T3 getObj() {
		return obj;
	}

	public void setObj(T3 obj) {
		this.obj = obj;
	}

	T1 id;
	T2 sort;
	T3 obj;
}