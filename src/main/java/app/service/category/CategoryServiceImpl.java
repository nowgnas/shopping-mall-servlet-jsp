package app.service.category;

import app.dao.category.CategoryDao;
import app.dao.category.CategoryDaoFrame;
import app.dto.category.response.SubCategory;
import app.dto.paging.Pagination;
import app.dto.product.ProductListItem;
import app.dto.product.response.ProductListWithPagination;
import app.entity.Category;
import app.exception.product.CategoryListNotFound;
import app.exception.product.ProductNotFoundException;
import app.utils.GetSessionFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class CategoryServiceImpl implements CategoryService {
  private static CategoryServiceImpl instance;
  private final SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();
  Logger log = Logger.getLogger("CategoryServiceImpl");
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
    if (categories.isEmpty()) throw new CategoryListNotFound();
    session.close();
    return categories;
  }

  @Override
  public ProductListWithPagination getProductListByCategoryName(
      Long memberId, String keyword, int curPage) {
    SqlSession session = sessionFactory.openSession();
    List<Long> idListItems = new ArrayList<>();
    List<SubCategory> categoryByName = dao.selectSubCategoryByName(keyword, session);

    for (SubCategory s : categoryByName) {
      if (s.getHigh() == null && s.getMiddle() == null) idListItems.add(s.getLow());
      else if (s.getHigh() == null) idListItems.add(s.getMiddle());
      else idListItems.add(s.getHigh());
    }

    log.info("id list " + idListItems.toString());
    if (idListItems.isEmpty()) throw new CategoryListNotFound();
    Map<String, Object> map = new HashMap<>();
    map.put("memberId", memberId);
    map.put("id", idListItems);
    map.put("offset", (curPage - 1) * 9);
    List<ProductListItem> productListItems = dao.selectProductBySubCategoryName(map, session);
    session.close();
    if (productListItems.isEmpty()) throw new ProductNotFoundException();

    int totalPage = (int) Math.ceil(productListItems.size() / 9);
    Pagination page = Pagination.builder().currentPage(curPage).perPage(9).build();
    return ProductListWithPagination.makeListWithPaging(productListItems, page, totalPage);
  }
}
