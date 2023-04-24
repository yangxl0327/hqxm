<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setAttribute("path",request.getContextPath());
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="${path}/boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="${path}/boot/js/jquery-2.2.1.min.js"></script>
    <script>
        function changeImage(){
            $('#img1').attr('src',"${pageContext.request.contextPath}/admin/yzm?d="+new Date()*1);

        };
        function login() {
            $.ajax({
                url:"${pageContext.request.contextPath}/admin/login",
                type:"POST",
                datatype:"JSON",
                data:$("#loginForm").serialize(),
                success:function (data) {
                    if (data!=null&data!=""){
                        $("#msg").html("<span class='error'>"+data+"</span>")
                    }else {
                        location.href = "${pageContext.request.contextPath}/jsp/main.jsp";
                    }
                }

            })
        }
    </script>
</head>
<body style=" background: url(../img/微信图片_20190603161551.jpg); background-size: 100%;">


<div class="modal-dialog  " style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="">
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input type="text" class="form-control"placeholder="用户名" autocomplete="off" name="name">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="验证码" autocomplete="off" name="code">
                <img src="${pageContext.request.contextPath}/img/微信图片_20190603161551.jpg" id="img1" onclick="changeImage()">
                <a class="btn btn-default" onclick="changeImage()">换一张</a>
            </div>
            <span id="msg" style="color: red"></span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录</button>
            </div>
<%--            <div class="form-group">--%>
<%--                <button type="button" class="btn btn-default form-control">注册</button>--%>
<%--            </div>--%>

        </div>
        </form>
    </div>
</div>
</body>
</html>
