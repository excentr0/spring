package com.excentro.geekbrains.security;

import com.excentro.geekbrains.persistence.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class UserAuthService implements UserDetailsService {
  private final UserRepository userRepository;

  @Autowired
  public UserAuthService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return userRepository
        .findByLogin(username)
        .map(
            user -> {
              String login = user.getLogin();
              String password = user.getPassword();
              String userRole = user.getRole();
              return new User(
                  login, password, Collections.singletonList(new SimpleGrantedAuthority(userRole)));
            })
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
