'use strict';

(function ($) {
    $(window).on('load', function () {
        $(".validation-form")
        const forms = $('.validation-form');
        Array.prototype.filter.call(forms, (form) => {
            form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }

                form.classList.add('was-validated');
            }, false);
        });
    });

    $("#email").on("focusout" , function() {
        let email = $("#email").val();
        
        $("#email").focus();
    });

})(jQuery);