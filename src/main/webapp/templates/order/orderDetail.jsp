<%@ page import="java.util.List" %>
<%@ page import="app.dto.response.ProductOrderDetailDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <h2>주문 정보</h2>
        <p>주문 번호: ${productOrderDetail.orderId}</p>
        <p>주문 상태: ${productOrderDetail.orderStatus}</p>
        <p>주문 날짜: ${productOrderDetail.orderDate}</p>
        <!-- 회원 이름, 배송지, 결제 수단, 총 상품 가격, 총 결제 금액 등을 출력해주세요 -->
        <p>회원 이름: ${productOrderDetail.memberName}</p>
        <p>배송지: ${productOrderDetail.address.roadName} ${productOrderDetail.address.addrDetail} ${productOrderDetail.address.zipCode}</p>
        <p>결제 수단: ${productOrderDetail.payment.paymentType}</p>
        <p>총 상품 가격: ${productOrderDetail.totalPrice}원</p>
        <p>할인 금액: ${productOrderDetail.discountPrice}원</p>
        <p>총 결제 금액: ${productOrderDetail.payment.actualAmount}원</p>
        <!-- 쿠폰 정보를 출력해주세요 -->
        <p>쿠폰 이름: ${productOrderDetail.coupon.couponName}</p>
        <p>쿠폰 상태: ${productOrderDetail.coupon.couponStatus}</p>
    </div>

    <h2>상품 목록</h2>
    <table>
        <tr>
            <th>상품 썸네일</th>
            <th>상품 이름</th>
            <th>가격</th>
            <th>수량</th>
        </tr>
        <c:forEach var="product" items="${productOrderDetail.products}">
            <tr>
                <td><img src="${product.thumbnailUrl}" alt="상품 썸네일 이미지"></td>
                <td>${product.productName}</td>
                <td>${product.price}원</td>
                <td>${product.quantity}개</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
