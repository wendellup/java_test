package test.com;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   LoaderHandler.java


import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.SocketPermission;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.rmi.server.LogStream;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.PropertyPermission;
import java.util.StringTokenizer;
import java.util.WeakHashMap;

import cn.egame.interfaces.EnumType;
import sun.rmi.runtime.Log;
import sun.security.action.GetPropertyAction;

public final class LoaderHandlerTest
{
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

 private static class LoaderEntry extends WeakReference
 {

     public LoaderKey key;
     public boolean removed;

     public LoaderEntry(LoaderKey loaderkey, Loader loader)
     {
         super(loader, LoaderHandlerTest.refQueue);
         removed = false;
         key = loaderkey;
     }
 }

 private static class LoaderKey
 {

     public int hashCode()
     {
         return hashValue;
     }

     public boolean equals(Object obj)
     {
         if(obj instanceof LoaderKey)
         {
             LoaderKey loaderkey = (LoaderKey)obj;
             if(parent != loaderkey.parent)
                 return false;
             if(urls == loaderkey.urls)
                 return true;
             if(urls.length != loaderkey.urls.length)
                 return false;
             for(int i = 0; i < urls.length; i++)
                 if(!urls[i].equals(loaderkey.urls[i]))
                     return false;

             return true;
         } else
         {
             return false;
         }
     }

     private URL urls[];
     private ClassLoader parent;
     private int hashValue;

     public LoaderKey(URL aurl[], ClassLoader classloader)
     {
         urls = aurl;
         parent = classloader;
         if(classloader != null)
             hashValue = classloader.hashCode();
         for(int i = 0; i < aurl.length; i++)
             hashValue ^= aurl[i].hashCode();

     }
 }


 private LoaderHandlerTest()
 {
 }

 /*
 private static synchronized URL[] getDefaultCodebaseURLs()
     throws MalformedURLException
 {
     if(codebaseURLs == null)
         if(codebaseProperty != null)
             codebaseURLs = pathToURLs(codebaseProperty);
         else
             codebaseURLs = new URL[0];
     return codebaseURLs;
 }
 */

 /*
 public static Class loadClass(String s, String s1, ClassLoader classloader)
     throws MalformedURLException, ClassNotFoundException
 {
     if(loaderLog.isLoggable(Log.BRIEF))
         loaderLog.log(Log.BRIEF, (new StringBuilder()).append("name = \"").append(s1).append("\", ").append("codebase = \"").append(s == null ? "" : s).append("\"").append(classloader == null ? "" : (new StringBuilder()).append(", defaultLoader = ").append(classloader).toString()).toString());
     URL aurl[];
     if(s != null)
         aurl = pathToURLs(s);
     else
         aurl = getDefaultCodebaseURLs();
     if(classloader != null)
         try
         {
             Class class1 = Class.forName(s1, false, classloader);
             if(loaderLog.isLoggable(Log.VERBOSE))
                 loaderLog.log(Log.VERBOSE, (new StringBuilder()).append("class \"").append(s1).append("\" found via defaultLoader, ").append("defined by ").append(class1.getClassLoader()).toString());
             return class1;
         }
         catch(ClassNotFoundException classnotfoundexception) { }
     return loadClass(aurl, s1);
 }
 */

