package app.service.product;

import app.entity.Product;
import app.error.ErrorCode;
import app.error.exception.cart.OutOfStockException;

public class StockCheckerServiceImpl implements StockCheckerService {

  @Override
  public void isStockEnough(Product product, Long requestQuantity) {
    if(product.getQuantity()-requestQuantity < 0) throw new OutOfStockException(
        ErrorCode.QUANTITY_IS_NOT_SUFFICIENT);
  }
}
