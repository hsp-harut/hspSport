package am.chronograph.service.passport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import am.chronograph.dao.passport.PassportDao;
import am.chronograph.domain.passport.Passport;
import am.chronograph.web.bean.passport.PassportBean;

/**
 * Implements PassportService interface
 * 
 * @author vahagn
 *
 */

@Service
@Transactional(readOnly = true)
public class PassportServiceImpl implements PassportService {

	@Autowired
	private PassportDao passportDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * am.chronograph.service.passport.PassportService#create(am.chronograph.web.
	 * bean.passport.PassportBean)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void create(PassportBean passportBean) {
		passportDao.save(initDomainByBean(passportBean));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * am.chronograph.service.passport.PassportService#update(am.chronograph.web.
	 * bean.passport.PassportBean)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(PassportBean passportBean) {
		passportDao.save(initDomainByBean(passportBean));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.chronograph.service.passport.PassportService#delete(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		Passport passport = passportDao.getById(id);

		passportDao.delete(passport);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.chronograph.service.passport.PassportService#getAll()
	 */
	@Override
	public List<PassportBean> getAll() {
		List<Passport> passports = passportDao.getAll();

		return initBeanListByDomainList(passports);
	}

	/**
	 * Initialize Domain by given Bean...
	 * 
	 * @param passportBean
	 * @return
	 */
	private Passport initDomainByBean(PassportBean passportBean) {
		Passport passport = (passportBean.getId() != null) ? passportDao.getById(passportBean.getId()) : new Passport();

		passport.setFirstName(passportBean.getFirstName());

		return passport;
	}

	/**
	 * Initialize Bean List by given Domain list...
	 * 
	 * @param passports
	 * @return
	 */
	private List<PassportBean> initBeanListByDomainList(List<Passport> passports) {
		List<PassportBean> passportBeans = new ArrayList<>();
		for (Passport passport : passports) {
			passportBeans.add(initBeanByDomain(passport));
		}

		return passportBeans;
	}

	/**
	 * Initialize Bean by given Domain...
	 * 
	 * @param country
	 * @return
	 */
	private PassportBean initBeanByDomain(Passport passport) {
		PassportBean passportBean = new PassportBean();

		passportBean.setId(passport.getId());
		passportBean.setFirstName(passport.getFirstName());

		return passportBean;
	}
}
