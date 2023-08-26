package app.service.category;

import app.dao.category.CategoryDao;
import app.dao.category.CategoryDaoFrame;
import app.dto.paging.Pagination;
import app.dto.product.ProductListItem;
import app.dto.product.response.ProductListWithPagination;
import app.entity.Category;
import app.utils.GetSessionFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class CategoryServiceImpl implements CategoryService {
  private static CategoryServiceImpl instance;
  private final SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();
  private CategoryDaoFrame<Long, Category> dao;

  public CategoryServiceImpl() {
    this.dao = CategoryDao.getInstance();
  }

  public static CategoryServiceImpl getInstance() {
    if (instance == null) return new CategoryServiceImpl();
    return instance;
  }

  @Override
  public List<Category> getAllCategory() throws Exception {
    SqlSession session = sessionFactory.openSession();
    List<Category> categories = dao.selectAll(session);
    session.close();
    return categories;
  }

  @Override
  public ProductListWithPagination getProductListByCategoryName(
      Long memberId, String keyword, int curPage) {
    SqlSession session = sessionFactory.openSession();
    Map<String, Object> map = new HashMap<>();
    map.put("memberId", memberId);
    map.put("keyword", keyword);
    map.put("curPage", curPage);
    List<ProductListItem> productListItems = dao.selectProductByCategoryName(map, session);
    session.close();
    int totalPage = (int) Math.ceil(productListItems.size() / 9);
    Pagination page = Pagination.builder().currentPage(curPage).perPage(9).build();
    return ProductListWithPagination.makeListWithPaging(productListItems, page, totalPage);
  }
}
