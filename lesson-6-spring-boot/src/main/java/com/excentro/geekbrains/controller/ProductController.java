package com.excentro.geekbrains.controller;

import com.excentro.geekbrains.persistence.entity.Product;
import com.excentro.geekbrains.persistence.entity.Role;
import com.excentro.geekbrains.persistence.entity.User;
import com.excentro.geekbrains.persistence.repo.ProductRepository;
import com.excentro.geekbrains.persistence.repo.ProductSpecification;
import com.excentro.geekbrains.persistence.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Controller
@RequestMapping("/products")
public class ProductController {

  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public ProductController(
      ProductRepository productRepository,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.productRepository = productRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping
  public String allProducts(
      Model model,
      @RequestParam(value = "title", required = false, defaultValue = "") String title,
      @RequestParam(value = "priceLess", required = false) BigDecimal priceLess,
      @RequestParam(value = "priceGreater", required = false) BigDecimal priceGreater,
      @RequestParam(value = "page") Optional<Integer> page,
      @RequestParam(value = "size") Optional<Integer> size,
      @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy) {

    PageRequest pageRequest;

    if (sortBy.equals("id") || sortBy.equals("title")) {
      pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), Sort.Direction.ASC, sortBy);
    } else {
      pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), Sort.Direction.ASC, "id");
    }

    Specification<Product> specification = ProductSpecification.trueLiteral();
    if (priceLess != null && priceGreater == null) {
      specification = specification.and(ProductSpecification.priceLess(priceLess));
    } else if (priceLess == null && priceGreater != null) {
      specification = specification.and(ProductSpecification.priceGreater(priceGreater));
    } else if (priceLess != null) {
      specification = specification.and(ProductSpecification.priceBetween(priceLess, priceGreater));
    } else {
      specification = specification.and(ProductSpecification.titleLike(title));
    }
    model.addAttribute("productsPage", productRepository.findAll(specification, pageRequest));
    return "products";
  }

  @GetMapping("/{id}")
  public String editProduct(@PathVariable("id") Long id, Model model) {
    Product product = productRepository.findById(id).orElse(new Product());
    model.addAttribute("product", product);
    return "product";
  }

  @PostMapping("/update")
  public String updateProduct(Product product) {
    productRepository.save(product);
    return "redirect:/products";
  }

  @GetMapping("/new")
  public String addNewProduct(Model model) {
    Product product = new Product("", new BigDecimal(0));
    model.addAttribute("product", product);
    return "product";
  }

  @DeleteMapping("/{id}/delete")
  public String deleteUser(@PathVariable("id") Long id) {
    productRepository.deleteById(id);
    return "redirect:/products";
  }

  /** Добавляем тестовые данные. */
  @PostConstruct
  public void init() {
    // добавляем продукты
    Product product =
        new Product("Phone1", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product1 =
        new Product("Phone2", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product2 =
        new Product("Phone3", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product3 =
        new Product("Phone4", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product4 =
        new Product("Phone5", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product5 =
        new Product("Phone6", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product6 =
        new Product("Phone7", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product7 =
        new Product("Phone8", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product8 =
        new Product("Phone9", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product9 =
        new Product("Phone10", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product10 =
        new Product("Phone11", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product11 =
        new Product("Phone12", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product12 =
        new Product("Phone13", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product13 =
        new Product("Phone14", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product14 =
        new Product("Phone15", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product15 =
        new Product("Phone16", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product16 =
        new Product("Phone17", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product17 =
        new Product("Phone18", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product18 =
        new Product("Phone19", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));
    Product product19 =
        new Product("Phone20", new BigDecimal(ThreadLocalRandom.current().nextInt(10000)));

    List<Product> products =
        Arrays.asList(
            product, product1, product2, product3, product4, product5, product6, product7, product8,
            product9, product10, product11, product12, product13, product14, product15, product16,
            product17, product18, product19);

    productRepository.saveAll(products);

    // добавляем пользователей и их роли
    Role adminRole = new Role("ROLE_ADMIN");
    Role managerRole = new Role("ROLE_MANAGER");

    User admin = new User("admin", passwordEncoder.encode("admin"));
    User manager = new User("manager", passwordEncoder.encode("manager"));

    admin.addRole(adminRole);
    manager.addRole(managerRole);

    userRepository.saveAll(Arrays.asList(admin, manager));
  }
}
