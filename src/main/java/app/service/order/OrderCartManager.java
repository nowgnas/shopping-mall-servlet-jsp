package app.service.order;

import app.dao.cart.CartDao;
import app.dto.cart.CartAndProductDto;
import app.entity.Cart;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.order.OrderCartDeleteException;
import app.exception.order.OrderProductNotEnoughStockQuantityException;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class OrderCartManager {

    private final CartDao cartDao = new CartDao();

    public List<CartAndProductDto> determineCartAndProductDtos(Long memberId, SqlSession session) {
        return cartDao.getAllCartsAndAllProductsByMember(memberId, session);
    }

    public void deleteAll(List<ProductAndMemberCompositeKey> productAndMemberCompositeKeys, SqlSession session) {
        int deletedRow = cartDao.bulkDelete(productAndMemberCompositeKeys, session);
        if (deletedRow != productAndMemberCompositeKeys.size()) {
            throw new OrderCartDeleteException();
        }
    }
}
