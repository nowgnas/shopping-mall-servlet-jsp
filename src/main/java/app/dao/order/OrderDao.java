package app.dao.order;

import static app.error.ErrorCode.*;

import app.dao.order.exception.CustomOrderException;
import app.entity.Order;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class OrderDao implements OrderDaoFrame<Long, Order> {

  private Logger log = Logger.getLogger("order");

  @Override
  public int insert(Order order, SqlSession session) throws Exception {
    int insertedRow = 0;
    try {
      insertedRow = session.insert("order.insert", order);
      if (insertedRow != 1) {
        log.error(CANNOT_INSERT_ORDER.getMessage());
        throw new CustomOrderException(CANNOT_INSERT_ORDER);
      }
      session.commit();
    } catch (PersistenceException ex) {
      log.error(ex.getMessage());
      throw new RuntimeException(INTERNAL_SERVER_ERROR.getMessage());
    } catch (Exception ex) {
      session.rollback();
      ex.printStackTrace();
    } finally {
      session.close();
    }

    return insertedRow;
  }

  @Override
  public int update(Order order, SqlSession session) throws Exception {
    int updatedRow = 0;
    try {
      updatedRow = session.update("order.update", order);
      if (updatedRow != 1) {
        log.error(CANNOT_INSERT_ORDER.getMessage());
        throw new CustomOrderException(CANNOT_UPDATE_ORDER);
      }
      session.commit();
    } catch (PersistenceException ex) {
      log.error(ex.getMessage());
      throw new RuntimeException(INTERNAL_SERVER_ERROR.getMessage());
    } catch (Exception ex) {
      session.rollback();
      ex.printStackTrace();
    } finally {
      session.close();
    }

    return updatedRow;
  }

  @Override
  public int deleteById(Long id, SqlSession session) throws Exception {
    int deletedRow = 0;
    try {
      deletedRow = session.delete("order.delete", id);
      if (deletedRow != 1) {
        log.error(CANNOT_INSERT_ORDER.getMessage());
        throw new CustomOrderException(CANNOT_FIND_ORDER);
      }
      session.commit();
    } catch (PersistenceException ex) {
      log.error(ex.getMessage());
      throw new RuntimeException(INTERNAL_SERVER_ERROR.getMessage());
    } catch (Exception ex) {
      session.rollback();
      ex.printStackTrace();
    } finally {
      session.close();
    }

    return deletedRow;
  }

  @Override
  public Optional<Order> selectById(Long id, SqlSession session) throws Exception {
    return Optional.empty();
  }

  @Override
  public List<Order> selectAll(SqlSession session) throws Exception {
    return null;
  }
}
