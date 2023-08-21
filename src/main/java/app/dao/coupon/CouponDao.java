package app.dao.coupon;

import app.entity.Coupon;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class CouponDao implements CouponDaoFrame<Long, Coupon> {

  @Override
  public int insert(Coupon coupon, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public int update(Coupon coupon, SqlSession session) throws Exception {
    return session.update("coupon.update", coupon);
  }

  @Override
  public int deleteById(Long aLong, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public Optional<Coupon> selectById(Long id, SqlSession session) throws Exception {
    return Optional.ofNullable(session.selectOne("coupon.select", id));
  }

  @Override
  public List<Coupon> selectAll(SqlSession session) throws Exception {
    return null;
  }

  @Override
  public int updateStatus(Coupon coupon, SqlSession session) throws Exception {
    return session.update("coupon.updateStatus", coupon);
  }
}
