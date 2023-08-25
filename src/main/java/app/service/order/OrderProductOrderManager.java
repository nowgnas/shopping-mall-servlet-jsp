package app.service.order;

import app.dao.productorder.ProductOrderDao;
import app.entity.ProductOrder;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public class OrderProductOrderManager {

    private final ProductOrderDao productOrderDao = new ProductOrderDao();

    public List<ProductOrder> determineProductOrdersByOrderId(Long orderId, SqlSession session) throws Exception {
        return productOrderDao.selectAllByOrderId(orderId, session);
    }

    public int createProductOrder(ProductOrder productOrder, SqlSession session) throws Exception {
        return productOrderDao.insert(productOrder, session);
    }

    public int createProductOrders(List<ProductOrder> productOrders, SqlSession session) throws Exception {
        return productOrderDao.bulkInsert(productOrders, session);
    }
}
