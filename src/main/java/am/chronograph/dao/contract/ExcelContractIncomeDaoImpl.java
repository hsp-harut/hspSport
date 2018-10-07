package am.chronograph.dao.contract;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import am.chronograph.dao.GenericDaoImpl;
import am.chronograph.domain.contract.ExcelContractIncome;

/**
 * Implements ExcelContractIncomeDao interface.
 * 
 * @author HARUT
 */
@Repository
public class ExcelContractIncomeDaoImpl extends GenericDaoImpl<ExcelContractIncome> implements ExcelContractIncomeDao {
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.dao.contract.ExcelContractIncomeDao#getByContractId(java.lang.Long)
	 */
	public List<ExcelContractIncome> getByContractId(Long contractId) {
		return list("FROM ExcelContractIncome exelContract WHERE exelContract.contract.id = :contractId ORDER BY exelContract.createdDate DESC", Collections.singletonMap("contractId", contractId));
	}
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.dao.contract.ExcelContractIncomeDao#getByContractImportedWithinDaysNumber(java.lang.Long, int)
	 */
	public List<ExcelContractIncome> getByContractImportedWithinDaysNumber(Long contractId, int days) {
	    Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("contractId", contractId);        
        parameters.put("days", days);
	    
	    return list("FROM ExcelContractIncome exelContract WHERE exelContract.contract.id = :contractId AND datediff(NOW(), exelContract.createdDate) < :days ", parameters);
	}
}
