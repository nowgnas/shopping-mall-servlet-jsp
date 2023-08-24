package app.service.checker;

import app.dao.DaoFrame;
import app.dao.product.ProductDao;
import app.entity.Product;
import app.error.ErrorCode;
import app.error.exception.product.ProductNotFoundException;
import app.utils.GetSessionFactory;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@AllArgsConstructor
public class ProductExistCheckerService implements
    EntityExistCheckerService<Long, Product> {

  @Override
  public Product isExisted(DaoFrame<Long, Product> daoFrame, Long id, SqlSession session) throws Exception {
   return ProductDao.getInstance().selectById(id, session)
        .orElseThrow(() -> new ProductNotFoundException(
            ErrorCode.ITEM_NOT_FOUND));
  }
}
