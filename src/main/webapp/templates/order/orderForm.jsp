<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

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

<!-- Offcanvas Menu Begin -->
<div class="offcanvas-menu-overlay"></div>
<div class="offcanvas-menu-wrapper">
    <div class="offcanvas__option">
        <div class="offcanvas__links">
            <a href="#">Sign in</a>
            <a href="#">FAQs</a>
        </div>
        <div class="offcanvas__top__hover">
            <span>Usd <i class="arrow_carrot-down"></i></span>
            <ul>
                <li>USD</li>
                <li>EUR</li>
                <li>USD</li>
            </ul>
        </div>
    </div>
    <div class="offcanvas__nav__option">
        <a href="#" class="search-switch"><img src="../../img/icon/search.png" alt=""></a>
        <a href="#"><img src="../../img/icon/heart.png" alt=""></a>
        <a href="#"><img src="../../img/icon/cart.png" alt=""> <span>0</span></a>
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
                            <a href="#">Sign in</a>
                            <a href="#">FAQs</a>
                        </div>
                        <div class="header__top__hover">
                            <span>Usd <i class="arrow_carrot-down"></i></span>
                            <ul>
                                <li>USD</li>
                                <li>EUR</li>
                                <li>USD</li>
                            </ul>
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
                    <a href="../../index.html"><img src="../../img/logo.png" alt=""></a>
                </div>
            </div>
            <div class="col-lg-6 col-md-6">
                <nav class="header__menu mobile-menu">
                    <ul>
                        <li><a href="../../index.html">Home</a></li>
                        <li class="active"><a href="../../shop.html">Shop</a></li>
                        <li><a href="#">Pages</a>
                            <ul class="dropdown">
                                <li><a href="../../about.html">About Us</a></li>
                                <li><a href="../../shop-details.html">Shop Details</a></li>
                                <li><a href="../../shopping-cart.html">Shopping Cart</a></li>
                                <li><a href="./orderForm.jsp">Check Out</a></li>
                                <li><a href="../../blog-details.html">Blog Details</a></li>
                            </ul>
                        </li>
                        <li><a href="../../blog.html">Blog</a></li>
                        <li><a href="../../contact.html">Contacts</a></li>
                    </ul>
                </nav>
            </div>
            <div class="col-lg-3 col-md-3">
                <div class="header__nav__option">
                    <a href="#" class="search-switch"><img src="../../img/icon/search.png" alt=""></a>
                    <a href="#"><img src="../../img/icon/heart.png" alt=""></a>
                    <a href="#"><img src="../../img/icon/cart.png" alt=""> <span>0</span></a>
                    <div class="price">$0.00</div>
                </div>
            </div>
        </div>
        <div class="canvas__open"><i class="fa fa-bars"></i></div>
    </div>
</header>
<!-- Header Section End -->

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__text">
                    <h4>주문하기</h4>
                    <div class="breadcrumb__links">
                        <a href="../../index.html">홈</a>
                        <a href="../../shop.html">상품</a>
                        <span>주문하기</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Checkout Section Begin -->
