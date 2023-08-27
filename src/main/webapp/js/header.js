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

    $(".likes-icon").on("click", (event) => {
        event.preventDefault();
        event.stopPropagation();

        Swal.fire({
            title: '로그인을 해주세요.',
            icon: 'error',
            confirmButtonText: '확인'
        })
    });


})(jQuery);