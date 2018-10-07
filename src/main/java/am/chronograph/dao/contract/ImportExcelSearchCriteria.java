package am.chronograph.dao.contract;

import am.chronograph.domain.contract.Contract;
import am.chronograph.domain.contract.ImportedExcel;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchCriteriaImpl;

/**
 * The implementation of Search API for {@link Contract} domain object.
 *
 * @author tigran
 *
 */
public class ImportExcelSearchCriteria extends SearchCriteriaImpl<ImportedExcel> implements SearchCriteria<ImportedExcel> {

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -6060693698488545084L;

    private Long id;
        
    private String fileName;        
    private Integer rows;        
    private Integer rowsUpdated;       
    private Integer rowsSkipped;        
    private Integer rowsNoFound;
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
}
