package com.excentro.geekbrains.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Authority {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String authority;

  @ManyToMany(mappedBy = "authority")
  private Set<User> users = new HashSet<>();

  public Authority() {}

  public Authority(String authority) {
    this.authority = authority;
  }
}
