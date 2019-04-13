<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pintuer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mask.css">

    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>

    <script src="${pageContext.request.contextPath}/pdfJs/pdf.js"></script>
    <script src="${pageContext.request.contextPath}/pdfJs/pdf.worker.js"></script>
    <style>
        .lightbox{
            position: fixed;
            top: 0px;
            left: 0px;
            height: 100%;
            width: 100%;
            z-index: 7;
            opacity: 0.3;
            display: block;
            background-color: rgb(0, 0, 0);
            display: none;
        }
        .pop,iframe{
            position: absolute;
            left: 50%;
            top:0;
            width: 893px;
            height: 100%;
            margin-left: -446.5px;
            z-index: 9;
        }
    </style>
</head>
<body>
<ul>
    <li><a href="${pageContext.request.contextPath}/test2.pdf" target="pdfContainer" onclick="showPdf(true)">0001_pdf</a></li>
</ul>
<div class="lightbox" id="lightbox"></div>
<div id="pop" class="pop" style="display: none;">
    <a href="javascript:close()" style="
            position: absolute;
            right: -90px;
            display: inline-block;
            width: 80px;
            height: 30px;
        " id="close">关闭</a>
    <iframe src="" frameborder="0" id="pdfContainer" name="pdfContainer"></iframe>
</div>
</body>

<script type="text/javascript">
    function showPdf(isShow) {
        var state = "";
        if (isShow) {
            state = "block";
        } else {
            state = "none";
        }
        var pop = document.getElementById("pop");
        pop.style.display = state;
        var lightbox = document.getElementById("lightbox");
        lightbox.style.display = state;
    }
    function close() {
        showPdf(false);
    }
</script>
</html>