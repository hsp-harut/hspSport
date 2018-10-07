package am.chronograph.domain.contract;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import am.chronograph.domain.AuditAwareEntity;

/**
 * Domain object. ORM to excels_imported table.
 * 
 * @author HARUT
 */
@Entity
@Table(name = "excels_imported")
public class ImportedExcel extends AuditAwareEntity {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 7635426242822756164L;
	
	@Column(name = "file_name", nullable = false, insertable = true, updatable = true)
	private String fileName;
	
	@Column(name = "rows", nullable = false, insertable = true, updatable = true)
	private Integer rows;
	
	@Column(name = "rows_updated", nullable = false, insertable = true, updatable = true)
	private Integer rowsUpdated;
	
	@Column(name = "rows_skipped_zero_amount", nullable = false, insertable = true, updatable = true)
	private Integer rowsSkipped;
	
	@Column(name = "rows_no_contract_found", nullable = false, insertable = true, updatable = true)
	private Integer rowsNoFound;
		
	/*file size in bytes*/
    @Column(name = "size", insertable = true, updatable = true, nullable = false, scale = 0, precision = 10)
    private Double size;
        
    @Column(name = "content_type", nullable = false, insertable = true, updatable = true)
	private String contentType;
	
	/**
	 * Default constructor...
	 */
	public ImportedExcel() {		
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the rows
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/**
	 * @return the rowsUpdated
	 */
	public Integer getRowsUpdated() {
		return rowsUpdated;
	}

	/**
	 * @param rowsUpdated the rowsUpdated to set
	 */
	public void setRowsUpdated(Integer rowsUpdated) {
		this.rowsUpdated = rowsUpdated;
	}

	/**
	 * @return the rowsSkipped
	 */
	public Integer getRowsSkipped() {
		return rowsSkipped;
	}

	/**
	 * @param rowsSkipped the rowsSkipped to set
	 */
	public void setRowsSkipped(Integer rowsSkipped) {
		this.rowsSkipped = rowsSkipped;
	}

	/**
	 * @return the rowsNoFound
	 */
	public Integer getRowsNoFound() {
		return rowsNoFound;
	}

	/**
	 * @param rowsNoFound the rowsNoFound to set
	 */
	public void setRowsNoFound(Integer rowsNoFound) {
		this.rowsNoFound = rowsNoFound;
	}

    /**
     * @return the size
     */
    public Double getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Double size) {
        this.size = size;
    }

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}        
}
