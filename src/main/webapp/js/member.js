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

    $("#email").on("focusout", function () {
        let email = $("#email").val();
        if (!email_check(email)) {
            $("#vaildEmail").text("이메일 형식이 아닙니다.").css("color", "red");
            return;
        }
        $.post("rest.bit?cmd=loginCheck", {
            email: email
        }, function (isValidEmail) {
            if (isValidEmail) {
                $("#vaildEmail").text("사용할 수 있는 아이디 입니다.").css("color", "green");
            } else {
                $("#vaildEmail").text("이미 존재하는 이메일 입니다.").css("color", "red");
            }
        })
            .fail(function (err) {
                console.log(err);
            });

    });


    function email_check(email) {
        let regex = new RegExp("([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])");

        return (email != '' && email != 'undefined' && regex.test(email));

    }


})(jQuery);