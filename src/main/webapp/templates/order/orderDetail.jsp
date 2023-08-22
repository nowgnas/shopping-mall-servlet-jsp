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
        <p>주문 상태: ${productOrderDetail.orderStatus.getMessage()}</p>
        <p>주문 날짜: ${productOrderDetail.orderDate}</p>
        <p>회원 이름: ${productOrderDetail.memberName}</p>
        <p>
            배송지: ${productOrderDetail.delivery.roadName} ${productOrderDetail.delivery.addrDetail} ${productOrderDetail.delivery.zipCode}</p>
        <p>배송 상태: ${productOrderDetail.delivery.deliveryStatus.getMessage()}</p>
        <p>결제 수단: ${productOrderDetail.payment.paymentType.getMessage()}</p>
        <p>총 상품 가격: ${productOrderDetail.getTotalPrice()}원</p>
        <p>할인 금액: ${productOrderDetail.getDiscountPrice()}원</p>
        <p>총 결제 금액: ${productOrderDetail.payment.actualAmount}원</p>
        <c:choose>
            <c:when test="${productOrderDetail.coupon eq null}">
                <p>적용 쿠폰: 없음</p>
            </c:when>
            <c:otherwise>
                <p>적용 쿠폰: ${productOrderDetail.coupon.couponName}</p>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${productOrderDetail.delivery.deliveryStatus.name() eq 'CANCELED' || productOrderDetail.delivery.deliveryStatus.name() eq 'PROCESSING'}">
                <button type="button" disabled>
                    주문 취소
                </button>
            </c:when>
            <c:otherwise>
                <button type="button"
                        onclick="location.href='/order.bit?view=detail&cmd=delete&orderId=${productOrderDetail.orderId}'">
                    주문 취소
                </button>
            </c:otherwise>
        </c:choose>
    </div>

    <h2>상품 목록</h2>
    <table>
        <tr>
            <th>상품 이미지</th>
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
