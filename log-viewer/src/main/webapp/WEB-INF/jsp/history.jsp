<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <base href="<%=request.getContextPath()%>/">
    <title>历史日志列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="static/css/bootstrap.min.css" crossorigin="anonymous">
    <link href="static/icon/favicon.ico" rel="icon" type="image/ico">
    <style type="text/css">
        table#indexlist {
            width: 100%;
        }

        table caption {
            font-size: 24px;
            border-bottom: 4px solid #cdc4c4;
            border-top: 4px solid #cdc4c4;
        }

        tfoot td, tfoot th {
            font-weight: bold;
            padding: 4px 8px 6px 9px;
            text-align: center;
        }

        /* Icon column
         * --------------------------------------------------- */
        th.indexcolicon,
        td.indexcolicon {
            width: 24px;
        }

        /* File name column
         * --------------------------------------------------- */
        th.indexcolicon,
        td.indexcolicon,
        th.indexcolname,
        td.indexcolname {
            padding: 5px;
            /*width: 24px;*/
        }


        /* Alternating even and odd rows
         * --------------------------------------------------- */
        tr.odd {
            background: #f5f5f5;
        }
        tr.even {
            background: #ffffff;
        }


        /*==========================*/

        /* Heading
-------------------------------------------------- */
        h1, h2, h3, h4, h5, h6 {
            font-family: 'Exo 2', sans-serif;
        }

        /* Navbar
        -------------------------------------------------- */
        body {
            padding-top: 3.7rem;
        }

        /* Footer
        -------------------------------------------------- */
        html {
            position: relative;
            min-height: 100%;
        }
        body {
            margin-bottom: 80px; /* Margin bottom by footer height */
        }
    </style>

    <script src="static/js/jquery-3.5.1.slim.min.js"
    <%--src="http://code.jquery.com/jquery-3.2.1.slim.min.js"--%>
    <%--integrity="sha256-k2WSCIexGzOj3Euiig+TlR8gA0EmPjuc79OEeY5L45g="--%>
            crossorigin="anonymous">
    </script>
</head>
<body>



<div class="container mt-4 mb-4">

   <%-- <div class="alert alert-success" role="alert">
        This directory tree contains current CentOS Linux and Stream releases.<br>
    </div>--%>

    <table id="indexlist">
        <caption>${title}</caption>
        <col></col><col></col><col></col><col class="action" align="center"></col><col></col>
        <thead>
        <tr class="even">
            <th class="indexcolicon"><img src="static/icon/blank.gif" alt="[   ]"></th>
            <th class="indexcolname"><a href="javascript:void(0)">序号</a></th>
            <th class="indexcolname"><a href="javascript:void(0)">日志文件名</a></th>
            <th class="indexcollastmod"><a href="javascript:void(0)">操作</a></th>
            <th class="indexcollastmod">&nbsp;&nbsp;&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <tr class="indexbreakrow">
            <th colspan="5">
                <hr>
            </th>
        </tr>
        ${content}
        </tbody>
        <tfoot><tr class="indexbreakrow"><td colspan="5">日志总数:${total}<td></tr></tfoot>
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