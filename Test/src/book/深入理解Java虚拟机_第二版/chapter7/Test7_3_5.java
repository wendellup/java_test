package book.深入理解Java虚拟机_第二版.chapter7;

public class Test7_3_5 {
	static int i = 1;
	static{
		i=0;
		System.out.println(i);
	}

	public static void main(String[] args) {
		{
			{
				System.out.println("xxx");
			}
		}
	}
	
}
