package com.excentro.geekbrains.springmvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("com.excentro.geekbrains.springmvc.persistence.repo")
public class PersistConfig {
  @Value("${database.driver.class}")
  private String driverClassName;

  @Value("${database.url}")
  private String databaseUrl;

  @Value("${database.username}")
  private String username;

  @Value("${database.password}")
  private String password;

  @Bean(name = "entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(dataSource());
    factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factory.setPackagesToScan("com.excentro.geekbrains.springmvc.persistence.entity");
    factory.setJpaProperties(jpaProperties());
    return factory;
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

  private Properties jpaProperties() {
    Properties jpaProperties = new Properties();
    jpaProperties.put("hibernate.hbm2ddl.auto", "update");
    jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
    jpaProperties.put("hibernate.max_fetch.depth", 3);
    jpaProperties.put("hibernate.jdbc.fetch_size", 50);
    jpaProperties.put("hibernate.jdbc.batch_size", 10);
    jpaProperties.put("hibernate.show_sql", true);
    jpaProperties.put("hibernate.format_sql", true);
    jpaProperties.put("connection.pool_size", 2);
    jpaProperties.put("hibernate.generate_statistics", true);
    return jpaProperties;
  }

  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager tm = new JpaTransactionManager();
    tm.setEntityManagerFactory(entityManagerFactory);
    return tm;
  }
}
