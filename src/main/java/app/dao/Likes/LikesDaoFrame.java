package app.dao.Likes;

import app.dao.DaoFrame;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public interface LikesDaoFrame<K, V> extends DaoFrame<K, V> {
  List<V> selectAll(Long memberId, SqlSession session) throws SQLException;
}
