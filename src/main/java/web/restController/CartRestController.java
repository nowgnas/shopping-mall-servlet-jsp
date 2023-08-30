package web.restController;

import app.dao.cart.CartDao;
import app.dao.cart.CartDaoFrame;
import app.dao.member.MemberDao;
import app.dao.member.MemberDaoFrame;
import app.dto.member.response.MemberDetail;
import app.entity.Cart;
import app.entity.Member;
import app.entity.Product;
import app.entity.ProductAndMemberCompositeKey;
import app.service.cart.*;
import app.service.checker.CartExistCheckerService;
import app.service.checker.EntityExistCheckerService;
import app.service.checker.MemberExistCheckerService;
import app.service.checker.ProductExistCheckerService;
import app.service.product.StockCheckerService;
import app.service.product.StockCheckerServiceImpl;
import web.RestControllerFrame;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartRestController implements RestControllerFrame {

  private final StockCheckerService stockCheckerService = new StockCheckerServiceImpl();
  private final CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDaoFrame = new CartDao();
  private final MemberDaoFrame<Long, Member> memberDao = new MemberDao();
  private final EntityExistCheckerService<Long, Member> memberExistCheckerService =
      new MemberExistCheckerService(memberDao);
  private final EntityExistCheckerService<Long, Product> productExistCheckerService =
      new ProductExistCheckerService();
  private final EntityExistCheckerService<ProductAndMemberCompositeKey, Cart>
      cartExistCheckerService = new CartExistCheckerService();
  private final UpdateCartService updateCartService =
      new UpdateCartServiceImpl(cartDaoFrame, new DeleteCartWhenRestOfQuantityUnder0(cartDaoFrame));
  private final CartService cartService =
      new CartServiceImpl(
          cartDaoFrame,
          memberDao,
          memberExistCheckerService,
          productExistCheckerService,
          cartExistCheckerService,
          stockCheckerService,
          updateCartService);

  @Override
  public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String cmd = request.getParameter("cmd");
    Object result = "";

    if (cmd != null) {
      result = build(request, cmd);
    }
    return result;
  }

  private Object build(HttpServletRequest request, String cmd) throws Exception {
    Object result = null;
    switch (cmd) {
      case "add":
        result = addCart(request);
        break;
    }
    return result;
  }

  private Object addCart(HttpServletRequest request) throws Exception {

    MemberDetail memberDetail = (MemberDetail) request.getSession().getAttribute("loginMember");
    Long productId = Long.parseLong(request.getParameter("productId"));
    Long quantity = Long.parseLong(request.getParameter("quantity"));
    cartService.putItemIntoCart(
        new ProductAndMemberCompositeKey(productId, memberDetail.getId()), quantity);
    return true;
  }
}
