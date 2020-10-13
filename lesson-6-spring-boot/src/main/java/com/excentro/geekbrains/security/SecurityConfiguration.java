package com.excentro.geekbrains.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  public static final String ADMIN_ROLE = "ADMIN";

  @Autowired
  public void authConfigure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("mike")
        .password("{bcrypt}$2y$12$0WUu5/CCi/HeHlzDZBy/weVz0R/b7MUDsfsxXghGg.PSvp.65aU42")
        .roles(ADMIN_ROLE);
  }

  @Configuration
  @Order(2)
  public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
      http.authorizeRequests()
          .antMatchers("/")
          .permitAll()
          .antMatchers("/products/**")
          .hasRole(ADMIN_ROLE)
          .and()
          .formLogin()
          .loginPage("/login")
          .failureUrl("/login-error")
          .and()
          .logout()
          .logoutSuccessUrl("/");
    }
  }

  @Configuration
  @Order(1)
  public static class ApiSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
      http.csrf()
          .disable()
          .antMatcher("/api/**")
          .authorizeRequests()
          .anyRequest()
          .hasRole(ADMIN_ROLE)
          .and()
          .httpBasic()
          .authenticationEntryPoint(
              (request, response, authException) -> {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setCharacterEncoding("UTF-8");
                response
                    .getWriter()
                    .println("{\"error\": \"" + authException.getLocalizedMessage() + "\"}");
              })
          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
  }
}
