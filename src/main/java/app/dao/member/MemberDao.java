package app.dao.member;

import app.dto.request.LoginDto;
import app.dto.response.OrderMemberDetail;
import app.entity.Member;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class MemberDao implements MemberDaoFrame<Long, Member> {

  @Override
  public int insert(Member member, SqlSession session) throws SQLException {
    return session.insert("member.insert", member);
  }

  @Override
  public int update(Member member, SqlSession session) throws SQLException {
    return session.update("member.update", member);
  }

  @Override
  public int deleteById(Long id, SqlSession session) throws SQLException {
    return session.delete("member.delete", id);
  }

  @Override
  public Optional<Member> selectById(Long id, SqlSession session) throws SQLException {
    return Optional.ofNullable(session.selectOne("member.select", id));
  }

  @Override
  public List<Member> selectAll(SqlSession session) throws SQLException {
    return session.selectList("member.selectall");
  }

  @Override
  public Optional<Member> selectByEmailAndPassword(LoginDto loginDto, SqlSession session)
      throws SQLException {
    return Optional.ofNullable(session.selectOne("member.selectByEmailAndPassword", loginDto));
  }

  @Override
  public Optional<Member> selectByEmail(String email, SqlSession session) throws SQLException {
    return Optional.ofNullable(session.selectOne("member.selectByEmail", email));
  }

  @Override
  public int countByEmail(String email, SqlSession session) throws SQLException {

    return session.selectOne("member.countByEmail", email);
  }

  public Optional<OrderMemberDetail> selectAddressAndCouponById(Long id, SqlSession session)
      throws SQLException {
    return Optional.ofNullable(session.selectOne("member.selectAddressAndCouponById", id));
  }


}
