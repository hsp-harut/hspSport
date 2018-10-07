package am.chronograph.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import am.chronograph.dao.user.PermissionDao;
import am.chronograph.dao.user.PermissionSearchCriteria;
import am.chronograph.domain.user.Permission;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchResult;

/**
 * The service implementation of the {@link PermissionService}.
 * 
 * @author tigranbabloyan
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class PermissionServiceImpl implements PermissionService{
	@Autowired
	private PermissionDao permissionDao;
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.SearchAware#search(am.chronograph.search.SearchCriteria)
	 */
	@Override
	public SearchResult<Permission> search(SearchCriteria<Permission> criteria) {
		return permissionDao.search(criteria);
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.SearchAware#getEmptyCriteria()
	 */
	@Override
	public SearchCriteria<Permission> getEmptyCriteria() {
		return new PermissionSearchCriteria();
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.PermissionService#getById(java.lang.Long)
	 */
	@Override
	public Permission getById(Long id) {
		return permissionDao.getById(id);
	}
}
