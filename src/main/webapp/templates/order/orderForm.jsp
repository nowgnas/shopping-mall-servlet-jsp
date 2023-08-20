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
</head>
<body>
<h1>상품 주문</h1>
<form action="/order.bit?view=direct&cmd=create" method="post">
    <label for="address">도로명 주소:</label>
    <input type="text" id="address" name="address" required><br><br>

    <label for="detail-address">상세 주소:</label>
    <input type="text" id="detail-address" name="detail-address" required><br><br>

    <label for="zipcode">우편번호:</label>
    <input type="text" id="zipcode" name="zipcode" required><br><br>

    <h2>상품 선택</h2>
    <ul>
        <%
            if (request.getAttribute("products") != null && request.getAttribute("products").getClass().isAssignableFrom(OrderCreateForm.ProductDto.class)) {
                OrderCreateForm.ProductDto product = (OrderCreateForm.ProductDto) request.getAttribute("product");
                if (product != null) {
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

    <label for="coupon">쿠폰 선택:</label>
    <select id="coupon" name="coupon">
        <option id="coupon-nothing" value="0">할인 없음</option>
        <%
            if (request.getAttribute("products") != null && request.getAttribute("coupons").getClass().isAssignableFrom(OrderCreateForm.CouponDto.class)) {
                List<OrderCreateForm.CouponDto> coupons = (List<OrderCreateForm.CouponDto>) request.getAttribute("coupons");
                for (OrderCreateForm.CouponDto coupon : coupons) {
                    if (coupon.getStatus().equals(CouponStatus.UNUSED) && coupon.getDiscountPolicy().equals(CouponPolicy.CASH)) {

        %>
        <option id="coupon-<%= coupon.getDiscountPolicy().name() %>"
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

        const couponDiscountPolicy = couponSelect.id.toString();
        const couponDiscountValue = parseInt(couponSelect.value);
        if (couponDiscountPolicy === 'coupon-CASH') {
            calculatedTotal -= couponDiscountValue;
        }
        if (couponDiscountPolicy === 'coupon-DISCOUNT') {
            calculatedTotal -= (calculatedTotal * (couponDiscountValue / 100));
        }

        calculatedTotalElem.textContent = calculatedTotal;
    }

    // 초기 총 가격 계산
    updateTotalPrice();
</script>
</body>
</html>




