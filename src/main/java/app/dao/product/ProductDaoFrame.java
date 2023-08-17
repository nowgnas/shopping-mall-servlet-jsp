package app.dao.product;

import app.dao.DaoFrame;
import app.entity.Product;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public interface ProductDaoFrame<K, V extends Product> extends DaoFrame<K, V> {
  List<Product> selectAllSortByPriceDesc(SqlSession session) throws Exception;

  List<Product> selectAllSortByPrice(SqlSession session) throws Exception;

  List<Product> selectAllSortByDate(SqlSession session) throws Exception;
}
