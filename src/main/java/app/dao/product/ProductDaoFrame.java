package app.dao.product;

import app.dao.DaoFrame;
import app.dto.product.ProductItemQuantity;
import app.entity.Product;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public interface ProductDaoFrame<K, V extends Product> extends DaoFrame<K, V> {
  List<Product> selectAllSortByPriceDesc(SqlSession session) throws Exception;

  List<Product> selectAllSortByPrice(SqlSession session) throws Exception;

  List<Product> selectAllSortByDate(SqlSession session) throws Exception;

  /**
   * 개별 상품 재고 개수 조회
   *
   * @param productId 상품 id
   * @param session sql session
   * @return 개수
   */
  ProductItemQuantity selectProductQuantity(Long productId, SqlSession session);
}
