package app.dao.delivery;

import app.dao.exception.CustomException;
import app.entity.Delivery;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static app.error.ErrorCode.*;

public class DeliveryDao implements DeliveryDaoFrame<Long, Delivery> {

  private Logger log = Logger.getLogger("delivery");

  @Override
  public int insert(Delivery delivery, SqlSession session) throws Exception {
    int insertedRow;
    try {
      insertedRow = session.insert("delivery.insert", delivery);
      if (insertedRow != 1) {
        String errorMessage = CANNOT_INSERT_DELIVERY.getMessage();
        log.error(errorMessage);
        throw new CustomException(errorMessage);
      }
    } catch (PersistenceException ex) {
      log.error(ex.getMessage());
      throw new PersistenceException(CANNOT_INSERT_DELIVERY.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(INTERNAL_SERVER_ERROR.getMessage());
    }

    return insertedRow;
  }

  @Override
  public int update(Delivery delivery, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public int deleteById(Long orderId, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public Optional<Delivery> selectById(Long orderId, SqlSession session) throws Exception {
    Optional<Delivery> optionalDelivery;
    try {
      optionalDelivery = Optional.ofNullable(session.selectOne("delivery.select", orderId));
      optionalDelivery.orElseThrow(
          () -> {
            String errorMessage = CANNOT_FIND_DELIVERY.getMessage();
            log.error(errorMessage);
            return new CustomException(errorMessage);
          });
    } catch (PersistenceException ex) {
      log.error(ex.getMessage());
      throw new PersistenceException(CANNOT_FIND_DELIVERY.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(INTERNAL_SERVER_ERROR.getMessage());
    }

    return optionalDelivery;
  }

  @Override
  public List<Delivery> selectAll(SqlSession session) throws Exception {
    return null;
  }
}
