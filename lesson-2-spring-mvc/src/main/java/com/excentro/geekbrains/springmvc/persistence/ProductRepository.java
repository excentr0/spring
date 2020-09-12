package com.excentro.geekbrains.springmvc.persistence;

import com.excentro.geekbrains.springmvc.persistence.entity.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductRepository {

  @PersistenceContext private EntityManager em;

  @Transactional
  public void insert(Product product) {
    em.persist(product);
  }

  @Transactional
  public void delete(Long id) {
    Product product = em.find(Product.class, id);
    if (product != null) {
      em.remove(product);
    }
  }

  public List<Product> getAllProducts() {
    return em.createQuery("from Product", Product.class).getResultList();
  }

  public Product getById(Long id) {
    return em.find(Product.class, id);
  }

  @Transactional
  public void update(Product product) {
    em.merge(product);
  }
}
