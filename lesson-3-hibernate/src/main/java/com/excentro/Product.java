package com.excentro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {

  @Column String title;
  @Column BigDecimal price;
  @ManyToOne Buyer buyer;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  public Product(String title, BigDecimal price, Buyer buyer) {
    this.title = title;
    this.price = price;
    this.buyer = buyer;
  }

  public Product() {}

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Buyer getBuyer() {
    return buyer;
  }

  public void setBuyer(Buyer buyer) {
    this.buyer = buyer;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Product{"
        + "title='"
        + title
        + '\''
        + ", price="
        + price
        + ", buyer="
        + buyer.name
        + ", buyer_id="
        + buyer.getId()
        + ", id="
        + id
        + '}';
  }
}
