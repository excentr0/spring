package com.excentro;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    EntityManagerFactory emFactory =
        new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    /*EntityManager entityManager = emFactory.createEntityManager();
    User user1 = new User(null, "Alex", "alex");
    User user2 = new User(null, "Mike", "mike");

    entityManager.getTransaction().begin();
    entityManager.persist(user1);
    entityManager.persist(user2);
    entityManager.getTransaction().commit();
    entityManager.close();*/

    EntityManager entityManager = emFactory.createEntityManager();

    User user = entityManager.find(User.class, 1);
    System.out.println(user);

    List<User> users = entityManager.createQuery("from User", User.class).getResultList();
    System.out.println(users);

    User singleResult =
        entityManager
            .createQuery("from User where login=:login", User.class)
            .setParameter("login", "mike")
            .getSingleResult();
    System.out.println(singleResult);
  }
}
