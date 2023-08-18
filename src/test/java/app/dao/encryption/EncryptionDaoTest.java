package app.dao.encryption;

import app.entity.Encryption;
import app.utils.CipherUtil;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

import static org.junit.jupiter.api.Assertions.assertSame;

class EncryptionDaoTest {

  private final EncryptionDao encryptionDao = new EncryptionDao();
  private final TestConfig testConfig = new TestConfig();
  private SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();

  @BeforeEach
  void beforeEach() throws Exception {
    SqlSession session = sessionFactory.openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
    session.close();
  }

  @AfterEach
  void afterEach() throws Exception {
    SqlSession session = sessionFactory.openSession();
    testConfig.init("clear-data.sql", session);
    session.close();
  }

  @Test
  @DisplayName("encryption 등록 테스트 ")
  void insert() throws Exception {
    // given
    SqlSession session = sessionFactory.openSession();
    Long memberId = 1L;
    String email = "aaa@naver.com";
    byte[] key = CipherUtil.generateKey("AES", 128);
    String salt = CipherUtil.byteArrayToHex(key);
    Encryption encryption = Encryption.builder().memberId(memberId).email(email).salt(salt).build();
    // when
    encryptionDao.insert(encryption, session);
    session.commit();
    // then
    Encryption getEncryption = encryptionDao.selectByEmail(email, session).get();
    assertSame(salt, getEncryption.getSalt());
  }
}
