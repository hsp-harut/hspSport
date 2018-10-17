package am.chronograph.web.bean.country;

import am.chronograph.web.bean.BaseBean;

/**
 * Bean class corresponds to Countries. Will contain presentation layer related data...
 * @author HARUT
 */
public class CountryBean extends BaseBean {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 5634311460378713692L;

	private String name;

	/**
	 * Default constructor...
	 */
	public CountryBean() {
	}
	
	/**
	 * Copy constructor...
	 * @param contractBean
	 */
	public CountryBean(CountryBean countryBean) {
		name = countryBean.getName();
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}		
}
