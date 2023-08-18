package app.dao.member;

import app.dao.DaoFrame;
import app.dto.request.LoginDto;
import app.entity.Member;
import java.sql.SQLException;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public interface MemberDaoFrame<K,V> extends DaoFrame<K, V> {

    Optional<Member> selectByIdAndPassword(LoginDto loginDto, SqlSession session) throws SQLException;
}
