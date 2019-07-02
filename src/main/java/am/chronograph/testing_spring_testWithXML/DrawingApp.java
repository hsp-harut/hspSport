package am.chronograph.testing_spring_testWithXML;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DrawingApp {

	public static void main(String[] args) {
		/*BeanFactory factory = new XmlBeanFactory(new FileSystemResource("/home/harut/projects/chronograph/src/main/resources/testSpringBeans.xml"));
		Triangle triangle = (Triangle) factory.getBean("triangle");
	
		triangle.draw();*/
		

		//ApplicationContext context = new ClassPathXmlApplicationContext("testSpringBeans.xml");		
		
		/*AbstractApplicationContext context = new ClassPathXmlApplicationContext("testSpringBeans.xml");


//		AbstractApplicationContext context = new ClassPathXmlApplicationContext("testSpringBeans.xml");
//		context.registerShutdownHook();    // for closing Application context
		
		/*
		 * Triangle triangle = (Triangle) context.getBean("triangle");
		 * 
		 * triangle.draw();  // draw triangle
		 */
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("testSpringBeans.xml");

		context.registerShutdownHook();
		
		 Shape shape = (Shape) context.getBean("circle");
		 shape.draw();
		

		triangle.draw();*/
		
		ApplicationContext context = new ClassPathXmlApplicationContext("testSpringBeans.xml");
		Triangle triangle = (Triangle) context.getBean("triangle");
		triangle.draw();
		
		
		

		// System.out.println(context.getMessage("greeting", null, "Default Greeting....", null));

	}
}
