package am.chronograph.testing_hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Class for initialize nested property, UserDetails class.
 * 
 * @author lusnak
 *
 */
@Embeddable
public class Address {

	@Column(name = "City_Name")
	private String city;
	
	@Column(name = "Street_Name")
	private String street;

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

}
