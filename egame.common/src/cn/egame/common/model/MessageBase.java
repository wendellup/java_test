package cn.egame.common.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import cn.egame.common.util.Utils;

public class MessageBase implements java.io.Serializable, Cloneable {
	private HashMap<String, Object> val = new HashMap<String, Object>();

	public Object get(String key) {
		return val.get(key);
	}

	public void set(String key, Object v) {
		val.put(key, v);
	}

	public int getInt(String key) {
		return Utils.toInt(get(key), -1);
	}

	public long getLong(String key) {
		return Utils.toLong(get(key), -1);
	}

	public String getString(String key) {
		if (val.containsKey(key))
			return (String) get(key);
		return null;
	}

	@Override
	public String toString() {
		Set<String> keys = val.keySet();
		StringBuilder sb = new StringBuilder();
		for (String key : keys) {
			Object v = val.get(key);
			sb.append("$"
					+ key
					+ "="
					+ String.valueOf(v).replace(" ", "").replace("$", "")
							.replace("\t", "").replace("\r", "")
							.replace("\n", ""));
		}

		if (sb.toString().length() > 0)
			return "{" + sb.substring(1) + "}";
		else
			return "{}";

	}

	public Object clone() {
		return Utils.clone(this);
	}
}
