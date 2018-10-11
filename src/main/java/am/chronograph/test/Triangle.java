package am.chronograph.test;

/**
 * class which implements Shape and override method(s)
 * 
 * @author gevorg
 *
 */
public class Triangle {

	private String type;

	/**
	 * method for print triangle
	 */
	public void draw() {
		System.out.println(getType() + " Drawing a Triangle");
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

}
