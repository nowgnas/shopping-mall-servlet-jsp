package app.dao.productorder;

import static app.error.ErrorCode.*;

import app.dao.exception.CustomException;
import app.entity.ProductOrder;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class ProductOrderDao implements ProductOrderDaoFrame<Long, ProductOrder> {

  private Logger log = Logger.getLogger("productOrder");

  @Override
  public int insert(ProductOrder productOrder, SqlSession session) throws Exception {
    int insertedRow;
    try {
      insertedRow = session.insert("productOrder.insert", productOrder);
      if (insertedRow != 1) {
        String errorMessage = CANNOT_INSERT_PRODUCT_ORDER.getMessage();
        log.error(errorMessage);
        throw new CustomException(errorMessage);
      }
    } catch (PersistenceException ex) {
      log.error(ex.getMessage());
      throw new PersistenceException(CANNOT_INSERT_PRODUCT_ORDER.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(INTERNAL_SERVER_ERROR.getMessage());
    }

    return insertedRow;
  }

  @Override
  public int update(ProductOrder productOrder, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public int deleteById(Long aLong, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public Optional<ProductOrder> selectById(Long aLong, SqlSession session) throws Exception {
    return Optional.empty();
  }

  @Override
  public List<ProductOrder> selectAll(SqlSession session) throws Exception {
    return null;
  }
}
