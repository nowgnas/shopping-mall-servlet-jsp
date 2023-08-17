package app.service.member;

import app.dao.DaoFrame;
import app.dao.member.MemberDao;
import app.dto.request.LoginDto;
import app.utils.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class MemberService {

    private final MemberDao memberDao;
    private final SqlSessionFactory sessionFactory;

    public MemberService() {
        memberDao = new MemberDao();
        sessionFactory = GetSessionFactory.getInstance();
    }

}
