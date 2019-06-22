package am.chronograph.testWithXML;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

public class DrawingApp {

	public static void main(String[] args) {
//		BeanFactory factory = new XmlBeanFactory(new FileSystemResource("/home/harut/projects/chronograph/src/main/resources/testSpringBeans.xml"));
//		Triangle triangle = (Triangle) factory.getBean("triangle");

		ApplicationContext context = new ClassPathXmlApplicationContext("testSpringBeans.xml");

//		AbstractApplicationContext context = new ClassPathXmlApplicationContext("testSpringBeans.xml");
//		context.registerShutdownHook();   // for closing Application context

		/*
		 * Triangle triangle = (Triangle) context.getBean("triangle"); 
		 * triangle.draw();     // draw triangle
		 * 
		 * Circle circle = (Circle) context.getBean("circle");
		 *  circle.draw(); //draw  circle.
		 */

		Shape shape = (Shape) context.getBean("triangle");
		shape.draw();

	}
}
