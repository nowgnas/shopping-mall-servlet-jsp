<%@ page import="java.util.List" %>
<%@ page import="app.dto.response.ProductOrderDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <%
        // 주문 목록 데이터 (더미 데이터 예시)
        if (request.getAttribute("productOrders") != null) {
            List<ProductOrderDto> productOrders = (List<ProductOrderDto>) request.getAttribute("productOrders");

            for (ProductOrderDto productOrder : productOrders) {
    %>
    <tr>
        <td><%= productOrder.getOrderId() %>
        </td>
        <td><%= productOrder.getOrderStatus() %>
        </td>
        <td><%= productOrder.getOrderDate() %>
        </td>
    </tr>
    <tr>
        <td colspan="3">
            <table border="1">
                <tr>
                    <th>개별 상품 썸네일</th>
                    <th>개별 상품 이름</th>
                    <th>개별 상품 가격</th>
                    <th>개별 상품 수량</th>
                </tr>
                <%
                    for (ProductOrderDto.ProductDto product : productOrder.getProducts()) {
                %>
                <tr>
                    <td><img src="<%= product.getThumbnailUrl() %>" alt="상품 썸네일"></td>
                    <td><%= product.getName() %>
                    </td>
                    <td><%= product.getPrice() %>원</td>
                    <td><%= product.getQuantity() %>개</td>
                </tr>
                <%
                    }
                %>
            </table>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
