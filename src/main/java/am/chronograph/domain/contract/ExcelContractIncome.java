package am.chronograph.domain.contract;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import am.chronograph.domain.AuditAwareEntity;

/**
 * Domain object. ORM to excels_imported table.
 * 
 * @author HARUT
 */
@Entity
@Table(name = "excel_contract_income")
public class ExcelContractIncome extends AuditAwareEntity {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 1917344637725263774L;
	
	@ManyToOne
	@JoinColumn(name = "excel_id", nullable=false, insertable=true, updatable=false)
	private ImportedExcel excel;
	
	@ManyToOne
	@JoinColumn(name = "contract_id", nullable=false, insertable=true, updatable=false)
	private Contract contract;
	
	@Column(name = "income", nullable = true, insertable = true, updatable = true)
	private Double income;
	
	@Column(name = "date_in_excel", nullable = true)
    private LocalDateTime dateInExcel;
	
	/**
	 * Default constructor...
	 */
	public ExcelContractIncome() {		
	}
	
	/**
	 * Initialize data by the given data...
	 * @param excel
	 * @param contract
	 * @param income
	 * @param dateInExcel
	 */
	public ExcelContractIncome(ImportedExcel excel, Contract contract, Double income, LocalDateTime dateInExcel) {
	    this.excel = excel;
	    this.contract = contract;
	    this.income = income;
	    this.dateInExcel = dateInExcel;
    }

	/**
	 * @return the excel
	 */
	public ImportedExcel getExcel() {
		return excel;
	}

	/**
	 * @param excel the excel to set
	 */
	public void setExcel(ImportedExcel excel) {
		this.excel = excel;
	}

	/**
	 * @return the contract
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @param contract the contract to set
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * @return the income
	 */
	public Double getIncome() {
		return income;
	}

	/**
	 * @param income the income to set
	 */
	public void setIncome(Double income) {
		this.income = income;
	}

    /**
     * @return the dateInExcel
     */
    public LocalDateTime getDateInExcel() {
        return dateInExcel;
    }

    /**
     * @param dateInExcel the dateInExcel to set
     */
    public void setDateInExcel(LocalDateTime dateInExcel) {
        this.dateInExcel = dateInExcel;
    }		
}
