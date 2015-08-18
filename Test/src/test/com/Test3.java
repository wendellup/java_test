package test.com;

import java.io.*;
import java.lang.ref.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.*;
import java.rmi.server.LogStream;
import java.security.*;
import java.util.*;
import sun.rmi.runtime.Log;
import sun.security.action.GetPropertyAction;

public class Test3 {
	static final int logLevel;
    static final Log loaderLog;
    private static String codebaseProperty = null;
    private static URL codebaseURLs[] = null;
    private static final Map codebaseLoaders;
    private static final HashMap loaderTable = new HashMap(5);
    private static final ReferenceQueue refQueue = new ReferenceQueue();
    private static final Map pathToURLsCache = new WeakHashMap(5);

    private static class Loader extends URLClassLoader
    {

        public String getClassAnnotation()
        {
            return annotation;
        }

        private void checkPermissions()
        {
            SecurityManager securitymanager = System.getSecurityManager();
            if(securitymanager != null)
            {
                for(Enumeration enumeration = permissions.elements(); enumeration.hasMoreElements(); securitymanager.checkPermission((Permission)enumeration.nextElement()));
            }
        }

        protected PermissionCollection getPermissions(CodeSource codesource)
        {
            PermissionCollection permissioncollection = super.getPermissions(codesource);
            return permissioncollection;
        }

        public String toString()
        {
            return (new StringBuilder()).append(super.toString()).append("[\"").append(annotation).append("\"]").toString();
        }

        private ClassLoader parent;
        private String annotation;
        private Permissions permissions;


        private Loader(URL aurl[], ClassLoader classloader)
        {
            super(aurl, classloader);
            parent = classloader;
            permissions = new Permissions();
            LoaderHandlerTest.addPermissionsForURLs(aurl, permissions, false);
            annotation = LoaderHandlerTest.urlsToPath(aurl);
        }

    }
    
    static 
    {
        logLevel = LogStream.parseLevel((String)AccessController.doPrivileged(new GetPropertyAction("sun.rmi.loader.logLevel")));
        loaderLog = Log.getLog("sun.rmi.loader", "loader", logLevel);
        String s = (String)AccessController.doPrivileged(new GetPropertyAction("java.rmi.server.codebase"));
        if(s != null && s.trim().length() > 0)
            codebaseProperty = s;
        codebaseLoaders = Collections.synchronizedMap(new IdentityHashMap(5));
        for(ClassLoader classloader = ClassLoader.getSystemClassLoader(); classloader != null; classloader = classloader.getParent())
            codebaseLoaders.put(classloader, null);

    }
	
	public static String getClassAnnotation(Class class1)
    {
        String s = class1.getName();
        int i = s.length();
        if(i > 0 && s.charAt(0) == '[')
        {
            int j;
            for(j = 1; i > j && s.charAt(j) == '['; j++);
            if(i > j && s.charAt(j) != 'L')
                return null;
        }
        ClassLoader classloader = class1.getClassLoader();
        if(classloader == null || codebaseLoaders.containsKey(classloader))
            return codebaseProperty;
        String s1 = null;
        if(classloader instanceof Loader)
            s1 = ((Loader)classloader).getClassAnnotation();
        else
        if(classloader instanceof URLClassLoader)
            try
            {
                URL aurl[] = ((URLClassLoader)classloader).getURLs();
                if(aurl != null)
                {
                    SecurityManager securitymanager = System.getSecurityManager();
                    if(securitymanager != null)
                    {
                        Permissions permissions = new Permissions();
                        for(int k = 0; k < aurl.length; k++)
                        {
                            Permission permission = aurl[k].openConnection().getPermission();
                            if(permission != null && !permissions.implies(permission))
                            {
                                securitymanager.checkPermission(permission);
                                permissions.add(permission);
                            }
                        }

                    }
                    s1 = urlsToPath(aurl);
                }
            }
            catch(SecurityException securityexception) { }
            catch(IOException ioexception) { }
        if(s1 != null)
            return s1;
        else
            return codebaseProperty;
    }
	
	private static String urlsToPath(URL aurl[])
    {
        if(aurl.length == 0)
            return null;
        if(aurl.length == 1)
            return aurl[0].toExternalForm();
        StringBuffer stringbuffer = new StringBuffer(aurl[0].toExternalForm());
        for(int i = 1; i < aurl.length; i++)
        {
            stringbuffer.append(' ');
            stringbuffer.append(aurl[i].toExternalForm());
        }

        return stringbuffer.toString();
    }
}
