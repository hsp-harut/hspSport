package am.chronograph.test;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * class which implements Shape and override method(s)
 * 
 * @author gevorg
 *
 */
public class Triangle implements Shape {

	private String type;
	private Point pointA;
	private Point pointB;
	private Point pointC;
	// private List<Point> points;

	/**
	 * method for print triangle
	 */

	public void draw() {

		// for (Point point : points) {
		// System.out.println(point.getX() + " " + point.getY());
		// }

		System.out.println(pointA.getX() + " " + pointA.getY());
		System.out.println(pointB.getX() + " " + pointB.getY());
		System.out.println(pointC.getX() + " " + pointC.getY());

	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/*
	 * @Override public void afterPropertiesSet() throws Exception {
	 * System.out.println("Start");
	 * 
	 * }
	 * 
	 * @Override public void destroy() throws Exception { System.out.println("End");
	 * 
	 * }
	 */

	/**
	 * method which will called from testSpringBeans.xml file and well print after
	 * init beans
	 */
	private void myInit() {
		System.out.println("MY INIT");
	}

	/**
	 * method which will called from testSpringBeans.xml file and well print before
	 * beans will destroy
	 */
	private void myDestroy() {
		System.out.println("MY END");
	}

	/**
	 * @return the pointA
	 */
	public Point getPointA() {
		return pointA;
	}

	/**
	 * @param pointA
	 *            the pointA to set
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
	 * @param pointB
	 *            the pointB to set
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
	 * @param pointC
	 *            the pointC to set
	 */
	public void setPointC(Point pointC) {
		this.pointC = pointC;
	}

	/**
	 * @return the points
	 */
	/*
	 * public List<Point> getPoints() { return points; }
	 * 
	 *//**
		 * @param points
		 *            the points to set
		 *//*
			 * public void setPoints(List<Point> points) { this.points = points; }
			 */

}
