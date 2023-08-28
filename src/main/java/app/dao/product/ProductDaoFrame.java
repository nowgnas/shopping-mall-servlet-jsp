package app.dao.product;

import app.dao.DaoFrame;
import app.dto.product.ProductDetail;
import app.dto.product.ProductItemQuantity;
import app.dto.product.ProductListItem;
import app.dto.product.ProductListItemOfLike;
import app.dto.product.response.ProductDetailForOrder;
import app.entity.Category;
import app.entity.Product;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

public interface ProductDaoFrame<K, V extends Product> extends DaoFrame<K, V> {

  int getTotalPage(SqlSession session);

  /**
   * 가격 내림차순 상품 조회
   *
   * @param session
   * @return product list
   * @throws Exception
   */
  List<ProductListItem> selectAllSortByPriceDesc(Map<String, Object> map, SqlSession session)
      throws Exception;

  /**
   * 가격 오름차순 상품 조회
   *
   * @param session
   * @return product list
   * @throws Exception
   */
  List<ProductListItem> selectAllSortByPrice(Map<String, Object> map, SqlSession session)
      throws Exception;

  /**
   * 최신 상품 조회
   *
   * @param session
   * @return
   * @throws Exception
   */
  List<ProductListItem> selectAllSortByDate(Map<String, Object> map, SqlSession session)
      throws Exception;

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

  /**
   * 상품 상세 정보 조회
   *
   * @param memberId 사용자 id - 찜 확인
   * @param productId 상품 id
   * @param session sql session
   * @return
   */
  ProductDetail selectProductDetailWithCategory(Long memberId, Long productId, SqlSession session)
      throws Exception;

  /**
   * 상품 카테고리 - 상세 정보를 위함
   *
   * @param categoryId 상품 정보
   * @return
   */
  List<Category> selectProductParentCategory(Long categoryId, SqlSession session);

  /**
   * 상품 상세 정보 - 바로 구매 시 넘길 정보
   *
   * @param productId
   * @param session
   * @return
   */
  ProductDetailForOrder selectProductDetail(Long productId, SqlSession session) throws Exception;

  int selectProductQuantity(Long productId, SqlSession session);

  /**
   * 키워드 기준 상품 검색
   *
   * @param keyword 사용자 입력
   * @param session sql session
   * @return
   */
  List<ProductListItem> selectProductsByKeyword(Map<String, Object> map, SqlSession session);
}
