'use strict';

(function ($) {
    /*------------------
        Accordin Active
    --------------------*/
    //Canvas Menu
    $(".canvas__open").on('click', function () {
        $(".offcanvas-menu-wrapper").addClass("active");
        $(".offcanvas-menu-overlay").addClass("active");
    });

    $(".offcanvas-menu-overlay").on('click', function () {
        $(".offcanvas-menu-wrapper").removeClass("active");
        $(".offcanvas-menu-overlay").removeClass("active");
    });


    $('#loginbtn').on('click', function (e) {
        e.preventDefault();
        const id = $('#email').val();
        const password = $('#password').val();

        if (id == "") {
            alert("id를 입력해주세요.");
            return;
        }
        if (password == "") {
            alert("password를 입력해주세요.");
            return;
        }

        $('#loginForm').submit();
    });

})(jQuery);