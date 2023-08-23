package app.dao.coupon;

import app.dao.delivery.DeliveryDao;
import app.entity.Coupon;
import app.entity.Delivery;
import app.enums.CouponPolicy;
import app.enums.CouponStatus;
import app.enums.DeliveryStatus;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

import static org.junit.jupiter.api.Assertions.*;

class CouponDaoUpdateTest {

  private CouponDao couponDao = new CouponDao();
  private SqlSession session;
  private final TestConfig testConfig = new TestConfig();

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("coupon/init-coupon-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("쿠폰 상태 수정 테스트 - 쿠폰 사용 되돌리기")
  void updateStatus() throws Exception {
    // given
    Long couponId = 2L;
    Long memberId = 1L;
    Coupon updatedCoupon =
        Coupon.builder()
            .id(couponId)
            .name("쿠폰 수정")
            .discountPolicy(CouponPolicy.CASH.name())
            .discountValue(10000)
            .memberId(memberId)
            .status(CouponStatus.UNUSED.name())
            .build();

    // when
    int updatedRow = couponDao.updateStatus(updatedCoupon, session);
    session.commit();

    // then
    Coupon findCoupon = couponDao.selectById(couponId, session).get();
    assertSame(1, updatedRow);
    assertNotEquals(updatedCoupon.getName(), findCoupon.getName());
    assertEquals(CouponStatus.UNUSED.name(), findCoupon.getStatus());
    assertTrue(findCoupon.getUpdatedAt().isAfter(findCoupon.getCreatedAt()));

    session.close();
  }
}
