package app.service.member;

import app.dao.DaoFrame;
import app.dao.encryption.EncryptionDao;
import app.dao.member.MemberDao;
import app.dao.member.MemberDaoFrame;
import app.dto.request.MemberRegisterDto;
import app.entity.Encryption;
import app.entity.Member;
import app.error.CustomException;
import app.error.ErrorCode;
import app.utils.CipherUtil;
import app.utils.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLIntegrityConstraintViolationException;

public class MemberService {

  private final MemberDaoFrame memberDao;
  private final DaoFrame encryptionDao;
  private final SqlSessionFactory sessionFactory;

  public MemberService() {
    memberDao = new MemberDao();
    encryptionDao = new EncryptionDao();
    sessionFactory = GetSessionFactory.getInstance();
  }

  public void register(MemberRegisterDto dto) {
    SqlSession sqlSession = sessionFactory.openSession();
    try {
      Member member = dto.toEntity();
      memberDao.insert(member, sqlSession);

      String salt = createSalt();

      Encryption encryption = Encryption.from(member.getId(), salt);
      encryptionDao.insert(encryption, sqlSession);

    } catch (SQLIntegrityConstraintViolationException e) {
      sqlSession.rollback();
      e.printStackTrace();
      throw new CustomException(ErrorCode.EMAIL_IS_NOT_DUPLICATE);
    } catch (Exception e) {
      sqlSession.rollback();
      throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
  }

  private String createSalt() throws NoSuchAlgorithmException {
    byte[] key = CipherUtil.generateKey("AES", 128);
    String salt = CipherUtil.byteArrayToHex(key);
    return salt;
  }
}
