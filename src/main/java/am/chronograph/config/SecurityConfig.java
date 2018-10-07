package am.chronograph.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * The spring managed spring security configuration.
 * 
 * @author tigranbabloyan
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_USER = "USER";
	public static final String ADMIN_EMAIL = "admin@hsp.am";

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
//		http.addFilterBefore(new OpenSessionFilter(), HeaderWriterFilter.class);
		http.csrf().disable().headers().disable()
			.authorizeRequests()
				.antMatchers("/javax.faces.resource/**", "/login", "/error", "/activate", "/forgot").permitAll()
				.anyRequest().authenticated()
			.and().
				formLogin()
					.loginPage("/login")
					.loginProcessingUrl("/login")
					.failureUrl("/login?error")
					.defaultSuccessUrl("/dashboard").permitAll()
				.and()
					.logout().invalidateHttpSession(true)
					.logoutSuccessUrl("/login?success").permitAll()
			.and().rememberMe().alwaysRemember(true).key("XACHATRYAN_GAGULIK");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	@Scope(WebApplicationContext.SCOPE_REQUEST)
	Authentication authentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
