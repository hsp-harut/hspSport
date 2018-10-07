package am.chronograph.domain.contract;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import am.chronograph.domain.AuditAwareEntity;
import am.chronograph.util.ChronoUtil;
import am.chronograph.web.util.Constants.ContractStatus;

/**
 * Domain object. ORM to contract table.
 * 
 * @author HARUT
 */
@Entity
@Table(name = "contract")
public class Contract extends AuditAwareEntity {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = -8698575474772679588L;

	@Column(name = "contractNumber", nullable = false, insertable = true, updatable = true)
	private String contractNumber;
	
	@Column(name = "bankAccountNumber", nullable = false, insertable = true, updatable = true)
	private String bankAccountNumber;
	
	@Column(name = "first_name", nullable = true, insertable = true, updatable = true)
	private String firstName;
	
	@Column(name = "last_name", nullable = true, insertable = true, updatable = true)
	private String lastName;
	
	@Column(name = "middle_name", nullable = true, insertable = true, updatable = true)
	private String middleName;
	
	@Column(name = "address", nullable = true, insertable = true, updatable = true)
	private String address;
	
	@Column(name = "working_place", nullable = true, insertable = true, updatable = true)
	private String workingPlace;
	
	@Column(name = "hotel", nullable = true, insertable = true, updatable = true)
	private String hotel;
	
	@Column(name = "nights_number", nullable = true, insertable = true, updatable = true)
	private Integer nightsNumber;
	
	@Column(name = "agent", nullable = true, insertable = true, updatable = true)
	private String agent;
	
	@Column(name = "amount_old", nullable = true, insertable = true, updatable = true)
	private Double amountOld;
	
	@Column(name = "amount_new", nullable = true, insertable = true, updatable = true)
	private Double amountNew;
	
	@Column(name = "amount_relax", nullable = true, insertable = true, updatable = true)
	private Double amountRelax;
	
	@Column(name = "amount_personal", nullable = true, insertable = true, updatable = true)
	private Double amountPersonal;
	
	@Column(name = "amount_total", nullable = true, insertable = true, updatable = true)
	private Double amountTotal;
	
	@Column(name = "periodic_payment", nullable = true, insertable = true, updatable = true)
	private Double periodicPayment;
	
	@Column(name = "amount_remains", nullable = true, insertable = true, updatable = true)
	private Double amountRemains;
	
	@Column(name = "isCopy", nullable = false, insertable = true, updatable = true)
	private boolean copy;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", insertable = true, nullable = false, updatable = true)
	private ContractStatus status;
	
	@Column(name = "contract_enter_date", nullable = true)
    private LocalDateTime enterDate;
	
	@Column(name = "contract_start_date", nullable = true)
	private LocalDateTime startDate;
	
	@Column(name = "contract_end_date", nullable = true)
	private LocalDateTime endDate;
		
	@Column(name = "peiodic_last_updated_at", nullable = false)
    private LocalDateTime periodicLastUpdatedAt;
	
	/**
	 * Default constructor...
	 */
	public Contract() {		
	}
	
	/**
	 * Calculates amount remains, and updates status if needed...
	 */
	@Transient
	public void initAmountRemainsAndStatus(LocalDateTime periodicUpdatedDate) {
		amountRemains = ChronoUtil.subtract(amountTotal, periodicPayment);
		
		/* when contract is updated, then we have to update also a status*/
		if(id != null) {
		    
		    if(periodicUpdatedDate != null) {
		        periodicLastUpdatedAt = periodicUpdatedDate;
		    }
		    
			if(amountRemains == 0) {
				status = ContractStatus.Paid;
			} else if(amountRemains < 0) {
				status = ContractStatus.Overpaid;
				
//			} else if(status != ContractStatus.Overdue) {
				
			/* when periodic payment is updated, then here we have 
			 * to reset status even for Overdue contracts */
			} else if(periodicUpdatedDate != null) {
				status = null;				
			}
		
		/* when contract is just created, then set periodic last updated value 
		 * to startDate - to make scheduler work correctly for defining Overdue contracts 
		 * (as 35 days starting point have to be contract start date - if no periodic payment is updated)...*/
		} else {
		    periodicLastUpdatedAt = startDate;
		}
	}

