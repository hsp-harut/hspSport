package am.chronograph.domain.student;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import am.chronograph.domain.AuditAwareEntity;

/**
 * Domain object. ORM to excels_imported table.
 * 
 * @author lusine
 *
 */
public class ExcelStudentIncome extends AuditAwareEntity {

	private static final long serialVersionUID = -4761659167487709593L;

	@ManyToOne
	@JoinColumn(name = "excel_id", nullable = false, insertable = true, updatable = false)
	private ImportedExcel1 excel;

	@ManyToOne
	@JoinColumn(name = "contract_id", nullable = false, insertable = true, updatable = false)
	private Student student;

	@Column(name = "income", nullable = true, insertable = true, updatable = true)
	private Double income;

	@Column(name = "date_in_excel", nullable = true)
	private LocalDateTime dateInExcel;

	/**
	 * Default constructor...
	 */
	public ExcelStudentIncome() {

	}

	/**
	 * Initialize data by the given data...
	 * 
	 * @param excel
	 * @param student
	 * @param income
	 * @param dateInExcel
	 */
	public ExcelStudentIncome(ImportedExcel1 excel, Student student, Double income, LocalDateTime dateInExcel) {
		this.excel = excel;
		this.student = student;
		this.income = income;
		this.dateInExcel = dateInExcel;
	}

	/**
	 * @return the excel
	 */
	public ImportedExcel1 getExcel() {
		return excel;
	}

	/**
	 * @param excel the excel to set
	 */
	public void setExcel(ImportedExcel1 excel) {
		this.excel = excel;
	}

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
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
