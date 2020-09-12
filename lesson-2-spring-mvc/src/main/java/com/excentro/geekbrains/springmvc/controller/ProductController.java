package com.excentro.geekbrains.springmvc.controller;

import com.excentro.geekbrains.springmvc.persistence.entity.Product;
import com.excentro.geekbrains.springmvc.persistence.repo.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

  public static final String PRODUCT_URL = "product";

  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

  @Autowired private ProductRepository productRepository;

  @GetMapping
  public String allProducts(
      Model model, @RequestParam(value = "title", required = false) String title) {
    List<Product> products;
    logger.info("Filtering by title: {}", title);
    if (title == null || title.isEmpty()) {
      products = productRepository.findAll();
    } else {
      products = productRepository.findByTitleLike("%" + title + "%");
    }
    model.addAttribute("products", products);
    return "products";
  }

  @GetMapping("/{id}")
  public String editProduct(@PathVariable("id") Long id, Model model) {
    Product product = productRepository.findById(id).orElse(new Product());
    model.addAttribute(PRODUCT_URL, product);
    return PRODUCT_URL;
  }

  @PostMapping("/update")
  public String updateProduct(Product product) {
    productRepository.save(product);
    return "redirect:/products";
  }

  @GetMapping("/new")
  public String addNewProduct(Model model) {
    Product product = new Product(null, "", new BigDecimal(0));
    model.addAttribute(PRODUCT_URL, product);
    return PRODUCT_URL;
  }

  @DeleteMapping("/{id}/delete")
  public String deleteUser(@PathVariable("id") Long id) {
    productRepository.deleteById(id);
    return "redirect:/products";
  }
}
