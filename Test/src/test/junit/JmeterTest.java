package test.junit;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JmeterTest {
	
//	@Test
	public void helloJmeter() {
		System.out.println("hello Jmeter...");
		
		String ip = "192.168.251.31";
		String regex = "^(127\\.0\\.0\\.1|192\\.168\\.251\\.31)$";
		System.out.println(ip.matches(regex));
	}
	
	public static void main(String[] args) {
		new JmeterTest().helloJmeter();
	}
}
