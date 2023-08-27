'use strict';

(function ($) {

  /*------------------
      Preloader
  --------------------*/
  $(window).on('load', function () {
    $(".loader").fadeOut();
    $("#preloder").delay(200).fadeOut("slow");
  });

  $('#cancel-likes-btn').on('click', (e) => {
    e.preventDefault();
    e.stopPropagation();

    if (selectedProductsList.length === 0) {
      Swal.fire({
        icon: 'warning',
        title: '선택한 상품이 없습니다',
        text: '찜을 취소할 상품을 골라주세요.',
      });
      return;
    }

    $.post("likes-rest.bit?cmd=cancelSome", {
      selectedProductsList: JSON.stringify(selectedProductsList)
    }).done(function (response) {
      // 페이지 리디렉션
      window.location.href = "/likes.bit?view=likes";
    });
  })

})(jQuery);

// 전역 변수로 선택한 상품의 ID를 담을 배열 생성
var selectedProductsList = [];

// 체크박스 클릭 시 선택한 상품의 ID를 배열에 추가 또는 제거하는 함수
function addToSelectedProducts(checkbox) {
  var productId = checkbox.value;
  if (checkbox.checked) {
    selectedProductsList.push(productId);
    console.log("check " + productId);
  } else {
    var index = selectedProductsList.indexOf(productId);
    if (index > -1) {
      selectedProductsList.splice(index, 1);
      console.log("uncheck " + productId);
    }
  }
}