package am.chronograph.test;

import java.util.ArrayList;
import java.util.List;

public class Triangle implements Shape {
	private String type;
	private List<Point> points = new ArrayList<Point>();
//	private Point A;
//	private Point B;
//	private Point C;

	@Override
	public void draw() {
		System.out.println("Drawing a " + type + " Triangle by points" + points.get(0) + points.get(1) + points.get(2));
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the points
	 */
	public List<Point> getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(List<Point> points) {
		this.points = points;
	}
}
