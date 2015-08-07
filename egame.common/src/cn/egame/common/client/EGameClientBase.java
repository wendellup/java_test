package cn.egame.common.client;

import java.rmi.ConnectException;
import java.rmi.MarshalException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.util.Utils;

public abstract class EGameClientBase {
	private static Logger logger = Logger.getLogger(EGameClientBase.class);
	protected static byte[] SyncRoot = new byte[0];
	Map<String, Object> hash = new HashMap<String, Object>();

	public Object getService(String serviceName) throws RemoteException {
		Object obj = hash.get(serviceName);
		String serverUrl = null;
		if (obj == null)
			synchronized (SyncRoot) {
				if (obj == null) {

					try {
						Properties properties = Utils
								.getProperties("server.properties");

						serverUrl = properties.getProperty("rmi." + serviceName
								+ "_url");
						logger.info("get service ,rmi server url = "
								+ serverUrl);
						if (serverUrl.startsWith("rmi://")) {
							// serverUrl=rmi://127.0.0.1:801/IGameService
							obj = Naming.lookup(serverUrl);
						} else {
							// serverUrl=cn.egame.core.uc.UserServiceRemoteCall
							Class clazz = Class.forName(serverUrl);
							obj = clazz.newInstance();
						}
						hash.put(serviceName, obj);
					} catch (Exception e) {
						logger.error("rmi." + serviceName + "_url = "
								+ serverUrl);
						throw ExceptionCommonBase.throwExceptionCommonBase(e);
					}
				}
			}

		return obj;
	}

	public void release(RemoteException ex, Object obj) {
		if (ex == null || ex instanceof ConnectException
				|| ex instanceof NoSuchObjectException
				|| ex instanceof MarshalException)

			synchronized (SyncRoot) {
				if (ex instanceof NoSuchObjectException
						|| ex instanceof MarshalException) {
					hash.clear();
					return;
				}
				Set<String> keys = hash.keySet();
				boolean isRemote = obj instanceof Remote;

				for (String key : keys) {
					Object item = hash.get(key);
					if (obj == item) {
						hash.remove(key);
						break;
					} else if (isRemote) {
						// ((Remote)obj).
					}
				}

			}
	}

	@java.lang.Deprecated
	public void release(Object obj) {
		this.release(null, obj);
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "1");
		map.put("b", "1");
		map.put("c", "1");
		map.put("d", "1");
		Set<String> keys = map.keySet();
		String obj = "a";
		for (String key : keys) {
			Object item = map.get(key);
			if (obj.equals(item)) {
				map.remove(key);
			}
		}
	}
}
