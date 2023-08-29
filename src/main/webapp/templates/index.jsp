<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="Male_Fashion Template">
    <meta name="keywords" content="Male_Fashion, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>롯데 시장</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap"
          rel="stylesheet">

    <!-- Css Styles -->
    <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.3.1/css/all.css'>
    <%--    <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">--%>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="css/style.css" type="text/css">

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>

<script>

</script>
<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>


<!-- Header Section -->
<jsp:include page="./common/header.jsp"/>

<script>
  const hostName = location.host;
  const queryParameters = new URLSearchParams(decodeURI(location.search));
  const errorMessage = queryParameters.get("errorMessage");
  if (errorMessage !== null) {
    Swal.fire({
      icon: 'error',
      title: "ERROR",
      text: errorMessage,
      footer: '<a href="https://github.com/lotte-bit-1/shopping-mall-servlet-jsp/issues">이슈 남기러 가기</a>'
    }).then((result) => {
      window.location.replace("http://" + hostName);
    });
  }
</script>

<!-- Banner Section Begin -->
<section class="banner spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-7 offset-lg-4">
                <div class="banner__item">
                    <div class="banner__item__pic">
                        <img src="img/banner/banner-1.jpg" alt="">
                    </div>
                    <div class="banner__item__text">
                        <h2>Clothing Collections 2030</h2>
                        <a href="/product.bit?view=shop&curPage=0&sort=DATE_DESC">Shop now</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-5">
                <div class="banner__item banner__item--middle">
                    <div class="banner__item__pic">
                        <img src="img/banner/banner-2.jpg" alt="">
                    </div>
                    <div class="banner__item__text">
                        <h2>Accessories</h2>
                        <a href="/product.bit?view=shop&curPage=0&sort=DATE_DESC">Shop now</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-7">
                <div class="banner__item banner__item--last">
                    <div class="banner__item__pic">
                        <img src="img/banner/banner-3.jpg" alt="">
                    </div>
                    <div class="banner__item__text">
                        <h2>Shoes Spring 2030</h2>
                        <a href="/product.bit?view=shop&curPage=0&sort=DATE_DESC">Shop now</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Banner Section End -->

<!-- Footer Section Begin -->
<jsp:include page="/templates/common/footer.jsp"/>
<!-- Footer Section End -->

<jsp:include page="common/search.jsp"/>
<!-- Js Plugins -->
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.nice-select.min.js"></script>
<script src="js/jquery.nicescroll.min.js"></script>
<script src="js/jquery.magnific-popup.min.js"></script>
<script src="js/jquery.countdown.min.js"></script>
<script src="js/jquery.slicknav.js"></script>
<script src="js/mixitup.min.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/main.js"></script>

</body>

</html>
