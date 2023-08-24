package app.dao.order;

import app.dao.cart.DaoFrame;
import app.dto.response.ProductOrderDetailDto;
import app.dto.response.ProductOrderDto;
import app.entity.Order;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;

public interface OrderDaoFrame<K, V extends Order> extends DaoFrame<K, V> {
  List<ProductOrderDto> selectProductOrdersForMemberCurrentYear(Long memberId, SqlSession session)
      throws Exception;

  Optional<ProductOrderDetailDto> selectOrderDetailsForMemberAndOrderId(
      Map<String, Long> orderIdAndMemberIdParameterMap, SqlSession session) throws Exception;
}
