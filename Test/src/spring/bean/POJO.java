package spring.bean;

import javax.annotation.PostConstruct;

public class POJO {
	public POJO(){
		System.out.println("I'm  construct  method .... in POJO");
	}
	
	@PostConstruct
	public void  initPojo(){
		System.out.println("I'm  init  method  using  @PostConstrut.... in POJO");
	}
}
