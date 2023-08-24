package app.service.checker;

import app.dao.cart.DaoFrame;
import org.apache.ibatis.session.SqlSession;

public interface EntityExistCheckerService<K,V> {
  V isExisted(DaoFrame<K,V> daoFrame, K id,SqlSession session) throws Exception;
}
