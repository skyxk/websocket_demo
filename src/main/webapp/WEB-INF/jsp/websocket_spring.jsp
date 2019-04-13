<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>websocket_spring_demo</title>
</head>
<body>
Welcome
<br/>

<button onclick="send('001')">开始签章</button>
<button onclick="send('003')">保存本次签章</button>
<hr/>
<button onclick="closeWebSocket()">关闭WebSocket连接</button>
<hr/>
<div id="message"></div>

<script type="text/javascript">
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://192.144.176.134:8081/webPdfSign/websocket_spring");
        // websocket = new WebSocket("ws://localhost:8081/websocket_spring");
    }
    else {
        alert('当前浏览器 Not support websocket_java')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        var result =event.data;
        if(result.indexOf("002") !== -1){
            //表示收到了跳转命令
            window.open("http://192.144.176.134:8080/toAddSeal?fileId="+result.substring(4));
            // window.open("http://localhost:8080/toAddSeal?fileId="+result.substring(4));
        }else {
            setMessageInnerHTML(event.data);
        }
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send(message) {
        // var message = document.getElementById('text').value;
        websocket.send(message);
    }
</script>
</body>
</html>