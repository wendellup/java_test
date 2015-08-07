package cn.egame.common.mongo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

import cn.egame.common.dataserver.BaseDBObj;
import cn.egame.common.dataserver.TableManager;
import cn.egame.common.dataserver.ann.DB_FIELD;

import com.mongodb.BasicDBObject;

public class MongoUtil {
	private static Logger logger = Logger.getLogger(MongoUtil.class);

	public static String parse_id(BaseDBObj dbObj) throws Exception {
		String _id = "";

		String[] uninId = dbObj.getUninId();
		for (int i = 0; i < uninId.length; i = i + 2) {
			if (i == 0) {
				_id += uninId[i+1].toString();
			} else {
				_id += ("_" + uninId[i+1].toString());
			}
		}
		return _id;
	}

	public static BaseDBObj getBean(Class<?> clz, BasicDBObject bdb) throws Exception {
		BaseDBObj igo = (BaseDBObj) clz.newInstance();
		Field[] fields = igo.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];

			field.setAccessible(true);
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			DB_FIELD ann = field.getAnnotation(DB_FIELD.class);
			if (ann == null) {
				continue;
			}
			if (ann != null && !ann.isDBField()) {
				continue;
			}
			// String name = field.getName();

			String dbField = ann.fieldName();
			Object property = field.get(igo);
			if (bdb.get(dbField) == null) {
				continue;
			}
			String value = bdb.get(dbField).toString();

			if (property != null) {
				if (property instanceof Integer) {
					field.set(igo, Integer.valueOf(value));
				} else if (property instanceof Long) {
					field.set(igo, Long.valueOf(value));
				} else if (property instanceof Float) {
					field.set(igo, Float.valueOf(value));
				} else if (property instanceof Double) {
					field.set(igo, Double.valueOf(value));
				} else if (property instanceof Boolean) {
					field.set(igo, Boolean.valueOf(value));
				} else if (property instanceof byte[]) {
					Object obj = bdb.get(dbField);
					field.set(igo, obj);
				} else {
					field.set(igo, value);
				}
			} else {
				// 字符串类型赋值--字符串类型不是原始类型
				field.set(igo, value);
			}
		}
		igo.set_id(bdb.get("_id").toString());
		return igo;
	}

	public static BasicDBObject toBasicDBObject(BaseDBObj dbObj) throws Exception {
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
			String dbField = ann.fieldName();

			if (dbField == null) {
				throw new RuntimeException("db field name is null");
			}
			if (value == null) {
				value = "";
			}

			baseObj.put(dbField, value);
		}

		baseObj.put("_id", dbObj.getUninKey());
		return baseObj;
	}

	public static BasicDBObject getCondition(BaseDBObj dbObj) {
		try {
			BasicDBObject where = new BasicDBObject();

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
							where.append(dbField, value);
					} else if (value instanceof Long) {
						if ((Long) value != 0)
							where.append(dbField, value);
					} else if (value instanceof Float) {
						if ((Float) value != 0)
							where.append(dbField, value);
					} else if (value instanceof Double) {
						if ((Double) value != 0)
							where.append(dbField, value);
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
						where.append(dbField, value);
				}
			}

			return where;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	
}
