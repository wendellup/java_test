package cn.egame.common.exception;

import java.lang.reflect.Field;
import java.util.TreeMap;

public class ErrorCodeBase {
	protected static TreeMap<Integer, String> _map;
	static {
		ErrorCodeBase ec = new ErrorCodeBase();
		_map = new TreeMap<Integer, String>();
		for (Field field : ErrorCodeBase.class.getFields()) {
			try {
				_map.put(field.getInt(ec), field.getName());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static final int UnDefinedServerError = -1;

	public static final int UnDefinedClientError = -2;
	public static final int UnDefinedOtherError = -3;
	public static final int UnDefinedDatabaseError = -4;

	public static final int ParameterError = -5;
	public static final int NotImplementCode = -6;
	public static final int UnDefinedMongoError = -7;
	public static final int SysOutOfMemoryError = -8;
	public static final int SysConfigError = -9;
	public static final int ExceptionMobile = -10;
	public static final int ExceptionWeb = -11;
	public static final int ExceptionText = -12;
	public static final int ExceptionIgnoreError = -13;
	public static final int ResultNotExsits = -14;
	public static final int DatabaseDuplicateKey = -101;

	public static final int DatabaseLengthLimit = -102;
	public static final int DatabaseIntegrityConstraintViolation = -103;
	
	
	public static final int EGAMECacheError = -150;
	
	public static final int EFSMkdirError=-502;
	public static final int EFSUploadError=-503;

	//charge e exception from -151 -111
	
	//user exception from -299  to -201
	
	//game exception from -499 to -301
	
	// efs excepion from -501 to -551
	
	//
	public static String getFieldName(int errorCode) {
		return _map.get(errorCode);
	}

	public static String getLangString(int code) {
		return "cn.egame.common.ErrorCode" + String.valueOf(code);
	}
}
