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

    var likesBtn = $(this);
    likesBtn.addClass();
    var productId = likesBtn.data("product-id");
    var loginMember = likesBtn.data("login-info");

    if (!loginMember) {
      login();
      return;
    }

    $.post("likes-rest.bit?cmd=add", {
      productId: productId
    }, function (data) {
      if (data == 1) {
        likesBtn.removeClass('likes-btn').addClass('likes-cancel-btn');
        likesBtn.find('img').attr('src', 'img/icon/fill_heart.png');
      }
    });
  });

  $('#common-parent-element').on('click', '.likes-cancel-btn', function (e) {
    e.preventDefault();
    e.stopPropagation();

    var likesCancelBtn = $(this);
    var productId = likesCancelBtn.data("product-id");
    var loginMember = likesCancelBtn.data("login-info");

    if (!loginMember) {
      login();
      return;
    }

    $.post("likes-rest.bit?cmd=cancel", {
      productId: productId
    }, function (data) {
      if (data == 1) {
        likesCancelBtn.removeClass('likes-cancel-btn').addClass('likes-btn');
        likesCancelBtn.find('img').attr('src', 'img/icon/heart.png');
      }
    });
  });

})(jQuery);