package am.chronograph.dao.contract;

import java.util.List;

import am.chronograph.dao.GenericDao;
import am.chronograph.domain.contract.ImportedExcel;
import am.chronograph.search.SearchSupport;

/**
 * Provides data access methods for ImportedExcel data.
 * 
 * @author HARUT
 */
public interface ImportedExcelDao extends GenericDao<ImportedExcel>, SearchSupport<ImportedExcel> {
    
    /**
     * Get ImportedExcel by name...
     * @param name
     * @param id
     * @return
     */
    ImportedExcel getByName(String name);
    
    /**
     * Get imported excels where file name is like given one... 
     * 
     * @param fileNameNoFormat
     * @return
     */
    List<ImportedExcel> getFileLikeName(String fileNameNoFormat);
}
