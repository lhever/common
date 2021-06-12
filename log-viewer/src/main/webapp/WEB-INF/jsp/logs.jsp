<%--
  Created by IntelliJ IDEA.
  User: ysc
  Date: 17/08/2017
  Time: 12:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.hikvision.sc.devops.logviewer.constant.LogViewerConst" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String port = "" + LogViewerConst.PORT;

    String host = LogViewerConst.HOST;

    String base = LogViewerConst.CONTEXT_PATH;

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <base href="<%=request.getContextPath()%>/">
    <title>历史日志列表</title>
    <link href="static/icon/favicon.ico" rel="icon" type="image/ico">
    <script
            src="static/js/jquery-3.5.1.slim.min.js"
    <%--src="http://code.jquery.com/jquery-3.2.1.slim.min.js"--%>
    <%--integrity="sha256-k2WSCIexGzOj3Euiig+TlR8gA0EmPjuc79OEeY5L45g="--%>
            crossorigin="anonymous"></script>
    <style type="text/css">

        body {
            text-align: center;
        }
        table {
            background-color: #FFF;
            border: none;
            color: #565;
            font: 22px arial;
            width: 50%;
        }

        table caption {
            font-size: 40px;
            border-bottom: 4px solid #B3DE94;
            border-top: 4px solid #B3DE94;
        }

        table, td, th {
            margin: 0;
            padding: 0;
            vertical-align: middle;
            text-align:left;
        }

        tbody td, tbody th {
            background-color: #DFC;
            border-bottom: 2px solid #B3DE94;
            border-top: 3px solid #FFFFFF;
            padding: 9px;
        }


        tfoot td, tfoot th {
            font-weight: bold;
            padding: 4px 8px 6px 9px;
            text-align:center;
        }

        thead th {
            font-size: 22px;
            font-weight: bold;
            line-height: 30px;
            padding: 0 8px 2px;
            text-align:center;
        }

        tbody tr.odd th,tbody tr.odd td { /*odd就是偶数行*/
            background-color: #CEA;
            border-bottom: 2px solid #67BD2A;
        }

        td+td+td, /*第三个td以及之后的td元素*/
        col.action{ /*类样式*/
            text-align:center;
        }

        tbody tr:hover td, tbody tr:hover th { /*tr也有hover样式*/
            background-color: #8b7;
            color:#fff;
        }

        div .tableBox {
            margin-top: 1000px;
        }
    </style>
    <script type="application/javascript">


    </script>
</head>
<body>
<div id="tableBox">
    <table id="logList">
        <caption>${title}</caption> <!-- caption 标签必须紧随 table 标签之后。您只能对每个表格定义一个标题。通常这个标题会被居中于表格之上。 -->
        <col></col><col></col><col class="action" align="center"></col>
        <thead><tr><th>序号</th><th>日志文件名</th><th>操作</th></tr></thead>
        <tbody>${content}</tbody>
        <tfoot><tr><th >日志总数</th><td colspan="4">${total}</td></tr></tfoot>
    </table>
</div>
</body>
<script>
    $(document).ready(function () {
        tdBindEvent();
    });


    function tdclick() {

        var self = $(this);
        var serviceName = self.attr("serviceName");
        var fileName = self.attr("fileName");

        var url = "/log/download?fileName=" + fileName +  "&serviceName=" + serviceName;

        console.log("url is: " + url)
        window.location.href = url;


       /* // 构造隐藏的form表单
        var $form = $("<form class='hidden' method='post'></form>");
        $form.attr("url", url);
        $("body").append($form);
        // 添加提交参数
        var $input1 = $("<input name='param1' type='text'></input>");
        $input1.attr("serviceName", serviceName);
        $("#download").append($input1);
        var $input2 = $("<input name='param2' type='text'></input>");
        $input1.attr("fileName", fileName);
        $("#download").append($input2);


        // 提交表单
        $form.submit();*/
    }

    function tdBindEvent() {
        var tds = $("td[name='download']");

        console.log(tds);

        var tdArr = tds.toArray();
        for (i=0; i< tdArr.length; i++)
        {
            $(tdArr[i]).bind("click", tdclick)
        }

    }

</script>
</html>