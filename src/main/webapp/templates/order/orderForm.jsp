<%@ page import="java.util.List" %>
<%@ page import="app.enums.CouponStatus" %>
<%@ page import="app.enums.CouponPolicy" %>
<%@ page import="app.dto.form.OrderCreateForm" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 주문</title>
    <link type="text/css" rel="stylesheet" href="css/order-form.css"/>
</head>
<body>
<h1>상품 주문</h1>
<form action="/order.bit?view=direct&cmd=create" method="post">
    <h2>회원 이름</h2>
    <%
        String memberName = (String) request.getAttribute("memberName");
        if (memberName != null) {
    %>
    <p id="member-name"><%= memberName %>
    </p>
    <%
        }
    %>

    <h2>배송지 입력</h2>

    <button id="set-address-btn">기본 주소지로 설정</button>

    <br/>
    <br/>

    <label for="road-name">도로명 주소</label>
    <input type="text" id="road-name" name="road-name" required oninvalid="this.setCustomValidity('도로명 주소를 입력해주세요.')"
           oninput="this.setCustomValidity('')"><br><br>

    <label for="addr-detail">상세 주소</label>
    <input type="text" id="addr-detail" name="addr-detail" required
           oninvalid="this.setCustomValidity('상세 주소를 입력해주세요.')" oninput="this.setCustomValidity('')"><br><br>

    <label for="zipcode">우편번호</label>
    <input type="text" id="zipcode" name="zipcode" required oninvalid="this.setCustomValidity('우편번호를 입력해주세요.')"
           oninput="this.setCustomValidity('')"><br><br>

    <h2>주문 상품 목록</h2>
    <ul>
        <%
            if (request.getAttribute("products") != null && request.getAttribute("products").getClass().isAssignableFrom(OrderCreateForm.ProductDto.class)) {
                OrderCreateForm.ProductDto product = (OrderCreateForm.ProductDto) request.getAttribute("product");
                if (product != null) {
        %>
        <li class="product-item">
            <input type="hidden" id="product_id" name="product_id" value="<%= product.getProductId() %>">
            <img src="<%= product.getImageUrl() %>" alt="상품 이미지">
            <span class="product-name"><%= product.getName() %></span>
            <span class="product-price">가격: <%= product.getPrice() %></span>
            <span class="product-quantity">수량: <%= product.getQuantity() %>개</span>
        </li>
        <%
                }
            }
        %>
    </ul>

    <label for="coupon">쿠폰 선택</label>
    <select id="coupon" name="coupon">
        <option value="0">적용 안함</option>
        <%
            if (request.getAttribute("coupons") != null && request.getAttribute("coupons").getClass().isAssignableFrom(OrderCreateForm.CouponDto.class)) {
                List<OrderCreateForm.CouponDto> coupons = (List<OrderCreateForm.CouponDto>) request.getAttribute("coupons");
                for (OrderCreateForm.CouponDto coupon : coupons) {
                    if (coupon.getStatus().equals(CouponStatus.UNUSED) && coupon.getDiscountPolicy().equals(CouponPolicy.CASH)) {

        %>
        <option id="<%= coupon.getCouponId() %>" name="<%= coupon.getDiscountPolicy().name() %>"
                value="<%= coupon.getDiscountValue() %>"><%= coupon.getName() %>>
        </option>
        <%
                    }
                }
            }
        %>
    </select>

    <br>
    <br>

    <h2>요약 정보</h2>
    <p id="total-price">총 가격: <span id="calculated-total">0</span>원</p>
    <input type="submit" value="구매하기">
</form>

<script>
    const productItems = document.querySelectorAll(".product-item");
    const calculatedTotalElem = document.getElementById("calculated-total");
    const couponSelect = document.getElementById("coupon");

    let total = 0;

    productItems.forEach(item => {
        const productPrice = parseInt(item.querySelector(".product-price").textContent.split(":")[1]);
        const productQuantity = parseInt(item.querySelector(".product-quantity").textContent.split(":")[1]);

        const totalItemPrice = productPrice * productQuantity;

        item.addEventListener("click", function () {
            updateTotalPrice();
        });
    });

    couponSelect.addEventListener("change", function () {
        updateTotalPrice();
    });

    function updateTotalPrice() {
        let calculatedTotal = total;

        productItems.forEach(item => {
            const productPrice = parseInt(item.querySelector(".product-price").textContent.split(":")[1]);
            const productQuantity = parseInt(item.querySelector(".product-quantity").textContent.split(":")[1]);

            const totalItemPrice = productPrice * productQuantity;
            calculatedTotal += totalItemPrice;
        });

        const selectedOption = couponSelect.options[couponSelect.selectedIndex];
        const couponDiscountPolicy = selectedOption.getAttribute("name");
        const couponDiscountValue = parseInt(couponSelect.value);

        if (couponDiscountPolicy === 'CASH') {
            calculatedTotal -= couponDiscountValue;
        }
        if (couponDiscountPolicy === 'DISCOUNT') {
            calculatedTotal -= (calculatedTotal * (couponDiscountValue / 100));
        }

        calculatedTotalElem.textContent = calculatedTotal;
    }

    // 초기 총 가격 계산
    updateTotalPrice();
</script>
<script>
    const setAddressBtn = document.getElementById("set-address-btn");
    const addressInput = document.getElementById("road-name");
    const detailAddressInput = document.getElementById("addr-detail");
    const zipcodeInput = document.getElementById("zipcode");

    setAddressBtn.addEventListener("click", function(event) {
        event.preventDefault();
        // 기본 주소지로 설정
        const defaultRoadName = "서울특별시 강남구 삼성동 123번지";
        const defaultAddrDetail = "상세 주소";
        const defaultZipCode = "12345";

        addressInput.value = defaultRoadName;
        detailAddressInput.value = defaultAddrDetail;
        zipcodeInput.value = defaultZipCode;
    });
</script>
</body>
</html>




