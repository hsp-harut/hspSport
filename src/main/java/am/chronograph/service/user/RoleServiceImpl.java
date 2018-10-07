package am.chronograph.service.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import am.chronograph.dao.user.RoleDao;
import am.chronograph.dao.user.RoleSearchCriteria;
import am.chronograph.domain.user.Role;
import am.chronograph.domain.user.User;
import am.chronograph.ex.ChronoDataException;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchResult;

/**
 * The service implementation of the {@link RoleService}.
 * 
 * @author tigranbabloyan
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.UserService#saveRole(am.chronograph.domain.user.Role)
	 */
	@Override
	@Transactional
	public Role saveRole(Role role) {
		return role.getId() == null ? roleDao.save(role) : roleDao.merge(role);
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.UserService#deleteRole(am.chronograph.domain.user.Role)
	 */
	@Override
	@Transactional
	public void deleteRole(Role role) {
		roleDao.refresh(role);
		if(!CollectionUtils.isEmpty(role.getUsers())){
			throw new ChronoDataException("Can't delete role, role has assigned users."); 
		}
		roleDao.delete(role);
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.UserService#getRoleByName(java.lang.String)
	 */
	@Override
	public Role findByName(String name) {
		return roleDao.findByName(name);
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.user.RoleService#getById(java.lang.Long)
	 */
	@Override
	public Role getById(Long id) {
		return roleDao.getById(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.user.RoleService#getRoleItems()
	 */
	@Override
	public List<SelectItem> getRoleItems() {
		List<Role> roles = roleDao.search(getEmptyCriteria()).getResult();
		
		return initItems(roles);
	}
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.user.RoleService#getRoleUserItems(java.lang.Long)
	 */
	@Override
	public List<SelectItem> getRoleUserItems(Long roleId) {
		Role role = roleDao.getById(roleId);
		
		return initUserItems(role.getUsers());
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.UserService#findRoles(am.chronograph.search.SearchCriteria)
	 */
	@Override
	public SearchResult<Role> search(SearchCriteria<Role> roleSearchCritaeria) {
		return roleDao.search(roleSearchCritaeria);
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.SearchAware#getEmptyCriteria()
	 */
	@Override
	public SearchCriteria<Role> getEmptyCriteria() {
		RoleSearchCriteria criteria = new RoleSearchCriteria();
		criteria.setHidden(false);
		return criteria;
	}
	
	/**
	 * Initialize SelectItem List by given Role domain List...
	 * @param roles
	 * @return
	 */
	private List<SelectItem> initItems(List<Role> roles) {
		List<SelectItem> roleItems = new ArrayList<SelectItem>();
		for(Role role : roles) {
			roleItems.add(new SelectItem(role.getId(), role.getName()));
		}
		
		return roleItems;
	}
	
	/**
	 * Initialize SelectItem List by given User domain List...
	 * @param roles
	 * @return
	 */
	private List<SelectItem> initUserItems(Collection<User> users) {
		if(users == null || users.isEmpty()) {
			return new ArrayList<SelectItem>();
		}
		
		List<SelectItem> userItems = new ArrayList<SelectItem>();		
		for(User user : users) {
			userItems.add(new SelectItem(user.getId(), user.getFullName()));
		}
		
		return userItems;
	}
}
