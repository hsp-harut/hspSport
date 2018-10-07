package am.chronograph.dao.contract;

import am.chronograph.domain.contract.Contract;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchCriteriaImpl;
import am.chronograph.web.util.Constants.ContractStatus;

/**
 * The implementation of Search API for {@link Contract} domain object.
 *
 * @author tigran
 *
 */
public class ContractSearchCriteria extends SearchCriteriaImpl<Contract> implements SearchCriteria<Contract> {

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -6060693698488545084L;

    private Long id;
    
    private String contractNumber;  
    private String bankAccountNumber;
    
    private String fullName;
        
    private String address; 
    private String workingPlace;
    
    private String hotel;
    private Integer nightsNumber;       
    private String agent;
        
    private String amountOld;               
    private String amountNew;    
    private String amountRelax;    
    private String amountPersonal;          
    private String amountTotal;
    
    private String periodicPayment;        
    private String amountRemains;
          
    private ContractStatus status;
    private String statusFilterBy;
    
    private String enterDate;
    private String startDate; 
    private String endDate;
    
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
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
     * @return the statusFilterBy
     */
    public String getStatusFilterBy() {
        return statusFilterBy;
    }

    /**
     * @param statusFilterBy the statusFilterBy to set
     */
    public void setStatusFilterBy(String statusFilterBy) {
        this.statusFilterBy = statusFilterBy;
    }

    /**
     * @return the enterDate
     */
    public String getEnterDate() {
        return enterDate;
    }

    /**
     * @param enterDate the enterDate to set
     */
    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }       
}