<section class="checkout spad">
    <div class="container">
        <div class="checkout__form">
            <form action="/order.bit?view=direct&cmd=create" method="post">
                <div class="row">
                    <div class="col-lg-8 col-md-6">
                        <h6 class="checkout__title">사용자 정보</h6>
                        <div class="checkout__input">
                            <p>이름<span></span></p>
                            <input type="text" name="memberName" value="${memberName}" disabled>
                        </div>
                        <div class="checkout__input">
                            <p>주소<span>*</span></p>
                            <input type="text" placeholder="도로명 주소" class="checkout__input__add"
                                   name="roadName"
                                   value="${defaultAddress.roadName}" required
                                   oninvalid="this.setCustomValidity('도로명 주소를 입력해주세요.')"
                                   oninput="this.setCustomValidity('')">
                            <input type="text" placeholder="상세 주소" class="checkout__input__add"
                                   name="addrDetail"
                                   value="${defaultAddress.addrDetail}" required
                                   oninvalid="this.setCustomValidity('상세 주소를 입력해주세요.')"
                                   oninput="this.setCustomValidity('')">
                        </div>
                        <div class="checkout__input">
                            <p>우편번호<span>*</span></p>
                            <input type="text" placeholder="우편번호" class="checkout__input__add"
                                   name="zipCode"
                                   value="${defaultAddress.zipCode}" required
                                   oninvalid="this.setCustomValidity('우편번호를 입력해주세요.')"
                                   oninput="this.setCustomValidity('')">
                        </div>
                        <div class="checkout__input">
                            <p>쿠폰 선택<span></span></p>
                            <select id="coupon" name="couponId" class="checkout__input__add" onchange="updateTotalPrice()">
                                <option value="0">적용 안함</option>
                                <c:forEach var="coupon" items="${coupons}">
                                    <option id="${coupon.discountValue}" name="${coupon.discountPolicy}"
                                            value="${coupon.id}">${coupon.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-5">
                        <div class="checkout__order">
                            <h4 class="order__title">주문 목록</h4>
                            <div class="checkout__order__products">상품 정보<span>가격</span></div>
                            <ul class="checkout__total__products">
                                <li class="product-item">
                                    <input type="hidden" class="product-id" name="productId" value="${product.id}">
                                    <input type="hidden" class="product-name" name="productName" value="${product.name}">
                                    <input type="hidden" class="product-price" name="productPrice" value="${product.price}">
                                    <input type="hidden" class="product-quantity" name="productQuantity" value="${productQuantity}">
                                    ${product.name} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${productQuantity}개<span
                                        class="product-price">${product.price} 원</span>
                                </li>
                            </ul>
                            <ul class="checkout__total__all">
                                <li>할인 가격 <span id="discountPrice"></span></li>
                                <input type="hidden" id="totalPrice" name="totalPrice">
                                <li>총 가격 <span id="calculated-total"></span></li>
                            </ul>
                            <button type="submit" class="site-btn">주문 하기</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>

<script>
    const productItems = document.querySelectorAll(".product-item");
    const calculatedTotalElem = document.getElementById("calculated-total");
    const calculatedTotalPrice = document.getElementById("totalPrice");
    const calculatedDiscountPrice = document.getElementById("discountPrice");
    const couponSelect = document.getElementById("coupon");

    let total = 0;

    function updateTotalPrice() {
        let calculatedTotal = total;

        productItems.forEach(item => {
            const productPrice = parseInt(item.querySelector(".product-price").value);
            const productQuantity = parseInt(item.querySelector(".product-quantity").value);

            const totalItemPrice = productPrice * productQuantity;
            calculatedTotal += totalItemPrice;
        });

        const selectedOption = couponSelect.options[couponSelect.selectedIndex];
        const couponDiscountPolicy = selectedOption.getAttribute("name");
        const couponDiscountValue = parseInt(selectedOption.getAttribute("id"));

        calculatedDiscountPrice.textContent = '0 원';
        if (couponDiscountPolicy === 'CASH') {
            calculatedTotal -= couponDiscountValue;
            calculatedDiscountPrice.textContent = couponDiscountValue + '원';
        }
        if (couponDiscountPolicy === 'DISCOUNT') {
            calculatedTotal -= (calculatedTotal * (couponDiscountValue / 100));
            calculatedDiscountPrice.textContent = (calculatedTotal * (couponDiscountValue / 100)) + '원';
        }

        calculatedTotalElem.textContent = calculatedTotal < 0 ? 0 : calculatedTotal + ' 원';
        calculatedTotalPrice.value = calculatedTotal < 0 ? 0 : calculatedTotal;
    }

    // 초기 총 가격 계산
    updateTotalPrice();
</script>

<!-- Checkout Section End -->

<!-- Footer Section Begin -->
<footer class="footer">
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-6 col-sm-6">
                <div class="footer__about">
                    <div class="footer__logo">
                        <a href="#"><img src="../../img/footer-logo.png" alt=""></a>
                    </div>
                    <p>The customer is at the heart of our unique business model, which includes design.</p>
                    <a href="#"><img src="../../img/payment.png" alt=""></a>
                </div>
            </div>
            <div class="col-lg-2 offset-lg-1 col-md-3 col-sm-6">
                <div class="footer__widget">
                    <h6>Shopping</h6>
                    <ul>
                        <li><a href="#">Clothing Store</a></li>
                        <li><a href="#">Trending Shoes</a></li>
                        <li><a href="#">Accessories</a></li>
                        <li><a href="#">Sale</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-lg-2 col-md-3 col-sm-6">
                <div class="footer__widget">
                    <h6>Shopping</h6>
                    <ul>
                        <li><a href="#">Contact Us</a></li>
                        <li><a href="#">Payment Methods</a></li>
                        <li><a href="#">Delivary</a></li>
                        <li><a href="#">Return & Exchanges</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-lg-3 offset-lg-1 col-md-6 col-sm-6">
                <div class="footer__widget">
                    <h6>NewLetter</h6>
                    <div class="footer__newslatter">
                        <p>Be the first to know about new arrivals, look books, sales & promos!</p>
                        <form action="#">
                            <input type="text" placeholder="Your email">
                            <button type="submit"><span class="icon_mail_alt"></span></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="footer__copyright__text">
                    <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                    <p>Copyright ©
                        <script>
                            document.write(new Date().getFullYear());
                        </script>
                        2020
                        All rights reserved | This template is made with <i class="fa fa-heart-o"
                                                                            aria-hidden="true"></i> by <a
                                href="https://colorlib.com" target="_blank">Colorlib</a>
                    </p>
                    <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                </div>
            </div>
        </div>
    </div>
</footer>
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
