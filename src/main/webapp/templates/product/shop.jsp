<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="../../css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="../../css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="../../css/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="../../css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="../../css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="../../css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="../../css/style.css" type="text/css">
</head>

<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>
<!-- Header Section Begin -->
<jsp:include page="../common/header.jsp"/>
<!-- Header Section End -->

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__text">
                    <h4>Shop</h4>
                    <div class="breadcrumb__links">
                        <a href="main.bit">Home</a>
                        <span>Shop</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Shop Section Begin -->
<section class="shop spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-3">
                <div class="shop__sidebar">
                    <div class="shop__sidebar__search">
                        <form action="#">
                            <input type="text" placeholder="Search...">
                            <button type="submit"><span class="icon_search"></span></button>
                        </form>
                    </div>
                    <%--  category --%>
                    <div class="shop__sidebar__accordion">
                        <div class="accordion" id="accordionExample">
                            <div class="card">
                                <div class="card-heading">
                                    <a data-toggle="collapse"
                                       data-target="#collapseOne">Categories</a>
                                </div>
                                <div id="collapseOne" class="collapse show"
                                     data-parent="#accordionExample">
                                    <div class="card-body">
                                        <div class="shop__sidebar__categories">
                                            <ul class="nice-scroll">
                                                <c:forEach var="category" items="${categories}">
                                                    <li>
                                                        <a href="/product.bit?view=category&keyword=${category.name}&curPage=0">${category.name}</a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-9">
                <div class="shop__product__option">
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-6">
                            <div class="shop__product__option__left">
                                <p>Showing 1–12 of 126 results</p>
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6">
                            <div class="shop__product__option__right">
                                <p>Sort Option:</p>
                                <select id="sortOption">
                                    <option value="DATE_DESC">Date</option>
                                    <option value="PRICE_DESC">Lowest Price First</option>
                                    <option value="PRICE_ASC">Highest Price First</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <%--  product item list --%>
                <div class="row">
                    <c:forEach var="product" items="${productList.item}">
                        <%-- each item --%>
                        <div class="col-lg-4 col-md-6 col-sm-6">
                            <div class="product__item">
                                <div class="product__item__pic set-bg"
                                     data-setbg="${product.url}">
                                    <ul class="product__hover">
                                        <li><a href="#"><img src="img/icon/heart.png" alt=""></a>
                                        </li>
                                        <li>
                                            <a href="/product.bit?view=shop-detail&productId=${product.id}"><img
                                                    src="img/icon/search.png" alt=""></a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="product__item__text">
                                    <h6>${product.name}</h6>
                                    <a href="#" class="add-cart">+ Add To Cart</a>
                                        <%-- price--%>
                                    <h5>${product.price}</h5>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="product__pagination">
                            <c:if test="${productList.paging.currentPage > 0}">
                                <a class="mr-3"
                                   href="/product.bit?view=shop&curPage=${productList.paging.currentPage - 1}&sort=PRICE_ASC">PREV</a>
                            </c:if>

                            <c:set var="startPage" value="${productList.paging.currentPage - 2}"/>
                            <c:set var="endPage" value="${productList.paging.currentPage + 2}"/>

                            <c:if test="${startPage < 1}">
                                <c:set var="startPage" value="1"/>
                                <c:set var="endPage" value="5"/>
                            </c:if>

                            <c:if test="${endPage > productList.paging.totalPage}">
                                <c:set var="endPage" value="${productList.paging.totalPage}"/>
                                <c:set var="startPage" value="${productList.paging.totalPage - 4}"/>
                                <c:choose>
                                    <c:when test="${startPage < 1}">
                                        <c:set var="startPage" value="1"/>
                                    </c:when>
                                </c:choose>
                            </c:if>
                            <c:forEach begin="${startPage}" end="${endPage}" var="page">
                                <c:choose>
                                    <c:when test="${page == productList.paging.currentPage}">
                                        <a id="curPage">${page}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="/product.bit?view=shop&curPage=${page}&sort=PRICE_ASC">${page}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                            <c:if test="${productList.paging.currentPage < productList.paging.totalPage - 1}">
                                <a href="/product.bit?view=shop&curPage=${productList.paging.currentPage + 1}&sort=PRICE_ASC">NEXT</a>
                            </c:if>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Shop Section End -->
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