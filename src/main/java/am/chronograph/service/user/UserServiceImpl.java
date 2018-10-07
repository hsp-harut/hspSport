package am.chronograph.service.user;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import am.chronograph.dao.user.UserDao;
import am.chronograph.dao.user.UserSearchCriteria;
import am.chronograph.domain.user.User;
import am.chronograph.ex.ChronoDataException;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchResult;
import am.chronograph.service.EmailService;

/**
 * The service implementation of the {@link UserService}.
 * 
 * @author tigranbabloyan
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EmailService emailService;
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.UserService#saveUser(am.chronograph.domain.user.User)
	 */
	@Override
	@Transactional
	public User saveUser(User user) {
		boolean newUser = false;
		if(user.getId() == null){
			if(getUserByEmail(user.getEmail()) != null){
				throw new ChronoDataException("User with given email allready exists");
			}
			newUser = true;
		}
		// save user to db
		user = newUser ? userDao.save(user) : userDao.merge(user);
		if(newUser){
			sendActivation(user);	
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.UserService#getUserById(java.lang.Long)
	 */
	@Override
	public User getUserById(Long id) {
		return userDao.getById(id);
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.UserService#getUserByEmail(java.lang.String)
	 */
	@Override
	public User getUserByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.UserService#findUsers(am.chronograph.search.SearchCriteria)
	 */
	@Override
	public SearchResult<User> search(SearchCriteria<User> userSearchCritaeria) {
		return userDao.search(userSearchCritaeria);
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.SearchAware#getEmptyCriteria()
	 */
	@Override
	public SearchCriteria<User> getEmptyCriteria() {
		return new UserSearchCriteria();
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.user.UserService#sendActivation(am.chronograph.domain.user.User)
	 */
	@Override
	@Transactional
	public void sendActivation(User user) {
		user.setPassword(null);
		user.setActivationCode(RandomStringUtils.randomAlphabetic(50));
		userDao.save(user);
		emailService.sendActivaetionEmail(user);
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.user.UserService#getUserByActivationCode(java.lang.String)
	 */
	@Override
	public User getUserByActivationCode(String activationCode) {
		return userDao.getUserByActivationCode(activationCode);
	}
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.user.UserService#getAllUsers()
	 */
	@Override
	public List<SelectItem> getAllUsers(boolean disabledItems) {
		List<User> users = userDao.search(getEmptyCriteria()).getResult();
		
		return initUserItems(users, disabledItems);
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.user.UserService#sendForgotEmail(java.lang.String)
	 */
	@Override
	@Transactional
	public void sendForgotEmail(String email) {
		User user = userDao.findByEmail(email);
		if(user == null){
			return;
		}
		sendActivation(user);
	}
	
	/**
	 * Initialize SelectItem List by given User domain List...
	 * @param roles
	 * @return
	 */
	private List<SelectItem> initUserItems(List<User> users, boolean disabledItems) {
		List<SelectItem> userItems = new ArrayList<SelectItem>();		
		for(User user : users) {
			userItems.add(new SelectItem(user.getId(), user.getFullName(), null, disabledItems));
			
		}
		
		return userItems;
	}
}
