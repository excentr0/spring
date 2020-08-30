package ru.geekbrains.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ChatServerRunner {
  public static void main(String[] args) {
    //    ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    ChatServer server = context.getBean("chatServer", ChatServer.class);
    server.start(7777);
  }
}
