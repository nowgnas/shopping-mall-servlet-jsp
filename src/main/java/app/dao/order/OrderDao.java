package app.dao.order;

import static app.error.ErrorCode.*;

import app.dao.exception.CustomException;
import app.dto.response.ProductOrderDetailDto;
import app.dto.response.ProductOrderDto;
import app.entity.Order;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class OrderDao implements OrderDaoFrame<Long, Order> {

  private Logger log = Logger.getLogger("order");

  @Override
  public int insert(Order order, SqlSession session) throws Exception {
    int insertedRow;
    try {
      insertedRow = session.insert("order.insert", order);
      if (insertedRow != 1) {
        String errorMessage = CANNOT_INSERT_ORDER.getMessage();
        log.error(errorMessage);
        throw new CustomException(errorMessage);
      }
    } catch (PersistenceException ex) {
      log.error(ex.getMessage());
      throw new PersistenceException(CANNOT_UPDATE_ORDER.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(INTERNAL_SERVER_ERROR.getMessage());
    }

    return insertedRow;
  }

  @Override
  public int update(Order order, SqlSession session) throws Exception {
    int updatedRow;
    try {
      updatedRow = session.update("order.update", order);
      if (updatedRow != 1) {
        String errorMessage = CANNOT_UPDATE_ORDER.getMessage();
        log.error(errorMessage);
        throw new CustomException(errorMessage);
      }
    } catch (PersistenceException ex) {
      log.error(ex.getMessage());
      throw new PersistenceException(CANNOT_UPDATE_ORDER.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(INTERNAL_SERVER_ERROR.getMessage());
    }

    return updatedRow;
  }

  @Override
  public int deleteById(Long id, SqlSession session) throws Exception {
    int deletedRow;
    try {
      deletedRow = session.delete("order.delete", id);
      if (deletedRow != 1) {
        String errorMessage = CANNOT_DELETE_ORDER.getMessage();
        log.error(errorMessage);
        throw new CustomException(errorMessage);
      }
    } catch (PersistenceException ex) {
      log.error(ex.getMessage());
      throw new PersistenceException(CANNOT_DELETE_ORDER.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(INTERNAL_SERVER_ERROR.getMessage());
    }

    return deletedRow;
  }

  @Override
  public Optional<Order> selectById(Long id, SqlSession session) throws Exception {
    Optional<Order> optionalOrder;
    try {
      optionalOrder = Optional.ofNullable(session.selectOne("order.select", id));
      optionalOrder.orElseThrow(
          () -> {
            String errorMessage = CANNOT_FIND_ORDER.getMessage();
            log.error(errorMessage);
            return new CustomException(errorMessage);
          });
    } catch (PersistenceException ex) {
      log.error(ex.getMessage());
      throw new PersistenceException(CANNOT_FIND_ORDER.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(INTERNAL_SERVER_ERROR.getMessage());
    }
    return optionalOrder;
  }

  @Override
  public List<Order> selectAll(SqlSession session) throws Exception {
    List<Order> orders;
    try {
      orders = session.selectList("order.selectAll");
    } catch (PersistenceException ex) {
      log.error(ex.getMessage());
      throw new PersistenceException(CANNOT_FIND_ORDER.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(INTERNAL_SERVER_ERROR.getMessage());
    }
    return orders;
  }

  @Override
  public List<ProductOrderDto> selectProductOrdersForMemberCurrentYear(
      Long memberId, SqlSession session) throws Exception {
    List<ProductOrderDto> orders;
    try {
      orders = session.selectList("order.selectProductOrdersForMemberCurrentYear", memberId);
    } catch (PersistenceException ex) {
      log.error(ex.getMessage());
      throw new PersistenceException(CANNOT_FIND_ORDER.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(INTERNAL_SERVER_ERROR.getMessage());
    }
    return orders;
  }

  @Override
  public Optional<ProductOrderDetailDto> selectOrderDetailsForMemberAndOrderId(
      Map<String, Long> orderIdAndMemberIdParameterMap, SqlSession session) throws Exception {
    Optional<ProductOrderDetailDto> optionalProductOrderDetailDto;
    try {
      optionalProductOrderDetailDto =
          Optional.ofNullable(
              session.selectOne(
                  "order.selectOrderDetailsForMemberAndOrderId", orderIdAndMemberIdParameterMap));
      optionalProductOrderDetailDto.orElseThrow(
          () -> {
            String errorMessage = CANNOT_FIND_ORDER.getMessage();
            log.error(errorMessage);
            return new CustomException(errorMessage);
          });
    } catch (PersistenceException ex) {
      log.error(ex.getMessage());
      throw new PersistenceException(CANNOT_FIND_ORDER.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(INTERNAL_SERVER_ERROR.getMessage());
    }
    return optionalProductOrderDetailDto;
  }
}