	/**
	 * @return the contractNumber
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * @param contractNumber the contractNumber to set
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * @return the bankAccountNumber
	 */
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	/**
	 * @param bankAccountNumber the bankAccountNumber to set
	 */
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
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
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the workingPlace
	 */
	public String getWorkingPlace() {
		return workingPlace;
	}

	/**
	 * @param workingPlace the workingPlace to set
	 */
	public void setWorkingPlace(String workingPlace) {
		this.workingPlace = workingPlace;
	}

	/**
	 * @return the hotel
	 */
	public String getHotel() {
		return hotel;
	}

	/**
	 * @param hotel the hotel to set
	 */
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	/**
	 * @return the nightsNumber
	 */
	public Integer getNightsNumber() {
		return nightsNumber;
	}

	/**
	 * @param nightsNumber the nightsNumber to set
	 */
	public void setNightsNumber(Integer nightsNumber) {
		this.nightsNumber = nightsNumber;
	}

	/**
	 * @return the agent
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * @param agent the agent to set
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * @return the amountOld
	 */
	public Double getAmountOld() {
		return amountOld;
	}

	/**
	 * @param amountOld the amountOld to set
	 */
	public void setAmountOld(Double amountOld) {
		this.amountOld = amountOld;
	}

	/**
	 * @return the amountNew
	 */
	public Double getAmountNew() {
		return amountNew;
	}

	/**
	 * @param amountNew the amountNew to set
	 */
	public void setAmountNew(Double amountNew) {
		this.amountNew = amountNew;
	}

	/**
	 * @return the amountRelax
	 */
	public Double getAmountRelax() {
		return amountRelax;
	}

	/**
	 * @param amountRelax the amountRelax to set
	 */
	public void setAmountRelax(Double amountRelax) {
		this.amountRelax = amountRelax;
	}

	/**
	 * @return the amountPersonal
	 */
	public Double getAmountPersonal() {
		return amountPersonal;
	}

	/**
	 * @param amountPersonal the amountPersonal to set
	 */
	public void setAmountPersonal(Double amountPersonal) {
		this.amountPersonal = amountPersonal;
	}

	/**
	 * @return the amountTotal
	 */
	public Double getAmountTotal() {
		return amountTotal;
	}

	/**
	 * @param amountTotal the amountTotal to set
	 */
	public void setAmountTotal(Double amountTotal) {
		this.amountTotal = amountTotal;
	}

	/**
	 * @return the periodicPayment
	 */
	public Double getPeriodicPayment() {
		return periodicPayment;
	}

	/**
	 * @param periodicPayment the periodicPayment to set
	 */
	public void setPeriodicPayment(Double periodicPayment) {
		this.periodicPayment = periodicPayment;
	}

	/**
	 * @return the amountRemains
	 */
	public Double getAmountRemains() {
		return amountRemains;
	}

	/**
	 * @param amountRemains the amountRemains to set
	 */
	public void setAmountRemains(Double amountRemains) {
		this.amountRemains = amountRemains;
	}		

	/**
	 * @return the copy
	 */
	public boolean isCopy() {
		return copy;
	}

	/**
	 * @param copy the copy to set
	 */
	public void setCopy(boolean copy) {
		this.copy = copy;
	}

	/**
	 * @return the status
	 */
	public ContractStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(ContractStatus status) {
		this.status = status;
	}		

	/**
     * @return the enterDate
     */
    public LocalDateTime getEnterDate() {
        return enterDate;
    }

    /**
     * @param enterDate the enterDate to set
     */
    public void setEnterDate(LocalDateTime enterDate) {
        this.enterDate = enterDate;
    }

    /**
	 * @return the startDate
	 */
	public LocalDateTime getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public LocalDateTime getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

    /**
     * @return the periodicLastUpdatedAt
     */
    public LocalDateTime getPeriodicLastUpdatedAt() {
        return periodicLastUpdatedAt;
    }

    /**
     * @param periodicLastUpdatedAt the periodicLastUpdatedAt to set
     */
    public void setPeriodicLastUpdatedAt(LocalDateTime periodicLastUpdatedAt) {
        this.periodicLastUpdatedAt = periodicLastUpdatedAt;
    }		
}
