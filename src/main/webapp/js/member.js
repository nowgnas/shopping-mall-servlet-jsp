'use strict';

(function ($) {
    let emailFlag = false;
    let passwordFlag = false;
    let rePasswordFlag = false;
    let nameFlag = false;

    $('.validation-form').on('submit', function (event) {
        const forms = $('.validation-form');
        const validList = $('.valid');
        Array.prototype.filter.call(forms, (form) => {
            if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
        if (!checkValid()) {
            alert("입력값을 확인해주세요.");
            return;
        }
    });


    $("#email").on("focusout", function () {
        let email = $("#email").val();
        if (!emailCheck(email)) {
            $("#vaildEmail").text("이메일 형식이 아닙니다.").css("color", "red");
            return;
        }
        $.post("rest.bit?cmd=loginCheck", {
            email: email
        }, function (isValidEmail) {
            if (isValidEmail) {
                $("#vaildEmail").text("사용할 수 있는 아이디 입니다.").css("color", "green");
                $("#txtBox").removeAttr("disabled");
            } else {
                $("#vaildEmail").text("이미 존재하는 이메일 입니다.").css("color", "red");
                $("#register-btn").attr("disabled", true);
            }
        })
            .fail(function (err) {
                console.log(err);
            });
    });

    $("#password").on("focusout", function () {
        let password = $("#password").val();
        if (!passwordCheck(password)) {
            $("#vaildPassword").text("비밀번호는 영문, 숫자 형식으로 8~20자 입력해야 합니다.").css("color", "red");
            return;
        }
        $("#vaildPassword").text("유효한 비밀번호 입니다.").css("color", "green");
    });

    $("#password2").on("focusout", function () {
        let password = $("#password").val()
        let password2 = $("#password2").val();

        if ((password2 != "" && password2 != "undefined") && password === password2) {
            $("#vaildPassword2").text("비밀번호가 일치합니다.").css("color", "green");
        } else {
            $("#vaildPassword2").text("비밀번호가 일치하지 않습니다.").css("color", "red");
        }

    });


    function emailCheck(email) {
        let regex = new RegExp("([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])");

        return (email != '' && email != 'undefined' && regex.test(email));

    }

    function passwordCheck(password) {
        let regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d~!@#$%^&*()+|=]{8,16}$/;
        return (password != '' && password != 'undefined' && regex.test(password));
    }

    function checkValid() {
        if (!emailFlag) {
            return false;
        }
        if (!passwordFlag) {
            return false;
        }
        if (!rePasswordFlag) {
            return false;
        }
        if (!nameFlag) {
            return false;
        }
        return true;
    }


})(jQuery);