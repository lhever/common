<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <script   type="text/javascript">
        var BASE = '<%=request.getContextPath()%>';
    </script >

    <base href="<%=request.getContextPath()%>/">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%--    <meta name="viewport" content="width=device-width, initial-scale=1">--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <title>登录认证页面</title>

    <link rel="stylesheet" href="static/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="icon"       href="static/icon/favicon.ico"      type="image/ico">
    <style>
        /*将整个登入的div垂直居中*/
        #all {
            transform: translateY(35%);  /*向下移动自身的35%*/
           /* background-color: white;
            height: 390px;*/
        }
    </style>
</head>


<body>

<div class="container">
    <div  class="row  row-center" id="all">
        <div  class="col-center">
            <div id="loginui" style="background-color: darkseagreen; padding: 28px;border-radius: 8px;border-color: #00a3ff;border-width: 2px  ">
                ${content}
            </div>
        </div>
    </div>
</div>



</body>

</html>