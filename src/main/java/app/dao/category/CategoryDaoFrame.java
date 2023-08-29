package app.dao.category;

import app.dao.DaoFrame;
import app.dto.product.ProductListItem;
import app.entity.Category;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public interface CategoryDaoFrame<K, V extends Category> extends DaoFrame<K, V> {
  List<ProductListItem> selectProductByCategoryName(Map<String, Object> map, SqlSession session);

  List<Long> selectSubCategoryByName(String keyword, SqlSession session);

  List<ProductListItem> selectProductBySubCategoryName(Map<String, Object> map, SqlSession session);

  List<Category> hierarchyCategory(SqlSession session);
}
