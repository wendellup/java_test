package cn.egame.common.util;

import java.util.LinkedHashMap;

public class CacheKeyUtil {
	/**
	 * @param className
	 * @param ids  idValue,idValue
	 * @return
	 */
	public static String getCacheIdKey(String className, String... ids) {
		if (ids == null) {
			throw new RuntimeException(
					"getCacheIdKey - >  args ids can't be null");
		}
		StringBuffer buf = new StringBuffer();
		buf.append(className);
		for (int i = 0; i < ids.length; i++) {
			buf.append("_").append(ids[i]);
		}
		return buf.toString();
	}
	
	/**
	 * @param className
	 * @param columValues columnName,value,columnName,value
	 * @return
	 */
	public static String getConditionKey(String className,String... columValues){
		if (columValues == null) {
			throw new RuntimeException(
					"getCacheIdKey - >  args ids can't be null");
		}
		StringBuffer buf = new StringBuffer();
		buf.append(className);
		for (int i = 0; i < columValues.length; i++) {
			buf.append("_").append(columValues[i]);
		}
		return buf.toString();
	}
}
