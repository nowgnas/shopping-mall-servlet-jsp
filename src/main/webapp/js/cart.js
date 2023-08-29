document.addEventListener("DOMContentLoaded", function () {
  const deleteButtons = document.querySelectorAll(".delete-button");
  const subtotalElement = document.getElementById("subtotal");
  const totalElement = document.getElementById("total");

  deleteButtons.forEach((button) => {
    button.addEventListener("click", function () {
      const productRow = this.parentElement.parentElement;
      const productId = productRow.querySelector(".cart__close").getAttribute(
          "product-id");
      const productPrice = parseFloat(
          productRow.querySelector(".cart__price").textContent);
      const quantitySelect = productRow.querySelector(".quantity-select");
      const selectedQuantity = parseInt(quantitySelect.value);
      console.log(selectedQuantity);
      let totalPrice = selectedQuantity * productPrice;
      console.log("totalPrice: " + totalPrice)
      let previousTotalPrice = parseFloat(subtotalElement.textContent);
      console.log("previousTotalPrice: " + previousTotalPrice)
      let newTotalPrice = previousTotalPrice - totalPrice;
      console.log("newTotalPrice: " + newTotalPrice);
      const params = new URLSearchParams();
      params.append("productId", productId);
      params.append("action", "delete");

      $.post('/cart.bit', params.toString(), function (response) {
        console.log(params);
        subtotalElement.textContent = `${newTotalPrice}원`;
        totalElement.textContent = `${newTotalPrice}원`
        productRow.remove()

      });
    });
  });
});

$('.shopping__cart__table').on('change', '.quantity-select',
    function (event) {
      var productId = $(this).find('.quantity-select').data('product-id');
      var selectedQuantity = parseInt(
          $(this).find('.quantity-select').val());

      var productRows = $('.shopping__cart__table tbody tr');
      var productTotalPrices = {};
      productRows.each(function () {
        var id = $(this).find('.quantity-select').data('product-id');
        var quantity = parseInt(
            $(this).find('.quantity-select').val());
        var productPrice = parseFloat(
            $(this).find('.cart__price').text().replace(/[^0-9.]/g, ""));

        var totalPrice = quantity * productPrice;
        productTotalPrices[id] = totalPrice;
      });
      var totalPriceSum = 0;
      for (var productPk in productTotalPrices) {
        totalPriceSum += productTotalPrices[productPk];
      }

      var data = {
        action: 'update',
        productId: productId,
        updatedQuantity: selectedQuantity
      };

      console.log("시작")
      $.post('/cart.bit', data, function (response) {
        console.log(data);
        console.log('Quantity updated successfully');
        subtotalElement.textContent = `${totalPriceSum}원`;
        totalElement.textContent = `${totalPriceSum}원`

      })
          .fail(function (error) {
            console.log('Error updating quantity:', error);
          })
    });

