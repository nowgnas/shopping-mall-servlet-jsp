<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zxx">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="Male_Fashion Template">
    <meta name="keywords" content="Male_Fashion, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Male-Fashion | Template</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap"
          rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="css/style.css" type="text/css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>

<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>

<!-- Header Section Begin -->
<jsp:include page="../common/header.jsp" />
<!-- Header Section End -->

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__text">
                    <h4>주문 상세조회</h4>
                    <div class="breadcrumb__links">
                        <a href="/main.bit">홈</a>
                        <a href="/order.bit?view=list&cmd=get">주문 조회</a>
                        <span>주문 상세조회</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Shopping Cart Section Begin -->
<section class="shopping-cart spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-8">
                <div class="shopping__cart__table">
                    <table>
                        <thead>
                        <tr>
                            <th>상품 정보</th>
                            <th>수량</th>
                            <th>총 가격</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="product" items="${products}">
                            <tr>
                                <td class="product__cart__item">
                                    <div class="product__cart__item__pic">
                                        <img src="${product.thumbnailUrl}" width="80" height="80" alt="">
                                    </div>
                                    <div class="product__cart__item__text">
                                        <h6>${product.productName}</h6>
                                        <h5>${product.price} 원</h5>
                                    </div>
                                </td>
                                <td class="quantity__item">
                                    <div class="quantity">
                                        <h5>${product.quantity} 개</h5>
                                    </div>
                                </td>
                                <c:set var="rowTotalPrice" value="${product.price * product.quantity}"/>
                                <td class="cart__price"><c:out value="${rowTotalPrice}"/> 원</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-lg-4">
                    <a href="#" class="primary-btn">배송 조회</a>
                <br/>
                <br/>
                <div class="cart__total">
                    <h6><b>주문 상세정보</b></h6>
                    <ul>
                        <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${productOrderDetail.orderDate}" var="parsedOrderDate"/>
                        <li>주문 날짜 <span><fmt:formatDate pattern="yyyy.MM.dd HH:mm" value="${parsedOrderDate}"/></span></li>
                        <li>주문 상태 <span>${productOrderDetail.orderStatus.getMessage()}</span></li>
                        <li>총 가격 <span>${productOrderDetail.getTotalPrice()} 원</span></li>
                        <li>할인 가격
                            <span>
                                <c:choose>
                                    <c:when test="${productOrderDetail.getDiscountPrice() == 0}">
                                        ${productOrderDetail.getDiscountPrice()} 원
                                    </c:when>
                                    <c:otherwise>
                                        - ${productOrderDetail.getDiscountPrice()} 원
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </li>
                        <li>결제 금액 <span>${payment.actualAmount} 원</span></li>
                        <li>결제 종류 <span>${payment.paymentType.getMessage()}</span></li>
                    </ul>
                    <c:choose>
                        <c:when test="${delivery.deliveryStatus.name() eq 'CANCELED' || delivery.deliveryStatus.name() eq 'PROCESSING'}">
                            <a id="orderCancelLink" class="primary-btn" disabled>주문 취소 불가</a>
                        </c:when>
                        <c:otherwise>
                            <a id="orderCancelLink" href="#" class="primary-btn" onclick="location.href='/order.bit?view=detail&cmd=delete&orderId=${productOrderDetail.orderId}'">주문 취소</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Shopping Cart Section End -->

<!-- Footer Section Begin -->
<jsp:include page="../common/footer.jsp" />
<!-- Footer Section End -->

<!-- Search Begin -->
<div class="search-model">
    <div class="h-100 d-flex align-items-center justify-content-center">
        <div class="search-close-switch">+</div>
        <form class="search-model-form">
            <input type="text" id="search-input" placeholder="Search here.....">
        </form>
    </div>
</div>
<!-- Search End -->

<!-- Js Plugins -->
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.nice-select.min.js"></script>
<script src="js/jquery.nicescroll.min.js"></script>
<script src="js/jquery.magnific-popup.min.js"></script>
<script src="js/jquery.countdown.min.js"></script>
<script src="js/jquery.slicknav.js"></script>
<script src="js/mixitup.min.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/main.js"></script>

</body>

</html>
