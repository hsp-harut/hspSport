package am.chronograph.testWithXML;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class Circle1 implements Shape, ApplicationEventPublisherAware {

	private Point center;
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource message;

	@Override
	public void draw() {
		System.out.println(this.message.getMessage("drawing.circle", null, "Default MESSAGEEEE....", null));
		
		System.out.println(this.message.getMessage("drawing.point", new Object[] {center.getX(), center.getY()}, "Default MESSAGEEEE....", null));
		
		//System.out.println("Center = (" + center.getX() + ", " + center.getY() + ") ");
		
		//System.out.println(this.message.getMessage("greeting", null, "Default Greeting....", null));

		
		DrawEvent drawEvent = new DrawEvent(this);
		publisher.publishEvent(drawEvent);
	}

	/*
	 * @Autowired
	 * 
	 * @Qualifier("circleRelated")
	 */
	@Resource(name = "point")
	public void setCenter(Point center) {
		this.center = center;
	}

	@PostConstruct
	public void initializeCircle() {
		System.out.println("Init of Circle");
	}

	@PreDestroy
	public void destroyCircle() {
		System.out.println("Destroy of Circle");
	}

	public Point getCenter() {
		return center;
	}

	public MessageSource getMessage() {
		return message;
	}
 
	@Autowired
	public void setMessage(MessageSource message) {
		this.message = message;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
	 this.publisher = publisher;
		
	}



}