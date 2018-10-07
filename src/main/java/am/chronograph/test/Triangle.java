package am.chronograph.test;

public class Triangle implements Shape {
	
	public void init() {
		System.out.println("Init method called!");
	}
	
	public void dest() {
		System.out.println("Dest method called!");
	}

	@Override
	public void draw() {
		System.out.println("Drawing a Triangle");
	}

}
