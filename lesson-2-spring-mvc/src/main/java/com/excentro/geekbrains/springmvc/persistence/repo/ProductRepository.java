package com.excentro.geekbrains.springmvc.persistence.repo;

import com.excentro.geekbrains.springmvc.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByTitleLike(String titlePattern);

  List<Product> findByCostLessThan(BigDecimal maxPrice);

  List<Product> findByCostGreaterThan(BigDecimal minPrice);

  List<Product> findByCostBetween(BigDecimal min, BigDecimal max);
}
