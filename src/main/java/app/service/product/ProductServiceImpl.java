package app.service.product;

import app.dao.product.ProductDao;
import app.dao.product.ProductDaoFrame;
import app.dto.paging.Pagination;
import app.dto.product.ProductDetail;
import app.dto.product.ProductListItem;
import app.dto.product.response.ProductListWithPagination;
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
  public ProductListWithPagination<List<ProductListItem>, Pagination> getProductsByHigherPrice() throws Exception {
    Pagination pagination = Pagination.builder().build();
    return dao.selectAllSortByPriceDesc(pagination, session);
  }

  @Override
  public List<ProductListItem> getProductsByDate() throws Exception {
    return dao.selectAllSortByDate(session);
  }
}
