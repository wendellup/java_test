package test.client.cache;

import cn.egame.common.util.Utils;

public class X {
	public static void main(String[] args) {
		System.out.println("xxxxxxxxxxxx");
		boolean cacheSwitch = Utils.toBoolean(("1111"), true);
		System.out.println("-----------"+toBoolean("ture"));
		System.out.println(cacheSwitch);
		
	}
	 private static boolean toBoolean(String name) { 
			return ((name != null) && name.equalsIgnoreCase("true"));
		    }
}
