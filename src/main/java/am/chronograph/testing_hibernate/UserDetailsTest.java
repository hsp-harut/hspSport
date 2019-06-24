package am.chronograph.testing_hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * UserDetails for testing annotation.
 * 
 * @author lusnak
 *
 */
@Entity
 //  @Table(name = "User_Details_Test")
public class UserDetailsTest {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	/*
	 * @OneToOne
	 * 
	 * @JoinColumn(name="VEHICLE_ID") private Vehicle vehicle; // relaction maping
	 * One To Ones
	 * 
	 *//**
		 * @return the vehicle
		 */
	/*
	 * public Vehicle getVehicle() { return vehicle; }
	 * 
	 *//**
		 * @param vehicle the vehicle to set
		 */
	/*
	 * public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
	 */

	
	
	
	/*
	 *  Relationship ManyToMany......
	 *  
	 * @ManyToMany private List<Vehicle> vehicle = new ArrayList<Vehicle>();
	 * 
	 *//**
		 * @return the vehicle
		 */
	/*
	 * public List<Vehicle> getVehicle() { return vehicle; }
	 * 
	 *//**
		 * @param vehicle the vehicle to set
		 *//*
			 * public void setVehicle(List<Vehicle> vehicle) { this.vehicle = vehicle; }
			 */

	
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