 public static void main(String[] args) {
	System.out.println(getClassAnnotation(EnumType.GameType.class));
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

 /*
 public static ClassLoader getClassLoader(String s)
     throws MalformedURLException
 {
     ClassLoader classloader = getRMIContextClassLoader();
     URL aurl[];
     if(s != null)
         aurl = pathToURLs(s);
     else
         aurl = getDefaultCodebaseURLs();
     SecurityManager securitymanager = System.getSecurityManager();
     if(securitymanager != null)
         securitymanager.checkPermission(new RuntimePermission("getClassLoader"));
     else
         return classloader;
     Loader loader = lookupLoader(aurl, classloader);
     if(loader != null)
         loader.checkPermissions();
     return loader;
 }
 */

 public static Object getSecurityContext(ClassLoader classloader)
 {
     if(classloader instanceof Loader)
     {
         URL aurl[] = ((Loader)classloader).getURLs();
         if(aurl.length > 0)
             return aurl[0];
     }
     return null;
 }

 public static void registerCodebaseLoader(ClassLoader classloader)
 {
     codebaseLoaders.put(classloader, null);
 }

 /*
 private static Class loadClass(URL aurl[], String s)
     throws ClassNotFoundException
 {
     ClassLoader classloader = getRMIContextClassLoader();
     if(loaderLog.isLoggable(Log.VERBOSE))
         loaderLog.log(Log.VERBOSE, (new StringBuilder()).append("(thread context class loader: ").append(classloader).append(")").toString());
     SecurityManager securitymanager = System.getSecurityManager();
     if(securitymanager == null)
         try
         {
             Class class1 = Class.forName(s, false, classloader);
             if(loaderLog.isLoggable(Log.VERBOSE))
                 loaderLog.log(Log.VERBOSE, (new StringBuilder()).append("class \"").append(s).append("\" found via ").append("thread context class loader ").append("(no security manager: codebase disabled), ").append("defined by ").append(class1.getClassLoader()).toString());
             return class1;
         }
         catch(ClassNotFoundException classnotfoundexception)
         {
             if(loaderLog.isLoggable(Log.BRIEF))
                 loaderLog.log(Log.BRIEF, (new StringBuilder()).append("class \"").append(s).append("\" not found via ").append("thread context class loader ").append("(no security manager: codebase disabled)").toString(), classnotfoundexception);
             throw new ClassNotFoundException((new StringBuilder()).append(classnotfoundexception.getMessage()).append(" (no security manager: RMI class loader disabled)").toString(), classnotfoundexception.getException());
         }
     Loader loader = lookupLoader(aurl, classloader);
     try
     {
         if(loader != null)
             loader.checkPermissions();
     }
     catch(SecurityException securityexception)
     {
         try
         {
             Class class3 = Class.forName(s, false, classloader);
             if(loaderLog.isLoggable(Log.VERBOSE))
                 loaderLog.log(Log.VERBOSE, (new StringBuilder()).append("class \"").append(s).append("\" found via ").append("thread context class loader ").append("(access to codebase denied), ").append("defined by ").append(class3.getClassLoader()).toString());
             return class3;
         }
         catch(ClassNotFoundException classnotfoundexception2) { }
         if(loaderLog.isLoggable(Log.BRIEF))
             loaderLog.log(Log.BRIEF, (new StringBuilder()).append("class \"").append(s).append("\" not found via ").append("thread context class loader ").append("(access to codebase denied)").toString(), securityexception);
         throw new ClassNotFoundException("access to class loader denied", securityexception);
     }
     try
     {
         Class class2 = Class.forName(s, false, loader);
         if(loaderLog.isLoggable(Log.VERBOSE))
             loaderLog.log(Log.VERBOSE, (new StringBuilder()).append("class \"").append(s).append("\" ").append("found via codebase, ").append("defined by ").append(class2.getClassLoader()).toString());
         return class2;
     }
     catch(ClassNotFoundException classnotfoundexception1)
     {
         if(loaderLog.isLoggable(Log.BRIEF))
             loaderLog.log(Log.BRIEF, (new StringBuilder()).append("class \"").append(s).append("\" not found via codebase").toString(), classnotfoundexception1);
         throw classnotfoundexception1;
     }
 }
 */

 /*
 public static Class loadProxyClass(String s, String as[], ClassLoader classloader)
     throws MalformedURLException, ClassNotFoundException
 {
     if(loaderLog.isLoggable(Log.BRIEF))
         loaderLog.log(Log.BRIEF, (new StringBuilder()).append("interfaces = ").append(Arrays.asList(as)).append(", ").append("codebase = \"").append(s == null ? "" : s).append("\"").append(classloader == null ? "" : (new StringBuilder()).append(", defaultLoader = ").append(classloader).toString()).toString());
     ClassLoader classloader1 = getRMIContextClassLoader();
     if(loaderLog.isLoggable(Log.VERBOSE))
         loaderLog.log(Log.VERBOSE, (new StringBuilder()).append("(thread context class loader: ").append(classloader1).append(")").toString());
     URL aurl[];
     if(s != null)
         aurl = pathToURLs(s);
     else
         aurl = getDefaultCodebaseURLs();
     SecurityManager securitymanager = System.getSecurityManager();
     if(securitymanager == null)
         try
         {
             Class class1 = loadProxyClass(as, classloader, classloader1, false);
             if(loaderLog.isLoggable(Log.VERBOSE))
                 loaderLog.log(Log.VERBOSE, (new StringBuilder()).append("(no security manager: codebase disabled) proxy class defined by ").append(class1.getClassLoader()).toString());
             return class1;
         }
         catch(ClassNotFoundException classnotfoundexception)
         {
             if(loaderLog.isLoggable(Log.BRIEF))
                 loaderLog.log(Log.BRIEF, "(no security manager: codebase disabled) proxy class resolution failed", classnotfoundexception);
             throw new ClassNotFoundException((new StringBuilder()).append(classnotfoundexception.getMessage()).append(" (no security manager: RMI class loader disabled)").toString(), classnotfoundexception.getException());
         }
     Loader loader = lookupLoader(aurl, classloader1);
     try
     {
         if(loader != null)
             loader.checkPermissions();
     }
     catch(SecurityException securityexception)
     {
         try
         {
             Class class3 = loadProxyClass(as, classloader, classloader1, false);
             if(loaderLog.isLoggable(Log.VERBOSE))
                 loaderLog.log(Log.VERBOSE, (new StringBuilder()).append("(access to codebase denied) proxy class defined by ").append(class3.getClassLoader()).toString());
             return class3;
         }
         catch(ClassNotFoundException classnotfoundexception2) { }
         if(loaderLog.isLoggable(Log.BRIEF))
             loaderLog.log(Log.BRIEF, "(access to codebase denied) proxy class resolution failed", securityexception);
         throw new ClassNotFoundException("access to class loader denied", securityexception);
     }
     try
     {
         Class class2 = loadProxyClass(as, classloader, ((ClassLoader) (loader)), true);
         if(loaderLog.isLoggable(Log.VERBOSE))
             loaderLog.log(Log.VERBOSE, (new StringBuilder()).append("proxy class defined by ").append(class2.getClassLoader()).toString());
         return class2;
     }
     catch(ClassNotFoundException classnotfoundexception1)
     {
         if(loaderLog.isLoggable(Log.BRIEF))
             loaderLog.log(Log.BRIEF, "proxy class resolution failed", classnotfoundexception1);
         throw classnotfoundexception1;
     }
 }
 */

 private static Class loadProxyClass(String as[], ClassLoader classloader, ClassLoader classloader1, boolean flag)
     throws ClassNotFoundException
 {
     Class aclass[];
     boolean aflag[];
label0:
     {
         ClassLoader classloader2 = null;
         aclass = new Class[as.length];
         aflag = (new boolean[] {
             false
         });
         if(classloader == null)
             break label0;
         try
         {
             classloader2 = loadProxyInterfaces(as, classloader, aclass, aflag);
             if(loaderLog.isLoggable(Log.VERBOSE))
             {
                 ClassLoader aclassloader[] = new ClassLoader[aclass.length];
                 for(int i = 0; i < aclassloader.length; i++)
                     aclassloader[i] = aclass[i].getClassLoader();

                 loaderLog.log(Log.VERBOSE, (new StringBuilder()).append("proxy interfaces found via defaultLoader, defined by ").append(Arrays.asList(aclassloader)).toString());
             }
         }
         catch(ClassNotFoundException classnotfoundexception)
         {
             break label0;
         }
         if(!aflag[0])
         {
             if(flag)
                 try
                 {
                     return Proxy.getProxyClass(classloader1, aclass);
                 }
                 catch(IllegalArgumentException illegalargumentexception) { }
             classloader2 = classloader;
         }
         return loadProxyClass(classloader2, aclass);
     }
     aflag[0] = false;
     ClassLoader classloader3 = loadProxyInterfaces(as, classloader1, aclass, aflag);
     if(loaderLog.isLoggable(Log.VERBOSE))
     {
         ClassLoader aclassloader1[] = new ClassLoader[aclass.length];
         for(int j = 0; j < aclassloader1.length; j++)
             aclassloader1[j] = aclass[j].getClassLoader();

         loaderLog.log(Log.VERBOSE, (new StringBuilder()).append("proxy interfaces found via codebase, defined by ").append(Arrays.asList(aclassloader1)).toString());
     }
     if(!aflag[0])
         classloader3 = classloader1;
     return loadProxyClass(classloader3, aclass);
 }

 private static Class loadProxyClass(ClassLoader classloader, Class aclass[])
     throws ClassNotFoundException
 {
     try
     {
         return Proxy.getProxyClass(classloader, aclass);
     }
     catch(IllegalArgumentException illegalargumentexception)
     {
         throw new ClassNotFoundException("error creating dynamic proxy class", illegalargumentexception);
     }
 }

 private static ClassLoader loadProxyInterfaces(String as[], ClassLoader classloader, Class aclass[], boolean aflag[])
     throws ClassNotFoundException
 {
     ClassLoader classloader1 = null;
     for(int i = 0; i < as.length; i++)
     {
         Class class1 = aclass[i] = Class.forName(as[i], false, classloader);
         if(Modifier.isPublic(class1.getModifiers()))
             continue;
         ClassLoader classloader2 = class1.getClassLoader();
         if(loaderLog.isLoggable(Log.VERBOSE))
             loaderLog.log(Log.VERBOSE, (new StringBuilder()).append("non-public interface \"").append(as[i]).append("\" defined by ").append(classloader2).toString());
         if(!aflag[0])
         {
             classloader1 = classloader2;
             aflag[0] = true;
             continue;
         }
         if(classloader2 != classloader1)
             throw new IllegalAccessError("non-public interfaces defined in different class loaders");
     }

     return classloader1;
 }

 /*
 private static URL[] pathToURLs(String s)
     throws MalformedURLException
 {
     Map map = pathToURLsCache;
     JVM INSTR monitorenter ;
     Object aobj[] = (Object[])(Object[])pathToURLsCache.get(s);
     if(aobj != null)
         return (URL[])(URL[])aobj[0];
     map;
     JVM INSTR monitorexit ;
       goto _L1
     Exception exception;
     exception;
     throw exception;
_L1:
     StringTokenizer stringtokenizer = new StringTokenizer(s);
     URL aurl[] = new URL[stringtokenizer.countTokens()];
     for(int i = 0; stringtokenizer.hasMoreTokens(); i++)
         aurl[i] = new URL(stringtokenizer.nextToken());

     synchronized(pathToURLsCache)
     {
         pathToURLsCache.put(s, ((Object) (new Object[] {
             aurl, new SoftReference(s)
         })));
     }
     return aurl;
 }
 */

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

 private static ClassLoader getRMIContextClassLoader()
 {
     return Thread.currentThread().getContextClassLoader();
 }

 /*
 private static Loader lookupLoader(URL aurl[], ClassLoader classloader)
 {
     Loader loader;
     synchronized(test.com.LoaderHandler.class)
     {
         do
         {
             LoaderEntry loaderentry;
             if((loaderentry = (LoaderEntry)refQueue.poll()) == null)
                 break;
             if(!loaderentry.removed)
                 loaderTable.remove(loaderentry.key);
         } while(true);
         LoaderKey loaderkey = new LoaderKey(aurl, classloader);
         LoaderEntry loaderentry1 = (LoaderEntry)loaderTable.get(loaderkey);
         if(loaderentry1 == null || (loader = (Loader)loaderentry1.get()) == null)
         {
             if(loaderentry1 != null)
             {
                 loaderTable.remove(loaderkey);
                 loaderentry1.removed = true;
             }
             AccessControlContext accesscontrolcontext = getLoaderAccessControlContext(aurl);
             loader = (Loader)AccessController.doPrivileged(new PrivilegedAction(aurl, classloader) {

                 public Object run()
                 {
                     return new Loader(urls, parent);
                 }

                 final URL val$urls[];
                 final ClassLoader val$parent;

         
         {
             urls = aurl;
             parent = classloader;
             super();
         }
             }
, accesscontrolcontext);
             loaderentry1 = new LoaderEntry(loaderkey, loader);
             loaderTable.put(loaderkey, loaderentry1);
         }
     }
     return loader;
 }
*/
 private static AccessControlContext getLoaderAccessControlContext(URL aurl[])
 {
     PermissionCollection permissioncollection = (PermissionCollection)AccessController.doPrivileged(new PrivilegedAction() {

         public Object run()
         {
             CodeSource codesource = new CodeSource(null, (java.security.cert.Certificate[])null);
             Policy policy = Policy.getPolicy();
             if(policy != null)
                 return policy.getPermissions(codesource);
             else
                 return new Permissions();
         }

     }
);
     permissioncollection.add(new RuntimePermission("createClassLoader"));
     permissioncollection.add(new PropertyPermission("java.*", "read"));
     addPermissionsForURLs(aurl, permissioncollection, true);
     ProtectionDomain protectiondomain = new ProtectionDomain(new CodeSource(aurl.length <= 0 ? null : aurl[0], (java.security.cert.Certificate[])null), permissioncollection);
     return new AccessControlContext(new ProtectionDomain[] {
         protectiondomain
     });
 }

 public static void addPermissionsForURLs(URL aurl[], PermissionCollection permissioncollection, boolean flag)
 {
     for(int i = 0; i < aurl.length; i++)
     {
         URL url = aurl[i];
         try
         {
             URLConnection urlconnection = url.openConnection();
             Permission permission = urlconnection.getPermission();
             if(permission == null)
                 continue;
             if(permission instanceof FilePermission)
             {
                 String s = permission.getName();
                 int j = s.lastIndexOf(File.separatorChar);
                 if(j != -1)
                 {
                     s = s.substring(0, j + 1);
                     if(s.endsWith(File.separator))
                         s = (new StringBuilder()).append(s).append("-").toString();
                     FilePermission filepermission = new FilePermission(s, "read");
                     if(!permissioncollection.implies(filepermission))
                         permissioncollection.add(filepermission);
                     permissioncollection.add(new FilePermission(s, "read"));
                     continue;
                 }
                 if(!permissioncollection.implies(permission))
                     permissioncollection.add(permission);
                 continue;
             }
             if(!permissioncollection.implies(permission))
                 permissioncollection.add(permission);
             if(!flag)
                 continue;
             URL url1 = url;
             for(URLConnection urlconnection1 = urlconnection; urlconnection1 instanceof JarURLConnection; urlconnection1 = url1.openConnection())
                 url1 = ((JarURLConnection)urlconnection1).getJarFileURL();

             String s1 = url1.getHost();
             if(s1 == null || !permission.implies(new SocketPermission(s1, "resolve")))
                 continue;
             SocketPermission socketpermission = new SocketPermission(s1, "connect,accept");
             if(!permissioncollection.implies(socketpermission))
                 permissioncollection.add(socketpermission);
         }
         catch(IOException ioexception) { }
     }

 }

 static final int logLevel;
 static final Log loaderLog;
 private static String codebaseProperty = null;
 private static URL codebaseURLs[] = null;
 private static final Map codebaseLoaders;
 private static final HashMap loaderTable = new HashMap(5);
 private static final ReferenceQueue refQueue = new ReferenceQueue();
 private static final Map pathToURLsCache = new WeakHashMap(5);

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


}


/*
	DECOMPILATION REPORT

	Decompiled from: D:\Java\jdk1.6.0_30\jre\lib\rt.jar
	Total time: 150 ms
	Jad reported messages/errors:
Overlapped try statements detected. Not all exception handlers will be resolved in the method pathToURLs
Couldn't fully decompile method pathToURLs
Couldn't resolve all exception handlers in method pathToURLs
Overlapped try statements detected. Not all exception handlers will be resolved in the method lookupLoader
	Exit status: 0
	Caught exceptions:
*/
