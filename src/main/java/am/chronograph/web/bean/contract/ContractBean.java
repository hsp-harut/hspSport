package am.chronograph.web.bean.contract;

import java.util.Date;

import am.chronograph.util.ChronoUtil;
import am.chronograph.web.bean.BaseBean;
import am.chronograph.web.util.Constants.ContractStatus;

/**
 * Bean class corresponds to Contracts. Will contain presentation layer related data...
 * @author HARUT
 */
public class ContractBean extends BaseBean {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = -6072263847165830419L;
	
	private Long id;

	private String contractNumber;	
	private String bankAccountNumber;
	private String bankAccountNumberOld;
	
	private String firstName;	
	private String lastName;	
	private String middleName;
		
	private String address;	
	private String workingPlace;
	
	private String hotel;
	private Integer nightsNumber;		
	private String agent;
		
	private String amountOld;	
	private Double amountOldDouble; // The same as above property, but in Double type. We need this only for product amount in the searching results -- to use <f:f:convertNumber/> for the correct format.
	
	private String amountNew;
	private Double amountNewDouble;
	
	private String amountRelax;	
	private Double amountRelaxDouble;
	
	private String amountPersonal;	
	private Double amountPersonalDouble;
	
	private String amountTotal;
	private Double amountTotalDouble;
		
	private String periodicPayment;
	private Double periodicPaymentDouble;
	private Double periodicPaymentOld;
	
	private String amountRemains;
	private Double amountRemainsDouble;
	
	private boolean copy;	
	private ContractStatus status;
	
	private Date enterDate;
	private Date startDate;		
	private Date endDate;
	private Date periodicLastUpdatedAt;
	
	/**
	 * Default constructor...
	 */
	public ContractBean() {
	}
	
	/**
	 * Copy constructor...
	 * @param contractBean
	 */
	public ContractBean(ContractBean contractBean) {
		id = contractBean.getId();
		
		contractNumber = contractBean.getContractNumber();	
		bankAccountNumber = contractBean.getBankAccountNumber();
		bankAccountNumberOld = contractBean.getBankAccountNumberOld();
		
		firstName = contractBean.getFirstName();	
		lastName = contractBean.getLastName();
		middleName = contractBean.getMiddleName();
			
		address = contractBean.getAddress();
		workingPlace = contractBean.getWorkingPlace();
		
		hotel = contractBean.getHotel();
		nightsNumber = contractBean.getNightsNumber();
		agent = contractBean.getAgent();
			
		amountOld = contractBean.getAmountOld();
		amountOldDouble = contractBean.getAmountOldDouble();
		
		amountNew = contractBean.getAmountNew();
		amountNewDouble = contractBean.getAmountNewDouble();
		
		amountRelax = contractBean.getAmountRelax();
		amountRelaxDouble = contractBean.getAmountRelaxDouble();
		
		amountPersonal = contractBean.getAmountPersonal();	
		amountPersonalDouble = contractBean.getAmountPersonalDouble();
		
		amountTotal = contractBean.getAmountTotal();
		amountTotalDouble = contractBean.getAmountTotalDouble();
			
		periodicPayment = contractBean.getPeriodicPayment();
		periodicPaymentDouble = contractBean.getPeriodicPaymentDouble();
		periodicPaymentOld = contractBean.getPeriodicPaymentOld();
		
		amountRemains = contractBean.getAmountRemains();
		amountRemainsDouble = contractBean.getAmountRemainsDouble();
		
		copy = contractBean.isCopy();
		status = contractBean.getStatus();
		
		enterDate = contractBean.getEnterDate();
		startDate = contractBean.getStartDate();
		endDate = contractBean.getEndDate();
	}
	
	/**
	 * Calculate total amoun based on corresponding amount data...
	 */
	public void calulateTotalAmount() {
	    amountTotalDouble = ChronoUtil.getDoubleOrZeroByString(amountOld) + ChronoUtil.getDoubleOrZeroByString(amountNew) 
	                                + ChronoUtil.getDoubleOrZeroByString(amountRelax) + ChronoUtil.getDoubleOrZeroByString(amountPersonal);
	    
	    amountTotal = ChronoUtil.getDoubleWithoutExponentialFormatAndDotZero(amountTotalDouble.toString());
	}
	
