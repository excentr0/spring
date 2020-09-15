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

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("/products")
public class ProductController {

  public static final String PRODUCT_URL = "product";

  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

  @Autowired private ProductRepository productRepository;

  @GetMapping
  public String allProducts(
      Model model,
      @RequestParam(value = "title", required = false) String title,
      @RequestParam(value = "priceLess", required = false) BigDecimal priceLess,
      @RequestParam(value = "priceGreater", required = false) BigDecimal priceGreater) {
    List<Product> products;
    logger.info("Filtering by title: {}", title);
    if ((title == null || title.isEmpty()) && (priceLess == null && priceGreater == null)) {
      products = productRepository.findAll();
    } else if (priceLess != null && priceGreater == null) {
      products = productRepository.findByCostLessThan(priceLess);
    } else if (priceLess == null && priceGreater != null) {
      products = productRepository.findByCostGreaterThan(priceGreater);
    } else if (priceLess != null) {
      products = productRepository.findByCostBetween(priceLess, priceGreater);
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

  /** Добавляем тестовые товары. */
  @PostConstruct
  public void init() {
    Product product =
        new Product(null, "Phone1", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product1 =
        new Product(null, "Phone2", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product2 =
        new Product(null, "Phone3", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product3 =
        new Product(null, "Phone4", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product4 =
        new Product(null, "Phone5", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product5 =
        new Product(null, "Phone6", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product6 =
        new Product(null, "Phone7", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product7 =
        new Product(null, "Phone8", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product8 =
        new Product(null, "Phone9", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product9 =
        new Product(null, "Phone10", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product10 =
        new Product(null, "Phone11", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product11 =
        new Product(null, "Phone12", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product12 =
        new Product(null, "Phone13", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product13 =
        new Product(null, "Phone14", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product14 =
        new Product(null, "Phone15", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product15 =
        new Product(null, "Phone16", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product16 =
        new Product(null, "Phone17", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product17 =
        new Product(null, "Phone18", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product18 =
        new Product(null, "Phone19", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product19 =
        new Product(null, "Phone20", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));

    List<Product> products =
        Arrays.asList(
            product, product1, product2, product3, product4, product5, product6, product7, product8,
            product9, product10, product11, product12, product13, product14, product15, product16,
            product17, product18, product19);

    productRepository.saveAll(products);
  }
}
