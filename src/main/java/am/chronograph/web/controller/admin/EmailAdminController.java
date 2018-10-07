package am.chronograph.web.controller.admin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;

import am.chronograph.domain.email.EmailTemplate;
import am.chronograph.ex.ChronoDataException;
import am.chronograph.service.EmailService;
import am.chronograph.web.controller.base.BaseController;
import am.chronograph.web.integration.Spring;
import am.chronograph.web.model.SearchAwareLazyModel;

@ViewScoped
@Named("emailAdminController")
public class EmailAdminController extends BaseController implements Serializable {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Thr email service.
	 */
	@Inject
	@Spring
	private transient EmailService emailService;
	
	/**
	 * Selected user.
	 */
	private EmailTemplate emailTemplate;

	/**
	 * The lazy model for showing roles in a data table.
	 */
	private LazyDataModel<EmailTemplate> templates;

	@PostConstruct
	@PreAuthorize("hasAnyAuthority('EDIT_EMAIL_TEMPLATES', 'ROLE_ADMIN')")
	public void init() {
		super.init();
		templates = new SearchAwareLazyModel<EmailTemplate>(emailService);
		reset();
	}

	/**
	 * Saves the role into the database.
	 */
	@PreAuthorize("hasAnyAuthority('EDIT_EMAIL_TEMPLATES', 'ROLE_ADMIN')")
	public void save() {
		EmailTemplate templateToSave = emailService.getEmailTemplate(emailTemplate.getType());
		emailTemplate.setId(null);
		BeanUtils.copyProperties(emailTemplate, templateToSave, "id", "type");
		try {
			emailTemplate = emailService.save(templateToSave);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Template saved successfully."));
		} catch (ChronoDataException ex) {
			// this should not happen
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Error while saving template. Please try again."));
		}
	}

	private void reset() {
		emailTemplate = new EmailTemplate();
	}

	/**
	 * Called when use selects a user in the data table.
	 * 
	 * @param event
	 *            The select event.
	 */
	@PreAuthorize("hasAnyAuthority('EDIT_EMAIL_TEMPLATES', 'ROLE_ADMIN')")
	public void onRowSelect(SelectEvent event) {
		EmailTemplate template = (EmailTemplate) event.getObject();
		emailTemplate = emailService.getEmailTemplate(template.getType());
		scrollTo("rootTemplateInputContainer");
		RequestContext.getCurrentInstance().reset("templateUpdateForm");
	}

	/**
	 * Called when use un-selects a user in the data table.
	 * 
	 * @param event
	 *            The select event.
	 */
	@PreAuthorize("hasAnyAuthority('EDIT_EMAIL_TEMPLATES', 'ROLE_ADMIN')")
	public void onRowUnselect(UnselectEvent event) {
		reset();
	}

	/**
	 * Returns the templates.
	 * 
	 * @return the templates
	 */
	public LazyDataModel<EmailTemplate> getTemplates() {
		return templates;
	}

	/**
	 * Sets the templates.
	 * 
	 * @param templates
	 *            the templates to set
	 */
	public void setTemplates(LazyDataModel<EmailTemplate> templates) {
		this.templates = templates;
	}

	/**
	 * Returns the emailTemplate.
	 * 
	 * @return the emailTemplate
	 */
	public EmailTemplate getEmailTemplate() {
		return emailTemplate;
	}

	/**
	 * Sets the emailTemplate.
	 * 
	 * @param emailTemplate
	 *            the emailTemplate to set
	 */
	public void setEmailTemplate(EmailTemplate emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

}
