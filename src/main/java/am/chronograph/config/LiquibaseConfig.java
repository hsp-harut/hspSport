package am.chronograph.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import liquibase.integration.spring.SpringLiquibase;

/**
 * The spring managed liquibase configuration.
 * @author tigranbabloyan
 *
 */
@Configuration
public class LiquibaseConfig {

	@Bean(name = "liquibase")
	SpringLiquibase liquibase(DataSource dataSource) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("classpath:db-migrations/db.changelog-main.xml");
		return liquibase;
	}
}
