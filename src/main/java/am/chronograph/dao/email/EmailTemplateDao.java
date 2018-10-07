package am.chronograph.dao.email;

import am.chronograph.dao.GenericDao;
import am.chronograph.domain.email.EmailTemplate;
import am.chronograph.search.SearchSupport;

/**
 * The Dao interface for accessing {@link EmailTemplate} domain object.
 *
 */
public interface EmailTemplateDao extends GenericDao<EmailTemplate>, SearchSupport<EmailTemplate> {
}
