package am.chronograph.test;

public class Triangle implements Shape {
	private String type;
	private int height;
	
 public Triangle(String type) {
		this.type = type;
	}
 
 public Triangle(String type, int height) {
		this.type = type;
		this.height = height;
	}
	
	@Override
	public void draw() {
		System.out.println(getType() + " Drawing a Triangle");
	}
	
	/*public void setType(String type) {
		this.type = type;
	}*/
	
	public String getType() {
		return type;
	}
	
	public int getHeight() {
		return height;
	}

}
