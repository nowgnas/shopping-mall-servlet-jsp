'use strict';

(function ($) {

    $(window).on('load', function () {
        window.Kakao.init("4337d77950ddb4dce28d3222251115bf");
    });

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


    $('#kakaoLogin-btn').on('click', (e) => {
        e.preventDefault();
        e.stopPropagation();
        kakaoLogin();
    });

    function kakaoLogin() {
        window.Kakao.Auth.login({
            scope: "profile_nickname, account_email",
            success: (authObj) => {
                window.Kakao.API.request({
                    url: "/v2/user/me",
                    success: (res) => {
                        console.log(res)
                        $.post("member-rest.bit?cmd=kakaoLogin", {
                            email: res.kakao_account.email,
                            nickname: res.properties.nickname
                        }, function () {
                            window.location.href = "/member.bit"
                        })
                            .fail(function (err) {
                                console.log(err);
                            });
                    },
                    fail: (res) => {
                        console.log(res);
                    },
                });
            },
        });
    }

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

