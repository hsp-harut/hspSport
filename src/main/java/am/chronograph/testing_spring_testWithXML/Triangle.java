package am.chronograph.testing_spring_testWithXML;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Triangle implements Shape, ApplicationContextAware, BeanNameAware, InitializingBean, DisposableBean {

	/*
	 * Use list for point class....
	 * 
	 * private List<Point> points;
	 * 
	 * public List<Point> getPoints() { return points; }
	 * 
	 * public void setPoints(List<Point> points) { this.points = points; }
	 */

	private Point pointA;
	private Point pointB;
	private Point pointC;

	private ApplicationContext context;

	@Override
	public void draw() {
		System.out.println("Drawing a Triangle");

		System.out.println("Point A = (" + getPointA().getX() + ", " + getPointA().getY() + "); ");
		System.out.println("Point B = (" + getPointB().getX() + ", " + getPointB().getY() + "); ");
		System.out.println("Point C = (" + getPointC().getX() + ", " + getPointC().getY() + "); ");

		/*
		 * Use for printing list points
		 * 
		 * for(Point point: points) { System.out.println("Point A = (" + points.getX() +
		 * ", " + points.getY() + "); "); }
		 */

	}

	/**
	 * @return the pointA
	 */
	public Point getPointA() {
		return pointA;
	}

	/**
	 * @param pointA the pointA to set
	 */
	public void setPointA(Point pointA) {
		this.pointA = pointA;
	}

	/**
	 * @return the pointB
	 */
	public Point getPointB() {
		return pointB;
	}

	/**
	 * @param pointB the pointB to set
	 */
	public void setPointB(Point pointB) {
		this.pointB = pointB;
	}

	/**
	 * @return the pointC
	 */
	public Point getPointC() {
		return pointC;
	}

	/**
	 * @param pointC the pointC to set
	 */
	public void setPointC(Point pointC) {
		this.pointC = pointC;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;

	}

	@Override
	public void setBeanName(String name) {
		System.out.println("Bean Name is " + name);

	}

	/**
	 * CallBack after initializing instance variable.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("InitializingBean init method called for  Triangle");

	}

	/**
	 */
	@Override
	public void destroy() throws Exception {
		System.out.println("DisposibleBean distroy method called for  Triangle");

	}
	
	
	public  void  myInit() {
		System.out.println("MyInit init method called for  Triangle");
	}

}
