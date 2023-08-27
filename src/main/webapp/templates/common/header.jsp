<%--
  Created by IntelliJ IDEA.
  User: ssjy4
  Date: 2023-08-22
  Time: 오후 5:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Offcanvas Menu Begin -->
<style>
    .container {
        padding: 2rem 0rem;
    }

    @media (min-width: 576px) {
        .modal-dialog {
            max-width: 400px;
        }

        .modal-dialog .modal-content {
            padding: 1rem;
        }
    }

    .modal-header .close {
        margin-top: -1.5rem;
    }

    .form-title {
        margin: -2rem 0rem 2rem;
    }

    .btn-round {
        border-radius: 3rem;
    }

    .delimiter {
        padding: 1rem;
    }

    .social-buttons .btn {
        margin: 0 0.5rem 1rem;
    }

    .signup-section {
        padding: 0.3rem 0rem;
    }
</style>
<div class="offcanvas-menu-overlay"></div>
<div class="offcanvas-menu-wrapper">
    <div class="offcanvas__option">
        <div class="offcanvas__links">
            <a href="#">Sign in</a>
            <a href="#">FAQs</a>
        </div>
    </div>
    <div class="offcanvas__nav__option">
        <a href="#" class="search-switch"><img src="img/icon/search.png" alt=""></a>
        <c:if test="${ empty loginMember }">
            <a href="#" id="like-icon"><img src="img/icon/heart.png" alt=""></a>
        </c:if>
        <c:if test="${ !empty loginMember }">
            <a href="/likes.bit?view=likes"><img src="img/icon/heart.png" alt=""></a>
        </c:if>
        <a href="shopping-cart.html"><img src="img/icon/cart.png" alt=""> <span>0</span></a>
        <div class="price">$0.00</div>
    </div>
    <div id="mobile-menu-wrap"></div>
    <div class="offcanvas__text">
        <p>Free shipping, 30-day return or refund guarantee.</p>
    </div>
</div>
<!-- Offcanvas Menu End -->

<!-- Header Section Begin -->
<header class="header">
    <div class="header__top">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-7">
                    <div class="header__top__left">
                        <p>Free shipping, 30-day return or refund guarantee.</p>
                    </div>
                </div>
                <div class="col-lg-6 col-md-5">
                    <div class="header__top__right">
                        <div class="header__top__links">
                            <c:if test="${ empty loginMember }">
                                <a href="#" data-toggle="modal" data-target="#loginModal">Sign in</a>
                            </c:if>
                            <c:if test="${ !empty loginMember }">
                                <a href="#" disabled>${loginMember.name}님</a>
                                <a href="member.bit?view=logout" target="_parent">logout</a>
                            </c:if>
                            <a href="#">FAQs</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-3">
                <div class="header__logo">
                    <a href="/"><img src="img/logo.png" alt=""></a>
                </div>
            </div>
            <div class="col-lg-6 col-md-6">
                <nav class="header__menu mobile-menu">
                    <ul>
                        <li class="active"><a href="/main.bit">Home</a></li>
                        <li><a href="/product.bit?view=shop&curPage=0&sort=DATE_DESC">Shop</a></li>
                        <li><a href="#">Pages</a>
                            <ul class="dropdown">
                                <li><a href="./about.html">About Us</a></li>
                                <li><a href="./shop-details.html">Shop Details</a></li>
                                <li><a href="./shopping-cart.html">Shopping Cart</a></li>
                                <li><a href="./checkout.html">Check Out</a></li>
                                <li><a href="./blog-details.html">Blog Details</a></li>
                            </ul>
                        </li>
                        <li><a href="./blog.html">Blog</a></li>
                        <c:if test="${ !empty loginMember }">
                            <li><a href="/order.bit?view=list&cmd=get">주문 조회</a></li>
                        </c:if>
                    </ul>
                </nav>
            </div>
            <div class="col-lg-3 col-md-3">
                <div class="header__nav__option">
                    <a href="#" class="search-switch"><img src="img/icon/search.png" alt=""></a>
                    <c:if test="${ empty loginMember }">
                        <a href="#" id="like-icon"><img src="img/icon/heart.png" alt=""></a>
                    </c:if>
                    <c:if test="${ !empty loginMember }">
                        <a href="/likes.bit?view=likes"><img src="img/icon/heart.png" alt=""></a>
                    </c:if>
                    <a href="shopping-cart.html"><img src="img/icon/cart.png" alt=""> <span>0</span></a>
                    <div class="price">$0.00</div>
                </div>
            </div>
        </div>
        <div class="canvas__open"><i class="fa fa-bars"></i></div>
    </div>
</header>

<jsp:include page="../member/loginModal.jsp"/>
<script src="js/header.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
