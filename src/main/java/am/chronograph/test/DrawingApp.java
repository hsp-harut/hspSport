package am.chronograph.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DrawingApp {

	public static void main(String[] args) {
		/*BeanFactory factory = new XmlBeanFactory(new FileSystemResource("/home/harut/projects/chronograph/src/main/resources/testSpringBeans.xml"));
		Triangle triangle = (Triangle) factory.getBean("triangle");
	
		triangle.draw();*/
		
		//ApplicationContext context = new ClassPathXmlApplicationContext("testSpringBeans.xml");		
		
		/*AbstractApplicationContext context = new ClassPathXmlApplicationContext("testSpringBeans.xml");
		context.registerShutdownHook();
		
		Triangle triangle = (Triangle) context.getBean("triangle");
		
		triangle.draw();*/
		
		ApplicationContext context = new ClassPathXmlApplicationContext("testSpringBeans.xml");
		Triangle triangle = (Triangle) context.getBean("triangle");
		triangle.draw();
		
		
		
	}
}
