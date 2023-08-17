package app.dao.member;

import app.dto.request.LoginDto;
import app.entity.Member;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class MemberDao implements MemberDaoFrame<Long, Member>{

    @Override
    public int insert(Member member, SqlSession session) throws Exception {
        return session.insert("member.insert", member);
    }

    @Override
    public int update(Member member, SqlSession session) throws Exception {
        return session.update("member.update", member);
    }

    @Override
    public int deleteById(Long id, SqlSession session) throws Exception {
        return session.delete("member.delete", id);
    }

    @Override
    public Optional<Member> selectById(Long id, SqlSession session) throws Exception {
        return Optional.ofNullable(session.selectOne("member.select", id));
    }

    @Override
    public List<Member> selectAll(SqlSession session) throws Exception {
        return session.selectList("selectall");
    }

    @Override
    public Optional<Member> selectByIdAndPassword(LoginDto loginDto, SqlSession session)
        throws Exception {
        return Optional.ofNullable(session.selectOne("member.selectByIdAndPassword", loginDto));
    }
}
