<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <script src="static/js/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
    <script src="static/js/base64.min.js" crossorigin="anonymous"></script>
    <script src="static/js/jquery.md5.js" crossorigin="anonymous"></script>

    <script   type="text/javascript">
        var $=jQuery.noConflict();
        var BASE = '<%=request.getContextPath()%>';
    </script >

    <base href="<%=request.getContextPath()%>/">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
<%--    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">--%>

    <meta name="description" content="">
    <title>登录认证页面</title>

    <link rel="stylesheet" href="static/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="static/css/signin.css" crossorigin="anonymous">
    <link rel="icon"       href="static/icon/favicon.ico"      type="image/ico">
</head>


<body>
<div class="container form-margin-top">
    <form id="loginForm" class="form-signin" action="/log/login" enctype="multipart/form-data" method="post" onsubmit="return encryptPwd()">
        <h2 class="form-signin-heading" align="center">运维中心统一登录平台</h2>
        <input id="userInput" type="text" name="username" class="form-control form-margin-top" placeholder="账号" required autofocus>
        <input id="pwdInput" type="password" name="password" class="form-control" placeholder="密码" required>
        ${errorMsg}
        <input type="checkbox" name="remember-me">自动登录
        <button id="ajaxBtn" class="btn btn-lg btn-primary btn-block" type="submit" <%--type="button"--%>>登录</button>
    </form>
</div>
</body>

<script>

    function encryptPwd() {
        // 密码检验
        var password = $("#pwdInput").val();
        if (password) {
            var newPwd = "123456" + password;
            var encodedPwd = $.md5(Base64.encode(newPwd) + "123!@#");
            $("#pwdInput").val(encodedPwd);
        }
        var user = $("#userInput").val();
        if (user) {
            var encodedUser = $.md5(Base64.encode(user + user));
            $("#userInput").val(encodedUser);
        }
        return true;
    }



    $(document).ready(function() {
      /*  $('#pwdInput').bind('input propertychange', function() {
            encryptPwd();
        });*/
    });
</script>

</html>