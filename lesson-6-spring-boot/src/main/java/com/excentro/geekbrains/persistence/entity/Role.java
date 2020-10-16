package com.excentro.geekbrains.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "authority")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String authority;

  @ManyToMany(mappedBy = "authority")
  private Set<User> users = new HashSet<>();

  public Role(String authority) {
    this.authority = authority;
  }

  @Override
  public String toString() {
    return authority;
  }
}
