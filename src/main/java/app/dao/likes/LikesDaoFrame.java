package app.dao.likes;

import app.dao.DaoFrame;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public interface LikesDaoFrame<K, V> extends DaoFrame<K, V> {
  List<Long> selectAllProduct(Long memberId, SqlSession session) throws SQLException;
}
