package am.chronograph.service;

import java.io.File;
import java.text.MessageFormat;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import am.chronograph.dao.email.EmailTemplateDao;
import am.chronograph.dao.email.EmailTemplateSearchCriteria;
import am.chronograph.domain.AbstractDataFile;
import am.chronograph.domain.email.EmailTemplate;
import am.chronograph.domain.email.EmailTemplate.Type;
import am.chronograph.domain.user.User;
import am.chronograph.ex.EmailSentFailedException;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchResult;

/**
 * The service implementation based on Thymeleaf and SMTP servers.
 * 
 * @author tigranbabloyan
 *
 */
@Service
@DependsOn("liquibase")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
class EmailServiceImpl implements EmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Autowired
	private EmailTemplateDao emailtemplateDao;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${smtp.from}")
	private String from;

	@Value("${mail.test.to}")
	private String testEmailTo;

	@Value("${mail.not.send}")
	private boolean notSend;

	@Value("${email.content.host}")
	private String serverHost;

	@Value("classpath:mail/images/logo-name.png")
	private Resource logo;

	@Value("classpath:mail/images/circle-icon-user.png")
	private Resource userIcon;

	@Value("classpath:mail/images/fail.png")
	private Resource failIcon;

	@Value("classpath:mail/images/success.png")
	private Resource successIcon;

	@Value("classpath:mail/images/time.png")
	private Resource timeIcon;

	@Value("classpath:mail/images/task.png")
	private Resource taskIcon;

	@Autowired
	private MessageSource messages;

	@Autowired
	@Qualifier("transactionManager")
	protected PlatformTransactionManager txManager;

	@PostConstruct
	private synchronized void inittemplates() {
		TransactionTemplate tmpl = new TransactionTemplate(txManager);
		tmpl.execute(status -> {
			SearchResult<EmailTemplate> result = search(getEmptyCriteria());
			Locale enLocale = new Locale.Builder().setLanguage("en").build();
			for (Type type : EmailTemplate.Type.values()) {
				boolean shouldImport = true;
				for (EmailTemplate template : result.getResult()) {
					if (type.equals(template.getType())) {
						shouldImport = false;
						break;
					}
				}
				if (!shouldImport) {
					continue;
				}
				EmailTemplate template = new EmailTemplate();
				template.setType(type);
				template.setBody(messages.getMessage(
						"email." + type.name().toLowerCase().replaceAll("_", ".") + ".body", null, enLocale));
				template.setSubject(messages.getMessage(
						"email." + type.name().toLowerCase().replaceAll("_", ".") + ".subject", null, enLocale));
				template.setTitle(messages.getMessage(
						"email." + type.name().toLowerCase().replaceAll("_", ".") + ".title", null, enLocale));
				emailtemplateDao.save(template);
			}
			return null;
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * am.chronograph.service.EmailService#sendActivaetionEmail(am.chronograph.
	 * domain.user.User)
	 */
	@Override
	public void sendActivaetionEmail(User user) {
		Locale enLocale = new Locale.Builder().setLanguage("en").build();
		// Prepare the evaluation context
		final Context ctx = new Context();
		final String activationURL = serverHost + "activate?code=" + user.getActivationCode();
		EmailTemplate template = getEmailTemplate(Type.ACTIVATION);
		String title = MessageFormat.format(template.getTitle(), user.getLastName(), user.getFirstName());
		String body = MessageFormat.format(template.getBody(), user.getLastName(), user.getFirstName());
		String subject = MessageFormat.format(template.getSubject(), user.getLastName(), user.getFirstName());
		ctx.setVariable("activationURL", activationURL);
		ctx.setVariable("logoResourceName", "logo.png");
		ctx.setVariable("userResourceName", "circle-icon-user.png");
		ctx.setVariable("title", title);
		ctx.setVariable("body", body);
		ctx.setVariable("requestedBy", user.getCreatedBy().getLastName() + " " + user.getCreatedBy().getFirstName());
		ctx.setVariable("companyName", messages.getMessage("application.company", null, enLocale));

		// Create the HTML body using Thymeleaf
		final String htmlContent = templateEngine.process("activation.html", ctx);

		sendMail(subject, htmlContent, user.getEmail(), "circle-icon-user.png", userIcon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.chronograph.service.EmailService#emailError(java.lang.Throwable)
	 */
	@Override
	public void emailError(Throwable throwable) {
		String subject = ExceptionUtils.getMessage(throwable);
		String content = ExceptionUtils.getStackTrace(throwable);
		sendMail(subject, content, "babltiga@mail.ru", null, null);
	}

	/**
	 * Send mail by given data...
	 * 
	 * @param subject
	 * @param to
	 * @param content
	 * @param inlineLogo
	 */
	private void sendMail(String subject, String content, String mailTo, String inlineLogo, Resource icon,
			AbstractDataFile... attachements) {
		if (notSend) {
			return;
		}

		try {
			if (!testEmailTo.isEmpty()) {
				StringBuilder testEmailContent = new StringBuilder();
				testEmailContent.append("------------------------------------------------------------------<br/>");
				testEmailContent.append("The original e-mail was address to: ").append(mailTo).append("<br/>");
				testEmailContent.append("------------------------------------------------------------------<br/>");
				testEmailContent.append(content);
				content = testEmailContent.toString();

				subject = "TEST MODE: " + subject;
				mailTo = testEmailTo;
			}

			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true
																									// =
																									// multipart
			message.setSubject(subject);
			message.setFrom(from);
			message.setTo(mailTo);
			if (attachements != null) {
				for (AbstractDataFile attachement : attachements) {
					message.addAttachment(attachement.getOriginalName(), new File(attachement.getSystemPath()));
				}
			}

			message.setText(content, true); // true = isHtml

			// Add the inline image, referenced from the HTML code as
			// "cid:${imageResourceName}"
			message.addInline("logo.png", logo, "image/png");
			if (icon != null && inlineLogo != null) {
				message.addInline(inlineLogo, icon, "image/png");
			}

			// Send mail
			mailSender.send(mimeMessage);
		} catch (MailException | MessagingException ex) {
			LOGGER.warn("Error while sending emial message {}", ex);
			throw new EmailSentFailedException("Error while sending email", ex);
		}
	}

	private void sendMail(String subject, String content, String mailTo, byte[] attachement, String attachementName) {
		if (notSend) {
			return;
		}

		try {
			if (!testEmailTo.isEmpty()) {
				StringBuilder testEmailContent = new StringBuilder();
				testEmailContent.append("------------------------------------------------------------------<br/>");
				testEmailContent.append("The original e-mail was address to: ").append(mailTo).append("<br/>");
				testEmailContent.append("------------------------------------------------------------------<br/>");
				testEmailContent.append(content);
				content = testEmailContent.toString();

				subject = "TEST MODE: " + subject;
				mailTo = testEmailTo;
			}

			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true
																									// =
																									// multipart
			message.setSubject(subject);
			message.setFrom(from);
			message.setTo(mailTo);
			if (attachement != null) {
				message.addAttachment(attachementName, new ByteArrayResource(attachement));
			}

			message.setText(content, true); // true = isHtml

			// Add the inline image, referenced from the HTML code as
			// "cid:${imageResourceName}"
			message.addInline("logo.png", logo, "image/png");

			// Send mail
			mailSender.send(mimeMessage);
		} catch (MailException | MessagingException ex) {
			LOGGER.warn("Error while sending emial message {}", ex);
			throw new EmailSentFailedException("Error while sending email", ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.chronograph.service.SearchAware#search(am.chronograph.search.
	 * SearchCriteria)
	 */
	@Override
	public SearchResult<EmailTemplate> search(SearchCriteria<EmailTemplate> criteria) {
		return emailtemplateDao.search(criteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.chronograph.service.SearchAware#getEmptyCriteria()
	 */
	@Override
	public SearchCriteria<EmailTemplate> getEmptyCriteria() {
		return new EmailTemplateSearchCriteria();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.chronograph.service.EmailService#getEmailTemplate(am.chronograph.
	 * domain.email.EmailTemplate.Type)
	 */
	@Override
	public EmailTemplate getEmailTemplate(Type type) {
		EmailTemplateSearchCriteria crit = new EmailTemplateSearchCriteria();
		crit.setType(type);
		SearchResult<EmailTemplate> results = search(crit);
		return results.getResult().get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * am.chronograph.service.EmailService#save(am.chronograph.domain.email.
	 * EmailTemplate)
	 */
	@Override
	@Transactional
	public EmailTemplate save(EmailTemplate emailTemplate) {
		return emailTemplate.getId() == null ? emailtemplateDao.save(emailTemplate)
				: emailtemplateDao.merge(emailTemplate);
	}
}
