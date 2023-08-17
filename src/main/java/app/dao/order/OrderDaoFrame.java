package app.dao.order;

import app.dao.DaoFrame;
import app.dto.response.ProductOrderDto;
import app.entity.Order;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public interface OrderDaoFrame<K, V extends Order> extends DaoFrame<K, V> {
  List<ProductOrderDto> selectProductOrdersForMemberCurrentYear(Long memberId, SqlSession session)
      throws Exception;
}
