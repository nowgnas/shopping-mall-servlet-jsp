package app.service.product;

import app.dao.product.ProductDao;
import app.dao.product.ProductDaoFrame;
import app.dto.product.ProductDetail;
import app.dto.product.ProductListItem;
import app.utils.GetSessionFactory;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public class ProductServiceImpl implements ProductService {
  private ProductDaoFrame dao;
  private SqlSession session;

  public ProductServiceImpl() {
    this.dao = ProductDao.getInstance();
    this.session = GetSessionFactory.getInstance().openSession();
  }

  @Override
  public ProductDetail getProductDetail(Long productId) throws Exception {
    return null;
  }

  @Override
  public List<ProductListItem> getProductsByLowerPrice() throws Exception {
    return dao.selectAllSortByPrice(session);
  }

  @Override
  public List<ProductListItem> getProductsByHigherPrice() throws Exception {
    return dao.selectAllSortByPriceDesc(session);
  }

  @Override
  public List<ProductListItem> getProductsByDate() throws Exception {
    return dao.selectAllSortByDate(session);
  }
}
