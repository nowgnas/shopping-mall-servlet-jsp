package app.service.member;

import app.dao.member.MemberDao;
import app.dao.member.MemberDaoFrame;
import app.dto.request.MemberRegisterDto;
import app.error.CustomException;
import app.error.ErrorCode;
import app.utils.CipherUtil;
import app.utils.GetSessionFactory;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class MemberService {

    private final MemberDaoFrame memberDao;
    private final SqlSessionFactory sessionFactory;

    public MemberService() {
        memberDao = new MemberDao();
        sessionFactory = GetSessionFactory.getInstance();
    }

    public void register(MemberRegisterDto dto) {
        SqlSession sqlSession = sessionFactory.openSession();
        try {
            byte[] key = CipherUtil.generateKey("AES", 128);
            memberDao.insert(dto,sqlSession);
        } catch (SQLIntegrityConstraintViolationException e) {
            sqlSession.rollback();
            e.printStackTrace();
            throw new CustomException(ErrorCode.EMAIL_IS_NOT_DUPLICATE);
        } catch (Exception e) {
            sqlSession.rollback();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
