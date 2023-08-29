package app.service.order.manager;

import app.dao.member.MemberDao;
import app.dto.member.response.OrderMemberDetail;
import app.entity.Member;
import app.exception.member.MemberEntityNotFoundException;
import app.exception.order.OrderMemberNotEnoughMoneyException;
import app.exception.order.OrderMemberUpdateMoneyException;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;

public class OrderMemberManager {

  private final MemberDao memberDao = new MemberDao();

  public Member determineMember(Long memberId, SqlSession session) throws Exception {
    return memberDao.selectById(memberId, session).orElseThrow(MemberEntityNotFoundException::new);
  }

  public OrderMemberDetail determineOrderMemberDetail(Long memberId, SqlSession session)
      throws SQLException {
    return memberDao
        .selectAddressAndCouponById(memberId, session)
        .orElseThrow(MemberEntityNotFoundException::new);
  }

  public void validateEnoughMoney(Long money, Long price) {
    if (money - price < 0) {
      throw new OrderMemberNotEnoughMoneyException();
    }
  }

  public void updateMemberMoney(Member member, Long money, SqlSession session) throws Exception {
    member.updateMoney(money);
    if (memberDao.update(member, session) == 0) {
      throw new OrderMemberUpdateMoneyException();
    }
  }
}
