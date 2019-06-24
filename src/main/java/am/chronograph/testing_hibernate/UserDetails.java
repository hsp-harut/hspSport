package am.chronograph.testing_hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class UserDetails {
	@Id
	@GeneratedValue // generated new id, for primary key field.
	private int id;
	private String first_name;
	private String last_name;
	
	@ElementCollection   // use for add element in collection
    private Set<Address> listOfAddresses = new HashSet<>();
	
	/**
	 * @return the listOfAddresses
	 */
	public Set<Address> getListOfAddresses() {
		return listOfAddresses;
	}

	/**
	 * @param listOfAddresses the listOfAddresses to set
	 */
	public void setListOfAddresses(Set<Address> listOfAddresses) {
		this.listOfAddresses = listOfAddresses;
	}
	
	
	@Embedded // nested property for address class, which contain 2 property
	@AttributeOverrides({

			@AttributeOverride(name = "street", column = @Column(name = "HOME_STREET_NAME")),
			@AttributeOverride(name = "city", column = @Column(name = "HOME_CITY_NAME")) })

	private Address homeAddress;

	/**
	 * @return the homeAddress
	 */
	public Address getHomeAddress() {
		return homeAddress;
	}

	/**
	 * @param homeAddress the homeAddress to set
	 */
	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	/**
	 * @return the officeAddress
	 */
	public Address getOfficeAddress() {
		return officeAddress;
	}

	/**
	 * @param officeAddress the officeAddress to set
	 */
	public void setOfficeAddress(Address officeAddress) {
		this.officeAddress = officeAddress;
	}

	@Embedded // nested property for address class, which contain 2 property
	private Address officeAddress;

	private int sport_id;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}

	/**
	 * @param first_name the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}

	/**
	 * @param last_name the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	/**
	 * @return the sport_id
	 */
	public int getSport_id() {
		return sport_id;
	}

	/**
	 * @param sport_id the sport_id to set
	 */
	public void setSport_id(int sport_id) {
		this.sport_id = sport_id;
	}

}
