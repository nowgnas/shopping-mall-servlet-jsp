package app.dao.order;

import app.dao.DaoFrame;
import app.dto.order.response.ProductOrderDetailDto;
import app.dto.order.response.ProductOrderDto;
import app.entity.Order;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderDaoFrame<K, V extends Order> extends DaoFrame<K, V> {
  List<ProductOrderDto> selectProductOrdersForMemberCurrentYear(Long memberId, SqlSession session)
      throws Exception;

  Optional<ProductOrderDetailDto> selectOrderDetailsForMemberAndOrderId(
      Map<String, Long> orderIdAndMemberIdParameterMap, SqlSession session) throws Exception;
}
