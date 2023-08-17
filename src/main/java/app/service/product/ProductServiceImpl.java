package app.service.product;

import app.dao.product.ProductDao;
import app.dao.product.ProductDaoFrame;
import app.dto.product.ProductListItem;
import app.entity.Product;
import app.utils.GetSessionFactory;
import app.utils.ModelMapperStrict;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.modelmapper.ModelMapper;

public class ProductServiceImpl implements ProductService {
  private ProductDaoFrame dao;
  private SqlSession session;

  public ProductServiceImpl() {
    this.dao = ProductDao.getInstance();
    this.session = GetSessionFactory.getInstance().openSession();
  }

  @Override
  public List<ProductListItem> getProductsByLowerPrice() throws Exception {
    List<Product> products = dao.selectAllSortByPrice(session);
    ModelMapper mapper = ModelMapperStrict.strictMapper();
    List<ProductListItem> list = new ArrayList<>();
    for (Product item : products) {
      list.add(mapper.map(item, ProductListItem.class));
    }
    return list;
  }

  @Override
  public List<ProductListItem> getProductsByHigherPrice() {
    return null;
  }

  @Override
  public List<ProductListItem> getProductsByDate() {
    return null;
  }
}
