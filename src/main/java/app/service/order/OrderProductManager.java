package app.service.order;

import app.dao.product.ProductDao;
import app.dto.product.response.ProductDetailForOrder;
import app.entity.Product;
import app.exception.order.OrderProductNotEnoughStockQuantityException;
import app.exception.order.OrderProductUpdateStockQuantityException;
import app.exception.product.ProductEntityNotFoundException;
import org.apache.ibatis.session.SqlSession;

public class OrderProductManager {

  private final ProductDao productDao = ProductDao.getInstance();

  public Product determineProduct(Long productId, SqlSession session) throws Exception {
    return productDao
        .selectById(productId, session)
        .orElseThrow(ProductEntityNotFoundException::new);
  }

  public ProductDetailForOrder determineProductDetailForOrder(Long productId, SqlSession session)
      throws Exception {
    return productDao.selectProductDetail(productId, session);
  }

  public void validateEnoughStockQuantity(Long stockQuantity, Long buyQuantity) {
    if (stockQuantity - buyQuantity < 0) {
      throw new OrderProductNotEnoughStockQuantityException();
    }
  }

  public void updateStockQuantity(Product product, Long quantity, SqlSession session)
      throws Exception {
    product.updateQuantity(quantity);
    if (productDao.update(product, session) == 0) {
      throw new OrderProductUpdateStockQuantityException();
    }
  }
}
