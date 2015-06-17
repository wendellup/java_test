package test.client.exception;

import java.rmi.RemoteException;
import java.sql.SQLException;

import cn.egame.common.exception.ExceptionCommonBase;

public class TestException {
	public static void main(String[] args) {
		int i=0;
		try {
			funThrowCommonEx();
		} catch (RemoteException e) {
			i++;
			System.out.println(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void funThrowCommonEx() throws RemoteException, SQLException{
//		throw ExceptionCommonBase.throwExceptionCommonBase(
//				new RuntimeException("呵呵"));
			throw ExceptionCommonBase.throwSQLException(new SQLException("呵呵"));
//			Integer.parseInt("aaa");
	}
}	