	/**
	 * Checks whether bank account has been updated or not...
	 * @return
	 */
	public boolean isBankAccountChanged() {
		return !bankAccountNumber.equals(bankAccountNumberOld);
	}
	
	/**
     * Checks whether periodic payment has been updated or not...
     * @return
     */
    public boolean isPeriodicPaymentChanged() {
        return (id != null) && !ChronoUtil.getDoubleOrZeroByString(periodicPayment).equals(periodicPaymentOld);
    }
	
	/**
	 * Initialize those Double values which have corresponding String representation...
	 * by corresponding String properties. Again -- these Double
	 * properties we only need to use in searching results datatables(To show them in the correct number format...).
	 */
	public void initDoubleValuesByString() {
		amountOldDouble = Double.parseDouble(amountOld);
		amountNewDouble = Double.parseDouble(amountNew);
		amountRelaxDouble = Double.parseDouble(amountRelax);
		amountPersonalDouble = Double.parseDouble(amountPersonal);
		amountTotalDouble = Double.parseDouble(amountTotal);
		amountRemainsDouble = Double.parseDouble(amountRemains);
		
		periodicPaymentDouble = Double.parseDouble(periodicPayment);
	}
	
	/**
	 * When contract is edited, double string properties are initialized.
	 * When we are assigning Double to String (toString() method) then String get
	 * the value with the last '.0' chars in the end - even when Double has no precision part...
	 * So that would be user friendly to remove this part from the string before showing it in the Edit UI...
	 * 
	 * Without this method we have in the UI e.g. 'price = 10.0, amount = 25.0'...
	 */
	public void removeLastTwoDoubleFromDoubleStrings() {
		amountOld = ChronoUtil.getDoubleWithoutExponentialFormatAndDotZero(amountOld);
		amountNew = ChronoUtil.getDoubleWithoutExponentialFormatAndDotZero(amountNew);
		amountRelax = ChronoUtil.getDoubleWithoutExponentialFormatAndDotZero(amountRelax);
		amountPersonal = ChronoUtil.getDoubleWithoutExponentialFormatAndDotZero(amountPersonal);
		amountTotal = ChronoUtil.getDoubleWithoutExponentialFormatAndDotZero(amountTotal);
		amountRemains = ChronoUtil.getDoubleWithoutExponentialFormatAndDotZero(amountRemains);
		periodicPayment = ChronoUtil.getDoubleWithoutExponentialFormatAndDotZero(periodicPayment);
	}
	
	/**
	 * Get status info 
	 * @return
	 */
	public String getStatusInfo() {
		if(status == ContractStatus.Overdue) {
			return "Ժամկետանց";
		}
		
		if(status == ContractStatus.Paid) {
			return "Մարված";
		}
		
		if(status == ContractStatus.Overpaid) {
			return "Գերավճար";
		}
		
		return status.name();
	}
	
