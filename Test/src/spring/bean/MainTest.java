package spring.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MainTest {
	
	public static void main(String[] args) {
		
		ApplicationContext  context = new ClassPathXmlApplicationContext("annotation.xml");
		
		PersonService   personService  =  (PersonService)context.getBean("personService");
		
		personService.dostory();
	}

}
