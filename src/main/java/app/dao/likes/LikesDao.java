package app.dao.likes;

import app.entity.Likes;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.likes.LikesEntityDuplicateException;
import app.exception.likes.LikesEntityNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

public class LikesDao implements LikesDaoFrame<ProductAndMemberCompositeKey, Likes> {

  private static LikesDao instance;

  private LikesDao() {}

  public static LikesDao getInstance() {
    if (instance == null) {
      instance = new LikesDao();
    }
    return instance;
  }

  @Override
  public int insert(Likes likes, SqlSession session) throws SQLException {
    try {
      return session.insert("likes.insert", likes);
    } catch (PersistenceException e) {
      throw new LikesEntityDuplicateException();
    }
  }

  @Override
  public int update(Likes likes, SqlSession session) throws SQLException {
    return 0;
  }

  @Override
  public int deleteById(
      ProductAndMemberCompositeKey productAndMemberCompositeKey, SqlSession session)
      throws SQLException {
    return session.delete("likes.delete", productAndMemberCompositeKey);
  }

  @Override
  public Optional<Likes> selectById(
      ProductAndMemberCompositeKey productAndMemberCompositeKey, SqlSession session)
      throws SQLException {
    Likes likes = session.selectOne("likes.select", productAndMemberCompositeKey);
    return Optional.ofNullable(likes);
  }

  @Override
  public List<Likes> selectAll(SqlSession session) throws SQLException {
    return null;
  }

  @Override
  public List<Long> selectAllProduct(Long memberId, SqlSession session) throws SQLException {
    List<Long> productIdList = session.selectList("likes.selectall", memberId);
    if (productIdList.isEmpty()) throw new LikesEntityNotFoundException();
    return productIdList;
  }
}
