package am.chronograph.web.bean.contract;

import java.util.Date;

import am.chronograph.web.bean.BaseBean;

/**
 * Bean class corresponds to ImportedExcel. Will contain presentation layer related data...
 * @author HARUT
 */
public class ImportedExcelBean extends BaseBean {

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -6334962310317166858L;

    private String fileName;
    private String fileRemainName;
    
    private Integer rows;    
    private Integer rowsUpdated;    
    private Integer rowsSkipped;    
    private Integer rowsNoFound;
    private Double size;
    private String contentType;
    
    private Date createdDate;
    
    /**
     * Default constructor...
     */
    public ImportedExcelBean() {        
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
	 * @return the fileRemainName
	 */
	public String getFileRemainName() {
		return fileRemainName;
	}

	/**
	 * @param fileRemainName the fileRemainName to set
	 */
	public void setFileRemainName(String fileRemainName) {
		this.fileRemainName = fileRemainName;
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
