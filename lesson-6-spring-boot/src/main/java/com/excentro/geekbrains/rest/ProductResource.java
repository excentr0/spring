package com.excentro.geekbrains.rest;

import com.excentro.geekbrains.persistence.entity.Product;
import com.excentro.geekbrains.persistence.repo.ProductRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/product")
@RestController
@Tag(name = "Product API", description = "API to operate products")
public class ProductResource {
  private final ProductRepository productRepository;

  @Autowired
  public ProductResource(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping(path = "/all")
  public List<Product> findAll() {
    return productRepository.findAll();
  }

  @GetMapping(path = "/{id}/id")
  public Product findById(@PathVariable Long id) {
    return productRepository.findById(id).orElse(new Product());
  }

  @PostMapping
  public Product createProduct(@RequestBody Product product) throws IllegalAccessException {
    if (product.getId() != null) {
      throw new IllegalAccessException("Product exists");
    }
    productRepository.save(product);
    return product;
  }

  @PutMapping
  public Product updateProduct(@RequestBody Product product) {
    productRepository.save(product);
    return product;
  }

  @DeleteMapping(path = "/{id}/id")
  public void deleteById(@PathVariable Long id) {
    productRepository.deleteById(id);
  }

  @ExceptionHandler
  public ResponseEntity<String> illegalAccessExceptionHandler(
      IllegalAccessException illegalAccessException) {
    return new ResponseEntity<>(
        illegalAccessException.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
  }
}
