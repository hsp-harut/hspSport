package am.chronograph.service;

import am.chronograph.domain.email.EmailTemplate;
import am.chronograph.domain.email.EmailTemplate.Type;
import am.chronograph.domain.user.User;

/**
 * Service interface which defines all the methods for sending the emails.
 * 
 * @author tigranbabloyan
 *
 */
public interface EmailService extends SearchAware<EmailTemplate>{

	/**
	 * Sends the activation email to the user.
	 * @param user The user to whom to send the activation link.
	 */
	void sendActivaetionEmail(User user);	
	
	/**
	 * Returns the email template for the given type;
	 * @param type The type of the email template.
	 * @return  the email template for the given type;
	 */
	EmailTemplate getEmailTemplate(Type type);
	
	/**
	 * Saves given email template into database.
	 * @param emailTemplate The email template to save.
	 */
	EmailTemplate save(EmailTemplate emailTemplate);
	
	/**
	 * Sends the error email to babltiga@mail.ru.
	 * @param throwable which should be emailed.
	 */
	void emailError(Throwable throwable);
}
