package cn.egame.common.dataserver;

import java.io.Serializable;

import org.apache.log4j.Logger;

public abstract class BaseDBObj implements IDBObj, Serializable {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(BaseDBObj.class);

	private String _id; // mongodb需要此主键

	@Override
	public String getTableName() {
		return this.getClass().getSimpleName().toLowerCase();
	}

	@Override
	public String getCacheKey() {
		String[] uninIdArr = this.getUninId();

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < uninIdArr.length; i += 2) {
			buf.append("_");
			buf.append(uninIdArr[i + 1]);
		}
		return this.getTableName() + buf.toString();
	}

	public String getUninKey() {
		String[] uninIdArr = this.getUninId();

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < uninIdArr.length; i += 2) {
			if (i != 0) {
				buf.append("_");
			}
			buf.append(uninIdArr[i + 1]);
		}
		return buf.toString();
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
	
	public abstract String[] getUninId();

	@Override
	public int getDBIndex() {
		return 0;
	}
}
