package app.dao.member;

import app.dto.request.LoginDto;
import app.entity.Member;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class MemberDao implements MemberDaoFrame<Long, Member>{

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
        return session.selectList("selectall");
    }

    @Override
    public Optional<Member> selectByIdAndPassword(LoginDto loginDto, SqlSession session)
        throws SQLException {
        return Optional.ofNullable(session.selectOne("member.selectByIdAndPassword", loginDto));
    }
}
