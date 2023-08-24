package app.service.cart;

import app.dao.cart.CartDao;
import app.dao.cart.CartDaoImpl;
import app.dao.member.MemberDao;
import app.dao.member.MemberDaoFrame;
import app.dto.cart.AllCartProductInfoDto;
import app.entity.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.entity.Member;
import app.entity.Product;
import app.exception.member.MemberNotFoundException;
import app.service.checker.CartExistCheckerService;
import app.service.checker.EntityExistCheckerService;
import app.service.checker.MemberExistCheckerService;
import app.service.checker.ProductExistCheckerService;
import app.service.product.StockCheckerService;
import app.service.product.StockCheckerServiceImpl;
import app.utils.GetSessionFactory;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

 class GetCartServiceTest {


    private final TestConfig testConfig = new TestConfig();
  private final CartDao<ProductAndMemberCompositeKey, Cart> cartDao = new CartDaoImpl();
  private final MemberDaoFrame<Long, Member> memberDao = new MemberDao();
  private final EntityExistCheckerService<Long, Member> memberExistCheckerService = new MemberExistCheckerService(
      memberDao);
   private final EntityExistCheckerService<Long, Product> productExistCheckerService = new ProductExistCheckerService();
   private final EntityExistCheckerService<ProductAndMemberCompositeKey, Cart> cartExistCheckerService = new CartExistCheckerService();
   private final UpdateCartService updateCartService = new UpdateCartServiceImpl(cartDao,
       new DeleteCartWhenRestOfQuantityUnder0(cartDao));
   private final StockCheckerService stockCheckerService = new StockCheckerServiceImpl();
   private final CartService cartService = new CartServiceImpl(cartDao, memberDao,
       memberExistCheckerService, productExistCheckerService, cartExistCheckerService,
       stockCheckerService, updateCartService);
   private SqlSession session;

   @DisplayName("init data")
   @BeforeEach
   void beforeEach() throws Exception {
     session = GetSessionFactory.getInstance().openSession();
     testConfig.init("schema.sql", session);
     testConfig.init("init-data.sql", session);
     testConfig.init("product/product-image.sql", session);
   }

   @AfterEach
   void afterEach() throws Exception {
     session = GetSessionFactory.getInstance().openSession();
     testConfig.init("clear-data.sql", session);
   }

   @DisplayName("멤버가 존재 하지 않을 때 상품 조회")
   @Test
   void deleteCartProduct_MemberIsNotExisted_CatchMemberNotFoundException() {
     Assertions.assertThrowsExactly(MemberNotFoundException.class,
         () -> cartService.delete(new ProductAndMemberCompositeKey(1L, 100L), 2L));
   }

   @DisplayName("카트에 상품이 있을 때 모든 카트의 상품의 상세 정보 총 가격 얻기")
   @Test
   void getAllCartInfo_WhenThereIsProductInCart_getAllProductInfoInCartDto() throws Exception {

     AllCartProductInfoDto allCartProductInfoDto = cartService.getCartProductListByMember(1L);
     allCartProductInfoDto.getCartProductDtoList().forEach(System.out::println);
     System.out.println(allCartProductInfoDto.getTotalPrice());
   }

   @DisplayName("카트에 상품이 없을 때 아무것도 반환 받지 않음")
   @Test
   void getAllCartInfo_WhenThereIsProductInCart_getSize0List() {

  }

}
