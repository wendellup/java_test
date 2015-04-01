package test.com;

public class TestStringFormat {
	public static void main(String[] args) {
		
		long beginMillis = System.currentTimeMillis();
		String str = "begin-------->(%1$s)--------->end";
		String sql = String.format(str.toString(), "xxxxxx");
		System.out.println("String format ->cost millis:"+(System.currentTimeMillis() - beginMillis));
		
		System.out.println(str);
		long beginMillis2 = System.currentTimeMillis();
		String directStr = "begin-------->("+6+","+"bbb"+","+10+","+20+","+"xxxxxx"+")--------->end";
		System.out.println("direct format ->cost millis:"+(System.currentTimeMillis() - beginMillis2));
		System.out.println(directStr);
		
	}
}
