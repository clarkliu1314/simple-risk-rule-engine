<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>修改邮箱配置</title>
    <link rel="stylesheet" type="text/css" href="/style/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/style/themes/icon.css">
    <script type="text/javascript" src="/style/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/style/jquery.easyui.min.js"></script>
</head>
<body style="width:100%;height:100%">
<div class="easyui-panel" title="修改邮箱配置" style="width:300px;height:200px;padding:10px;margin: 0 auto;">
    <form action="/ccmis/risk/email/setEmailConfig" method="post">
        <div id="message" style="font-size:6;color: red"> ${message}</div>
        <p> 服务器：<input type="text" name="host"></p>
        <p> 发件人：<input type="text" name="from"></p>
        <p> 用户名：<input type="text" name="username"></p>
        <p> 密  码：<input type="password" name="password"></p>
        <p> <input type="reset" name="reset" value="取消"><input type="submit" name="btnsubmit" value="修改"></p>
    </form>
</div>
</body>
</html>