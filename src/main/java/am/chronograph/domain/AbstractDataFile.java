package am.chronograph.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * This class keeps the information about files. The files can be uploaded and
 * they can have different types, such as IMAGE_ATTACHEMET,
 * DOCUMNET_ATTACHEMENT.
 *
 * @author tigranbabloyan
 *
 */
@Entity
@Table(name = "data_file")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractDataFile extends AuditAwareEntity {
	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 8829226221345353524L;

	/**
	 * The original file name.
	 *
	 * @see #getOriginalName()
	 * @see #setOriginalName(String)
	 */
	@Basic
	@Column(name = "orig_name", insertable = true, updatable = true, nullable = false, length = 255)
	protected String originalName = null;
	/**
	 * The file content type.
	 *
	 * @see #getContentType()
	 * @see #setContentType(String)
	 */
	@Basic
	@Column(name = "content_type", insertable = true, updatable = true, nullable = false, length = 100)
	protected String contentType = null;
	/**
	 * The relative system path.
	 *
	 * @see #getSystemPath()
	 * @see #setSystemPath(String)
	 */
	@Basic
	@Column(name = "system_path", insertable = true, updatable = true, nullable = false, length = 255)
	protected String systemPath = null;
	/**
	 * The file size in bytes.
	 *
	 * @see #getSize()
	 * @see #setSize(Long)
	 */
	@Basic
	@Column(name = "size", insertable = true, updatable = true, nullable = false, scale = 0, precision = 10)
	protected Long size = null;

	/**
	 * The default constructor. Does nothing but initialization.
	 */
	public AbstractDataFile() {
		super();
	}

	/**
	 * Retrieves the original file name.
	 * 
	 * @return The original file name.
	 */

	public String getOriginalName() {
		return originalName;
	}

	/**
	 * Sets the given original file name.
	 * 
	 * @param originalName
	 *            The original file name to set.
	 */
	public void setOriginalName(final String originalName) {
		this.originalName = originalName;
	}

	/**
	 * Retrieves the file content type.
	 * 
	 * @return The file content type.
	 */

	public String getContentType() {
		return contentType;
	}

	/**
	 * Sets the given file content type.
	 * 
	 * @param contentType
	 *            The file content type to set.
	 */
	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	/**
	 * retrieves the relative system path.
	 * 
	 * @return The relative system path.
	 */

	public String getSystemPath() {
		return systemPath;
	}

	/**
	 * Sets the given relative system path.
	 * 
	 * @param systemPath
	 *            The relative system path to set.
	 */
	public void setSystemPath(final String systemPath) {
		this.systemPath = systemPath;
	}

	/**
	 * Retrieves the file size in bytes.
	 * 
	 * @return The file size in bytes.
	 */

	public Long getSize() {
		return size;
	}

	/**
	 * Sets the given file size in bytes.
	 * 
	 * @param size
	 *            The file size in bytes to set.
	 */
	public void setSize(final Long size) {
		this.size = size;
	}
}

/*
 * $Log$
 */
