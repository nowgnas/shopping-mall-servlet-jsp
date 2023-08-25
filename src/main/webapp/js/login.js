'use strict';

(function ($) {

    $('#loginbtn').on('click', function (e) {
        e.preventDefault();
        if (!loginCheck()) {
            return;
        }
        $("#checkEmail").text("");
        $("#checkPassword").text("");
        $('#loginForm').submit();
    });

    $('#password').on('keyup', (event) => {
        if (event.which === 13) {
            if (!loginCheck()) {
                return;
            }
            $("#checkEmail").text("");
            $("#checkPassword").text("");
            $('#loginForm').submit();
        }

    });

    $('#email').on('focusout', () => {
        const id = $('#email').val();
        if (!emailCheck(id)) {
            $("#checkEmail").text("이메일 형식이 아니거나 입력되지 않았습니다.").css("color", "red");
            return;
        }
        $("#checkEmail").text("");
    });

    function loginCheck() {

        const id = $('#email').val();
        const password = $('#password').val();

        if (!emailCheck(id)) {
            $("#checkEmail").text("이메일 형식이 아니거나 입력되지 않았습니다.").css("color", "red");
            return false;
        }
        if (password == "") {
            $("#checkPassword").text("비밀번호를 입력 해주세요.").css("color", "red");
            return false;
        }
        return true;
    }

    function emailCheck(email) {
        let regex = new RegExp("([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])");

        return (email != '' && email != 'undefined') && regex.test(email);
    }


})(jQuery);

