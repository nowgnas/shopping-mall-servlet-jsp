package app.dao.payment;

import app.dao.exception.CustomException;
import app.entity.Payment;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static app.error.ErrorCode.*;

public class PaymentDao implements PaymentDaoFrame<Long, Payment> {

  private Logger log = Logger.getLogger("payment");

  @Override
  public int insert(Payment payment, SqlSession session) throws Exception {
    int insertedRow;
    try {
      insertedRow = session.insert("payment.insert", payment);
      if (insertedRow != 1) {
        String errorMessage = CANNOT_INSERT_PAYMENT.getMessage();
        log.error(errorMessage);
        throw new CustomException(errorMessage);
      }
    } catch (PersistenceException ex) {
      log.error(ex.getMessage());
      throw new PersistenceException(CANNOT_INSERT_PAYMENT.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(INTERNAL_SERVER_ERROR.getMessage());
    }

    return insertedRow;
  }

  @Override
  public int update(Payment payment, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public int deleteById(Long aLong, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public Optional<Payment> selectById(Long aLong, SqlSession session) throws Exception {
    return Optional.empty();
  }

  @Override
  public List<Payment> selectAll(SqlSession session) throws Exception {
    return null;
  }
}
