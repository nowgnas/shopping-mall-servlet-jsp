package app.service.order;

import app.dao.coupon.CouponDao;
import app.entity.Coupon;
import app.enums.CouponStatus;
import app.exception.coupon.CouponEntityNotFoundException;
import app.exception.order.OrderCouponUpdateStatusException;
import org.apache.ibatis.session.SqlSession;

public class OrderCouponManager {

  private final CouponDao couponDao = new CouponDao();

  public Coupon determineCoupon(Long couponId, SqlSession session) throws Exception {
    return couponDao.selectById(couponId, session).orElseThrow(CouponEntityNotFoundException::new);
  }

  public boolean isCouponUsed(Long couponId) {
    return couponId != null;
  }

  public void updateCouponStatus(Coupon coupon, CouponStatus status, SqlSession session)
      throws Exception {
    coupon.updateStatus(status.name());
    if (couponDao.update(coupon, session) == 0) {
      throw new OrderCouponUpdateStatusException();
    }
  }
}
