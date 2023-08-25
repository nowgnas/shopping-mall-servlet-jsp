package web.controller;

import app.dto.response.MemberDetail;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.DomainException;
import app.service.likes.ProductLikesService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import web.ControllerFrame;
import web.dispatcher.Navi;

public class LikesController implements ControllerFrame {

  private final ProductLikesService likesService = new ProductLikesService();
  private Long memberId;
  private Long productId;

  public LikesController() { super(); }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String next = Navi.REDIRECT_MAIN;
    String view = request.getParameter("view");
    String cmd = request.getParameter("cmd");

    HttpSession session = request.getSession();
    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");

    if (loginMember == null)
      return Navi.REDIRECT_MAIN;

    memberId = loginMember.getId();

    if (view != null && cmd != null) {
      next = build(request, view, cmd);
    }

    return next;
  }

  private String build(HttpServletRequest request, String view, String cmd) {
    String path = Navi.FORWARD_MAIN;
    switch (cmd) {
      case "add":
        productId = Long.parseLong(request.getParameter("productId"));
        return addLikes(request);
      case "cancel":
        productId = Long.parseLong(request.getParameter("productId"));
        return cancelLikes(request);
      case "cancelSome":
        String[] selectedProducts = request.getParameterValues("productIdList");
        List<Long> productIdList = Arrays.stream(selectedProducts)
            .map(Long::valueOf)
            .collect(Collectors.toList());
        return cancelSomeLikes(request, productIdList);
    }
    return path;
  }

  // 찜 추가
  private String addLikes(HttpServletRequest request) {
    try {
      likesService.addLikes(
          ProductAndMemberCompositeKey.builder()
              .memberId(memberId)
              .productId(productId)
              .build()
      );
      
      // 이전 페이지
      return request.getHeader("Referer");
    } catch (DomainException e) {
      e.printStackTrace();
      return Navi.FORWARD_MAIN;
    } catch (Exception e) {
      e.printStackTrace();
      return Navi.REDIRECT_MAIN;
    }
  }
  
  // 찜 취소
  private String cancelLikes(HttpServletRequest request) {
    try {
      likesService.removeLikes(
          ProductAndMemberCompositeKey.builder()
              .memberId(memberId)
              .productId(productId)
              .build()
      );

      // 이전 페이지
      return request.getHeader("Referer");
    } catch (DomainException e) {
      e.printStackTrace();
      return Navi.FORWARD_MAIN;
    } catch (Exception e) {
      e.printStackTrace();
      return Navi.REDIRECT_MAIN;
    }
  }

  // 찜 벌크 취소
  private String cancelSomeLikes(HttpServletRequest request, List<Long> productIdList) {
    try {
      List<ProductAndMemberCompositeKey> compKey = new ArrayList<>();
      for (Long pId : productIdList) {
        compKey.add(
            ProductAndMemberCompositeKey.builder()
                .memberId(memberId)
                .productId(pId)
                .build()
        );
      }
      likesService.removeSomeLikes(compKey);

      // 이전 페이지
      return request.getHeader("Referer");
    } catch (DomainException e) {
      e.printStackTrace();
      return Navi.FORWARD_MAIN;
    } catch (Exception e) {
      e.printStackTrace();
      return Navi.REDIRECT_MAIN;
    }
  }
}
