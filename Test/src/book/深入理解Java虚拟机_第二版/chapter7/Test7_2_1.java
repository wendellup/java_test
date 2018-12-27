package book.深入理解Java虚拟机_第二版.chapter7;

public class Test7_2_1 {
	public static void main(String[] args) {
//		System.out.println(SubClass.value2);
		
//		SuperClass[] sca = new SuperClass[10];
		
		System.out.println(SuperClass.HELLO_WORLD);
	}
}

class SuperClass{
	public static int value = 123;
	static {
		System.out.println("SuperClass init...");
	}
	
	public static final String HELLO_WORLD = "hello world!";
}


class SubClass extends SuperClass{
	static {
		System.out.println("SubClass init...");
	}
	
	public static int value2 = 123;
}