package am.chronograph.web.bean.upload;

import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import am.chronograph.ex.ChronoDataException;

/**
 * Class containing upload file related data...
 * @author harut
 *
 */
public class UploadedFileBean {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadedFileBean.class);

    private String originalFileName;
    private String fileName;
    
    private long size;    
    private String contentType;
    
    private UploadedFile uploadedFile;
    
    /**
     * Default constructor...
     */
    public UploadedFileBean() {        
    }
    
    /**
     * Copy data by given param...
     * @param uploadedFile
     */
    public UploadedFileBean(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
        
        originalFileName = uploadedFile.getFileName();
        size = uploadedFile.getSize();
        contentType = uploadedFile.getContentType();
    }
    
    /**
     * Writes the uploaded file to the given file path.
     * 
     * @param filePath
     */
    public void write(String filePath) {
        try {
            uploadedFile.write(filePath);
        } catch (Exception ex) {
            LOGGER.error("Error while storing file at {} {}", filePath, ex);
            throw new ChronoDataException("Error while storing file " + filePath);
        }
    }       

    /**
	 * @return the originalFileName
	 */
	public String getOriginalFileName() {
		return originalFileName;
	}

	/**
	 * @param originalFileName the originalFileName to set
	 */
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
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
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(long size) {
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
