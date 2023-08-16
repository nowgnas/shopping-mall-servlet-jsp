package utils;

import java.io.IOException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class GetSessionFactory {

  private static SqlSessionFactory instance;

  // Singleton
  public static synchronized SqlSessionFactory getInstance() {
    if (instance == null) {
      try {
        instance =
            new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis.xml"));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return instance;
  }
}
