package book.深入理解Java虚拟机_第二版.chapter8;

public class Test8_3_2_1 {
	abstract class Human{
		
	}
	
	class Man extends Human{
		
	}
	
	class Woman extends Human{
		
	}
	
	public void sayHello(Human guy){
		System.out.println("hello, guy!");
	}
	
	public void sayHello(Man guy){
		System.out.println("hello, gentleman!");
	}
	
	public void sayHello(Woman guy){
		System.out.println("hello, lady!");
	}
	
	public static void main(String[] args) {
		Human man = new Test8_3_2_1().new Man();
		Human woman = new Test8_3_2_1().new Woman();
		Test8_3_2_1 test = new Test8_3_2_1();
		test.sayHello(man);
		test.sayHello(woman);
	}
}
