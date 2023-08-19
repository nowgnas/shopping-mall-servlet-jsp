package app.dao.product;

import app.dao.DaoFrame;
import app.dto.paging.Pagination;
import app.dto.product.ProductItemQuantity;
import app.dto.product.ProductListItem;
import app.dto.product.ProductListItemOfLike;
import app.dto.product.response.ProductListWithPagination;
import app.entity.Product;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public interface ProductDaoFrame<K, V extends Product> extends DaoFrame<K, V> {

  /**
   * 가격 내림차순 상품 조회
   *
   * @param session
   * @return product list
   * @throws Exception
   */
  ProductListWithPagination<List<ProductListItem>, Pagination> selectAllSortByPriceDesc(
      Pagination pagination, SqlSession session) throws Exception;

  /**
   * 가격 오름차순 상품 조회
   *
   * @param session
   * @return product list
   * @throws Exception
   */
  List<ProductListItem> selectAllSortByPrice(SqlSession session) throws Exception;

  /**
   * 최신 상품 조회
   *
   * @param session
   * @return
   * @throws Exception
   */
  List<ProductListItem> selectAllSortByDate(SqlSession session) throws Exception;

  /**
   * 개별 상품 재고 개수 조회
   *
   * <p>parameter: List<productId> | return: product id, quantity, name, url, price
   *
   * @param productId 상품 id list
   * @param session sql session
   * @return 상품 정보 (이름, 이미지, 가격, 개수)
   */
  List<ProductItemQuantity> selectProductQuantity(List<Long> productId, SqlSession session);

  /**
   * todo: 찜 목록 상품 정보
   *
   * <p>parameter: List<productId> | return: product id, name, url, price
   */
  List<ProductListItemOfLike> selectProductListItemOfLike(List<Long> productId, SqlSession session)
      throws Exception;

  /**
   * todo: 상품 재고 확인
   *
   * <p>parameter: List<productId> | return: productId, quantity
   */

  /**
   * todo: 주문 화면 폼 - 장바구니
   *
   * <p>장바구니 구매 정보
   */

  /**
   * todo: 주문 화면 폼 - 바로구매
   *
   * <p>바로구매 상품 정보
   */

  /**
   * todo: 취소한 상품에 대한 수량 증가
   *
   * <p>List<productId, cancel quantity >, return: update log 반환
   */
}
