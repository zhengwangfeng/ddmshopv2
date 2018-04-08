<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>调用微信相机</title>
<link rel="stylesheet" href="css/weui.min.css"/>
</head>
<body>
<br/><br/>
<div>
	<div class="hd">
        <h1 class="page_title" ><span id="current_user"></span></h1>
    </div>
     	<br/>
	<div class="page slideIn button">
	    <div class="bd spacing">
	    	<br/><br/><br/><br/>
	        <a class="weui_btn weui_btn_primary" href="javascript:;" onclick="take_a_photo()">调用微信相机</a></br></br>
	        <a class="weui_btn weui_btn_primary" href="javascript:;" onclick="scanQRCode()">微信扫一扫</a>
	    </div>
	</div>
</div>

<br/><br/><br/><br/>
<div>
	  appId:<span id="appId"></span><br/>
	  timestamp:<span id="timestamp"></span><br/>
	  nonceStr:<span id="nonceStr"></span><br/>
	  jsapi_ticket:<span id="jsapi_ticket"></span><br/>
	  signature:<span id="signature"></span><br/>
	
	  originalStr:<span id="originalStr"></span><br/>
	  scan_result:<span id="result"></span><br/>
</div>	

<script type="text/javascript" src="<%=path%>/plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="js/weixin_util.js"></script>
<script type="text/javascript">
// 通过config接口注入权限验证配置
$(function(){
	get_wx_config();
})

function get_wx_config(){
	debugger;
	//url（当前网页的URL，不包含#及其后面部分）
	var url = window.location.href.split('#')[0];
	alert("url====="+url);
	var indata = {"url":url};
	//$.post("http://wwww.axxx.cn/xxx/weixin/getConfigInfo.json", indata, function(data){
	$.post("http://1m6309f047.iok.la/shop/rest/wechatUploadController/getConfigInfo.json", indata, function(data){
        if(!data.success){
        	alert("系统错误");
        }else{
        	var result = data.obj;
        	$("#appId").text(result.appId);
        	$("#timestamp").text(result.timestamp);
        	$("#nonceStr").text(result.nonceStr);
        	$("#jsapi_ticket").text(result.jsapi_ticket);
        	$("#signature").text(result.signature);
        	$("#originalStr").text(result.originalStr);
        	
        	//步骤三：通过config接口注入权限验证配置
        	wx.config({
        	    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        	    appId: result.appId,   // 必填，公众号的唯一标识
        	    timestamp: result.timestamp, // 必填，生成签名的时间戳
        	    nonceStr: result.nonceStr, // 必填，生成签名的随机串
        	    signature: result.signature,// 必填，签名，见附录1
        	    jsApiList: ["chooseImage", "previewImage", "uploadImage", "downloadImage", "scanQRCode"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        	    // 基本思路是，上传图片到微信服务器->下载多媒体接口讲图片下载到服务器->返回服务器存储路径->前台显示
        	});
        	
        	// 步骤四：通过ready接口处理成功验证
        	wx.ready(function(){
				alert("wx.config success.");
        	});
        	
        	wx.error(function(res){
        		alert("wx.config failed.");
				//alert(res);
        	    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，
        	    // 也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
        	});
        	
			// {"errMsg":"config:invalid signature"}
        }
      },'json');
}


// 图片接口
// 拍照、本地选图
var images = {
	localId: [],
	serverId: []
};	

// 拍照或者选择照片
function take_a_photo(){
	wx.chooseImage({
		count: 1, // 默认9，这里每次只处理一张照片
		sizeType: ['original', 'compressed'], 	// 可以指定是原图还是压缩图，默认二者都有
	    sourceType: ['album', 'camera'], 		// 可以指定来源是相册还是相机，默认二者都有
        success: function (res) {
            images.localId = res.localIds; 		// 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
            
            var i = 0, length = images.localId.length;  
            function upload() {  
                wx.uploadImage({  
                    localId: images.localId[i],  
                    success: function(res) {  
                        i++;  
                        alert('已上传：' + i + '/' + length);  
                        images.serverId.push(res.serverId); 
                        // res.serverId 就是 media_id，根据它去微信服务器读取图片数据：
                        var indata = {"media_id":res.serverId};
                        $.post("/weixin/getPhoto.json", indata, function(data){
					        if(data.rs == 'f'){
					        	alert("系统错误");
					        }else{
					        	alert(data.body);	// 返回 图片在我们自己的服务器的url
					        }
					      },'json');
                        
                        if (i < length) {  
                            upload();  
                        }  
                    },  
                    fail: function(res) {  
                        alert(JSON.stringify(res));  
                    }  
                });  
            }  
            upload();  
        }  
    });
}
 
function scanQRCode(){
	wx.scanQRCode({
	    needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
	    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
	    success: function (res) {
	    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
	}
	});	
}
</script>
</body>
</html>
