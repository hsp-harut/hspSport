package am.chronograph.service.contract;

import java.io.InputStream;
import java.util.List;

import am.chronograph.domain.contract.ImportedExcel;
import am.chronograph.ex.ChronoDataException;
import am.chronograph.service.SearchBeanAware;
import am.chronograph.web.bean.contract.ImportedExcelBean;
import am.chronograph.web.bean.upload.UploadedFileBean;

/**
 * Provides services for getting, managing Contracts.
 * 
 * @author HARUT
 */
public interface ImportDataService extends SearchBeanAware<ImportedExcelBean, ImportedExcel>{

	/**
	 * Import new excel file...
	 * @param uploadedFileBean
	 * @return
	 */
	void importFile(UploadedFileBean uploadedFileBean, List<String> agentTypes) throws ChronoDataException;	
		
	/**
	 * Delete imported excel by given id...
	 * @param examId
	 */
	void deleteFile(Long id);
	
	/**
	 * Checks whether is there another file by given name or not...
	 * 
	 * @param name
	 * @param id
	 * @return
	 */
    boolean existFileByName(String name);
    
    /**
     * Checks whether is there another file by given name uploaded for one of selected agents...
     * 
     * @param name
     * @param agentTypes
     * @return
     */
    boolean existFileByName(String name, List<String> agentTypes);
	
	/**
	 * Get file by given id...
	 * @param id
	 * @return
	 */
    ImportedExcelBean getFileById(Long id);

    /**
     * Get file content...
     * @param fileName
     * @return
     */
    InputStream getFileContent(String fileName, boolean original);
}
