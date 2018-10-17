package am.chronograph.service.country;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import am.chronograph.dao.country.CountryDao;
import am.chronograph.domain.country.Country;
import am.chronograph.web.bean.country.CountryBean;

/**
 * Implements CountryService interface...
 * 
 * @author HARUT
 */
@Service
@Transactional(readOnly = true)
public class CountryServiceImpl implements CountryService {
	
	@Autowired
	private CountryDao countryDao;

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.country.CountryService#create(am.chronograph.web.bean.country.CountryBean)
	 */	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void create(CountryBean countryBean) {
		countryDao.save(initDomainByBean(countryBean));
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.country.CountryService#update(am.chronograph.web.bean.country.CountryBean)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(CountryBean countryBean) {
		countryDao.save(initDomainByBean(countryBean));
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.country.CountryService#delete(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		Country country = countryDao.getById(id);
		
		countryDao.delete(country);
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.country.CountryService#getAll()
	 */
	@Override
	public List<CountryBean> getAll() {
		List<Country> countries = countryDao.getAll();
		
		return initBeanListByDomainList(countries);
	}

	/**
	 * Initialize Domain by given Bean...
	 * @param countryBean
	 * @return
	 */
	private Country initDomainByBean(CountryBean countryBean) {
		Country country = (countryBean.getId() != null) ? countryDao.getById(countryBean.getId()) : new Country();
		
		country.setName(countryBean.getName());
		
		return country;
	}
	
	/**
	 * Initialize Bean List by given Domain list...
	 * @param countries
	 * @return
	 */
	private List<CountryBean> initBeanListByDomainList(List<Country> countries) {
		List<CountryBean> countryBeans = new ArrayList<>();
		for(Country country : countries) {
			countryBeans.add(initBeanByDomain(country));
		}
		
		return countryBeans;
	}

	/**
	 * Initialize Bean by given Domain...
	 * @param country
	 * @return
	 */
	private CountryBean initBeanByDomain(Country country) {
		CountryBean countryBean = new CountryBean();
		
		countryBean.setId(country.getId());
		countryBean.setName(country.getName());
		
		return countryBean;
	}
}
