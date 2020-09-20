package com.excentro.geekbrains.springmvc.persistence.repo;

import com.excentro.geekbrains.springmvc.persistence.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public final class ProductSpecification {
  private ProductSpecification() {}

  public static Specification<Product> trueLiteral() {
    return (root, query, builder) -> builder.isTrue(builder.literal(true));
  }

  public static Specification<Product> titleLike(String title) {
    return (root, query, builder) -> builder.like(root.get("title"), "%" + title + "%");
  }

  public static Specification<Product> priceLess(BigDecimal price) {
    return (root, query, builder) -> builder.lessThan(root.get("cost"), price);
  }

  public static Specification<Product> priceGreater(BigDecimal price) {
    return (root, query, builder) -> builder.greaterThan(root.get("cost"), price);
  }

  public static Specification<Product> priceBetween(BigDecimal priceLo, BigDecimal priceHi) {
    return (root, query, builder) ->
        builder.and(
            builder.greaterThan(root.get("cost"), priceLo),
            builder.lessThan(root.get("cost"), priceHi));
  }
}
