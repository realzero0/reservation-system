package bicycle.reservation.config;

import javax.sql.*;

import org.apache.commons.dbcp2.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.*;
import org.springframework.transaction.annotation.*;

@Configuration
@PropertySource("classpath:/application.properties")
@EnableTransactionManagement
public class DbConfig {
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transctionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
