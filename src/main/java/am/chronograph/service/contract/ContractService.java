package am.chronograph.service.contract;

import java.util.List;

import am.chronograph.domain.contract.Contract;
import am.chronograph.service.SearchBeanAware;
import am.chronograph.web.bean.contract.ContractBean;
import am.chronograph.web.bean.contract.ExcelContractBean;

/**
 * Provides services for getting, managing Contracts.
 * 
 * @author HARUT
 */
public interface ContractService extends SearchBeanAware<ContractBean, Contract>{

	/**
	 * Create or Update given Exam object in the db.
	 * @param contractBean
	 */
	Long createUpdateContract(ContractBean contractBean, boolean newCopyCreated);	
		
	/**
	 * Delete exam by given id...
	 * @param examId
	 */
	void deleteContract(Long contractId);
	
	/**
     * Checks whether is there another Contract by given contract number or not...
     * @param contractNumber
     * @param id
     * @return
     */
    boolean existContractByNumberNotById(String contractNumber, Long id);
    
    /**
     * Checks whether is there another Contract by given contract bankAccount or not...
     * @param contractNumber
     * @param id
     * @return
     */
    boolean existContractByBankNotById(String bankAccount, Long id);
	
	/**
	 * Search exams by given criteria...
	 * @return
	 */
	List<ContractBean> getAllContracts();
	
	/**
	 * Get contract by given id...
	 * @param contractId
	 * @return
	 */
	ContractBean getContractById(Long contractId);
	
	/**
	 * Get history of imported excels for given contract...
	 * @param contractId
	 * @return
	 */
	List<ExcelContractBean> getContractHistory(Long contractId);
	
	/**
	 * Scheduler method called each day at 12:00 PM.
	 * Will check and update contracts status accordingly...
	 */
	void updateContractsStatus();
}
