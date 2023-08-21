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
      String salt = createSalt();
      String hashedPassword = createHashedPassword(dto.getPassword(), salt);

      Member member = dto.toEntity(hashedPassword);
      memberDao.insert(member, sqlSession);

      Encryption encryption = Encryption.from(member, salt);
      encryptionDao.insert(encryption, sqlSession);

      sqlSession.commit();

    } catch (PersistenceException e) {
      sqlSession.rollback();
      //      e.printStackTrace();
      throw new CustomException(ErrorCode.EMAIL_IS_NOT_DUPLICATE);
    } catch (Exception e) {
      sqlSession.rollback();
      e.printStackTrace();
      throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
    } finally {
      sqlSession.close();
    }
  }

  public MemberDetail login(LoginDto dto) {
    SqlSession sqlSession = sessionFactory.openSession();
    MemberDetail loginMember = null;
    try {
      String hashedPassword = getHashedPassword(dto, sqlSession);
      dto.setPassword(hashedPassword);

      Member member =
          memberDao
              .selectByEmailAndPassword(dto, sqlSession)
              .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAIL));

      loginMember = MemberDetail.of(member);

    } catch (SQLException e) {
      e.printStackTrace();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      sqlSession.close();
    }
    return loginMember;
  }

  public MemberDetail get(Long id) {
    SqlSession sqlSession = sessionFactory.openSession();
    MemberDetail memberDetail = null;
    try {
      Member member =
          memberDao
              .selectById(id, sqlSession)
              .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
      memberDetail = MemberDetail.of(member);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      sqlSession.close();
    }
    return memberDetail;
  }

  public boolean isDuplicatedEmail(String email) {
    SqlSession sqlSession = sessionFactory.openSession();
    int result = 0;
    try {
      result = memberDao.countByEmail(email, sqlSession);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      sqlSession.close();
    }
    return result == 0 ? true : false;
  }

  private String getHashedPassword(LoginDto dto, SqlSession sqlSession) throws SQLException {
    Encryption encryption =
        encryptionDao
            .selectByEmail(dto.getEmail(), sqlSession)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    String hashedPassword = createHashedPassword(dto.getPassword(), encryption.getSalt());
    return hashedPassword;
  }

  private String createHashedPassword(String password, String salt) throws SQLException {
    return new String(CipherUtil.getSHA256(password, salt));
  }

  private String createSalt() throws NoSuchAlgorithmException {
    byte[] key = CipherUtil.generateKey("AES", 128);
    String salt = CipherUtil.byteArrayToHex(key);
    return salt;
  }
}
