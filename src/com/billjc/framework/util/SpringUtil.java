package com.billjc.framework.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {
		private SpringUtil(){
		   context =
			   new ClassPathXmlApplicationContext(new String[] {"conf/springmvc.xml"});
	   }

	   private static SpringUtil instance = new SpringUtil();
	   private ApplicationContext context;
	   
	   public ApplicationContext getContext() {
		   return context;
	   }

	   public void setContext(ApplicationContext context) {
		   this.context = context;
	   }

	   public static SpringUtil getInstance(){
		   return instance;
	   }


}
