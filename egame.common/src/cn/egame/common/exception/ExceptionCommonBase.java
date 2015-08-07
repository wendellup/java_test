package cn.egame.common.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.egame.common.util.Utils;

/**
 * @author Danch
 * 
 */

public class ExceptionCommonBase extends RemoteException implements
		IExceptionCommon {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4594875950045103211L;
	private static final Pattern pattern = Pattern
			.compile("ErrorCode:(-[0-9]*) / Message:");

	private static Logger logger = Logger.getLogger(ExceptionCommonBase.class
			.getSimpleName());

	public static String errorCodeMessage(int errorCode) {
		return errorCodeMessage(errorCode, null);
	}

	public static String errorCodeMessage(int errorCode, String message) {
		return "ErrorCode:" + errorCode + " / Message:" + message;
	}

	public static boolean isDatabaseIntegrityConstraintViolation(Throwable ex) {
		// com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException:
		// Duplicate entry
		boolean is = ex.toString().indexOf(
				"MySQLIntegrityConstraintViolationException") > 0;
		return is;
	}

	public static boolean isDuplicateKeyException(Throwable ex) {
		// com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException:
		// Duplicate entry
		boolean is = ex.toString().indexOf(
				"MySQLIntegrityConstraintViolationException: Duplicate entry") > 0;
		return is;
	}

	public static boolean isOutOfMemory(Throwable ex) {
		// com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException:
		// Duplicate entry
		boolean is = ex.toString().indexOf(
				"java.lang.OutOfMemoryError: Java heap space") > 0;
		return is;
	}

	public static void logStack() {
		logStack("stack");
	}

	public static void logStack(String message) {
		Utils.printStack(message);
	}

	public static int parseErrorCode(String reason, int defaultValue) {
		Matcher matcher = pattern.matcher(reason);
		if (matcher.find()) {
			String errorCode = matcher.group(1);
			return Integer.parseInt(errorCode);
		}
		return defaultValue;
	}

	public static int parseErrorCode(Throwable ex, int defaultValue) {
		if (ex != null) {
			if (ex instanceof RemoteException)
				return parseErrorCode(ex.getMessage(), defaultValue);
			else if (ex instanceof ExceptionCommonBase)
				return ((ExceptionCommonBase) ex).getErrorCode();
			else if (ex instanceof IExceptionCommon)
				return ((IExceptionCommon) ex).getErrorCode();
			else {
				Throwable cause = ex.getCause();
				if (cause instanceof ExceptionCommonBase)
					return ((ExceptionCommonBase) cause).getErrorCode();
				if (cause instanceof IExceptionCommon)
					return ((IExceptionCommon) cause).getErrorCode();

				return parseErrorCode(ex.toString(), defaultValue);
			}
		}
		return defaultValue;
	}

	public static void releaseMemory() {
		try {
			System.gc();
			// Runtime.getRuntime().freeMemory();
		} catch (Throwable e) {
			logger.error(e);
		}
	}

	private static void resetErrorCode(ExceptionCommonBase e) {
		if (e != null) {
			if (e.cause != null) {
				int errorCode = ExceptionCommonBase.parseErrorCode(e.cause, 0);
				if (errorCode < 0) {
					e.setErrorCode(errorCode);
					return;
				}
			}

			int errorCode = ExceptionCommonBase.parseErrorCode(e.getMessage(),
					0);
			if (errorCode < 0)
				e.setErrorCode(errorCode);
		}
	}

	public static void resetErrorCode(Throwable e) {
		if (e != null && e instanceof IExceptionCommon) {
			int errorCode = ExceptionCommonBase.parseErrorCode(e, 0);
			if (errorCode < 0)
				((IExceptionCommon) e).setErrorCode(errorCode);
		}
	}

	public static ExceptionCommonBase throwExceptionCommonBase(Throwable ex)
			throws ExceptionCommonBase {
		logger.error("ExceptionCommonBase", ex);
		if (ex instanceof ExceptionCommonBase)
			return (ExceptionCommonBase) ex;
		else if (ex instanceof IExceptionCommon) {
			IExceptionCommon e = (IExceptionCommon) ex;
			return new ExceptionCommonBase(e.getErrorCode(), ex);
		} else if (ex instanceof SQLException) {
			if (isDuplicateKeyException(ex))
				return new ExceptionCommonBase(
						ErrorCodeBase.DatabaseDuplicateKey, ex.getMessage(), ex);
			else if (isDatabaseIntegrityConstraintViolation(ex))
				return new ExceptionCommonBase(
						ErrorCodeBase.DatabaseIntegrityConstraintViolation,
						ex.getMessage(), ex);
			else {
				int code = parseErrorCode(ex,
						ErrorCodeBase.UnDefinedDatabaseError);
				return new ExceptionCommonBase(code, ex.getMessage(), ex);
			}
		}

//		else if (ex instanceof MongoException)
//			return new ExceptionCommonBase(ErrorCodeBase.UnDefinedMongoError,
//					ex.getMessage(), ex);
		else if (ex instanceof OutOfMemoryError) {
			releaseMemory();
			return new ExceptionCommonBase(ErrorCodeBase.SysOutOfMemoryError,
					ex.getMessage(), ex);
		} else {
			if (isDuplicateKeyException(ex))
				return new ExceptionCommonBase(
						ErrorCodeBase.DatabaseDuplicateKey, ex.getMessage(), ex);

			if (isDatabaseIntegrityConstraintViolation(ex))
				return new ExceptionCommonBase(
						ErrorCodeBase.DatabaseIntegrityConstraintViolation,
						ex.getMessage(), ex);

			if (isOutOfMemory(ex)) {
				releaseMemory();
				return new ExceptionCommonBase(
						ErrorCodeBase.SysOutOfMemoryError, ex.getMessage(), ex);

			}

			return new ExceptionCommonBase(ErrorCodeBase.UnDefinedServerError,
					ex.getMessage(), ex);
		}
	}

	public static SQLException throwSQLException(Throwable ex) {
		if (ex instanceof SQLException)
			return (SQLException) ex;
		else
			return new SQLException(ex);
	}

	protected int errorCode = -1;

	private Throwable cause = null;

	private String message = null;

	public ExceptionCommonBase(int errorCode) {
		this(errorCode, null, null);
	}

	public ExceptionCommonBase(int errorCode, String message) {
		this(errorCode, message, null);
	}

	public ExceptionCommonBase(int errorCode, String message, Throwable cause) {
		super();
		this.errorCode = errorCode;
		this.message = message;
		this.cause = cause;
	}

	public ExceptionCommonBase(int errorCode, Throwable cause) {
		this(errorCode, null, cause);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		if (cause != null && Utils.stringIsNullOrEmpty(message))
			message = this.cause.getMessage();
		if (ErrorCodeBase.ExceptionText == getErrorCode())
			return message;
		else
			return "ErrorCode:" + getErrorCode() + " / Message:" + message;
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
		if (cause != null) {
			cause.printStackTrace();
		}
	}

	@Override
	public void printStackTrace(PrintStream ps) {
		super.printStackTrace(ps);
		if (cause != null) {
			cause.printStackTrace(ps);
		}
	}

	@Override
	public void printStackTrace(PrintWriter pw) {
		super.printStackTrace(pw);
		if (cause != null) {
			cause.printStackTrace(pw);
		}
	}

	public void resetErrorCode() {
		ExceptionCommonBase.resetErrorCode(this);
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
