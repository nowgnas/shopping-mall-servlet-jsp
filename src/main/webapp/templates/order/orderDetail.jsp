<%@ page import="java.util.List" %>
<%@ page import="app.dto.response.ProductOrderDetailDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>주문 상세 조회</title>
    <link type="text/css" rel="stylesheet" href="css/order-detail.css"/>
</head>
<body>
<div class="container">
    <h1>주문 상세 조회</h1>

    <div class="order-info">
        <%
            ProductOrderDetailDto productOrderDetail = (ProductOrderDetailDto) request.getAttribute("productOrderDetail");
            if (productOrderDetail != null) {
        %>
        <h2>주문 정보</h2>
        <p>주문 번호: <%= productOrderDetail.getOrderId() %></p>
        <p>주문 상태: <%= productOrderDetail.getOrderStatus() %></p>
        <p>주문 날짜: <%= productOrderDetail.getOrderDate() %></p>
        <!-- 회원 이름, 배송지, 결제 수단, 총 상품 가격, 총 결제 금액 등을 출력해주세요 -->
        <p>회원 이름: <%= productOrderDetail.getMemberName() %></p>
        <p>배송지: <%= productOrderDetail.getAddress().getRoadName() %> <%= productOrderDetail.getAddress().getAddrDetail() %> <%= productOrderDetail.getAddress().getZipCode() %></p>
        <p>결제 수단: <%= productOrderDetail.getPayment().getPaymentType() %></p>
        <p>총 상품 가격: <%= productOrderDetail.getTotalPrice() %>원</p>
        <p>할인 금액: <%= productOrderDetail.getDiscountPrice() %>원</p>
        <p>총 결제 금액: <%= productOrderDetail.getPayment().getActualAmount() %>원</p>
        <!-- 쿠폰 정보를 출력해주세요 -->
        <p>쿠폰 이름: <%= productOrderDetail.getCoupon().getCouponName() %></p>
        <p>쿠폰 상태: <%= productOrderDetail.getCoupon().getCouponStatus() %></p>
    </div>

    <h2>개별 상품 목록</h2>
    <table>
        <tr>
            <th>상품 이름</th>
            <th>가격</th>
            <th>수량</th>
        </tr>
        <!-- 개별 상품 정보를 반복해서 출력해주세요 -->
        <%
            if (productOrderDetail.getProducts() != null) {
                List<ProductOrderDetailDto.ProductDto> products = productOrderDetail.getProducts();
        %>
        <c:forEach var="product" items="<%= products %>">
            <tr>
                <td>${product.productName}</td>
                <td>${product.price}원</td>
                <td>${product.quantity}개</td>
            </tr>
        </c:forEach>
        <%
            }
        %>
    </table>
    <%
        }
    %>
</div>
</body>
</html>
