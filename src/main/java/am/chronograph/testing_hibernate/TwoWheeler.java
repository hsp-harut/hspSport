package am.chronograph.testing_hibernate;

import javax.persistence.Entity;

/**
 * SupClass for Vehice.... testing inheritance in hibernate
 * 
 * @author lusnak
 *
 */
@Entity
public class TwoWheeler extends Vehicle {

	private String steeringHnadle;

	/**
	 * @return the steeringHnadle
	 */
	public String getSteeringHnadle() {
		return steeringHnadle;
	}

	/**
	 * @param steeringHnadle the steeringHnadle to set
	 */
	public void setSteeringHnadle(String steeringHnadle) {
		this.steeringHnadle = steeringHnadle;
	}

}
