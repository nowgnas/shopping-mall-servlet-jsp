package app.service.order;

import app.dao.delivery.DeliveryDao;
import app.entity.Delivery;
import app.entity.Order;
import app.enums.DeliveryStatus;
import app.enums.OrderStatus;
import app.exception.delivery.DeliveryEntityNotFoundException;
import app.exception.order.*;
import org.apache.ibatis.session.SqlSession;

public class OrderDeliveryManager {

    private final DeliveryDao deliveryDao = new DeliveryDao();

    public Delivery determineDelivery(Long orderId, SqlSession session) throws Exception {
        return deliveryDao
                .selectById(orderId, session)
                .orElseThrow(DeliveryEntityNotFoundException::new);
    }

    public void checkDeliveryCanceled(Delivery delivery) {
        if (delivery.getStatus().equals(DeliveryStatus.CANCELED.name())) {
            throw new OrderDeliveryAlreadyCanceledException();
        }
    }

    public void checkDeliveryProcessing(Delivery delivery) {
        if (delivery.getStatus().equals(DeliveryStatus.PROCESSING.name())) {
            throw new OrderDeliveryProcessingException();
        }
    }

    public void updateDeliveryStatus(Delivery delivery, DeliveryStatus status, SqlSession session) throws Exception {
        delivery.updateStatus(status.name());
        if (deliveryDao.update(delivery, session) == 0) {
            throw new OrderDeliveryUpdateStatusException();
        }
    }

    public int createDelivery(Delivery delivery, SqlSession session) throws Exception {
        return deliveryDao.insert(delivery, session);
    }
}
