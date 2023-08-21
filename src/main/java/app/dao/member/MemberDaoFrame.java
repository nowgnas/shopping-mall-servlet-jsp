package app.dao.member;

import app.dao.DaoFrame;
import app.dto.request.LoginDto;
import app.entity.Member;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.Optional;

public interface MemberDaoFrame<K, V> extends DaoFrame<K, V> {

  Optional<Member> selectByEmailAndPassword(LoginDto loginDto, SqlSession session)
      throws SQLException;

  Optional<Member> selectByEmail(String email, SqlSession session) throws SQLException;

  int countByEmail(String email, SqlSession session) throws SQLException;
}
