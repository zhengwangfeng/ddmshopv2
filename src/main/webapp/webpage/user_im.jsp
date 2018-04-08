<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<script src="/jeecg2/plug-in/jquery/jquery-1.8.0.min.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<h2>Hello World! Action Websoket  <span>当前登录用户：${andyAdmin.userName}</span></h2>
<div style="width: 100%;">
    <div style="float: left; width: 70%; height: 100%; background-color: antiquewhite;">
        <p id="message"></p>
    </div>
    <div id="friend" style="float: right; width: 30%; height: 100%; background-color: aquamarine;">
        <p>好友列表</p>
        <c:forEach items="${adminList}" var="admin">
            <p><a href="#" onclick="createReceiveUser('${admin.userName}')">${admin.userName}</a></p>
        </c:forEach>
    </div>
</div>
<hr/>
<p>给<span id="sendName">${andyAdmin.userName}</span>发送消息：<input id="text" type="text"/>&nbsp;<button onclick="send()">发送消息</button></p>
<button onclick="closeWebSocket()">关闭WebSocket连接</button>
</body>
<script type="text/javascript">
    var userName = "${andyAdmin.userName}";
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
   /*  if ('WebSocket' in window) {
        //取到一个客户端的socket
        //websocket = new WebSocket("ws://127.0.0.1:8080/jeecg2/websocket.ws/"+userName);
    	//websocket = new WebSocket("ws://beckjo.xicp.net/jeecg2/websocket.ws/"+userName);
    	websocket = new WebSocket("ws://localhost:8080/tw/webSocketServer");
    } */
    /* if ('WebSocket' in window) {
        ws= new WebSocket("ws://localhost:8080/jeecg2/webSocketServer");
    }else {
        ws = new SockJS("http://localhost:8080/jeecg2/sockjs/webSocketServer");
    } */
    if ('WebSocket' in window) {  
        websocket = new WebSocket("ws://127.0.0.1:8080/jeecg2/wsMy?jspCode=" + userName);  
    } else if ('MozWebSocket' in window) {  
        websocket = new MozWebSocket("ws://127.0.0.1:8080/jeecg2/wsMy?jspCode=" + userName);  
    } else {  
        websocket = new SockJS("http://127.0.0.1:8080/jeecg2/wsMy/sockjs?jspCode=" + userName);  
    }  
    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };
    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("<span style='color:red;'>WebSocket连接成功</span>");
    }
    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
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
    function send() {
        //var message = document.getElementById('text').value;
        var message = {"userName":"111","password":"222"};
        //var paMsg = JSON.stringify(message); 
        var sendName = $("#sendName").text();
        var textJson = '{"sendName":"'+sendName+'","businessCode":"order","message":{"userName":"111","password":"222"}}'
        //var textJson = '{"sendName":"'+sendName+'","message":{"userName":"111","password":"222"}}'
        if(websocket.readyState != 3){
            websocket.send(textJson);
        }else{
            alert("websocket连接已经关闭或者出现网络异常！");
        }
    }
    //指定消息接收人
    function createReceiveUser(userName){
        alert("给"+userName+"发送消息");
        $("#sendName").text(userName);
    }
</script>
</html>