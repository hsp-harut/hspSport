package am.chronograph.dao.contract;

import java.util.List;

import am.chronograph.dao.GenericDao;
import am.chronograph.domain.contract.Contract;
import am.chronograph.search.SearchSupport;

/**
 * Provides data access methods for Contract data.
 * 
 * @author HARUT
 */
public interface ContractDao extends GenericDao<Contract>, SearchSupport<Contract> {
	
	/**
	 * Get contract by contractNumber and not by id...
	 * @param name
	 * @param id
	 * @return
	 */
	Contract getContractByNumberNotById(String contractNumber, Long id);
	
	/**
     * Get contract by bank account and not by id...
     * @param bankAccountNumber
     * @param id
     * @return
     */
    List<Contract> getContractsByBankNotById(String bankAccountNumber, Long id);
    
    /**
     * Get contract by bank account...
     * @param bankAccountNumber
     * @param id
     * @return
     */
    List<Contract> getContractsByBank(String bankAccountNumber, List<String> agentTypes);
    
    /**
     * Get contracts which have start date before than days number before now...
     * @param days
     * @return
     */
    List<Contract> getContractsStartedLaterThanDaysNumber(int days);
    
    /**
     * Set statuc as copy for the contracts by given bank account...
     * @param bankAccountNumber
     */
    void setCopyContractsByBank(String bankAccountNumber);
	
	/**
     * Get needed totals by given search criteria...
     * @param criteria
     * @return
     */
    Object[] getTotals(ContractSearchCriteria criteria);
    
    /**
     * Checks and if needed updates status to Paid...
     */
    void checkAndUpdateStatusToPaid();
    
    /**
     * Checks and if needed updates status to Paid...
     */
    void checkAndUpdateStatusToOverPaid();
}
