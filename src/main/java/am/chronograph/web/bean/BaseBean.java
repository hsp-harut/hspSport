package am.chronograph.web.bean;

import java.io.Serializable;

/**
 * Base bean class for other jsf beans...
 * 
 * @author HARUT
 */
public abstract class BaseBean implements Serializable {

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = 2274719932286031938L;
    
    protected Long id;

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
}
