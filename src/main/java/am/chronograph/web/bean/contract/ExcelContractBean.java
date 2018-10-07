package am.chronograph.web.bean.contract;

import java.util.Date;

import am.chronograph.web.bean.BaseBean;

/**
 * Bean class corresponds to Imported Excel rows per contract relationship...
 * 
 * @author HARUT
 */
public class ExcelContractBean extends BaseBean {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = -1387791261760934337L;
	
	private String excelName;
	private Double income;	
    private Date dateInExcel;
    private Date createdDate;
    
	/**
	 * @return the excelName
	 */
	public String getExcelName() {
		return excelName;
	}
	
	/**
	 * @param excelName the excelName to set
	 */
	public void setExcelName(String excelName) {
		this.excelName = excelName;
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
	public Date getDateInExcel() {
		return dateInExcel;
	}
	
	/**
	 * @param dateInExcel the dateInExcel to set
	 */
	public void setDateInExcel(Date dateInExcel) {
		this.dateInExcel = dateInExcel;
	}
	
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}        
}
