package app.service.order.interfaces;

public interface OrderDeleteService {

  void cancelOrder(Long orderId) throws Exception;
}
