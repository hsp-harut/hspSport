package am.chronograph.test;

public class Triangle implements Shape {
	private String type;
	private int height;	
	
	/**
	 * @param type
	 */
	public Triangle(String type) {
		super();
		this.type = type;
	}	
		
	/**
	 * @param height
	 */
	public Triangle(int height) {
		super();
		this.height = height;
	}

	/**
	 * @param type
	 * @param height
	 */
	public Triangle(String type, int height) {
		super();
		this.type = type;
		this.height = height;
	}



	public void init() {
		System.out.println("Init method called!");
	}
	
	public void dest() {
		System.out.println("Dest method called!");
	}

	@Override
	public void draw() {
		System.out.println(getType() + " Triangle is Drawing by height of " + getHeight());
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
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

}
