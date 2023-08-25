package web.controller;

import app.dto.product.ProductListItemOfLike;
import app.dto.response.MemberDetail;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.DomainException;
import app.service.likes.ProductLikesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.JsonArray;
import com.mysql.cj.xdevapi.JsonParser;
import com.mysql.cj.xdevapi.JsonValue;
import java.io.StringReader;
import java.net.URLEncoder;
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

  public LikesController() {
    super();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String next = Navi.REDIRECT_MAIN;
    String view = request.getParameter("view");

    HttpSession session = request.getSession();
    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");

    if (loginMember == null) return next;

    memberId = loginMember.getId();

    switch (view) {
      case "likes":
        return getLikes(request);
      case "cancelSome":

        JsonArray jsonValues = JsonParser.parseArray(new StringReader(request.getParameter("selectedProductsList")));
        String json = request.getParameter("selectedProductsList");
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<Long> selectedProductsList  = (ArrayList<Long>) objectMapper.readValue(json, List.class)
            .stream()
            .map(obj -> Long.valueOf(obj.toString()))
            .collect(Collectors.toList());

        System.out.println(selectedProductsList.get(0));
        System.out.println(selectedProductsList.get(1));

//        ArrayList<Long> selectedProductsList = objectMapper.readValue(str, List.class)
//            .stream()
//            .map(obj -> Long.valueOf(obj.toString()))
//            .collect(Collectors.toList());

        return cancelSomeLikes(request, selectedProductsList);
    }

    return next;
  }

  public String getLikes(HttpServletRequest request) {
    String errorMessage = "";
    try {
      errorMessage = URLEncoder.encode("시스템 에러", "UTF-8");

      List<ProductListItemOfLike> list = likesService.getMemberLikes(memberId);
      request.setAttribute("list", list);
      return Navi.FORWARD_LIKES_LIST;
    } catch (DomainException e) {
      return Navi.REDIRECT_LIKES_LIST + String.format("?errorMessage=%s", e.getMessage());
    } catch (Exception e) {
      return Navi.REDIRECT_MAIN + String.format("?errorMessage=%s", errorMessage);
    }
  }

  // 찜 벌크 취소
  private String cancelSomeLikes(HttpServletRequest request, List<Long> selectedProductsList) {
    List<ProductAndMemberCompositeKey> compKey = new ArrayList<>();
    String errorMessage = "";
    try {
      errorMessage = URLEncoder.encode("시스템 에러", "UTF-8");

      for (Long productId : selectedProductsList) {
        compKey.add(
            ProductAndMemberCompositeKey.builder().memberId(memberId).productId(productId).build());
      }
      likesService.removeSomeLikes(compKey);
      return getLikes(request);
    } catch (DomainException e) {
      return Navi.REDIRECT_LIKES_LIST + String.format("?errorMessage=%s", e.getMessage());
    } catch (Exception e) {
      return Navi.REDIRECT_MAIN + String.format("?errorMessage=%s", errorMessage);
    }
  }
}
