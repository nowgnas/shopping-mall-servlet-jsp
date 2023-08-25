package app.service.category;

import app.dao.category.CategoryDao;
import app.dao.category.CategoryDaoFrame;
import app.entity.Category;
import app.utils.GetSessionFactory;
import java.util.List;
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
    return dao.selectAll(session);
  }
}
