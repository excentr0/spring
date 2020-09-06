package com.excentro;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    EntityManagerFactory emFactory =
        new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    if (args.length == 0) {
      listAndHelp(emFactory);
    } else if (args[0].equals("add")) {
      if (args[1].equals("product")) {
        addProduct(args, emFactory);
      } else if (args[1].equals("buyer")) {
        addBuyer(args[2], emFactory);
      }
    } else if (args[0].equals("list")) {
      listBuyersProducts(args[1], emFactory);
    } else if (args[0].equals("del")) {
      deleteProductOrBuyer(args, emFactory);
    } else if (args[0].equals("search")) {
      searchProduct(args[1], emFactory);
    }
  }

  private static void listAndHelp(EntityManagerFactory emFactory) {
    EntityManager entityManager = emFactory.createEntityManager();
    entityManager.getTransaction().begin();
    List<Buyer> buyers = entityManager.createQuery("from Buyer ", Buyer.class).getResultList();
    System.out.println("Example: add product title price buyer_id");
    System.out.println("Example: add buyer name");
    System.out.println("Example: list buyer_id");
    System.out.println("Example: search product_name");
    System.out.println("Example: del product/buyer id");
    System.out.println(buyers);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  private static void addProduct(String[] args, EntityManagerFactory emFactory) {
    EntityManager entityManager = emFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Buyer buyer = entityManager.find(Buyer.class, Integer.valueOf(args[4]));
    if (buyer == null) {
      System.out.println("Where is now buyers yet. Add some");
      return;
    }
    Product product = new Product(args[2], new BigDecimal(args[3]), buyer);
    entityManager.persist(product);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  private static void addBuyer(String arg, EntityManagerFactory emFactory) {
    EntityManager entityManager = emFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Buyer buyer = new Buyer(arg);
    entityManager.persist(buyer);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  private static void listBuyersProducts(String arg, EntityManagerFactory emFactory) {
    EntityManager entityManager = emFactory.createEntityManager();
    entityManager.getTransaction().begin();
    List<Product> products =
        entityManager
            .createQuery("from Product where buyer.id=:id", Product.class)
            .setParameter("id", Integer.valueOf(arg))
            .getResultList();
    entityManager.getTransaction().commit();
    entityManager.close();
    System.out.println(products);
  }

  private static void deleteProductOrBuyer(String[] args, EntityManagerFactory emFactory) {
    EntityManager entityManager = emFactory.createEntityManager();
    entityManager.getTransaction().begin();
    if (args[1].equals("product")) {
      Product product = entityManager.find(Product.class, Integer.valueOf(args[2]));
      entityManager.remove(product);
    } else if (args[1].equals("buyer")) {
      Buyer buyer = entityManager.find(Buyer.class, Integer.valueOf(args[2]));
      entityManager.remove(buyer);
    }
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  private static void searchProduct(String arg, EntityManagerFactory emFactory) {
    EntityManager entityManager = emFactory.createEntityManager();
    entityManager.getTransaction().begin();
    List<Product> products =
        entityManager
            .createQuery("from Product where title like :ss", Product.class)
            .setParameter("ss", "%" + arg + "%")
            .getResultList();
    System.out.println(products);
    entityManager.getTransaction().commit();
    entityManager.close();
  }
}
