<%@ page import="app.dto.form.CartOrderCreateForm" %>
<%@ page import="java.util.List" %>
<%@ page import="app.enums.CouponStatus" %>
<%@ page import="app.enums.CouponPolicy" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 주문</title>
    <link type="text/css" rel="stylesheet" href="css/order-cart-form.css"/>
</head>
<body>
<h1>상품 주문</h1>
<form action="/order.bit?view=cart&cmd=create" method="post">
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
    <label for="address">도로명 주소</label>
    <input type="text" id="address" name="address" required><br><br>

    <label for="detail-address">상세 주소</label>
    <input type="text" id="detail-address" name="detail-address" required><br><br>

    <label for="zipcode">우편번호</label>
    <input type="text" id="zipcode" name="zipcode" required><br><br>

    <h2>주문 상품 목록</h2>
    <ul>
        <%
            if (request.getAttribute("products") != null && request.getAttribute("products").getClass().isAssignableFrom(CartOrderCreateForm.ProductDto.class)) {
                List<CartOrderCreateForm.ProductDto> products = (List<CartOrderCreateForm.ProductDto>) request.getAttribute("products");
                for (CartOrderCreateForm.ProductDto product : products) {
        %>
        <li class="product-item">
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
            if (request.getAttribute("coupons") != null && request.getAttribute("coupons").getClass().isAssignableFrom(CartOrderCreateForm.CouponDto.class)) {
                List<CartOrderCreateForm.CouponDto> coupons = (List<CartOrderCreateForm.CouponDto>) request.getAttribute("coupons");
                for (CartOrderCreateForm.CouponDto coupon : coupons) {
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
    </select><br><br>

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
</body>
</html>




