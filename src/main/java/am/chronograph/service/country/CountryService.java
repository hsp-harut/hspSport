package am.chronograph.service.country;

import java.util.List;

import am.chronograph.web.bean.country.CountryBean;

/**
 * Provides services for getting, managing Countries.
 * 
 * @author HARUT
 */
public interface CountryService {
	
	/**
	 * Creates given country...
	 * @param countryBean
	 */
	void create(CountryBean countryBean);
	
	/**
	 * Updates country by given one...
	 * @param countryBean
	 */
	void update(CountryBean countryBean);
	
	/**
	 * Delets country by given id...
	 * @param id
	 */
	void delete(Long id);

	/**
	 * Get all countries...
	 * @return
	 */
	List<CountryBean> getAll();
}
