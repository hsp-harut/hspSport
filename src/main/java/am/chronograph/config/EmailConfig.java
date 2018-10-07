package am.chronograph.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * The spring managed email configuration.
 * 
 * @author tigranbabloyan
 *
 */
@Configuration
public class EmailConfig {

	private static final String SMTP_HOST_KEY = "smtp.host";

	private static final String SMTP_PORT_KEY = "smtp.port";

	private static final String SMTP_PASSWORD_KEY = "smtp.password";

	private static final String SMTP_USER_KEY = "smtp.user";
	
	private static final String SMTP_USE_TLS= "smtp.tls";
	
	private static final String SMTP_USE_SSL= "smtp.ssl";

	@Bean
	JavaMailSender javaMailService(Environment env) {
		
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(env.getProperty(SMTP_HOST_KEY));
		javaMailSender.setPort(env.getProperty(SMTP_PORT_KEY, Integer.class));
		javaMailSender.setPassword(env.getProperty(SMTP_PASSWORD_KEY));
		javaMailSender.setUsername(env.getProperty(SMTP_USER_KEY));
		javaMailSender.setProtocol("smtp");
		Properties props = new Properties();
		props.put("mail.smtp.connectiontimeout", "60000");
		props.put("mail.smtp.timeout", "60000");
		if(env.getProperty(SMTP_USE_TLS) != null && "true".equalsIgnoreCase(env.getProperty(SMTP_USE_TLS))){
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
		}
		if(env.getProperty(SMTP_USE_SSL) != null && "true".equalsIgnoreCase(env.getProperty(SMTP_USE_SSL))){
			javaMailSender.setProtocol("smtps");
			props.setProperty("mail.transport.protocol", "smtps");
			props.setProperty("mail.smtp.socketFactory.port", env.getProperty(SMTP_PORT_KEY)); 
	        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.setProperty("mail.smtp.socketFactory.fallback", "false");
	        props.setProperty("mail.smtp.ssl.enable", "true");
		}
		javaMailSender.setJavaMailProperties(props);
		return javaMailSender;
	}

}
