<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<h2>Login Websoket IM View</h2>
<p>请输入用户名：<input id="userName" type="text"/></p>
<p>请输入密码：<input id="password" type="password"/></p>
<button onclick="login()">登录</button>
<hr/>
<script src="/jeecg2/plug-in/jquery/jquery-1.8.0.min.js"></script>
</body>
<script type="text/javascript">
    function login(){
        var userName = $("#userName").val();
        var password = $("#password").val();
        $.ajax({
            type:"post",
            url:"admin.do?adminLogin",
            data:{userName:userName,password:password},
            dataType:"json",
            success:function(data){
                    window.location.href = "andysoso.do?actionImView";
            }
        });
    }
</script>
</html>