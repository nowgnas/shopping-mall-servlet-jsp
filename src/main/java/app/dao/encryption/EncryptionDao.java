package app.dao.encryption;

import app.dao.cart.DaoFrame;
import app.entity.Encryption;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EncryptionDao implements DaoFrame<Long, Encryption> {

  @Override
  public int insert(Encryption encryption, SqlSession session) throws SQLException {
    int result = session.insert("encryption.insert", encryption);
    session.commit();
    return result;
  }

  @Override
  public int update(Encryption encryption, SqlSession session) throws SQLException {
    int result = session.update("encryption.update", encryption);
    session.commit();
    return result;
  }

  @Override
  public int deleteById(Long memberId, SqlSession session) throws SQLException {
    int result = session.delete("encryption.delete", session);
    session.commit();
    return result;
  }

  @Override
  public Optional<Encryption> selectById(Long memberId, SqlSession session) throws SQLException {
    return Optional.ofNullable(session.selectOne("encryption.select", memberId));
  }

  @Override
  public List<Encryption> selectAll(SqlSession session) throws SQLException {
    return session.selectList("encryption.selectall");
  }

  public Optional<Encryption> selectByEmail(String email, SqlSession session) throws SQLException {
    return Optional.ofNullable(session.selectOne("encryption.selectByEmail", email));
  }
}
