package com.excentro.geekbrains.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String login;
  private String password;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<Role> roles = new HashSet<>();
}
