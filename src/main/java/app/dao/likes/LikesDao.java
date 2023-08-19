package app.dao.likes;

import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Likes;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class LikesDao implements LikesDaoFrame<ProductAndMemberCompositeKey, Likes> {

  @Override
  public int insert(Likes likes, SqlSession session) throws SQLException {
    return session.insert("likes.insert", likes);
  }

  @Override
  public int update(Likes likes, SqlSession session) throws SQLException {
    return 0;
  }

  @Override
  public int deleteById(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      SqlSession session) throws SQLException {
    return session.delete("likes.delete", productAndMemberCompositeKey);
  }

  @Override
  public Optional<Likes> selectById(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      SqlSession session) throws SQLException {
    return Optional.ofNullable(session.selectOne("likes.select", productAndMemberCompositeKey));
  }

  @Override
  public List<Likes> selectAll(SqlSession session) throws SQLException {
    return null;
  }

  @Override
  public List<Likes> selectAll(Long memberId, SqlSession session) throws SQLException {
    return session.selectList("likes.selectall", memberId);
  }
}
