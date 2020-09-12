package com.excentro.geekbrains.springmvc.controller;

import com.excentro.geekbrains.springmvc.persistence.ProductRepository;
import com.excentro.geekbrains.springmvc.persistence.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

  public static final String PRODUCT_URL = "product";

  @Autowired private ProductRepository productRepository;

  @GetMapping
  public String allProducts(Model model) {
    List<Product> products = productRepository.getAllProducts();
    model.addAttribute("products", products);
    return "products";
  }

  @GetMapping("/{id}")
  public String editProduct(@PathVariable("id") Long id, Model model) {
    Product product = productRepository.getById(id);
    model.addAttribute(PRODUCT_URL, product);
    return PRODUCT_URL;
  }

  @PostMapping("/update")
  public String updateProduct(Product product) {
    if (product.getId() == null) {
      productRepository.insert(product);
    } else {
      productRepository.update(product);
    }
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
    productRepository.delete(id);
    return "redirect:/products";
  }
}
