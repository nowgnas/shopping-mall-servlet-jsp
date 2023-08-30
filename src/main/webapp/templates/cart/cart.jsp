<%--
  Created by IntelliJ IDEA.
  User: ung
  Date: 2023/08/24
  Time: 5:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<html lang="zxx">

<head>
    <jsp:include page="../common/meta-data.jsp" />

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap"
          rel="stylesheet">

    <!-- custom cart css-->
    <link rel="stylesheet" href="css/cart-pagination.css">

    <!-- Css Styles -->
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="css/style.css" type="text/css">


</head>

<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>
<jsp:include page="../common/header.jsp"/>

<section class="breadcrumb-option">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="breadcrumb__text">
                        <h4>Shopping Cart</h4>
                        <div class="breadcrumb__links">
                            <a href="/main.bit">Home</a>
                            <span>Shopping Cart</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</section>


<section class="shopping-cart spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-8">
                <div class="shopping__cart__table">
                    <table>
                        <thead>
                        <tr>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Total</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${productList}" var="product">
                            <tr>
                                <td class="product__cart__item">
                                    <div class="product__cart__item__pic">
                                        <img style="height: 250px; width: 250px" alt="" src="${product.imgUrl}">
                                    </div>
                                    <div class="product__cart__item__text">
                                        <h6>${product.productName}</h6>
                                        <h5>${product.productPrice}원</h5>
                                    </div>
                                </td>
                                <td class="quantity__item">
                                    <div class="quantity">
<%--                                        <div class="pro-qty-2">--%>
                                            <select class="quantity-select"
                                                    data-product-id="${product.productId}">
                                                <c:forEach begin="1" end="${product.stock}"
                                                           var="quantity">
                                                    <option value="${quantity}"
                                                            data-quantity-in-cart="${product.productInCart}"
                                                        ${quantity == product.productInCart ? 'selected' : ''}>
                                                            ${quantity}
                                                    </option>
                                                </c:forEach>
                                            </select>
<%--                                        </div>--%>
                                    </div>
                                </td>

                                <td class="cart__price">${product.price}원</td>
                                <td class="cart__close" product-id="${product.productId}">
                                    <button class="delete-button" style="border: white; background: white"><i class="fa fa-close"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
<%--                <div class="pagination">--%>

<%--                    <a href="${pageContext.request.contextPath}/cart?page=${pagination.currentPage - 1}">Previous</a>--%>
<%--                    <c:forEach begin="1" end="${pagination.totalPage}" var="pageNumber">--%>
<%--                        <a href="${pageContext.request.contextPath}/cart?page=${pageNumber}">${pageNumber}</a>--%>

<%--                    </c:forEach>--%>
<%--                    <c:if test="${pagination.currentPage < pagination.totalPage}">--%>
<%--                        <a href="${pageContext.request.contextPath}/cart?page=${pagination.currentPage + 1}">Next</a>--%>
<%--                    </c:if>--%>
<%--                </div>--%>


                <div class="cart__total">
                    <h6>Cart total</h6>
                    <ul>
                        <input type="hidden" id="preTotalPrice" value="${totalPrice}">
                        <li>Subtotal <span id="subtotal">${totalPrice}원</span></li>
                        <li>Total <span id="total">${totalPrice}원</span></li>
                    </ul>
                    <a href="order.bit?view=cart&cmd=form" class="primary-btn">Proceed to checkout</a>
                </div>

            </div>
        </div>
    </div>
</section>
<!-- Shopping Cart Section End -->


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
<script src="js/cart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js.map"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js.map"></script>

<script>

</script>

</body>

</html>
