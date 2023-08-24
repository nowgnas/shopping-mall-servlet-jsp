package app.dao.productorder;

import app.dao.DaoFrame;
import app.entity.ProductOrder;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public interface ProductOrderDaoFrame<K, V extends ProductOrder> extends DaoFrame<K, V> {
    List<ProductOrder> selectAllByOrderId(Long orderId, SqlSession session) throws Exception;
    int bulkInsert(List<ProductOrder> productOrders, SqlSession session) throws Exception;
}
