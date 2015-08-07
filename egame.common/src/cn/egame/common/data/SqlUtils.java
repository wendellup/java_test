package cn.egame.common.data;

import java.sql.Date;

import cn.egame.common.util.Utils;

public class SqlUtils {

	private static String DEFAULT_DATE = "0001-01-01";
	
	private static String DEFAULT_TIME = "0001-01-01 00:00:00";
	
	public static String QuataLikeStr(String str) {
		if (str == null)
			return "NULL";
		str = str.replace("\"", "\\\"");
		str = str.replace("'", "\\'");
		str = str.replace("%", "\\%");
		str = str.replace("*", "\\*");
		str = str.replace("--", "\\--");
		str = str.replace("*/", "\\*/");
		str = str.replace(";", "\\;");
		str = str.replace("xp_", "\\xp_");
		str = str.replace("Xp_", "\\Xp");
		str = str.replace("xP_", "\\xP_");
		str = str.replace("XP_", "\\XP_");
		return "'%" + str + "%'";
	}
	
	public static String QuataStr(String str) {
		if (str == null)
			return "NULL";
		str = str.replace("\"", "\\\"");
		str = str.replace("'", "\\'");
		str = str.replace("%", "\\%");
		str = str.replace("*", "\\*");
		str = str.replace("--", "\\--");
		str = str.replace("*/", "\\*/");
		str = str.replace(";", "\\;");
		str = str.replace("xp_", "\\xp_");
		str = str.replace("Xp_", "\\Xp");
		str = str.replace("xP_", "\\xP_");
		str = str.replace("XP_", "\\XP_");
		return "'" + str + "'";
	}

	public static String QuataDate(long time) {
		return QuataDate(new Date(time));
	}
	
	public static String QuataDate(Date date) {
		if(date==null) return DEFAULT_DATE;
		return Utils.toDateString(date,"yyyy-MM-dd");
	}
	
	public static String QuataDateTime(long time) {
		return QuataDateTime(new Date(time));
	}
	
	public static String QuataDateTime(Date time) {
		if(time==null) return DEFAULT_TIME;
		return Utils.toDateString(time,"yyyy-MM-dd HH:mm:ss");
	}
	
	public static Boolean isInjectSqlParameter(String parameter) {
		if (parameter == null || "".equals(parameter)) {
			return false;
		}
		parameter = parameter.toUpperCase();
		if (parameter.indexOf(" OR ") != -1 || parameter.indexOf(" AND ") != -1
		        || parameter.indexOf("'OR") != -1 || parameter.indexOf("'AND") != -1
		        || parameter.indexOf("AND(") != -1 || parameter.indexOf("OR(") != -1) {
			return true;
		} else {
			return false;
		}
	}
}
