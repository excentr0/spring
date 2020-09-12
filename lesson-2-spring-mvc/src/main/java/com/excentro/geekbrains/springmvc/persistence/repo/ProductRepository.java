package com.excentro.geekbrains.springmvc.persistence.repo;

import com.excentro.geekbrains.springmvc.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByTitleLike(String titlePattern);
}
