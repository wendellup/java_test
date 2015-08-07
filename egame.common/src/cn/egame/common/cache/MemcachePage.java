package cn.egame.common.cache;

import java.util.ArrayList;
import java.util.List;

public class MemcachePage implements java.io.Serializable {
	public MemcachePage() {
		keys = new ArrayList<String>();
	}

	public MemcachePage(byte[] buffer) {
		this.buffer = buffer;
	}

	private static final long serialVersionUID = 1L;
	List<String> keys = null;

	private byte[] buffer = null;

	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}
}
