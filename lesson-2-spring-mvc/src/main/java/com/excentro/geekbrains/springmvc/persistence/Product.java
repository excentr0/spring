package com.excentro.geekbrains.springmvc.persistence;

import java.math.BigDecimal;

public class Product {

  private final int id;
  private final String title;
  private final BigDecimal cost;

  public Product(int id, String title, BigDecimal cost) {
    this.id = id;
    this.title = title;
    this.cost = cost;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public BigDecimal getCost() {
    return cost;
  }
}
