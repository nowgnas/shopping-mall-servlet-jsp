<%--
  Created by IntelliJ IDEA.
  User: KMJ
  Date: 2023-08-25
  Time: 오전 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<!-- Header Section -->
<jsp:include page="../common/header.jsp"/>

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__text">
                    <h4>Likes</h4>
                    <div class="breadcrumb__links">
                        <a href="index.jsp">Home</a>
                        <span>Likes</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Likes Section Begin -->
<section class="shopping-cart spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-8">
                <div class="shopping__cart__table">
                    <table>
                        <thead>
                        <tr>
                            <th>Select</th> <!-- 체크박스 추가 -->
                            <th>Thumbnail</th>
                            <th>Product</th>
                            <th>Price</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${products.list}" var="product">
                            <tr>
                                <td class="product__cart__item">
                                    <div class="product__cart__item__pic">
                                        <input type="checkbox" onclick="addToSelectedProducts(this)" name="selectedProducts" value="${product.id}">
                                    </div>
                                </td>
                                <td class="product__cart__item">
                                    <div class="product__cart__item__pic">
                                        <img alt="" src="${product.url}" width="200px" height="150px">
                                    </div>
                                </td>
                                <td class="product__cart__item">
                                    <div class="quantity__item">
                                        <h6>${product.name}</h6>
                                    </div>
                                </td>
                                <td class="product__cart__item">
                                    <div class="cart__price">
                                        <h5>${product.price}</h5>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="product__pagination">
                            <c:if test="${products.paging.currentPage > 1}">
                                <a class="mr-3"
                                   href="/likes.bit?view=likes&curPage=${products.paging.currentPage - 1}">PREV</a>
                            </c:if>

                            <c:set var="startPage" value="${products.paging.currentPage - 2}"/>
                            <c:set var="endPage" value="${products.paging.currentPage + 2}"/>

                            <c:if test="${startPage < 1}">
                                <c:set var="startPage" value="1"/>
                                <c:set var="endPage" value="5"/>
                            </c:if>

                            <c:if test="${endPage > products.paging.totalPage}">
                                <c:set var="endPage" value="${products.paging.totalPage}"/>
                                <c:set var="startPage" value="${products.paging.totalPage - 4}"/>
                                <c:choose>
                                    <c:when test="${startPage < 1}">
                                        <c:set var="startPage" value="1"/>
                                    </c:when>
                                </c:choose>
                            </c:if>
                            <c:forEach begin="${startPage}" end="${endPage}" var="page">
                                <c:choose>
                                    <c:when test="${page == products.paging.currentPage}">
                                        <a id="curPage">${page}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="/likes.bit?view=likes&curPage=${page}">${page}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                            <c:if test="${products.paging.currentPage < products.paging.totalPage}">
                                <a href="/likes.bit?view=likes&curPage=${products.paging.currentPage + 1}">NEXT</a>
                            </c:if>
                        </div>

                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-6">
                        <div class="continue__btn">
                            <a href="/product.bit?view=shop&curPage=0&sort=DATE_DESC">Continue Shopping</a>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-6">
                        <div class="continue__btn update__btn">
                            <a href="#" id="cancel-likes-btn"><i class="fa"></i>cancel Likes</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Likes Section End -->

<!-- Js Plugins -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/likesList.js"></script>
</body>

</html>