package app.dao.coupon;

import app.dao.cart.DaoFrame;
import app.entity.Coupon;
import org.apache.ibatis.session.SqlSession;

public interface CouponDaoFrame<K, V extends Coupon> extends DaoFrame<K, V> {
    int updateStatus(Coupon coupon, SqlSession session) throws Exception;
}
