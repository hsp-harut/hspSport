package am.chronograph.web.controller.contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import am.chronograph.domain.contract.ImportedExcel;
import am.chronograph.ex.ChronoDataException;
import am.chronograph.service.contract.ImportDataService;
import am.chronograph.web.bean.contract.ImportedExcelBean;
import am.chronograph.web.bean.upload.UploadedFileBean;
import am.chronograph.web.controller.base.BaseController;
import am.chronograph.web.integration.Spring;
import am.chronograph.web.model.SearchAwareBeanLazyModel;
import am.chronograph.web.util.FacesMessagesUtil;

/**
 * Controller class will handle actions from contractManagement.xhtml page...
 * 
 * @author HARUT
 */
@Named("importDataController")
@ViewScoped
public class ImportDataController extends BaseController implements Serializable {
		
    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = 1098894068700370260L;

    @Inject
	@Spring
	private transient ImportDataService dataService;
		
	private LazyDataModel<ImportedExcelBean> importedExcelsModel;
	
	private StreamedContent content;
	
	private List<String> agentTypes;
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.web.controller.base.BaseController#init()
	 */
	@PostConstruct
	public void init() {
		super.init();
				
		agentTypes = new ArrayList<String>();
		importedExcelsModel = new SearchAwareBeanLazyModel<ImportedExcelBean, ImportedExcel, ImportDataService>(dataService);		
	}	
    
    /**
     * ActionListener method called when 'Upload' button is clicked...
     */
	public void onFileUpload(FileUploadEvent event) {
	    UploadedFileBean uploadedFileBean = initFileUploadBean(event.getFile());
	    if(!isValidToUpload(uploadedFileBean.getOriginalFileName())) {
	        return;
	    }
        
	    try {	        
	        dataService.importFile(uploadedFileBean, agentTypes);
	        agentTypes = new ArrayList<>();
	    } catch(ChronoDataException e) {
	        FacesMessagesUtil.addError(e.getMessage());	        
	        
	        return;
	    }
	    
	    addInfoMessage("contractUploadSuccess");
    }
	
	/**
	 * Initialize UploadedFileBean by UploadedFile primefaces specific object...
	 * @param uploadedFile
	 * @return
	 */
	private UploadedFileBean initFileUploadBean(UploadedFile uploadedFile) {
	    UploadedFileBean uploadedFileBean = new UploadedFileBean(uploadedFile);
	    uploadedFileBean.setFileName(initFileNameWithAgentTypeInfo(uploadedFileBean.getOriginalFileName()));
	    
	    return uploadedFileBean;
	}
	
	/**
     * Initialize new file name by appending to its name agent types related info...
     * @param fileName
     * @return
     */
    private String initFileNameWithAgentTypeInfo(String fileName) {
        return fileName.replace(".xl", (initAgentTypesString() + ".xl"));
    }
    
    /**
     * Initialize agent types related String...
     * @param agentTypes
     * @return
     */
    private String initAgentTypesString() {
        StringBuilder agentTypesString = new StringBuilder();
        for(String agentType : agentTypes) {
            agentTypesString.append("_").append(agentType);
        }
        
        return agentTypesString.toString();
    }
    
	/**
	 * Checks whether everything is valid to upload file or not...
	 * @param event
	 * @return
	 */
	private boolean isValidToUpload(String originalFileName) { 
	    return isAgentTypeSelected() && isValidFile(originalFileName);
	}
	
	/**
	 * Checks whether user selected agent type before uploading or not...
	 * @return
	 */
	private boolean isAgentTypeSelected() {
	    if(agentTypes.isEmpty()) {
	        addErrorMessage("importForm:agent", "agenTypeNotSelected");
	        
	        return false;
	    }
	    
	    return true;
	}
	
    /**
     * Method for Person validation -- for inserting/updating
     * corresponding data into database...
     * @return
     */
    private boolean isValidFile(String originalFileName) {        
        boolean noError = true;                
        
        if(!originalFileName.endsWith("xls") && !originalFileName.endsWith("xlsx")) {
            addErrorMessage("importForm:fileUpload", "contractUploadFormat");
            
            noError = false;
        }
        
        if (dataService.existFileByName(originalFileName, agentTypes)) {
//            addErrorMessage("importForm:fileUpload", "contractUploadFileUnique");
        	addErrorMessage("importForm:fileUpload", "contractUploadFileUniqueAgentType");
            
            noError = false;
        }
        
        return noError;
    }
    
    /**
     * ActionListener method called when Delete icon is clicked to delete selected ImportedExcelBean...
     * @param selectedFileBean
     */
    public void onRemoveFile(ImportedExcelBean selectedFileBean) {
        dataService.deleteFile(selectedFileBean.getId());
    }
    
    /**
     * Action Listener method called when user tries to download uploaded file (original or remaining part)
     * @param original
     */
    public void onDownload(ImportedExcelBean selectedFileBean, boolean original) {
    	content = new DefaultStreamedContent(dataService.getFileContent(selectedFileBean.getFileName(), original), 
    			selectedFileBean.getContentType(), 
    			original ? selectedFileBean.getFileName() : selectedFileBean.getFileRemainName());
    }

    /**
     * @return the importedExcelsModel
     */
    public LazyDataModel<ImportedExcelBean> getImportedExcelsModel() {
        return importedExcelsModel;
    }

    /**
     * @param importedExcelsModel the importedExcelsModel to set
     */
    public void setImportedExcelsModel(LazyDataModel<ImportedExcelBean> importedExcelsModel) {
        this.importedExcelsModel = importedExcelsModel;
    }

	/**
	 * @return the content
	 */
	public StreamedContent getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(StreamedContent content) {
		this.content = content;
	}

    /**
     * @return the agentTypes
     */
    public List<String> getAgentTypes() {
        return agentTypes;
    }

    /**
     * @param agentTypes the agentTypes to set
     */
    public void setAgentTypes(List<String> agentTypes) {
        this.agentTypes = agentTypes;
    }    
}
