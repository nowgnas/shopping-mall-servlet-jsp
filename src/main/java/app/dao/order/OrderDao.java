package app.dao.order;

import app.dto.response.ProductOrderDetailDto;
import app.dto.response.ProductOrderDto;
import app.entity.Order;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class OrderDao implements OrderDaoFrame<Long, Order> {

  @Override
  public int insert(Order order, SqlSession session) throws Exception {
    return session.insert("order.insert", order);
  }

  @Override
  public int update(Order order, SqlSession session) throws Exception {
    return session.update("order.update", order);
  }

  @Override
  public int deleteById(Long id, SqlSession session) throws Exception {
    return session.delete("order.delete", id);
  }

  @Override
  public Optional<Order> selectById(Long id, SqlSession session) throws Exception {
    return Optional.ofNullable(session.selectOne("order.select", id));
  }

  @Override
  public List<Order> selectAll(SqlSession session) throws Exception {
    return session.selectList("order.selectAll");
  }

  @Override
  public List<ProductOrderDto> selectProductOrdersForMemberCurrentYear(
      Long memberId, SqlSession session) throws Exception {
    return session.selectList("order.selectProductOrdersForMemberCurrentYear", memberId);
  }

  @Override
  public Optional<ProductOrderDetailDto> selectOrderDetailsForMemberAndOrderId(
      Map<String, Long> orderIdAndMemberIdParameterMap, SqlSession session) throws Exception {
    return Optional.ofNullable(
        session.selectOne(
            "order.selectOrderDetailsForMemberAndOrderId", orderIdAndMemberIdParameterMap));
  }
}
