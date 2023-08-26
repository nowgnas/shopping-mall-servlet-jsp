package app.dao.category;

import app.dto.product.ProductListItem;
import app.entity.Category;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;

public class CategoryDao implements CategoryDaoFrame<Long, Category> {
  private static CategoryDao instance;
  private Logger log = Logger.getLogger("CategoryDao");

  private CategoryDao() {}

  public static CategoryDao getInstance() {
    if (instance == null) return new CategoryDao();
    return instance;
  }

  @Override
  public int insert(Category category, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public int update(Category category, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public int deleteById(Long aLong, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public Optional<Category> selectById(Long aLong, SqlSession session) throws Exception {
    return Optional.empty();
  }

  @Override
  public List<Category> selectAll(SqlSession session) throws Exception {
    return session.selectList("category.first-category");
  }

  @Override
  public List<ProductListItem> selectProductByCategoryName(
      Map<String, Object> map, SqlSession session) {
    return session.selectList("category.search-product-by-category", map);
  }
}
