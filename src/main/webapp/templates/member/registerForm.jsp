<%--
  Created by IntelliJ IDEA.
  User: ssjy4
  Date: 2023-08-23
  Time: 오전 9:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zxx">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="Male_Fashion Template">
    <meta name="keywords" content="Male_Fashion, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Register</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap"
          rel="stylesheet">

    <!-- Css Styles -->
    <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.3.1/css/all.css'>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<style>
    body {
        min-height: 100vh;
        background-color: #f3f2ee;
    }

    .input-form {
        max-width: 680px;

        margin-top: 80px;
        padding: 32px;

        background: #fff;
        -webkit-border-radius: 10px;
        -moz-border-radius: 10px;
        border-radius: 10px;
        -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
        -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
        box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15)
    }

</style>
<body>

<!-- Header Section -->
<jsp:include page="../common/header.jsp"/>
<!-- Header Section -->

<section>
    <div class="container">
        <div class="input-form-backgroud row">
            <div class="input-form col-md-12 mx-auto">
                <h4 class="mb-3">Register</h4>
                <form class="validation-form" novalidate>

                    <div class="mb-3">
                        <label for="registerEmail">이메일</label>
                        <input type="email" class="form-control" name="registerEmail" id="registerEmail" placeholder="you@example.com" required>
                        <div class="invalid-feedback">
                            이메일을 입력해주세요.
                        </div>
                        <span class="valid" id="vaildEmail"></span>
                    </div>

                    <div class="mb-3">
                        <label for="registerPassword">비밀번호</label>
                        <input type="password" class="form-control" name="registerPassword" id="registerPassword" placeholder="영문, 숫자 형식으로 8~20자"
                               required>
                        <div class="invalid-feedback">
                            비밀번호를 입력해주세요.
                        </div>
                        <span class="valid" id="vaildPassword"></span>
                    </div>

                    <div class="mb-3">
                        <label for="registerPassword2">비밀번호 확인</label>
                        <input type="password" class="form-control" id="registerPassword2" placeholder="비밀번호 확인" required>
                        <div class="invalid-feedback">
                            비밀번호를 입력해주세요.
                        </div>
                        <span class="valid" id="vaildPassword2"></span>
                    </div>


                    <div class="mb-3">
                        <label for="registerName">이름</label>
                        <input type="name" class="form-control" name="registerName" id="registerName" placeholder="홍길동" required>
                        <div class="invalid-feedback">
                            이름을 입력해주세요.
                        </div>
                        <span class="valid" id="validName"></span>
                    </div>

                    <hr class="mb-4">
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="aggrement" required>
                        <label class="custom-control-label" for="aggrement">개인정보 수집 및 이용에 동의합니다.</label>
                    </div>
                    <div class="mb-4"></div>
                    <div style="text-align: center">
                        <button id="register-btn" class="primary-btn center" type="submit">가입 완료</button>
                    </div>
                </form>
            </div>
        </div>
        <footer class="my-3 text-center text-small">
            <p class="mb-1">&copy; 2023 Bit-Lotte-1Team</p>
        </footer>
    </div>
</section>

<!-- Footer Section Begin -->
<jsp:include page="../common/footer.jsp"/>
<!-- Footer Section End -->

<!-- Js Plugins -->
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/member.js"></script>
</body>
</html>
