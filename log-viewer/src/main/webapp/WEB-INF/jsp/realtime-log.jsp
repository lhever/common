<%--
  Created by IntelliJ IDEA.
  User: ysc
  Date: 17/08/2017
  Time: 12:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.lhever.sc.devops.logviewer.constant.LogViewerConst" %>
<%@ page import="com.lhever.sc.devops.core.utils.FileUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String serviceName = request.getParameter("serviceName");
    if(StringUtils.isBlank(serviceName)){
        response.getWriter().println("参数serviceName未指定");
        return;
    }
    String WEB_SOCKET_ADDRESS = "" + LogViewerConst.WEB_SOCKET_ADDRESS;
    WEB_SOCKET_ADDRESS = FileUtils.trimTail(WEB_SOCKET_ADDRESS);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <base href="<%=request.getContextPath()%>/">
    <title>实时日志-<%=serviceName%></title>
    <link href="static/icon/favicon.ico" rel="icon" type="image/ico">
    <script
            src="static/js/jquery-3.5.1.slim.min.js"
            <%--src="http://code.jquery.com/jquery-3.2.1.slim.min.js"--%>
            <%--integrity="sha256-k2WSCIexGzOj3Euiig+TlR8gA0EmPjuc79OEeY5L45g="--%>
            crossorigin="anonymous"></script>
    <style type="text/css">
        html
        {
            height:100%;
            margin:0;
        }
        body
        {
            overflow:hidden;
            height:100%;
            margin:0;
        }
        div > b
        {
            color: blue;
            float:right;
            cursor: pointer;
        }
        div > b:hover
        {
            color: red;
            font-size: 18px;
        }
        .lineCountInLog
        {
            color: red;
        }
        .error
        {
            color: red;
        }
        .info
        {
            color: green;
        }
        .debug
        {
            color: blue;
        }
        .warn
        {
            color: yellow;
        }
        .divide
        {
            color: red;
        }
        span
        {
            background: black;
        }
        .total
        {
            color: white;
        }
        .connect
        {
            color: white;
        }
        span a {
            color: white;
        }
    </style>
    <script type="application/javascript">
        var lineCount = 0;
        var scroll = true;
        function backToTop(){
            scroll = false;
            changeState();
            $("#realtime-log-container").scrollTop(0);
        }
        function backToBottom(){
            scroll = true;
            changeState();
            $("#realtime-log-container").scrollTop($("#realtime-log-container div").height() - $("#realtime-log-container").height());
        }
        function switchScroll(){
            scroll = !scroll;
            changeState();
        }
        function changeState(){
            if(scroll){
                $("#switchScroll").html("禁用滚动&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            }else{
                $("#switchScroll").html("启用滚动&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            }
        }
        var state = '未连接';
        function recconnect(){
            closeSocket();
            connect();
        }
        var errorCount = 0;
        var warnCount = 0;
        var infoCount = 0;
        var debugCount = 0;
        function connect(){
            websocket = new WebSocket('<%=WEB_SOCKET_ADDRESS%>/show/online/<%=serviceName%>');
            websocket.onopen = function(event){
                state = '已连接';
                $("#state").text(state);
                $("#realtime-log-container div").append("<hr/>");
            }
            websocket.onmessage = function(event) {
                if(event.data.indexOf("--force close--") != -1){
                    window.close();
                }
                if(event.data.indexOf(" ERROR ") != -1){
                    errorCount++;
                }
                if(event.data.indexOf(" WARN ") != -1){
                    warnCount++;
                }
                if(event.data.indexOf(" INFO ") != -1){
                    infoCount++;
                }
                if(event.data.indexOf(" DEBUG ") != -1){
                    debugCount++;
                }
                state = '已连接';
                $("#state").text(state);
                lineCount++;
                var dentCount=6-(''+ lineCount).length;
                $("#realtime-log-container div")
                    .append("<span class='lineCountInLog'>"
                        +(''+lineCount).padStart((''+lineCount).length+('&nbsp;'.length*dentCount), '&nbsp;')+"&nbsp;&nbsp;&nbsp;&nbsp;</span>"
                        +event.data.replace(" ERROR ", " <span class='error'>ERROR</span> ")
                            .replace(" WARN ", " <span class='warn'>WARN</span> ")
                            .replace(" INFO ", " <span class='info'>INFO</span> ")
                            .replace(" DEBUG ", " <span class='debug'>DEBUG</span> ")
                            .replace(" --- ", " <span class='divide'>---</span> "));
                $("#errorCount").text(errorCount);
                $("#warnCount").text(warnCount);
                $("#infoCount").text(infoCount);
                $("#debugCount").text(debugCount);
                $("#lineCount").text(lineCount);
                if(scroll){
                    $("#realtime-log-container").scrollTop($("#realtime-log-container div").height() - $("#realtime-log-container").height());
                }
            };
            websocket.onclose = function(event) {
                state = '已关闭';
                $("#state").text(state);
                console.log(state,event);
            };
            websocket.onerror = function(event) {
                state = '发生错误';
                $("#state").text(state);
                console.log(state,event);
            };
        }
        function detect(){
            if(state != '已连接'){
                recconnect();
            }
        }

        function sendHeartBeat() {
            try{
                websocket.send("HeartBeat");
            } catch (e) {
                console.log(e)
            }
        }

        function closeSocket() {
            try{
                websocket.close();
            }catch (e) {
                console.log(e)
            }
        }

        function heartBeatAndReconnect() {
            sendHeartBeat();
            detect();
        }


        // setInterval(detect, 10000);
        // setInterval(sendHeartBeat, 5000);
        setInterval(heartBeatAndReconnect, 8000);
        changeState();
    </script>
</head>
<body>
<div style="height: 2%;">

</div>

<div style="height: 4%;">
            <span>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="total">日志总行数: <span id="lineCount"></span></span>
                &nbsp;&nbsp;<span class="error">ERROR: <span id="errorCount"></span></span>
                &nbsp;&nbsp;<span class="warn">WARN: <span id="warnCount"></span></span>
                &nbsp;&nbsp;<span class="info">INFO: <span id="infoCount"></span></span>
                &nbsp;&nbsp;<span class="debug">DEBUG: <span id="debugCount"></span></span>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="connect">连接状态: <span id="state"></span></span>
                &nbsp;&nbsp;&nbsp;&nbsp;
            </span>
    &nbsp;&nbsp;
    <b onclick="backToBottom()">回到底部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
    <b id="historyLink"><a target="_blank" href="history?serviceName=<%=serviceName%>">历史日志</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
</div>

<div id="realtime-log-container" style="height: 86%; overflow-y: scroll; background: black; color: white; padding: 10px;">
    <div></div>
</div>

<div style="height: 4%;">
    <b onclick="switchScroll()" id="switchScroll">禁用滚动&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b><b onclick="backToTop()">回到顶部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
</div>

<div style="height: 2%;">

</div>
</body>
<script>
    $(document).ready(function() {

       /* var url = "history?serviceName=<%=serviceName%>";
        $("#historyLink").on("click", function () {
            window.open(url);
        })*/
        connect();
    });
</script>
</html>