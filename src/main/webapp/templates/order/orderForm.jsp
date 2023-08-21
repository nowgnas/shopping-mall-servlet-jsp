<%@ page import="app.dto.form.OrderCreateForm" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <p id="member-name">${createOrderForm.memberName}</p>

    <h2>배송지 입력</h2>

    <button id="set-address-btn">기본 주소지로 설정</button>

    <br/>
    <br/>

    <label for="roadName">도로명 주소</label>
    <input type="text" id="roadName" name="roadName" required oninvalid="this.setCustomValidity('도로명 주소를 입력해주세요.')"
           oninput="this.setCustomValidity('')"><br><br>

    <label for="addrDetail">상세 주소</label>
    <input type="text" id="addrDetail" name="addrDetail" required
           oninvalid="this.setCustomValidity('상세 주소를 입력해주세요.')" oninput="this.setCustomValidity('')"><br><br>

    <label for="zipCode">우편번호</label>
    <input type="text" id="zipCode" name="zipCode" required oninvalid="this.setCustomValidity('우편번호를 입력해주세요.')"
           oninput="this.setCustomValidity('')"><br><br>

    <h2>주문 상품</h2>
    <ul>
        <li class="product-item">
            <input type="hidden" id="productId" name="productId" value="${createOrderForm.product.productId}">
            <img src="${createOrderForm.product.imageUrl}" alt="상품 썸네일 이미지">
            <span class="product-name">${createOrderForm.product.name}</span>
            <span class="product-price">가격: ${createOrderForm.product.price}</span>
            <span class="product-quantity">수량: ${createOrderForm.product.quantity}개</span>
        </li>
    </ul>

    <label for="coupon">쿠폰 선택</label>
    <select id="coupon" name="coupon">
        <option value="0">적용 안함</option>
        <c:forEach var="coupon" items="${createOrderForm.coupons}">
            <option name="${coupon.discountPolicy}" value="${coupon.discountValue}">${coupon.name}</option>
        </c:forEach>
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
        console.log(productPrice);

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

        calculatedTotalElem.textContent = calculatedTotal < 0 ? 0 : calculatedTotal;
    }

    // 초기 총 가격 계산
    updateTotalPrice();
</script>
<script>
    const setAddressBtn = document.getElementById("set-address-btn");
    const addressInput = document.getElementById("roadName");
    const detailAddressInput = document.getElementById("addrDetail");
    const zipcodeInput = document.getElementById("zipCode");

    setAddressBtn.addEventListener("click", function (event) {
        event.preventDefault();
        // 기본 주소지로 설정
        const defaultRoadName = '${createOrderForm.defaultAddress.roadName}';
        const defaultAddrDetail = '${createOrderForm.defaultAddress.addrDetail}';
        const defaultZipCode = '${createOrderForm.defaultAddress.zipCode}';

        addressInput.value = defaultRoadName;
        detailAddressInput.value = defaultAddrDetail;
        zipcodeInput.value = defaultZipCode;
    });
</script>
</body>
</html>




