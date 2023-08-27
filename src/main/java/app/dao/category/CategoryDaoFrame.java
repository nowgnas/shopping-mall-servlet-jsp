package app.dao.category;

import app.dao.DaoFrame;
import app.dto.category.CategoryIdListItem;
import app.dto.product.ProductListItem;
import app.dto.product.response.ProductSearchBySubCategory;
import app.entity.Category;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

public interface CategoryDaoFrame<K, V extends Category> extends DaoFrame<K, V> {
  List<ProductListItem> selectProductByCategoryName(Map<String, Object> map, SqlSession session);

  List<Long> selectSubCategoryByName(String keyword, SqlSession session);

  List<ProductListItem> selectProductBySubCategoryName(
      Map<String, Object> map, SqlSession session);
}
