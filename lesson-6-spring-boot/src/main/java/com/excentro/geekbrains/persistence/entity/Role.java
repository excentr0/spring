package com.excentro.geekbrains.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String roleName;

  @ManyToMany(mappedBy = "roles")
  private Set<User> users = new HashSet<>();
}
