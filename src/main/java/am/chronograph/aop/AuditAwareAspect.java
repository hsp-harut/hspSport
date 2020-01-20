package am.chronograph.aop;

import java.time.LocalDateTime;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import am.chronograph.config.SecurityConfig;
import am.chronograph.domain.AuditAwareEntity;
import am.chronograph.domain.user.User;
import am.chronograph.security.UserDetails;
import am.chronograph.service.user.UserService;

/**
 * Aspect which is used to automatically populate the data for
 * {@link AuditAwareEntity}.
 * 
 * @author tigranbabloyan
 *
 */
@Aspect 
@Component
class AuditAwareAspect {

	@Autowired
	private UserService userService;

	/**
	 * Intercept all the dao save calls and put the audit data.
	 * @param auditAwareEntity
	 */
	@Before("execution(* am.chronograph.dao.*.save(..)) && args(auditAwareEntity)")
	public void auditSave(AuditAwareEntity auditAwareEntity) {
		String email = SecurityConfig.ADMIN_EMAIL;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			Object object = auth.getPrincipal();
			if(object instanceof UserDetails){
				UserDetails userDetails = (UserDetails) object;
				email = userDetails.getEmail();
			}
		}
		
		User user = userService.getUserByEmail(email);
		// may be user has changed his email while logged in so lets use admin email for now
		if(user == null){
			user = userService.getUserByEmail(SecurityConfig.ADMIN_EMAIL);
		}
		if (auditAwareEntity.getId() == null) {
			auditAwareEntity.setCreatedBy(user);
			auditAwareEntity.setCreatedDate(LocalDateTime.now());
		}
		
		auditAwareEntity.setModifiedAt(LocalDateTime.now());
		auditAwareEntity.setModifiedBy(user);
	}
	
	@Before("execution(* am.chronograph.dao.*.merge(..)) && args(auditAwareEntity)")
	public void auditMerge(AuditAwareEntity auditAwareEntity) {
		String email = SecurityConfig.ADMIN_EMAIL;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			Object object = auth.getPrincipal();
			if(object instanceof UserDetails){
				UserDetails userDetails = (UserDetails) object;
				email = userDetails.getEmail();
			}
		}
		User user = userService.getUserByEmail(email);		
		auditAwareEntity.setModifiedAt(LocalDateTime.now());
		auditAwareEntity.setModifiedBy(user);
	}
}
