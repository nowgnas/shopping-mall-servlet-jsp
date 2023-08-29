'use strict';

(function ($) {

    $(window).on('load', function () {
        window.Kakao.init("4337d77950ddb4dce28d3222251115bf");
    });

    $('#login-btn').on('click', function (e) {
        e.preventDefault();
        e.stopPropagation();
        if (!loginCheck()) {
            return;
        }
        $("#checkEmail").text("");
        $("#checkPassword").text("");
        login();
    });

    $('#password').on('keyup', (event) => {
        if (event.which === 13) {
            if (!loginCheck()) {
                return;
            }
            event.preventDefault();
            event.stopPropagation();
            $("#checkEmail").text("");
            $("#checkPassword").text("");
            login();
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

    function login() {
        const email = $('#email').val();
        const password = $('#password').val();
        $.post("member-rest.bit?cmd=login", {
            email: email,
            password: password
        }, function (result) {
            if (result) {
                window.location.href = "main.bit";
            }
        }).fail((err) => {
            console.log(err);
            Swal.fire({
                icon: 'error',
                title: '로그인 실패',
                text: '아이디나 비밀번호를 확인해주세요'
            })
        })
    }

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
                            window.location.href = "/main.bit"
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

