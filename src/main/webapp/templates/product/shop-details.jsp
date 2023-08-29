<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

    <style>
      .hover-color-change:hover {
        color: red; /* Change to the desired color */
      }
    </style>
</head>

<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>

<!-- Header Section Begin -->
<jsp:include page="../common/header.jsp"/>
<!-- Header Section End -->

<!-- Shop Details Section Begin -->
<section class="shop-details">
    <div class="product__details__pic">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="product__details__breadcrumb">
                        <a href="./index.html">Home</a>
                        <a href="./shop.html">Shop</a>
                        <span>Product Details</span>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="product__details__pic__item">
                    <img src="${productDetail.detail.url}" alt="">
                </div>
            </div>
        </div>
    </div>
    <div class="product__details__content">
        <div class="container">
            <div class="row d-flex justify-content-center">
                <div class="col-lg-8">
                    <div class="row">
                        <c:forEach varStatus="idx" begin="0"
                                   items="${productDetail.category.categoryList}"
                                   var="category">
                            <c:set var="splitCategory" value="${fn:split(category, '=')[1]}"/>
                            <div class="col-md-2 element"><a class="hover-color-change"
                                                             href="/product.bit?view=category&keyword=${splitCategory}&curPage=1">${splitCategory}</a>
                                <c:if test="${idx.index < 2}">
                                    &nbsp;&nbsp;&nbsp;>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="product__details__text">
                        <%-- todo: product name --%>
                        <h4>${productDetail.detail.name}</h4>
                        <%-- todo: price --%>
                        <div class="row">
                            <div class="col-md">&#8361;</div>
                            <div class="col-md" id="unitPrice">${productDetail.detail.price}</div>
                        </div>
                        <%-- todo: description --%>
                        <p>${productDetail.detail.code}</p>
                        <div class="product__details__cart__option">
                            <div class="input-group">
                                <input type="text" name="quantity-input"
                                       class="form-control text-center"
                                       id="quantity-input">
                                <div class="input-group-append">
                                    <button class="btn btn-outline-secondary" type="button"
                                            id="increase-btn">+
                                    </button>
                                </div>
                                <div class="input-group-prepend">
                                    <button class="btn btn-outline-secondary" type="button"
                                            id="decrease-btn">-
                                    </button>
                                    <a href="#" class="primary-btn">add to cart</a>
                                </div>
                            </div>
                        </div>
                        <p>Total Price: $<span id="totalPrice">10.00</span></p>
                        <div class="product__details__btns__option">
                            <%-- todo: likes --%>
                            <c:if test="${productDetail.detail.isLiked eq false}">
                                <a href="#"><span>&#9825;</span>add to wishlist</a>
                            </c:if>
                            <c:if test="${productDetail.detail.isLiked eq true}">
                                <a href="#"><span>&#9829;</span>add to wishlist</a>
                            </c:if>
                        </div>
                        <a id="order-link" href="" class="btn btn-outline-success">buy</a>
                        <div>
                            PRODUCT CODE: ${productDetail.detail.description}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%--todo: 수량 표시 전 수량 확인 후 표시, 찜, 장바구니 추가, 구매 버튼 클릭 ≠ --%>

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

<script>
  // Set the default value when the page loads
  window.onload = function () {
    var quantityInput = document.getElementById("quantity-input");
    quantityInput.value = "1"; // Set the default value
    var totalPriceSpan = document.getElementById("totalPrice");
    totalPriceSpan.textContent = '0';
  };
</script>


<script>
  var unitPrice = parseFloat(document.getElementById("unitPrice").textContent);
  var quantityInput = document.getElementById("quantity-input");
  var increaseBtn = document.getElementById("increase-btn");
  var decreaseBtn = document.getElementById("decrease-btn");
  var totalPriceSpan = document.getElementById("totalPrice");

  // Update total price when quantity changes
  function updateTotalPrice() {
    var quantity = parseInt(quantityInput.value);
    var totalPrice = unitPrice * quantity;
    totalPriceSpan.textContent = totalPrice;
  }

  increaseBtn.addEventListener("click", function () {
    var currentQuantity = parseInt(quantityInput.value);
    quantityInput.value = currentQuantity + 1;
    updateTotalPrice();
  });

  decreaseBtn.addEventListener("click", function () {
    var currentQuantity = parseInt(quantityInput.value);
    if (currentQuantity > 1) {
      quantityInput.value = currentQuantity - 1;
      updateTotalPrice();
    }
  });

  // Update total price when quantity input changes directly
  quantityInput.addEventListener("input", updateTotalPrice);

  // Initial total price calculation
  updateTotalPrice();
</script>

<%--get product quantity--%>
<script>
  function getQuantity() {
    var editableDiv = document.getElementById("quantity-input");
    console.log(editableDiv.value);
    return parseInt(editableDiv.value);
  }

  // Update the href attribute of the link
  var orderLink = document.getElementById("order-link");
  orderLink.addEventListener('click', () => {
    orderLink.href = "/order.bit?view=direct&cmd=form&productId=${productDetail.detail.id}&quantity="
        + getQuantity();
  });
</script>
</body>

</html>