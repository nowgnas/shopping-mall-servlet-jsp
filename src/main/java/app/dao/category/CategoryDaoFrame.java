package app.dao.category;

import app.dao.DaoFrame;
import app.dto.category.response.CategoryHierarchy;
import app.dto.category.response.SubCategory;
import app.dto.product.ProductListItem;
import app.entity.Category;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public interface CategoryDaoFrame<K, V extends Category> extends DaoFrame<K, V> {
  List<ProductListItem> selectProductByCategoryName(Map<String, Object> map, SqlSession session);

  List<SubCategory> selectSubCategoryByName(String keyword, SqlSession session);

  List<ProductListItem> selectProductBySubCategoryName(Map<String, Object> map, SqlSession session);

  List<CategoryHierarchy> hierarchyCategory(SqlSession session);
}
