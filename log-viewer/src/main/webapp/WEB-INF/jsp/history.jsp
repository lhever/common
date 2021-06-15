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

        .hover {
            background-color: rgb(178, 60, 117);
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
            <th class="indexcollastmod">   </th>
        </tr>
        </thead>
        <tbody>
        <tr class="indexbreakrow">
            <th colspan="6">
                <hr>
            </th>
        </tr>
        ${content}
        </tbody>
        <tfoot><tr class="indexbreakrow"><td colspan="6">日志总数:${total}<td></tr></tfoot>
    </table>
</div>



</body>


<script>
    $(document).ready(function () {
        //注册下载事件
        bindDownloadEvent();
        // 注册查看内容事件
        bindViewEvent();

        //注册鼠标悬浮事件
        // bindHoverEvent();
    });


    function clickAndDownload() {

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


    function clickAndView() {
        var self = $(this);
        var serviceName = self.attr("serviceName");
        var fileName = self.attr("fileName");

        var url = "/log/view?fileName=" + fileName +  "&serviceName=" + serviceName;
        console.log("url is: " + url)
        window.location.href = url;
    }

    function bindDownloadEvent() {
        var tds = $("td[name='download']");
        var tdArr = tds.toArray();
        for (var i=0; i< tdArr.length; i++)
        {
            $(tdArr[i]).bind("click", clickAndDownload)

            $(tdArr[i]).bind("mouseover", function () {
                var level = $(this).attr("level")
                hoverTr(level, true);
            })
            $(tdArr[i]).bind("mouseout", function () {
                var level = $(this).attr("level");
                hoverTr(level, false);
            })
        }
    }



    function bindViewEvent() {
        var tds = $("td[name='view']");
        var tdArr = tds.toArray();
        for (var i=0; i< tdArr.length; i++)
        {
            $(tdArr[i]).bind("click", clickAndView)

            $(tdArr[i]).bind("mouseover", function () {
                var level = $(this).attr("level")
                hoverTr(level, true);
            })
            $(tdArr[i]).bind("mouseout", function () {
                var level = $(this).attr("level")
                hoverTr(level, false);
            })
        }
    }


    function hoverTr(level,  add) {
        var exp = "tr[level='" + level + "']";
        var trr = $(exp);
        var trs = trr.toArray();
        for (var j = 0; j < trs.length; j++) {
            var cls = $(trs[j]).attr("class");
            console.log(cls);
            var even = (cls == "even");

            if (add) {
                // $(trs[j]).addClass("hover");
                $(trs[j]).css("background-color", "rgba(227,15,15,0.56)");
            } else {
                // $(trs[j]).removeClass("hover");
                if (even) {
                    $(trs[j]).css("background-color", "#ffffff");
                } else {
                    $(trs[j]).css("background-color", "#f5f5f5");
                }

            }
        }
    }




    function bindHoverEvent() {
       /* $("tr").bind("mouseover", function () {
            $(this).css("background-color", "#eeeeee");
        })
        $("tr").bind("mouseout", function () {
            $(this).css("background-color", "#ffffff");
        })*/

        var trs = $("tr");
        var trArr = trs.toArray();
        console.log("tr total is " + trArr.length);
        for (var i=0; i< trArr.length; i++)
        {
            $(trArr[i]).bind("mouseover", function () {
                $(this).addClass("hover");
            })
            $(trArr[i]).bind("mouseout", function () {
                $(this).removeClass("hover");
            })
        }
    }

</script>
</html>