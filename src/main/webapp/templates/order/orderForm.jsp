<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="../common/meta-data.jsp"/>

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

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
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
                    <h4>Direct Order</h4>
                    <div class="breadcrumb__links">
                        <a href="../../index.html">Home</a>
                        <a href="../../shop.html">Product</a>
                        <span>Direct Order</span>
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
            <form id="order-form" action="/order.bit?view=direct&cmd=create" method="post">
                <div class="row">
                    <div class="col-lg-8 col-md-6">
                        <h6 class="checkout__title">사용자 정보</h6>
                        <div class="checkout__input">
                            <p>이름<span></span></p>
                            <input id="memberName" type="text" name="memberName" value="${memberName}" disabled>
                        </div>
                        <div class="checkout__input">
                            <p>주소<span>*</span></p>
                            <input type="button" onclick="getDaumPostcode()" value="우편번호 찾기" style="color: black;"><br>
                            <input type="text" placeholder="도로명 주소" class="checkout__input__add"
                                   id="roadName"
                                   name="roadName"
                                   value="<c:if test="${defaultAddress.roadName != null}">${defaultAddress.roadName}</c:if>" required
                                   oninvalid="this.setCustomValidity('도로명 주소를 입력해주세요.')"
                                   oninput="this.setCustomValidity('')" style="color: black;">
                            <input type="text" placeholder="상세 주소" class="checkout__input__add"
                                   id="addrDetail"
                                   name="addrDetail"
                                   value="<c:if test="${defaultAddress.addrDetail != null}">${defaultAddress.addrDetail}</c:if>" required
                                   oninvalid="this.setCustomValidity('상세 주소를 입력해주세요.')"
                                   oninput="this.setCustomValidity('')" style="color: black;">
                        </div>
                        <div class="checkout__input">
                            <p>우편번호<span>*</span></p>
                            <input type="text" placeholder="우편번호" class="checkout__input__add"
                                   id="zipCode"
                                   name="zipCode"
                                   value="<c:if test="${defaultAddress.zipCode != null}">${defaultAddress.zipCode}</c:if>" required
                                   oninvalid="this.setCustomValidity('우편번호를 입력해주세요.')"
                                   oninput="this.setCustomValidity('')" style="color: black;">
                        </div>
                        <div class="checkout__input">
                            <p>쿠폰 선택<span></span></p>
                            <select id="coupon" name="couponId" class="checkout__input__add"
                                    onchange="updateTotalPrice()">
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
                                    <input type="hidden" class="product-name" name="productName"
                                           value="${product.name}">
                                    <input type="hidden" class="product-price" name="productPrice"
                                           value="${product.price}">
                                    <input type="hidden" class="product-quantity" name="productQuantity"
                                           value="${productQuantity}">
                                    ${product.name} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${productQuantity}개<span
                                        class="product-price">${product.price}원</span>
                                </li>
                            </ul>
                            <ul class="checkout__total__all">
                                <li>할인 가격 <span id="discountPrice"></span></li>
                                <input type="hidden" id="totalPrice" name="totalPrice">
                                <li>총 가격 <span id="calculated-total"></span></li>
                            </ul>
                            <a id="payment-btn" href="#"><img src="../../img/payments/payment_icon_yellow_large.png" height="70"></a>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>

<!-- Checkout Section End -->

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

        calculatedDiscountPrice.textContent = '0원';
        if (couponDiscountPolicy === 'CASH') {
            calculatedTotal -= couponDiscountValue;
            calculatedDiscountPrice.textContent = '-' + couponDiscountValue + '원';
        }
        if (couponDiscountPolicy === 'DISCOUNT') {
            calculatedTotal -= (calculatedTotal * (couponDiscountValue / 100));
            calculatedDiscountPrice.textContent = '-' + (calculatedTotal * (couponDiscountValue / 100)) + '원';
        }

        calculatedTotalElem.textContent = calculatedTotal < 0 ? 0 : calculatedTotal + '원';
        calculatedTotalPrice.value = calculatedTotal < 0 ? 0 : calculatedTotal;
    }

    // 초기 총 가격 계산
    updateTotalPrice();
</script>

<script>
    function kakaoPay() {
        const calculatedTotalPrice = document.getElementById("totalPrice").value;
        const roadName = document.getElementById('roadName').value;
        const zipCode = document.getElementById('zipCode').value;

        var IMP = window.IMP;
        IMP.init('imp11402415');
        IMP.request_pay({
            pg : 'kakaopay',
            pay_method : 'card',
            merchant_uid: "order_no_" + new Date().getTime(),
            name : '<c:out value="${product.name}"/>',
            amount : calculatedTotalPrice,
            buyer_email : 'test@naver.com',
            buyer_name : '<c:out value="${memberName}"/>',
            buyer_tel : '010-1234-5678',
            buyer_addr : roadName,
            buyer_postcode : zipCode,
            m_redirect_url : 'http://localhost:8080'
        }, function(response) {
            // 실패 시
            if (!response.success) {
                Swal.fire({
                    icon: 'error',
                    title: "ERROR",
                    text: response.error_msg,
                    footer: '<a href="https://github.com/lotte-bit-1/shopping-mall-servlet-jsp/issues">이슈 남기러 가기</a>'
                });
            } else {
                const formData = $('#order-form').serialize();
                $.ajax({
                    type: "POST",
                    url: "/order-rest.bit?cmd=orderCreate",
                    dataType: 'text',
                    data: formData,
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    error: function (request, status, error) {
                        Swal.fire({
                            icon: 'error',
                            title: "ERROR",
                            text: request.responseText,
                            footer: '<a href="https://github.com/lotte-bit-1/shopping-mall-servlet-jsp/issues">이슈 남기러 가기</a>'
                        });
                    },
                    success: function (data) {
                        window.location.replace(`http://localhost:8080/order.bit?view=detail&cmd=get&orderId=` + data);
                    }
                });
            }
        });
    }
</script>

<script>
    function getDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                let roadName = data.roadAddress;
                let addrDetail = data.jibunAddress;
                let zipCode = data.zonecode;

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                let roadNameInput = document.getElementById('roadName');
                let addrDetailInput = document.getElementById("addrDetail");
                let zipCodeInput = document.getElementById('zipCode');
                roadNameInput.value = roadName;
                addrDetailInput.value = addrDetail;
                zipCodeInput.value = zipCode;
            }
        }).open();
    }
</script>

<script>
    const paymentBtn = document.getElementById("payment-btn");
    paymentBtn.addEventListener('click', () => {
        const memberName = document.getElementById("memberName").value;
        const roadName = document.getElementById("roadName").value;
        const addrDetail = document.getElementById("addrDetail").value;
        const zipCode = document.getElementById("zipCode").value;
        if(memberName === '' || roadName === '' || addrDetail === '' || zipCode === '') {
            Swal.fire({
                icon: 'error',
                title: "ERROR",
                text: '모든 필드를 입력해주세요.',
                footer: '<a href="https://github.com/lotte-bit-1/shopping-mall-servlet-jsp/issues">이슈 남기러 가기</a>'
            });
        } else {
            kakaoPay();
        }
    })
</script>

</body>

</html>
