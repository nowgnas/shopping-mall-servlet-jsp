'use strict';

(function ($) {
    let emailFlag = false;
    let passwordFlag = false;
    let rePasswordFlag = false;
    let nameFlag = false;

    $('#register-btn').on('click', function (event) {
        event.preventDefault();
        event.stopPropagation();

        const forms = $('.validation-form');
        let flag = true;
        Array.prototype.filter.call(forms, (form) => {
            if (form.checkValidity() === false) {
                flag = false;
            }
            form.classList.add('was-validated');
        }, false);

        if (!flag) {
            return;
        }

        if (!checkValid()) {
            Swal.fire({
                icon: 'error',
                title: '입력 값을 확인 해주세요.',
            })
            return;
        }

        const email = $("#registerEmail").val();
        const password = $("#registerPassword").val();
        const name = $("#registerName").val();

        $.post("member-rest.bit?cmd=register", {
            registerEmail: email,
            registerPassword: password,
            registerName: name
        }, function (result) {
            if (result) {
                Swal.fire({
                    icon: 'success',
                    title: '가입을 축하합니다.',
                    showConfirmButton: false,
                    timer: 1000
                }).then(() => {
                    location.href = "main.bit";
                });
            } else {
                Swal.fire({
                    icon: 'error',
                    title: '회원가입에 실패하였습니다.',
                })
            }

        })
            .fail(function (err) {
                Swal.fire({
                    icon: 'error',
                    title: '회원가입에 실패하였습니다.',
                })
            });
    });

    $("#registerEmail").on("focusout", function () {
        let email = $("#registerEmail").val();
        if (!emailCheck(email)) {
            $("#vaildEmail").text("이메일 형식이 아닙니다.").css("color", "red");
            return;
        }
        $.post("member-rest.bit?cmd=loginCheck", {
            email: email
        }, function (isValidEmail) {
            if (isValidEmail) {
                $("#vaildEmail").text("사용할 수 있는 아이디 입니다.").css("color", "green");
                emailFlag = true;
            } else {
                $("#vaildEmail").text("이미 존재하는 이메일 입니다.").css("color", "red");
                emailFlag = false;
            }
        })
            .fail(function (err) {
                console.log(err);
            });
    });

    $("#registerPassword").on("focusout", function () {
        let registerPassword = $("#registerPassword").val();
        if (!passwordCheck(registerPassword)) {
            $("#vaildPassword").text("비밀번호는 영문, 숫자 형식으로 8~20자 입력해야 합니다.").css("color", "red");
            passwordFlag = false;
            return;
        }
        $("#vaildPassword").text("유효한 비밀번호 입니다.").css("color", "green");
        passwordFlag = true;
    });

    $("#registerPassword2").on("focusout", function () {
        let password = $("#registerPassword").val()
        let password2 = $("#registerPassword2").val();
        if ((password2 != "" && password2 != "undefined") && password === password2) {
            $("#vaildPassword2").text("비밀번호가 일치합니다.").css("color", "green");
            rePasswordFlag = true;
        } else {
            $("#vaildPassword2").text("비밀번호가 일치하지 않습니다.").css("color", "red");
            rePasswordFlag = false;
        }
    });

    $("#registerName").on("focusout", function () {
        let name = $("#registerName").val()

        if ((name != "" && name != "undefined") && name.length > 1) {
            $("#validName").text("");
            nameFlag = true;
        } else {
            $("#validName").text("2자 이상 입력 해주세요.").css("color", "red");
            nameFlag = false;
        }
    });


    function emailCheck(email) {
        let regex = new RegExp("([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])");

        return (email != '' && email != 'undefined') && regex.test(email);

    }

    function passwordCheck(password) {
        let regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d~!@#$%^&*()+|=]{8,16}$/;
        return (password != '' && password != 'undefined') && regex.test(password);
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