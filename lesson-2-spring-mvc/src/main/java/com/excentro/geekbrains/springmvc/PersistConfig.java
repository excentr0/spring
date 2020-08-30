package com.excentro.geekbrains.springmvc;

import java.sql.SQLException;
import javax.sql.DataSource;
import com.excentro.geekbrains.springmvc.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class PersistConfig {

  @Value("${database.driver.class}")
  private String driverClassName;

  @Value("${database.url}")
  private String databaseUrl;

  @Value("${database.username}")
  private String username;

  @Value("${database.password}")
  private String password;

  @Bean
  public UserRepository userRepository(DataSource dataSource) throws SQLException {
    return new UserRepository(dataSource);
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(driverClassName);
    ds.setUsername(username);
    ds.setPassword(password);
    ds.setUrl(databaseUrl);
    return ds;
  }
}
