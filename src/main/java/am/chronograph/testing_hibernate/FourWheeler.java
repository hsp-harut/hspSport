package am.chronograph.testing_hibernate;

import javax.persistence.Entity;

/**
 * SupClass for Vehice.... testing inheritance in hibernate
 * 
 * @author lusnak
 *
 */
@Entity
public class FourWheeler extends Vehicle {

	/**
	 * @return the steeringWheel
	 */
	public String getSteeringWheel() {
		return steeringWheel;
	}

	/**
	 * @param steeringWheel the steeringWheel to set
	 */
	public void setSteeringWheel(String steeringWheel) {
		this.steeringWheel = steeringWheel;
	}

	private String steeringWheel;

}
