package app.dao;

import app.entity.Product;
import app.utils.GetSessionFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSession;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductDaoImplTest {
  ProductDaoImpl productDao = new ProductDaoImpl();
  SqlSession session;
  private DataSource dataSource;
  private Connection connection;

  public ProductDaoImplTest() {
    this.session = GetSessionFactory.getInstance().openSession();
  }

  @BeforeEach
  public void setUp() throws SQLException {
    dataSource = new JdbcDataSource();
    ((JdbcDataSource) dataSource).setURL("jdbc:h2:mem:shoppingmall;MODE=MySQL");
    connection = dataSource.getConnection();

    // Initialize database schema and test data
    runSqlScript("schema.sql");
    runSqlScript("producttest.sql");
  }

  // Helper method to execute SQL scripts
  private void runSqlScript(String scriptName) throws SQLException {
    try (InputStream scriptStream = getClass().getResourceAsStream("/" + scriptName);
        InputStreamReader reader = new InputStreamReader(scriptStream);
        BufferedReader bufferedReader = new BufferedReader(reader)) {

      String line;
      StringBuilder sqlStatement = new StringBuilder();

      while ((line = bufferedReader.readLine()) != null) {
        if (!line.trim().isEmpty() && !line.trim().startsWith("—")) {
          sqlStatement.append(line);

          if (line.endsWith(";")) {
            connection.createStatement().execute(sqlStatement.toString());
            sqlStatement.setLength(0);
          }
        }
      }
    } catch (IOException e) {
      // Handle IOException if needed
    }
  }

  @Test
  void insert() {
    // product 추가는 없다
  }

  @Test
  void update() {}

  @Test
  void deleteById() {}

  @Test
  void selectById() {
    // 상품 id를 이용한 상품 조회

  }

  @Test
  void selectAll() throws Exception {
    // case
    List<Product> products = productDao.selectAll(session); // 모든 상품 조회
    Assertions.assertEquals(1, products.size());
  }
}
