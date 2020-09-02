package com.excentro.geekbrains.springmvc.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

  private final Connection conn;

  @Autowired
  public ProductRepository(DataSource dataSource) throws SQLException {
    this(dataSource.getConnection());
  }

  public ProductRepository(Connection conn) throws SQLException {
    this.conn = conn;
    createTableIfNotExists(conn);
  }

  private void createTableIfNotExists(Connection conn) throws SQLException {
    try (Statement stmt = conn.createStatement()) {
      stmt.execute(
          "create table products"
              + "("
              + "id int auto_increment,"
              + "title varchar(250) null,"
              + "cost decimal(15,2) null,"
              + "constraint products_pk "
              + "primary key (id)"
              + ");");
    } catch (SQLException e) {

    }
  }

  public void insert(Product product) throws SQLException {
    try (PreparedStatement stmt =
        conn.prepareStatement("insert into products(title, cost) values (?, ?);")) {
      stmt.setString(1, product.getTitle());
      stmt.setBigDecimal(2, product.getCost());
      stmt.execute();
    }
  }

  public List<Product> getAllProducts() throws SQLException {
    List<Product> res = new ArrayList<>();
    try (Statement stmt = conn.createStatement()) {
      try (ResultSet rs = stmt.executeQuery("select id, title, cost from products")) {
        while (rs.next()) {
          res.add(new Product(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3)));
        }
      }
    }
    return res;
  }

  public Product getById(Long id) {
    try (PreparedStatement preparedStatement =
        conn.prepareStatement("select id, title, cost from products where id = ?")) {
      preparedStatement.setLong(1, id);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          return new Product(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3));
        }
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return new Product(-1, "", new BigDecimal(0));
  }

  public void update(Product product) throws SQLException {
    try (PreparedStatement preparedStatement =
        conn.prepareStatement("update products set title = ?, cost =? where id = ?")) {
      preparedStatement.setString(1, product.getTitle());
      preparedStatement.setBigDecimal(2, product.getCost());
      preparedStatement.setInt(3, product.getId());
      int updated = preparedStatement.executeUpdate();
      if (updated > 0) {
        System.out.println(product + " updated successfully");
      }
    }
  }
}
