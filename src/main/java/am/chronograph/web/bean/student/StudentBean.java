package am.chronograph.web.bean.student;

import java.util.Date;

import am.chronograph.web.bean.BaseBean;

public class StudentBean extends BaseBean {

	private static final long serialVersionUID = 1241875206414585150L;

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String eMail;

	private Date enterDate;
	private Date startDate;
	private Date endDate;
	private Date periodicLastUpdatedAt;

	/**
	 * Default constructor...
	 */
	public StudentBean() {

	}

	/**
	 * Copy Constructor....
	 * 
	 * @param studentBean
	 */
	public StudentBean(StudentBean studentBean) {

		firstName = studentBean.getFirstName();
		lastName = studentBean.getLastName();
		phoneNumber = studentBean.getPhoneNumber();
		eMail = studentBean.geteMail();

	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the eMail
	 */
	public String geteMail() {
		return eMail;
	}

	/**
	 * @param eMail the eMail to set
	 */
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * @return the enterDate
	 */
	public Date getEnterDate() {
		return enterDate;
	}

	/**
	 * @param enterDate the enterDate to set
	 */
	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the periodicLastUpdatedAt
	 */
	public Date getPeriodicLastUpdatedAt() {
		return periodicLastUpdatedAt;
	}

	/**
	 * @param periodicLastUpdatedAt the periodicLastUpdatedAt to set
	 */
	public void setPeriodicLastUpdatedAt(Date periodicLastUpdatedAt) {
		this.periodicLastUpdatedAt = periodicLastUpdatedAt;
	}

}
