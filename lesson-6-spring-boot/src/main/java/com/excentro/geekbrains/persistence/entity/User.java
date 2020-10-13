package com.excentro.geekbrains.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String login;
  private String password;
  private boolean enabled;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<Role> roles = new HashSet<>();

  public User(String login, String password) {
    this.login = login;
    this.password = password;
    this.enabled = true;
  }

  public User() {}

  public void addRole(Role role) {
    roles.add(role);
    role.getUsers().add(this);
  }
}