	/**
	 * Get full name...
	 * @return
	 */
	public String getFullName() {
	    return String.join(" ", lastName, firstName, middleName.endsWith("ի") ? middleName : (middleName + "ի"));
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the bankAccountNumberOld
	 */
	public String getBankAccountNumberOld() {
		return bankAccountNumberOld;
	}

	/**
	 * @param bankAccountNumberOld the bankAccountNumberOld to set
	 */
	public void setBankAccountNumberOld(String bankAccountNumberOld) {
		this.bankAccountNumberOld = bankAccountNumberOld;
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
	public String getAmountOld() {
		return amountOld;
	}

	/**
	 * @param amountOld the amountOld to set
	 */
	public void setAmountOld(String amountOld) {
		this.amountOld = amountOld;
	}

	/**
	 * @return the amountOldDouble
	 */
	public Double getAmountOldDouble() {
		return amountOldDouble;
	}

	/**
	 * @param amountOldDouble the amountOldDouble to set
	 */
	public void setAmountOldDouble(Double amountOldDouble) {
		this.amountOldDouble = amountOldDouble;
	}

	/**
	 * @return the amountNew
	 */
	public String getAmountNew() {
		return amountNew;
	}

	/**
	 * @param amountNew the amountNew to set
	 */
	public void setAmountNew(String amountNew) {
		this.amountNew = amountNew;
	}

	/**
	 * @return the amountNewDouble
	 */
	public Double getAmountNewDouble() {
		return amountNewDouble;
	}

	/**
	 * @param amountNewDouble the amountNewDouble to set
	 */
	public void setAmountNewDouble(Double amountNewDouble) {
		this.amountNewDouble = amountNewDouble;
	}

	/**
	 * @return the amountRelax
	 */
	public String getAmountRelax() {
		return amountRelax;
	}

	/**
	 * @param amountRelax the amountRelax to set
	 */
	public void setAmountRelax(String amountRelax) {
		this.amountRelax = amountRelax;
	}

	/**
	 * @return the amountRelaxDouble
	 */
	public Double getAmountRelaxDouble() {
		return amountRelaxDouble;
	}

	/**
	 * @param amountRelaxDouble the amountRelaxDouble to set
	 */
	public void setAmountRelaxDouble(Double amountRelaxDouble) {
		this.amountRelaxDouble = amountRelaxDouble;
	}

	/**
	 * @return the amountPersonal
	 */
	public String getAmountPersonal() {
		return amountPersonal;
	}

	/**
	 * @param amountPersonal the amountPersonal to set
	 */
	public void setAmountPersonal(String amountPersonal) {
		this.amountPersonal = amountPersonal;
	}

	/**
	 * @return the amountPersonalDouble
	 */
	public Double getAmountPersonalDouble() {
		return amountPersonalDouble;
	}

	/**
	 * @param amountPersonalDouble the amountPersonalDouble to set
	 */
	public void setAmountPersonalDouble(Double amountPersonalDouble) {
		this.amountPersonalDouble = amountPersonalDouble;
	}	
	
	/**
	 * @return the periodicPayment
	 */
	public String getPeriodicPayment() {
		return periodicPayment;
	}

	/**
	 * @param periodicPayment the periodicPayment to set
	 */
	public void setPeriodicPayment(String periodicPayment) {
		this.periodicPayment = periodicPayment;
	}

	/**
	 * @return the periodicPaymentDouble
	 */
	public Double getPeriodicPaymentDouble() {
		return periodicPaymentDouble;
	}

	/**
	 * @param periodicPaymentDouble the periodicPaymentDouble to set
	 */
	public void setPeriodicPaymentDouble(Double periodicPaymentDouble) {
		this.periodicPaymentDouble = periodicPaymentDouble;
	}		
	
	/**
     * @return the periodicPaymentOld
     */
    public Double getPeriodicPaymentOld() {
        return periodicPaymentOld;
    }

    /**
     * @param periodicPaymentOld the periodicPaymentOld to set
     */
    public void setPeriodicPaymentOld(Double periodicPaymentOld) {
        this.periodicPaymentOld = periodicPaymentOld;
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

    /**
	 * @return the amountTotal
	 */
	public String getAmountTotal() {
		return amountTotal;
	}

	/**
	 * @param amountTotal the amountTotal to set
	 */
	public void setAmountTotal(String amountTotal) {
		this.amountTotal = amountTotal;
	}

	/**
	 * @return the amountTotalDouble
	 */
	public Double getAmountTotalDouble() {
		return amountTotalDouble;
	}

	/**
	 * @param amountTotalDouble the amountTotalDouble to set
	 */
	public void setAmountTotalDouble(Double amountTotalDouble) {
		this.amountTotalDouble = amountTotalDouble;
	}

	/**
	 * @return the amountRemains
	 */
	public String getAmountRemains() {
		return amountRemains;
	}

	/**
	 * @param amountRemains the amountRemains to set
	 */
	public void setAmountRemains(String amountRemains) {
		this.amountRemains = amountRemains;
	}

	/**
	 * @return the amountRemainsDouble
	 */
	public Double getAmountRemainsDouble() {
		return amountRemainsDouble;
	}

	/**
	 * @param amountRemainsDouble the amountRemainsDouble to set
	 */
	public void setAmountRemainsDouble(Double amountRemainsDouble) {
		this.amountRemainsDouble = amountRemainsDouble;
	}		
}