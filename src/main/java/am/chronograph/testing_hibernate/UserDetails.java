package am.chronograph.testing_hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "sport")
public class UserDetails {
	@Id
	private int id;
	private String first_name;
	private String last_name;
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
