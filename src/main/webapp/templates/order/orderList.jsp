<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>주문 목록</title>
    <link type="text/css" rel="stylesheet" href="css/order-list.css"/>
</head>
<body>
<h1>주문 목록</h1>

<table border="1">
    <tr>
        <th>주문 번호</th>
        <th>주문 상태</th>
        <th>주문 날짜</th>
    </tr>
    <c:forEach var="productOrder" items="${productOrders}">
        <tr>

            <td>${productOrder.orderId}</td>
            <td>${productOrder.orderStatus.getMessage()}</td>
            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${productOrder.orderDate}" var="parsedOrderDate"/>
            <td><fmt:formatDate pattern="yyyy.MM.dd HH:mm" value="${parsedOrderDate}"/></td>
            <td>
                <button type="button" onclick="location.href='/order.bit?view=detail&cmd=get&orderId=${productOrder.orderId}'">
                    주문 상세
                </button>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <table border="1">
                    <tr>
                        <th>상품 이미지</th>
                        <th>상품 이름</th>
                        <th>상품 가격</th>
                        <th>상품 수량</th>
                    </tr>
                    <c:forEach var="product" items="${productOrder.products}">
                        <tr>
                            <input type="hidden" name="product_id" value="${product.productId}">
                            <td><img src="${product.thumbnailUrl}" alt="상품 썸네일 이미지"></td>
                            <td>${product.name}</td>
                            <td>${product.price}원</td>
                            <td>${product.quantity}개</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
