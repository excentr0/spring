package ru.geekbrains.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.geekbrains.server.auth.AuthService;
import ru.geekbrains.server.auth.AuthServiceJdbcImpl;
import ru.geekbrains.server.persistence.UserRepository;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = "ru.geekbrains.server")
public class SpringConfig {
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUsername("root");
    dataSource.setPassword("root");
    dataSource.setUrl(
        "jdbc:mysql://localhost:3306/network_chat?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8");
    return dataSource;
  }

  @Bean
  public UserRepository userRepository(DataSource dataSource) throws SQLException {
    return new UserRepository(dataSource);
  }

  @Bean
  public AuthService authService(UserRepository userRepository) {
    return new AuthServiceJdbcImpl(userRepository);
  }

  //  @Bean
  //  public ChatServer chatServer(AuthService authService) {
  //    return new ChatServer(authService);
  //  }
  // ChatServer сам найдется из-за аннотации Component над классом
}
