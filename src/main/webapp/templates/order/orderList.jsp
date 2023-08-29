<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zxx">

<head>
    <jsp:include page="../common/meta-data.jsp"/>

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
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>

<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>

<script>
    const hostName = location.host;
    const queryParameters = new URLSearchParams(decodeURI(location.search));
    const errorMessage = queryParameters.get("errorMessage");
    if (errorMessage !== null) {
        Swal.fire({
            icon: 'error',
            title: "ERROR",
            text: errorMessage,
            footer: '<a href="https://github.com/lotte-bit-1/shopping-mall-servlet-jsp/issues">이슈 남기러 가기</a>'
        }).then((result) => {
            window.location.replace("http://" + hostName + "/order.bit?view=list&cmd=get");
        });
    }
</script>

<!-- Header Section Begin -->
<jsp:include page="../common/header.jsp"/>
<!-- Header Section End -->

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__text">
                    <h4>Order List</h4>
                    <div class="breadcrumb__links">
                        <a href="/main.bit">Home</a>
                        <span>Order List</span>
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
            <div class="col-lg-9 centered">
                <div class="shopping__cart__table">
                    <c:forEach var="productOrder" items="${productOrders}">
                        <table class="table"
                               style="border: 2px solid whitesmoke; box-shadow: 1px 2px whitesmoke; margin-bottom: 30px; text-align: center; vertical-align: center; align-content: center;">
                            <thead>
                            <tr>
                                <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${productOrder.orderDate}"
                                               var="parsedOrderDate"/>
                                <th><fmt:formatDate pattern="yyyy.MM.dd HH:mm" value="${parsedOrderDate}"/> 주문</th>
                                <th>${productOrder.orderStatus.getMessage()}</th>
                                <th><a href="/order.bit?view=detail&cmd=get&orderId=${productOrder.orderId}"
                                       class="btn btn-light" style="border: 2px solid whitesmoke;"><b>상세 보기</b></a></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="product" items="${productOrder.products}">
                                <tr>
                                    <td class="product__cart__item">
                                        <div class="product__cart__item__pic">
                                            <img src="${product.thumbnailUrl}" width="80" height="80" alt="">
                                        </div>
                                        <div class="product__cart__item__text" style="display: block">
                                            <h6>${product.name}</h6>
                                            <h5>${product.price}원</h5>
                                        </div>
                                    </td>
                                    <td class="product__cart__item">
                                        <div class="product__cart__item__text">
                                            <h5>${product.quantity}개</h5>
                                        </div>
                                    </td>
                                    <td class="product__cart__item"></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Shopping Cart Section End -->

<!-- Footer Section Begin -->
<jsp:include page="../common/footer.jsp"/>
<!-- Footer Section End -->

<jsp:include page="../common/search.jsp"/>

<!-- Js Plugins -->
<script src="../../js/jquery-3.3.1.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/jquery.nice-select.min.js"></script>
<script src="../../js/jquery.nicescroll.min.js"></script>
<script src="../../js/jquery.magnific-popup.min.js"></script>
<script src="../../js/jquery.countdown.min.js"></script>
<script src="../../js/jquery.slicknav.js"></script>
<script src="../../js/mixitup.min.js"></script>
<script src="../../js/owl.carousel.min.js"></script>
<script src="../../js/main.js"></script>

</body>

</html>
