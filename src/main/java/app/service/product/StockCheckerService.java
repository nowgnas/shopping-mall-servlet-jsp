package app.service.product;

import app.entity.Product;

public interface StockCheckerService {
  void isStockEnough(Product product, Long requestQuantity);
}
