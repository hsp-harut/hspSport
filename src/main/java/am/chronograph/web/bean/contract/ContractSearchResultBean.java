package am.chronograph.web.bean.contract;

import am.chronograph.search.SearchResultBean;

/**
 * Search result corresponds to contracts. 
 * Besides general properties from super class, here we have to store required totals...
 * 
 * @author HARUT
 */
public class ContractSearchResultBean extends SearchResultBean<ContractBean> {

	private Double totalOfAmountOld;
	private Double totalOfAmountNew;
	private Double totalOfAmountRelax;
	private Double totalOfAmountPersonal;
	private Double totalOfAmountRemains;
	
	private Double totalOfAmountTotal;        
    private Double totalOfPeriodicPayment;        
    
	/**
	 * @return the totalOfAmountOld
	 */
	public Double getTotalOfAmountOld() {
		return totalOfAmountOld;
	}

	/**
	 * @param totalOfAmountOld the totalOfAmountOld to set
	 */
	public void setTotalOfAmountOld(Double totalOfAmountOld) {
		this.totalOfAmountOld = totalOfAmountOld;
	}

	/**
	 * @return the totalOfAmountNew
	 */
	public Double getTotalOfAmountNew() {
		return totalOfAmountNew;
	}

	/**
	 * @param totalOfAmountNew the totalOfAmountNew to set
	 */
	public void setTotalOfAmountNew(Double totalOfAmountNew) {
		this.totalOfAmountNew = totalOfAmountNew;
	}

	/**
	 * @return the totalOfAmountRelax
	 */
	public Double getTotalOfAmountRelax() {
		return totalOfAmountRelax;
	}

	/**
	 * @param totalOfAmountRelax the totalOfAmountRelax to set
	 */
	public void setTotalOfAmountRelax(Double totalOfAmountRelax) {
		this.totalOfAmountRelax = totalOfAmountRelax;
	}

	/**
	 * @return the totalOfAmountPersonal
	 */
	public Double getTotalOfAmountPersonal() {
		return totalOfAmountPersonal;
	}

	/**
	 * @param totalOfAmountPersonal the totalOfAmountPersonal to set
	 */
	public void setTotalOfAmountPersonal(Double totalOfAmountPersonal) {
		this.totalOfAmountPersonal = totalOfAmountPersonal;
	}		

	/**
     * @return the totalOfAmountRemains
     */
    public Double getTotalOfAmountRemains() {
        return totalOfAmountRemains;
    }

    /**
     * @param totalOfAmountRemains the totalOfAmountRemains to set
     */
    public void setTotalOfAmountRemains(Double totalOfAmountRemains) {
        this.totalOfAmountRemains = totalOfAmountRemains;
    }

    /**
	 * @return the totalOfAmountTotal
	 */
	public Double getTotalOfAmountTotal() {
		return totalOfAmountTotal;
	}
	
	/**
	 * @param totalOfAmountTotal the totalOfAmountTotal to set
	 */
	public void setTotalOfAmountTotal(Double totalOfAmountTotal) {
		this.totalOfAmountTotal = totalOfAmountTotal;
	}
	
	/**
	 * @return the totalOfPeriodicPayment
	 */
	public Double getTotalOfPeriodicPayment() {
		return totalOfPeriodicPayment;
	}
	
	/**
	 * @param totalOfPeriodicPayment the totalOfPeriodicPayment to set
	 */
	public void setTotalOfPeriodicPayment(Double totalOfPeriodicPayment) {
		this.totalOfPeriodicPayment = totalOfPeriodicPayment;
	}        
}
