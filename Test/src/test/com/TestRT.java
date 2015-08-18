package test.com;

import java.net.InetAddress;
import java.security.PrivilegedAction;

public class TestRT {
	public static void main(String[] args) {
		InetAddress inetAddress  = java.security.AccessController.doPrivileged(
              new PrivilegedAction() {
              public Object run() {
              try {
                  return InetAddress.getLocalHost();
              } catch (Exception e) {
              }
              return null;
              }
          });
      System.out.println(inetAddress);
      
      System.out.println("test...");
  }
}
