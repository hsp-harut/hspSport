package am.chronograph.test;

import java.util.List;

/**
 * class which implements Shape and override method(s)
 * 
 * @author gevorg
 *
 */
public class Triangle {

	private String type;
	private List<Point> points;

	/**
	 * method for print triangle
	 */
	public void draw() {
		
		for(Point point : points) {
		System.out.println(point.getX() + " " + point.getY());
		}
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
	 * @return the points
	 */
	public List<Point> getPoints() {
		return points;
	}

	/**
	 * @param points
	 *            the points to set
	 */
	public void setPoints(List<Point> points) {
		this.points = points;
	}

}
