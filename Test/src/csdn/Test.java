package csdn;
class Hello
{
	public String str = "Hello World";
	public void fun()
	{
		System.out.println(str);
	}
}

public class Test
{
	public static void main(String[] args)
	{
		Hello hello = new Hello();
		hello.fun();
		
		System.out.println("----------------------");
		
		//Hello类的类加载器
		ClassLoader classLoaderOfHello = Hello.class.getClassLoader();
		
		System.out.println("Hello is Loaded by : "+classLoaderOfHello);
		
		System.out.println("----------------------");
		
		//Hello类的类加载器的Class对象
		Class AppClazz = classLoaderOfHello.getClass();
		
		//分析Hello类的类加载器的Class对象的类继承关系
		while(AppClazz != null)
		{
			System.out.println(AppClazz);
			
			AppClazz = AppClazz.getSuperclass();
		}
		
		System.out.println("----------------------");
		
		//取得扩展器加载器的类对象Class
		Class ExtClazz = classLoaderOfHello.getParent().getClass();
		
		while(ExtClazz != null)
		{
			System.out.println(ExtClazz);
			
			ExtClazz = ExtClazz.getSuperclass();
		}
	}
}
