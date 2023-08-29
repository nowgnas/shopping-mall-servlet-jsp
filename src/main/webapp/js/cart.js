document.addEventListener("DOMContentLoaded", function () {
  const deleteButtons = document.querySelectorAll(".delete-button");

  deleteButtons.forEach((button) => {
    button.addEventListener("click", function () {
      const productRow = this.parentElement.parentElement;
      const productId = productRow.querySelector(
          ".cart__close").getAttribute("product-id");
      const productAction = "delete"; // Set the action

      const productPrice = parseFloat(
          productRow.querySelector(".cart__price").textContent);
      productRow.remove();

      // Create a URL-encoded parameter string with productId and action
      const params = new URLSearchParams();
      params.append("productId", productId);
      params.append("action", "delete");

      axios.post('/cart.bit', params.toString())
          .then(response => {
            console.log(response.data);
          })
          .catch(error => {
            console.error(error);
          });

      const subtotalElement = document.getElementById("subtotal");
      const totalElement = document.getElementById("total");

      const currentSubtotal = parseFloat(subtotalElement.textContent);
      const currentTotal = parseFloat(totalElement.textContent);

      const newSubtotal = currentSubtotal - productPrice;
      const newTotal = currentTotal - productPrice;

      subtotalElement.textContent = `${newSubtotal}원`;
      totalElement.textContent = `${newTotal}원`;
    });
  });
});

$(document).ready(function () {
  $('.shopping__cart__table').on('change', '.quantity-select',
    function (event) {
      console.log('Change event occurred');
      // Get the selected quantity value from the <option> element
      var selectedQuantity = parseInt($(this).val());
      console.log("current selected quantity:" + selectedQuantity);
      console.log('Selected Quantity:', selectedQuantity);
      var productId = $(this).data('product-id');
      console.log('Product ID:', productId);


      var data = {
        action: 'update',
        productId: productId,
        updatedQuantity: selectedQuantity
      };

        $.post('/cart.bit', data)
          .done(function (response) {
            console.log("시작")
            location.reload()
          console.log("끝");
        })
        .fail(function (error) {
          console.log('Error updating quantity:', error);
        });
    });
});

