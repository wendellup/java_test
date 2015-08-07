package cn.egame.common.dataserver;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

import cn.egame.common.dataserver.ann.DB_FIELD;

import com.mongodb.BasicDBObject;

public class CacheKeyUtil {
	private static Logger logger = Logger.getLogger(CacheKeyUtil.class);
	private static String SPLITE_KEY = "_";

	/**
	 * className_ID1Value_ID1Value
	 * 
	 * @param dbObj
	 * @return
	 */
	public static String getUninIdKey(BaseDBObj dbObj) {
		String className = dbObj.getClass().getSimpleName();
		StringBuffer buf = new StringBuffer();
		buf.append(className).append(SPLITE_KEY);
		String[] uninId = dbObj.getUninId();
		for (int i = 0; i < uninId.length; i += 2) {
			if (i != 0) {
				buf.append("_");
			}
			buf.append(uninId[i + 1]);
		}
		return buf.toString();
	}

	/**
	 * javaBean属性中不能有"_"做为属性字段
	 * 
	 * className_columnName_value_columnName_value
	 * 
	 * @param dbObj
	 * @return
	 */
	public static String getConditionKey(BaseDBObj dbObj) {
		try {
			String className = dbObj.getClass().getSimpleName();
			StringBuffer buf = new StringBuffer();
			buf.append(className).append("_");

			Field[] fields = dbObj.getClass().getDeclaredFields();
			BasicDBObject baseObj = new BasicDBObject();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];

				field.setAccessible(true);
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				DB_FIELD ann = field.getAnnotation(DB_FIELD.class);

				if (ann != null && !ann.isDBField()) {
					continue;
				}
				// String name = field.getName();
				Object value = field.get(dbObj);
				String name = field.getName();

				String dbField = ann.fieldName();

				if (dbField == null) {
					throw new RuntimeException("db field name is null");
				}

				if (value != null) {
					if (value instanceof Integer) {
						if ((Integer) value != 0)
							buf.append(dbField).append("_").append(value);
					} else if (value instanceof Long) {
						if ((Long) value != 0)
							buf.append(dbField).append("_").append(value);
					} else if (value instanceof Float) {
						if ((Float) value != 0)
							buf.append(dbField).append("_").append(value);
					} else if (value instanceof Double) {
						if ((Double) value != 0)
							buf.append(dbField).append("_").append(value);
					} else if (value instanceof Boolean) {
						// 暂时不处理

					} else if (value instanceof byte[]) {
						// 不作为查询条件

					} else {
						// Object 不作为查询条件

					}
				} else {
					// 字符串类型赋值--字符串类型不是原始类型
					if ((String) value != null)
						buf.append(dbField).append("_").append(value);
				}
			}

			return buf.toString();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}
