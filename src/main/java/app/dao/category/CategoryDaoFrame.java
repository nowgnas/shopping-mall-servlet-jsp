package app.dao.category;

import app.dao.DaoFrame;
import app.dto.product.ProductListItem;
import app.entity.Category;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public interface CategoryDaoFrame<K, V extends Category> extends DaoFrame<K, V> {
  List<ProductListItem> selectProductByCategoryName(Long memberId, String keyword, SqlSession session);
}
