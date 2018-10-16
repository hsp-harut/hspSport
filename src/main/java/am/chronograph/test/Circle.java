package am.chronograph.test;

/**
 * class for draw circle
 * 
 * @author gevorg
 *
 */
public class Circle implements Shape {

	private Point center;

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.chronograph.test.Shape#draw()
	 */
	@Override
	public void draw() {

		System.out.println("Drawing Circle");
		System.out.println(center.getX() + " " + center.getY());
	}

	/**
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * @param center
	 *            the center to set
	 */
	public void setCenter(Point center) {
		this.center = center;
	}

}
