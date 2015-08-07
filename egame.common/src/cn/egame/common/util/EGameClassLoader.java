package cn.egame.common.util;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class EGameClassLoader extends URLClassLoader {

	public EGameClassLoader(URL[] repositories) {
		super(repositories);
	}

	public EGameClassLoader(URL[] repositories, ClassLoader parent) {
		super(repositories, parent);
	}

	public static File[] getJars(File libDir) {
		return libDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				boolean accept = false;
				String smallName = name.toLowerCase();
				if (smallName.endsWith(".jar")) {
					accept = true;
				} else if (smallName.endsWith(".zip")) {
					accept = true;
				}
				return accept;
			}
		});
	}

	public EGameClassLoader(ClassLoader parent, File libDir)
			throws MalformedURLException {
		super(new URL[] { libDir.toURI().toURL() }, parent);

		File[] jars = EGameClassLoader.getJars(libDir);

		if (jars == null) {
			return;
		}

		for (int i = 0; i < jars.length; i++) {
			if (jars[i].isFile()) {
				addURL(jars[i].toURI().toURL());
			}
		}
	}

	public static ClassLoader createClassLoader(final ClassLoader parent,
			File libDir) throws MalformedURLException {
		File[] jars = EGameClassLoader.getJars(libDir);
		final URL[] array = new URL[jars.length];
		for (int i = 0; i < array.length; i++) {
			array[i] = jars[i].toURI().toURL();
		}

		return AccessController
				.doPrivileged(new PrivilegedAction<EGameClassLoader>() {
					@Override
					public EGameClassLoader run() {
						if (parent == null)
							return new EGameClassLoader(array);
						else
							return new EGameClassLoader(array, parent);
					}
				});

	}
}