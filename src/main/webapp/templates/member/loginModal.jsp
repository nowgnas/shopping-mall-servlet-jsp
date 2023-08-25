<%--
  Created by IntelliJ IDEA.
  User: ssjy4
  Date: 2023-08-25
  Time: 오전 8:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- login modal begin -->
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog"
     aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header border-bottom-0">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-title text-center">
                    <h4>Login</h4>
                </div>
                <div class="d-flex flex-column text-center">
                    <form id="loginForm" method="POST" action="member.bit?view=login">
                        <div class="form-group">
                            <input type="email" class="form-control" name="email" id="email"
                                   placeholder="Your email address...">
                            <span id="checkEmail"></span>
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" name="password" id="password"
                                   placeholder="Your password...">
                            <span id="checkPassword"></span>
                        </div>
                        <button type="button" class="btn btn-info btn-block btn-round" id="loginbtn">Login</button>
                    </form>

                    <div class="text-center text-muted delimiter">or use a social network</div>
                    <div class="d-flex justify-content-center social-buttons">
                        <button type="button" class="btn btn-secondary btn-round" data-toggle="tooltip"
                                data-placement="top" title="Twitter">
                            <i class="fab fa-twitter"></i>
                        </button>
                        <button type="button" class="btn btn-secondary btn-round" data-toggle="tooltip"
                                data-placement="top" title="Facebook">
                            <i class="fab fa-facebook"></i>
                        </button>
                        <button type="button" class="btn btn-secondary btn-round" data-toggle="tooltip"
                                data-placement="top" title="Linkedin">
                            <i class="fab fa-linkedin"></i>
                        </button>
                    </div>
                </div>
            </div>
            <div class="modal-footer d-flex justify-content-center">
                <div class="signup-section">Not a member yet? <a href="member.bit?view=registerForm" class="text-info"> Sign Up</a>.</div>
            </div>
        </div>
    </div>
</div>

<!-- login modal end -->

<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js'></script>
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/login.js"></script>