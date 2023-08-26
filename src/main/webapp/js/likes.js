// 'use strict';
//
// (function ($) {
//
//   /*------------------
//       Preloader
//   --------------------*/
//   $(window).on('load', function () {
//     $(".loader").fadeOut();
//     $("#preloder").delay(200).fadeOut("slow");
//   });
//
//   $('#likes-btn').on('click', function (e) {
//     e.preventDefault();
//     e.stopPropagation();
//
//     var productId = $(this).data("product-id");
//
//     $.post("likes-rest.bit?cmd=add", {
//       productId: productId
//     }, function (data) {
//       console.log(data);
//       if (data == 1) {
//         $('#likes-btn').hide();
//         $('#likes-cancel-btn').show();
//       }
//     });
//   });
//
//   $('#likes-cancel-btn').on('click', function (e) {
//     e.preventDefault();
//     e.stopPropagation();
//
//     var productId = $(this).data("product-id");
//
//     $.post("likes-rest.bit?cmd=cancel", {
//       productId: productId
//     }, function (data) {
//       console.log(data);
//       if (data == 1) {
//         $('#likes-cancel-btn').hide();
//         $('#likes-btn').show();
//       }
//     });
//   });
//
// })(jQuery);

'use strict';

(function ($) {

  /*------------------
      Preloader
  --------------------*/
  $(window).on('load', function () {
    $(".loader").fadeOut();
    $("#preloder").delay(200).fadeOut("slow");
  });

  $('#common-parent-element').on('click', '.likes-btn', function (e) {
    e.preventDefault();
    e.stopPropagation();

    var $likesBtn = $(this);
    var productId = $likesBtn.data("product-id");

    $.post("likes-rest.bit?cmd=add", {
      productId: productId
    }, function (data) {
      if (data == 1) {
        $likesBtn.removeClass('likes-btn').addClass('likes-cancel-btn');
        $likesBtn.find('img').attr('src', 'img/icon/fill_heart.png');
      }
    });
  });

  $('#common-parent-element').on('click', '.likes-cancel-btn', function (e) {
    e.preventDefault();
    e.stopPropagation();

    var $likesCancelBtn = $(this);
    var productId = $likesCancelBtn.data("product-id");

    $.post("likes-rest.bit?cmd=cancel", {
      productId: productId
    }, function (data) {
      if (data == 1) {
        $likesCancelBtn.removeClass('likes-cancel-btn').addClass('likes-btn');
        $likesCancelBtn.find('img').attr('src', 'img/icon/heart.png');
      }
    });
  });

})(jQuery);