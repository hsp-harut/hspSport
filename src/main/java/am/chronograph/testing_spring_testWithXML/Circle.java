package am.chronograph.testing_spring_testWithXML;

import org.springframework.beans.factory.annotation.Required;

public class Circle implements Shape {

	private Point center;
	
	@Override
	public void draw() {
		System.out.println("Draw Circle: ");	

		System.out.println("Point A = (" + getCenter().getX() + ", " + getCenter().getY() + "); ");
	}
	

	/**
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * @param center the center to set
	 */
	/*
	 * @Required  //for required annotation
	 */
	public void setCenter(Point center) {
		this.center = center;
	}


}
