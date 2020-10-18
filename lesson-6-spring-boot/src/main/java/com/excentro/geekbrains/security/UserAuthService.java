package com.excentro.geekbrains.security;

import com.excentro.geekbrains.persistence.repo.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class UserAuthService implements UserDetailsService {
  private final UserRepository userRepository;

  public UserAuthService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return userRepository
        .findByLogin(username)
        .map(
            user ->
                new User(
                    user.getLogin(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))))
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
