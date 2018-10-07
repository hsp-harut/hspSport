package am.chronograph.dao.contract;

import java.util.List;

import am.chronograph.dao.GenericDao;
import am.chronograph.domain.contract.ExcelContractIncome;

/**
 * Provides data access methods for ExcelContractIncome data.
 * 
 * @author HARUT
 */
public interface ExcelContractIncomeDao extends GenericDao<ExcelContractIncome> {
	
	/**
	 * Get ExcelContractIncome by contract id...
	 * @param contractId
	 * @return
	 */
	List<ExcelContractIncome> getByContractId(Long contractId);
	
	/**
	 * Get data by contract id and which have created between last 35 days... 
	 * @param contractId
	 * @param contractStartDate
	 * @return
	 */
	List<ExcelContractIncome> getByContractImportedWithinDaysNumber(Long contractId, int days);
}
