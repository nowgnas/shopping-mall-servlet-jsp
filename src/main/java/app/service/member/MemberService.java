package app.service.member;

import app.dao.encryption.EncryptionDao;
import app.dao.member.MemberDao;
import app.dto.request.LoginDto;
import app.dto.request.MemberRegisterDto;
import app.dto.response.MemberDetail;
import app.entity.Encryption;
import app.entity.Member;
import app.error.CustomException;
import app.error.ErrorCode;
import app.utils.CipherUtil;
import app.utils.GetSessionFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class MemberService {

  private final MemberDao memberDao;
  private final EncryptionDao encryptionDao;
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

      Encryption encryption = Encryption.from(member, salt);
      encryptionDao.insert(encryption, sqlSession);
      sqlSession.commit();

    } catch (PersistenceException e) {
      sqlSession.rollback();
      //      e.printStackTrace();
      throw new CustomException(ErrorCode.EMAIL_IS_NOT_DUPLICATE);
    } catch (Exception e) {
      sqlSession.rollback();
      //      e.printStackTrace();
      throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
    } finally {
      sqlSession.close();
    }
  }

  public MemberDetail login(LoginDto dto) {
    SqlSession sqlSession = sessionFactory.openSession();
    MemberDetail loginMember = null;
    try {
      String hashedPassword = createHashedPassword(dto, sqlSession);
      dto.setPassword(hashedPassword);
      Member member =
          memberDao
              .selectByEmailAndPassword(dto, sqlSession)
              .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAIL));

      loginMember = MemberDetail.of(member);

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      sqlSession.close();
    }
    return loginMember;
  }

  private String createHashedPassword(LoginDto dto, SqlSession sqlSession) throws SQLException {
    Encryption encryption = encryptionDao.selectByEmail(dto.getEmail(), sqlSession).get();
    return new String(CipherUtil.getSHA256(dto.getPassword(), encryption.getSalt()));
  }

  private String createSalt() throws NoSuchAlgorithmException {
    byte[] key = CipherUtil.generateKey("AES", 128);
    String salt = CipherUtil.byteArrayToHex(key);
    return salt;
  }
}
