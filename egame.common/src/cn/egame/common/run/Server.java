package cn.egame.common.run;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import org.apache.log4j.Logger;

import cn.egame.common.util.EGameClassLoader;
import cn.egame.common.util.Utils;

public class Server {
	static IServer server = null;

	public static void main(String[] args) {
		try {
			Utils.setAppRoot("E:\\svn\\code\\egame\\server\\trunk\\egame.daemon\\target");
			Utils.initLog4j();
			Properties p = Utils.getProperties("container.properties");
			String clazz = p.getProperty("container.server.class");
			if (Utils.stringIsNullOrEmpty(clazz)) {
				Logger.getLogger("Container").error(
						"container.server.class is null.");
				return;
			}
			
			System.setProperty("java.security.policy",Utils.getConfigFile("server.policy"));

			File path = new File(Utils.getAppRoot() + "/lib/loader");
			Logger.getLogger("Container").info("loader:" + path.toString());

			final ClassLoader parent = findParentClassLoader();

			ClassLoader loader = null;
			if (path.exists() && path.isDirectory())
				loader = EGameClassLoader.createClassLoader(parent, path);

			Class c = null;
			Object o = null;
			if (loader == null) {
				Thread.currentThread().setContextClassLoader(loader);
				c = Class.forName(clazz);
				o = c.newInstance();
			} else {
				c = loader.loadClass(clazz);
				o = c.newInstance();
			}

			if (o instanceof IServer) {
				server = (IServer) o;
				server.start();
			} else
				Logger.getLogger("Container").error(
						"container(" + clazz + ") is not instanceof IServer.");
		} catch (Exception ex) {
			Logger.getLogger("Server").error(null, ex);
		}
	}

	private static ClassLoader findParentClassLoader() {
		ClassLoader parent = Thread.currentThread().getContextClassLoader();
		if (parent == null) {
			parent = Server.class.getClassLoader();
			if (parent == null) {
				parent = ClassLoader.getSystemClassLoader();
			}
		}
		return parent;
	}
}
