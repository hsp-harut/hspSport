package am.chronograph.service.passport;

import java.util.List;

import am.chronograph.web.bean.passport.PassportBean;

/**
 * Provides services for getting, managing passports.
 * 
 * @author vahagn
 *
 */
public interface PassportService {

	/**
	 * Creates given country...
	 * 
	 * @param passportBean
	 */
	void create(PassportBean passportBean);

	/**
	 * Updates country by given one...
	 * 
	 * @param countryBean
	 */
	void update(PassportBean passportBean);

	/**
	 * Deletes country by given id...
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * Get all countries...
	 * 
	 * @return
	 */
	List<PassportBean> getAll();
}
