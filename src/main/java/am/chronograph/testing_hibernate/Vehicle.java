package am.chronograph.testing_hibernate;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)   // create one table in all properties
public class Vehicle {

	@Id
	@GeneratedValue
	private int vehicleId;
	private String vehicleName;
	
	//if the instance does not have get () and set (), we use this annotation to avoid generating an exception
	@NotFound(action = NotFoundAction.IGNORE)
	private UserDetailsTest usersss;
	
	/*
	 * Relationship ManyToMany
	 * 
	 * @ManyToMany(mappedBy = "vehicle") private Collection<UserDetailsTest>
	 * userList = new ArrayList<>();
	 * 
	 *//**
		 * @return the userList
		 */
	/*
	 * public Collection<UserDetailsTest> getUserList() { return userList; }
	 *//**
		 * @param userList the userList to set
		 *//*
			 * public void setUserList(Collection<UserDetailsTest> userList) { this.userList
			 * = userList; }
			 */
	
	

	/**
	 * @return the vehicleId
	 */
	public int getVehicleId() {
		return vehicleId;
	}

	/**
	 * @param vehicleId the vehicleId to set
	 */
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	/**
	 * @return the vehicleName
	 */
	public String getVehicleName() {
		return vehicleName;
	}

	/**
	 * @param vehicleName the vehicleName to set
	 */
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

}
