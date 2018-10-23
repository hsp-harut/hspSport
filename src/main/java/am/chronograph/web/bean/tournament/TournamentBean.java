package am.chronograph.web.bean.tournament;

import java.util.Date;
import am.chronograph.web.bean.BaseBean;

/**
 * Bean class corresponds to Tournaments. Will contain presentation layer
 * related data...
 * 
 * @author davitpetrosyan
 *
 */
public class TournamentBean extends BaseBean {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = -5138932862013131783L;

	private String name;
	private Date startDate;
	private Integer maxPartCount;

	/**
	 * Default constructor...
	 */
	public TournamentBean() {
	}

	/**
	 * Copy constructor...
	 * 
	 * @param contractBean
	 */
	public TournamentBean(TournamentBean tournamentBean) {
		name = tournamentBean.getName();
		startDate = tournamentBean.getStartDate();
		maxPartCount = tournamentBean.getMaxPartCount();
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

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the maxPartCount
	 */
	public Integer getMaxPartCount() {
		return maxPartCount;
	}

	/**
	 * @param maxPartCount the maxPartCount to set
	 */
	public void setMaxPartCount(Integer maxPartCount) {
		this.maxPartCount = maxPartCount;
	}

}