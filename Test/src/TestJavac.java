public class TestJavac {
	private static final String root = "PUSH-";
	
	public static void getPushImsiModK() {
		String str =  root + "pushImsiModK";
	}
	
	public static void main(String[] args) {
		long beginMillis = System.currentTimeMillis();
		for(int i=0; i<100000; i++){
			getPushImsiModK();
		}
		
		long endMillis = System.currentTimeMillis();
		System.out.println(endMillis-beginMillis);
		
	}
}
