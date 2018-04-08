<%--
  Created by IntelliJ IDEA.
  User: wangkun
  Date: 2016/4/23
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp"%>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title><%-- <t:mutiLang langKey="jeect.platform"/> --%>点单么美食平台</title>

    <meta name="keywords" content="点单么美食平台">
    <meta name="description" content="点单么美食平台">

    <link rel="shortcut icon" href="images/favicon.ico">
    <link href="plug-in-ui/hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link rel="stylesheet" href="plug-in/ace/assets/css/font-awesome.min.css" />
    <!--[if IE 7]>
    <link rel="stylesheet" href="plug-in/ace/assets/css/font-awesome-ie7.min.css" />
    <![endif]-->
    <!-- Sweet Alert -->
    <link href="plug-in-ui/hplus/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/animate.css" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/style.css?v=4.1.0" rel="stylesheet">
    <!--右键菜单-->
    <link href="plug-in/hplus/smartMenu.css" rel="stylesheet">
    <script language="javascript" src="lodop/LodopFuncs.js"></script>
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation" style="z-index: 1991;">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element" >
                        <span><img alt="image" class="img-circle" src="plug-in/login/images/jeecg-aceplus.png" /></span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                                <span class="block m-t-xs"><strong class="font-bold">${userName }</strong></span>
                                <span class="text-muted text-xs block">${roleName }<b class="caret"></b></span>
                                <input type="hidden" id="userrole" value="${roleName }">
                                <input type="hidden" id="shopId" value="">
                               
                                </span>
                        </a> 
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li>
                                <a href="javascript:add('<t:mutiLang langKey="common.change.password"/>','userController.do?changepassword','',550,200)">
                                    <t:mutiLang langKey="common.change.password"/>
                                </a>
                            </li>
                            <li><a href="javascript:openwindow('<t:mutiLang langKey="common.profile"/>','userController.do?userinfo')"><t:mutiLang langKey="common.profile"/></a></li>
                            <li><a href="javascript:openwindow('<t:mutiLang langKey="common.ssms.getSysInfos"/>','tSSmsController.do?getSysInfos')"><t:mutiLang langKey="common.ssms.getSysInfos"/></a></li>
                            <li><a href="javascript:add('<t:mutiLang langKey="common.change.style"/>','userController.do?changestyle','',550,250)"><t:mutiLang langKey="common.my.style"/></a></li>
                            <li><a href="javascript:clearLocalstorage()"><t:mutiLang langKey="common.clear.localstorage"/></a></li>
                           <!--  <li><a href="http://yun.jeecg.org" target="_blank">云应用中心</li> -->
                            <li class="divider"></li>
                            <li><a href="javascript:logout()">注销</a></li>
                        </ul>
                    </div>
                    <div class="logo-element">点单么美食平台
                    </div>
                </li>

                <t:menu style="hplus" menuFun="${menuMap}"></t:menu>

            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header" style="height: 60px;"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                    <form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                        <div class="form-group">
                            <input type="text" placeholder="欢迎使用点单么美食平台 …" class="form-control" name="top-search" id="top-search">
                        </div>
                    </form>
                </div>
                
                
                <ul class="nav navbar-top-links navbar-right">
                    <!--  <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-envelope"></i> <span class="label label-warning">0</span>
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <li>
                                <a>
                                    <div>
                                        <i class="fa fa-envelope fa-fw"></i> 您有0条未读消息
                                        <span class="pull-right text-muted small">4分钟前</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="text-center link-block">
                                    <a class="" href="javascript:goAllNotice();">
                                        <i class="fa fa-envelope"></i> <strong> 查看所有消息</strong>
                                    </a>
                                </div>
                            </li>
                        </ul>
                    </li> -->
                    <!-- 订单信息提示begin -->
                     <li class="dropdown" id="orderIco">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-shopping-cart"></i> <span class="label label-warning" id="orderCountIndex">0</span>
                        	订单消息
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <li>
                                <a>
                                    <div>
                                        <i class="fa fa-envelope fa-fw"></i> 您有<span id="orderCount"></span>条未读消息
                                        <!-- <span class="pull-right text-muted small">4分钟前</span> -->
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="text-center link-block">
                                    <a class="" href="javascript:goAllSubOrder();">
                                        <i class="fa fa-envelope"></i> <strong> 查看所有消息</strong>
                                    </a>
                                </div>
                            </li>
                        </ul>
                    </li>  
                    <!-- 订单信息提示end -->
                    
                    <!-- 用户选择服务begin -->
                    <li class="dropdown" id="messageIco">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-bell"></i> <span class="label label-primary" id="serviceIndex">0</span>
                            服务信息
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <li>
                                <a>
                                    <div>
                                        <i class="fa fa-envelope fa-fw"></i> 您有<span id="serviceCount">0</span>条未读消息
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="text-center link-block">
                                      <a class="" href="javascript:goAllUserService();">
			                            <i class="fa fa-tasks"></i>查看所有
			                      		</a>
                                </div>
                            </li>
                        </ul>
                    </li> 
                     <!-- 用户选择服务end -->
                     
                     
                      <!-- 支付消息begin -->
                    <li class="dropdown" id="paymessageIco">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-bell"></i> <span class="label label-primary" id="payNoticeIndex">0</span>
                            	支付消息
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <li>
                                <a>
                                    <div>
                                        <i class="fa fa-envelope fa-fw"></i> 您有<span id="payNoticeCount">0</span>条未读消息
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="text-center link-block">
                                      <a class="" href="javascript:goAllpayNotice();">
			                            <i class="fa fa-tasks"></i>查看所有
			                      		</a>
                                </div>
                            </li>
                        </ul>
                    </li> 
                     <!-- 支付消息end -->
                     
                     
                    <li class="dropdown">
                    	<a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <span ><strong class="font-bold">${userName }</strong></span>
                                <span >${roleName }<b class="caret"></b></span>
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <li>
                                <a href="javascript:add('<t:mutiLang langKey="common.change.password"/>','userController.do?changepassword','',550,200)">
                                    <t:mutiLang langKey="common.change.password"/>
                                </a>
                            </li>
                            <li><a href="javascript:openwindow('<t:mutiLang langKey="common.profile"/>','userController.do?userinfo')"><t:mutiLang langKey="common.profile"/></a></li>
                            <li><a href="javascript:openwindow('<t:mutiLang langKey="common.ssms.getSysInfos"/>','tSSmsController.do?getSysInfos')"><t:mutiLang langKey="common.ssms.getSysInfos"/></a></li>
                            <li><a href="javascript:add('<t:mutiLang langKey="common.change.style"/>','userController.do?changestyle','',550,250)"><t:mutiLang langKey="common.my.style"/></a></li>
                            <li><a href="javascript:clearLocalstorage()"><t:mutiLang langKey="common.clear.localstorage"/></a></li>
                            <!-- <li><a href="javascript:toJeecgYun()">云应用中心</a></li> -->
                            <!-- <li><a href="http://yun.jeecg.org" target="_blank">云应用中心</li> -->
                           <!--  <li class="divider"></li>
                            <li><a href="javascript:logout()">注销</a></li> -->
                        </ul>
                    </li>
                    
                     
                    <li class="dropdown hidden-xs">
                        <a class="right-sidebar-toggle" aria-expanded="false">
                            <i class="fa fa-tasks"></i> 主题
                        </a>
                    </li>
                      <li class="dropdown">
                     <a href="javascript:logout()" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
                     </li>
                </ul>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="loginController.do?hplushome">首页</a>
                </div>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            
            <!-- 
            <a href="javascript:logout()" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
             -->
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="loginController.do?hplushome" frameborder="0" data-id="loginController.do?hplushome" seamless></iframe>
        </div>
        <div class="footer">
            <%-- <div class="pull-right">&copy; <t:mutiLang langKey="system.version.number"/> <a href="http://www.jeecg.org/" target="_blank">jeecg</a>
            </div> --%>
            <div class="pull-right">&copy; 点单么美食平台</a>
            </div>
        </div>
    </div>
    <!--右侧部分结束-->
    <!--右侧边栏开始-->
    <div id="right-sidebar">
        <div class="sidebar-container">

            <ul class="nav nav-tabs navs-3">

                <li class="">
                    <a data-toggle="tab" href="#tab-1">
                        <i class="fa fa-gear" aria-hidden="true"></i> 主题
                    </a>
                </li>
               <li class="active"><a data-toggle="tab" href="#tab-2">
                   <i class="fa fa-comments-o" ></i>通知
                </a>
                </li>
                <li><a data-toggle="tab" href="#tab-3">
                    <i class="fa fa-info-circle" aria-hidden="true"></i>公告
                </a>
                </li>
            </ul>

            <div class="tab-content">
                <div id="tab-1" class="tab-pane">
                    <div class="sidebar-title">
                        <h3> <i class="fa fa-comments-o"></i> 主题设置</h3>
                        <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
                    </div>
                    <div class="skin-setttings">
                        <div class="title">主题设置</div>
                        <div class="setings-item">
                            <span>收起左侧菜单</span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
                                    <label class="onoffswitch-label" for="collapsemenu">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                            <span>固定顶部</span>

                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar">
                                    <label class="onoffswitch-label" for="fixednavbar">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                                <span>
                        固定宽度
                    </span>

                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout">
                                    <label class="onoffswitch-label" for="boxedlayout">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="title">皮肤选择</div>
                        <div class="setings-item default-skin nb">
                                <span class="skin-name ">
                         <a href="#" class="s-skin-0">
                             默认皮肤
                         </a>
                    </span>
                        </div>
                        <div class="setings-item blue-skin nb">
                                <span class="skin-name ">
                        <a href="#" class="s-skin-1">
                            蓝色主题
                        </a>
                    </span>
                        </div>
                        <div class="setings-item yellow-skin nb">
                                <span class="skin-name ">
                        <a href="#" class="s-skin-3">
                            黄色/紫色主题
                        </a>
                    </span>
                        </div>
                    </div>
                </div>
                <div id="tab-2" class="tab-pane active">
                    <div class="sidebar-title">
                        <h3> <i class="fa fa-comments-o"></i> 通知<small id="messageCount"><i class="fa fa-tim"></i> 您当前有0条未读通知</small></h3>
                    </div>
                    <ul class="sidebar-list">
                        <li id="noreadmessageContent">
	                   
                        </li>

                        <li>
                            <a href="javascript:goAllMessage();" id="messageFooter">
                                查看全部
                                <i class="icon-arrow-right"></i>
                            </a>
                        </li>
                    </ul>
                </div>
                <div id="tab-3" class="tab-pane">
                    <div class="sidebar-title">
                        <h3> <i class="fa fa-cube"></i> 最新公告<small id="noticeCount"><i class="fa fa-tim"></i> 您当前有1个公告</small></h3>

                    </div>
                    <ul class="sidebar-list">
                        <li id="noticeContent">

                        </li>
                        <li>
                            <a href="javascript:goAllNotice();" id="noticeFooter">
                                查看所有公告
                                <i class="icon-arrow-right"></i>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

        </div>
    </div>
    <!--右侧边栏结束-->
    <!--mini聊天窗口开始-->
    <%--<div class="small-chat-box fadeInRight animated">

        <div class="heading" draggable="true">
            <small class="chat-date pull-right">
                2015.9.1
            </small> 与 Beau-zihan 聊天中
        </div>

        <div class="content">

            <div class="left">
                <div class="author-name">
                    Beau-zihan <small class="chat-date">
                    10:02
                </small>
                </div>
                <div class="chat-message active">
                    你好
                </div>

            </div>
            <div class="right">
                <div class="author-name">
                    游客
                    <small class="chat-date">
                        11:24
                    </small>
                </div>
                <div class="chat-message">
                    你好，请问H+有帮助文档吗？
                </div>
            </div>
            <div class="left">
                <div class="author-name">
                    Beau-zihan
                    <small class="chat-date">
                        08:45
                    </small>
                </div>
                <div class="chat-message active">
                    有，购买的H+源码包中有帮助文档，位于docs文件夹下
                </div>
            </div>
            <div class="right">
                <div class="author-name">
                    游客
                    <small class="chat-date">
                        11:24
                    </small>
                </div>
                <div class="chat-message">
                    那除了帮助文档还提供什么样的服务？
                </div>
            </div>
            <div class="left">
                <div class="author-name">
                    Beau-zihan
                    <small class="chat-date">
                        08:45
                    </small>
                </div>
                <div class="chat-message active">
                    1.所有源码(未压缩、带注释版本)；
                    <br> 2.说明文档；
                    <br> 3.终身免费升级服务；
                    <br> 4.必要的技术支持；
                    <br> 5.付费二次开发服务；
                    <br> 6.授权许可；
                    <br> ……
                    <br>
                </div>
            </div>


        </div>
        <div class="form-chat">
            <div class="input-group input-group-sm">
                <input type="text" class="form-control"> <span class="input-group-btn"> <button
                    class="btn btn-primary" type="button">发送
            </button> </span>
            </div>
        </div>

    </div>--%>
    <%--<div id="small-chat">
        <span class="badge badge-warning pull-right">5</span>
        <a class="open-small-chat">
            <i class="fa fa-comments"></i>

        </a>
    </div>--%>
    <!--mini聊天窗口结束-->
</div>

<!-- 全局js -->
<script src="plug-in-ui/hplus/js/jquery.min.js?v=2.1.4"></script>
<script src="plug-in-ui/hplus/js/bootstrap.min.js?v=3.3.6"></script>
<script src="plug-in-ui/hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="plug-in-ui/hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>

<!-- 自定义js -->
<script src="plug-in-ui/hplus/js/hplus.js?v=4.1.0"></script>
<!--右键菜单-->
<script type="text/javascript" src="plug-in/hplus/jquery-smartMenu.js"></script>
<script type="text/javascript" src="plug-in/hplus/contabs.js"></script>
<t:base type="tools"></t:base>
<!-- 第三方插件 -->
<script src="plug-in-ui/hplus/js/plugins/pace/pace.min.js"></script>
<!-- Sweet alert -->
<script src="plug-in-ui/hplus/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="plug-in/jquery-plugs/storage/jquery.storageapi.min.js"></script>

<!-- 弹出TAB -->
<script type="text/javascript" src="plug-in/hplus/hplus-tab.js"></script>
<!-- 在线聊天 -->
<%-- <%@include file="/context/layui.jsp"%> --%>
<script>
var t2;
var t3;
var t4;
    function logout(){
        /*bootbox.confirm("<t:mutiLang langKey="common.exit.confirm"/>", function(result) {
            if(result)
                location.href="loginController.do?logout";
        });*/
        /*swal({
            title: "您确定要注销吗？",
            text: "注销后需要重新登录！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            closeOnConfirm: false,
            closeOnCancel: true
        },
        function (isConfirm) {
            if (isConfirm) {
                //swal("注销成功！", "您已经成功注销。", "success");
                location.href="loginController.do?logout";
            } else {
                return false;
            }
        });*/
        layer.confirm('您确定要注销吗？', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(){
            location.href="loginController.do?logout";
        }, function(){
            return;
        });
    }
    function clearLocalstorage(){
        var storage=$.localStorage;
        if(!storage)
            storage=$.cookieStorage;
        storage.removeAll();
        //bootbox.alert( "浏览器缓存清除成功!");
        layer.msg("浏览器缓存清除成功!");
    }
    function toJeecgYun(){
    	window.open("http://yun.jeecg.org","_blank");
    }

/*     $(document).ready(function(){
        //加载公告
        var url = "noticeController.do?getNoticeList";
        jQuery.ajax({
            url:url,
            type:"GET",
            dataType:"JSON",
            async: false,
            success:function(data){
                if(data.success){
                    var noticeList = data.attributes.noticeList;
                    var noticeCount = data.obj;
                    //加载公告条数
                    if(noticeCount>99){
                        $("#noticeCount").html("99+");
                    }else{
                        $("#noticeCount").html(noticeCount);
                    }
                    //加载公告提示
                    var noticeTip = "";
                    noticeTip += "<i class='icon-warning-sign'></i>";
                    noticeTip += noticeCount+" "+data.attributes.tip;
                    $("#noticeTip").html(noticeTip);

                    //加载公告条目
                    var noticeContent = "";
                    if(noticeList.length > 0){
                        for(var i=0;i<noticeList.length;i++){
                            noticeContent +="<li><a href='javascript:goNotice(&quot;"+noticeList[i].id+"&quot;)' ";
                            noticeContent +="style='word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>";
                            noticeContent +="<i class='btn btn-xs btn-primary fa fa-user'></i>";
                            noticeContent +="&nbsp;"+noticeList[i].noticeTitle + "</a></li></ul></li>";
                        }
                    }
                    //alert(noticeContent);
                    $("#noticeContent").html(noticeContent);

                    //加载公告底部文字
                    var noticeSeeAll = data.attributes.seeAll +"<i class='ace-icon fa fa-arrow-right'></i>";
                    $("#noticeFooter").html(noticeSeeAll);
                }
            }
        }); */


/*         //加载消息
        var url = "tSSmsController.do?getMessageList";
        $.ajax({
            url:url,
            type:"GET",
            dataType:"JSON",
            async: false,
            success:function(data){
                if(data.success){
                    var messageList = data.attributes.messageList;
                    var messageCount = data.obj;
                    //加载消息条数
                    if(messageCount>99){
                        $("#messageCount").html("99+");
                    }else{
                        $("#messageCount").html(messageCount);
                    }
                    //加载消息tip提示
                    var messageTip = "";
                    messageTip += "<i class='ace-icon fa fa-envelope-o'></i>";
                    messageTip += messageCount+" "+data.attributes.tip;
                    $("#messageTip").html(messageTip);

                    //加载消息条目（有限）
                    var messageContent = "";
                    if(messageList.length > 0){
                        for(var i=0;i<messageList.length;i++){
                            messageContent +="<li><a href='javascript:goMessage(&quot;"+messageList[i].id+"&quot;)' class='clearfix'>";
                            messageContent +="<img src='plug-in/ace/avatars/avatar3.png' class='msg-photo' alt='Alex’s Avatar' />";
                            messageContent +="<span class='msg-body'><span class='msg-title'>";
                            messageContent +="<span class='blue'>"+messageList[i].esSender+":</span>";
                            messageContent += messageList[i].esTitle + "</span>";
                            messageContent +="<span class='msg-time'><i class='ace-icon fa fa-clock-o'></i><span>"+messageList[i].esSendtimeTxt+"</span></span>";
                            messageContent +="</span></a><input id='"+messageList[i].id+"_title' type='hidden' value='"+messageList[i].esTitle+"'>";
                            messageContent +="<input id='"+messageList[i].id+"_status' type='hidden' value='"+messageList[i].esStatus+"'>";
                            messageContent +="<input id='"+messageList[i].id+"_content' type='hidden' value='"+messageList[i].esContent+"'></li>";
                        }
                    }
                    $("#messageContent").html(messageContent);

                    //加载消息底部文字
                    var messageSeeAll = data.attributes.seeAll +"<i class='ace-icon fa fa-arrow-right'></i>";
                    $("#messageFooter").html(messageSeeAll);
                }
            }
        });

    }); */

    function goAllNotice(){
        var addurl = "noticeController.do?noticeList";
        createdetailwindow("公告", addurl, 800, 400);
    }
	//订单信息
    function goAllSubOrder(){
		
    	 var serviceIndex = $("#orderCountIndex").html(0);
    	 var serviceCount = $("#orderCount").html(0);
    	 window.clearInterval(t2);
        //var url = "tcsbOrderController.do?listAccept";
        var url = "tcsbSubOrderController.do?listAccept";
        createdetailwindow("订单消息通知", url, 850, 500);
    }
    //用户服务信息
    function goAllUserService(){
    	
		 var serviceIndex = $("#serviceIndex").html(0);
    	 var serviceCount = $("#serviceCount").html(0);
    	 window.clearInterval(t3);
        var url = "tcsbCallServiceController.do?NotReadList";
        createdetailwindow("用户服务消息通知", url, 680, 500);
    }
    
    //支付消息通知
    function goAllpayNotice(){
    	var serviceIndex = $("#payNoticeIndex").html(0);
   	 	var serviceCount = $("#payNoticeCount").html(0);
   	 	window.clearInterval(t4);
       //var url = "tcsbOrderController.do?listAccept";
        var url = "tcsbPayNoticeController.do?NotReadList";
        createdetailwindow("订单消息通知", url, 850, 500);
    }
    
    function goNotice(id){
        var addurl = "noticeController.do?goNotice&id="+id;
        createdetailwindow("通知公告详情", addurl, 750, 600);
    }

    function goAllMessage(){
        var addurl = "tSSmsController.do?getSysInfos";
        createdetailwindow("通知", addurl, 800, 400);
    }

    function goMessage(id){
        var title = $("#"+id+"_title").val();
        var content = $("#"+id+"_content").val();
        $("#msgId").val(id);
        $("#msgTitle").html(title);
        $("#msgContent").html(content);
        var status = $("#"+id+"_status").val();
        if(status==1){
            $("#msgStatus").html("未读");
        }else{
            $("#msgStatus").html("已读");
        }

        $('.theme-popover-mask').fadeIn(100);
        $('.theme-popover').slideDown(200);
    }

    function readMessage(){
        var msgId = $("#msgId").val();
        var url = "tSSmsController.do?readMessage";
        $.ajax({
            url:url,
            type:"GET",
            dataType:"JSON",
            data:{
                messageId:msgId
            },
            success:function(data){
                if(data.success){
                    $("#msgStatus").html("已读");
                    $("#"+msgId+"_status").val('2');
                }
            }
        });
    }
</script>

<audio id="messageAudio" src="<%=basePath %>/voice/diandangmamsg.mp3" controls="controls" style="display:none;"></audio>
<audio id="orderAudio" src="<%=basePath %>/voice/diandanmaorder.mp3" controls="controls" style="display:none;"></audio>

<script type="text/javascript">

/* function getLocalTime(nS) {     
	   return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');     
	}   */

	function audioplaymsg(){
		 var audio = $("#messageAudio")[0];
		    audio.play();
	}
	

	function audioplayorder(){
		 var audio = $("#orderAudio")[0];
		    audio.play();
	}
	
	function isread(id){
		var url = "tcsbCallServiceController.do?isread";
		$.post(
					url,
					{ID:id},
					function(data){
						getMessage();
					}
		);
	}
	
	function getMessage(){
        var url = "tcsbCallServiceController.do?getcallMessage";
        $.post(
        	url,
        	function(data){
        		if(data != null){
        			var isplay = 0;
        			data = jQuery.parseJSON(data);
        			for(var i=0;i<data.length;i++ ){
        				if(data[i].isplay == 1){
        					isplay = 1;
        				}
        			} 
        			if(isplay == 1){
        				audioplaymsg();
        			}
        			$("#serviceIndex").html(data.length);
        			$("#serviceCount").html(data.length);
        		}
        	}
        );
	}

	function getSubOrder(){
        var url = "tcsbSubOrderController.do?getNotReadOrder";
        $.post(
        	url,
        	function(data){
        		if(data != null){
        			data = jQuery.parseJSON(data);
        			if(data.success){
        				if(data.obj!=0){
        					for(i=0;i<data.obj;i++){
        						audioplayorder();
        					}
        				}
        				$("#orderCountIndex").html(data.obj);
            			$("#orderCount").html(data.obj);
        			}
        			
        		}
        	}
        );
	}
	function Initload(){
		var userrole = $("#userrole").val();
		if(userrole == "管理员"){
			$("#messageIco").hide();
			$("#orderIco").hide();
			$("#paymessageIco").hide();
		}else{
			//var t1 = window.setInterval("getMessage()",10000);
			//var t2 = window.setInterval("getSubOrder()",10000); 
		}
	}
	//window.setTimeout("getMessage()",1000);//使用字符串执行方法 
	//window.setTimeout("getSubOrder()",1000);//使用字符串执行方法 
	Initload();
	
</script>
	<script type="text/javascript">
		//请求一次，获取当前的店铺ID
		$(document).ready(function() {
			
			var userrole = $("#userrole").val();
			if(userrole == "管理员"){
				
			}else{
				var url = "tcsbCallServiceController.do?getCallShopId";
				$.post(
						url,
						function(data) {
						$("#shopId").val(data.replace("\"","").replace("\"",""));
				})
			}
			
			
			
		});
	</script>

	<script type="text/JavaScript">
      var websocket = null;
       
      //判断当前浏览器是否支持WebSocket
      if('WebSocket' in window){
          websocket = new WebSocket("ws://www.diandanme.xyz/socket/chat");
          //websocket = new WebSocket("ws://www.xmxxit.cn/socket/chat");
    	  //websocket = new WebSocket("ws://localhost:8080/socket/chat");
      }
      else{
          alert('Not support websocket');
      }
       
      //连接发生错误的回调方法
      websocket.onerror = function(){
          setMessageInnerHTML("error");
      };
       
      //连接成功建立的回调方法
      websocket.onopen = function(event){
          setMessageInnerHTML("open");
      };
       
      //接收到消息的回调方法
      websocket.onmessage = function(){
          setMessageInnerHTML(event.data);
      };
       
      //连接关闭的回调方法
      websocket.onclose = function(){
          setMessageInnerHTML("close");
      };
       
      //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      window.onbeforeunload = function(){
          websocket.close();
      };
       
      //将消息显示在网页上
      function setMessageInnerHTML(innerHTML){
    	  var data = jQuery.parseJSON(innerHTML);
    	  var shopid = $("#shopId").val();
    	  if(data.shopid == shopid){
    		  if(data.state==1){
    	    		 var serviceIndex = $("#orderCountIndex").html();
    	        	 var serviceCount = $("#orderCount").html();
    	        	 $("#orderCountIndex").html(serviceIndex*1+1);
    	             $("#orderCount").html(serviceCount*1+1);
    	             var subOrderId = data.subOrderNo; 
    	             //todo调用打印系统
    	             var url = "tcsbSubOrderController.do?getPrintSubOrder";
			        $.ajax({
			            url:url,
			            type:"GET",
			            dataType:"JSON",
			            data:{
			                id:subOrderId
			            },
			            success:function(data){
			                if(data.success){
					                var j =	data.obj;
					            	var shopName = j.shopName;
					            	var deskNo = j.deskName;
					            	var orderNo = j.orderNo;
					            	var startT = j.createTime; 
					            	var isJustNowServing = j.isJustNowServing;
					            	//遍历所有打印机 
					        		for(var printer in j.tcsbPrinterEntities){
					        			LODOP=getLodop();  
						        		LODOP.PRINT_INIT("自动打印下单发票");
						        		
						        		LODOP.ADD_PRINT_TEXT(5,15,355,30,"桌号:"+shopName);
						        		LODOP.SET_PRINT_STYLEA(1,"FontSize",16);
						        		LODOP.SET_PRINT_STYLEA(1,"Bold",1);
						        		
						        		LODOP.ADD_PRINT_LINE(30,14,30,510,0,1);
						        		LODOP.ADD_PRINT_TEXT(55,15,250,20,"桌号:"+deskNo);
						        		LODOP.SET_PRINT_STYLEA(3,"FontSize",13);
						        		LODOP.SET_PRINT_STYLEA(3,"Bold",1);
						        		LODOP.ADD_PRINT_TEXT(80,15,250,20,"是否上菜:"+isJustNowServing);
						        		LODOP.SET_PRINT_STYLEA(4,"FontSize",13);
						        		LODOP.SET_PRINT_STYLEA(4,"Bold",1);
						        		LODOP.ADD_PRINT_TEXT(105,15,250,20,"订单号:"+orderNo);
						        		LODOP.SET_PRINT_STYLEA(5,"FontSize",10);
						        		LODOP.SET_PRINT_STYLEA(5,"Bold",1);
						        		LODOP.ADD_PRINT_TEXT(130,15,250,20,"下单时间:"+startT); 
						        		LODOP.SET_PRINT_STYLEA(6,"FontSize",10);
						        		LODOP.SET_PRINT_STYLEA(6,"Bold",1);
						        		LODOP.ADD_PRINT_LINE(155,14,155,510,0,1);
						        		
						        		LODOP.ADD_PRINT_TEXT(180,15,100,20,"菜单名称");
						        		LODOP.SET_PRINT_STYLEA(8,"FontSize",13);
						        		LODOP.SET_PRINT_STYLEA(8,"Bold",1);
						        		LODOP.ADD_PRINT_TEXT(180,130,100,20,"数量");
						        		LODOP.SET_PRINT_STYLEA(9,"FontSize",13);
						        		LODOP.SET_PRINT_STYLEA(9,"Bold",1);
						        		LODOP.ADD_PRINT_TEXT(180,170,100,20,"单位");
						        		LODOP.SET_PRINT_STYLEA(10,"FontSize",13);
						        		LODOP.SET_PRINT_STYLEA(10,"Bold",1);
						        		//获取该打印机所要打印的食物
						        		var curLine = 205;
						        		var styleIndex = 11;
						        		for( var t in j.tcsbPrinterEntities[printer].tcsbOrderItemPrintVOs){
							        		LODOP.ADD_PRINT_TEXT(curLine,15,100,20,j.tcsbPrinterEntities[printer].tcsbOrderItemPrintVOs[t].foodName);
							        		LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",13);       
							        		LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
							        		styleIndex = styleIndex +1;
							        		LODOP.ADD_PRINT_TEXT(curLine,130,100,20,j.tcsbPrinterEntities[printer].tcsbOrderItemPrintVOs[t].count);
							        		LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",13);
							        		LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
							        		styleIndex = styleIndex +1;
							        		LODOP.ADD_PRINT_TEXT(curLine,170,100,20,j.tcsbPrinterEntities[printer].tcsbOrderItemPrintVOs[t].unitName);
							        		LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",13);
							        		LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
							        		styleIndex = styleIndex +1;
							        		if(j.tcsbPrinterEntities[printer].tcsbOrderItemPrintVOs[t].funFoodTaste!=undefined){
							        			LODOP.ADD_PRINT_TEXT(curLine+25,15,100,20,j.tcsbPrinterEntities[printer].tcsbOrderItemPrintVOs[t].funFoodTaste);
								        		LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",13);
								        		LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
								        		styleIndex = styleIndex +1;
							        		}
							        		curLine = curLine +25;
						        		}
						        		LODOP.SET_PRINT_PAGESIZE(3,1385,45,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽1385mm；45表示页底空白4.5mm
						        		LODOP.SET_PRINTER_INDEX(j.tcsbPrinterEntities[printer].printIndex);
						        		LODOP.PRINT();
						        		//打印子单
						        		//主单和子单要分开来打印避免浪费纸的情况（分别创建对象来独立打印）
										LODOPchild=getLodop(); 
										LODOPchild.PRINT_INIT("订单打印子发票");
										styleIndex = 1;
					        			if(j.tcsbPrinterEntities[printer].printChild==1){
					        				for(var y in j.tcsbPrinterEntities[printer].tcsbOrderItemPrintVOs){
					        					LODOPchild.NewPage();
					        					/* LODOP.ADD_PRINT_TEXT(15,15,355,30,shopName);
					        					LODOP.SET_PRINT_STYLEA(1,"FontSize",13);
					        					LODOP.SET_PRINT_STYLEA(1,"Bold",1);
					        					
					        					LODOP.ADD_PRINT_LINE(40,14,40,510,0,1); */
					        					//styleIndex=styleIndex+2;
					        					LODOPchild.ADD_PRINT_TEXT(5,15,250,20,"桌号:"+deskNo);
					        					LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",12);
					        					LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					        					styleIndex=styleIndex+1;
					        					//LODOPchild.ADD_PRINT_TEXT(90,15,250,20,orderNo);
					        					//LODOPchild.ADD_PRINT_TEXT(115,15,250,20,startT); 
					        					LODOPchild.ADD_PRINT_TEXT(40,15,100,20,"菜单名称");
					        					LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
					        					LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					        					styleIndex=styleIndex+1;
					        					LODOPchild.ADD_PRINT_TEXT(40,100,100,20,"数量");
					        					LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",10);  
					        					LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					        					styleIndex=styleIndex+1;
					        					LODOPchild.ADD_PRINT_TEXT(40,150,100,20,"金额");
					        					LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
					        					LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					        					styleIndex=styleIndex+1;
					        					LODOPchild.ADD_PRINT_TEXT(65,0,100,20,j.tcsbPrinterEntities[printer].tcsbOrderItemPrintVOs[y].foodName);
					        					LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
					        					LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					        					styleIndex=styleIndex+1;
					        					LODOPchild.ADD_PRINT_TEXT(65,100,100,20,j.tcsbPrinterEntities[printer].tcsbOrderItemPrintVOs[y].count);
					        					LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
					        					LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					        					styleIndex=styleIndex+1;
					        					 LODOPchild.ADD_PRINT_TEXT(65,150,100,20,j.tcsbPrinterEntities[printer].tcsbOrderItemPrintVOs[y].price);
					        					LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
					        					LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					        					styleIndex=styleIndex+1; 
					        					//var iCurLine=140;//标题行之后的数据从位置165px开始打印
					        					/* if($(this).find("#GG"+i).val()!=undefined){
					        						LODOPchild.ADD_PRINT_TEXT(90,0,100,20,$(this).find("#GG"+i).val());
					        						styleIndex=styleIndex+1;
					        						iCurLine=iCurLine+25+25;//每行占25px 
					        					}
					        					if($(this).find("#GG"+i).val()==undefined){
					        						iCurLine=iCurLine+25;//每行占25px 
					        					}  */
					        				}
					        			}
					        			LODOPchild.SET_PRINT_PAGESIZE(3,1385,45,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽1385mm；45表示页底空白4.5mm
					        			LODOPchild.SET_PRINTER_INDEX(j.tcsbPrinterEntities[printer].printIndex);
					        			LODOPchild.PRINT();
					        			
					        		}
					        		
					            	
					        		 //更新为已接单状态
					                var url = "tcsbSubOrderController.do?receiveOrder";
					                $.ajax({
					                    url:url,
					                    type:"GET",
					                    dataType:"JSON",
					                    data:{
					                        id:subOrderId
					                    },
					                    success:function(data){
					                    	//更新消息
					                    }
					                });
			                }
			               
			            }
			        });
    	          /*   LODOP=getLodop();  
   					LODOP.PRINT_INIT("自动打印发票");
   					LODOP.ADD_PRINT_TEXT(0,100,355,30,"退菜");
   					LODOP.SET_PRINT_STYLEA(1,"FontSize",13);
   					LODOP.SET_PRINT_STYLEA(1,"Bold",1);
   					LODOP.ADD_PRINT_LINE(25,14,25,510,0,1);
   					/* LODOP.ADD_PRINT_TEXT(50,15,355,30,deskName);
   					LODOP.SET_PRINT_STYLEA(3,"FontSize",13);
   					LODOP.SET_PRINT_STYLEA(3,"Bold",1); 
   					LODOP.ADD_PRINT_LINE(75,14,75,510,0,1);
   					LODOP.ADD_PRINT_TEXT(100,0,100,100,"名称");
   					LODOP.SET_PRINT_STYLEA(5,"FontSize",13);
   					LODOP.SET_PRINT_STYLEA(5,"Bold",1);
   					LODOP.ADD_PRINT_TEXT(100,100,100,100,"数量");
   					LODOP.SET_PRINT_STYLEA(6,"FontSize",13);
   					LODOP.SET_PRINT_STYLEA(6,"Bold",1); 
   					
   				 	LODOP.ADD_PRINT_TEXT(125,0,100,100,foodName);
   					LODOP.SET_PRINT_STYLEA(7,"FontSize",13);
   					LODOP.SET_PRINT_STYLEA(7,"Bold",1);
   					LODOP.ADD_PRINT_TEXT(125,100,100,100,value);
   					LODOP.SET_PRINT_STYLEA(8,"FontSize",13);
   					LODOP.SET_PRINT_STYLEA(8,"Bold",1); 
   					LODOP.SET_PRINT_PAGESIZE(3,1385,25,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽1385mm；25表示页底空白2.5mm
   					if (LODOP.SET_PRINTER_INDEX(0))
   					LODOP.PRINT(); */  
    	             audioplaymsg();
    	             t2 = window.setInterval("audioplaymsg()",10000); 
    	    	 }else{
    	    		 if(data.state==2){
    	    			 var serviceIndex = $("#serviceIndex").html();
    	    	    	 var serviceCount = $("#serviceCount").html();
    	    	    	 $("#serviceIndex").html(serviceIndex*1+1);
    	    	         $("#serviceCount").html(serviceCount*1+1);
    	    	         audioplaymsg();
    	    	         t3 = window.setInterval("audioplaymsg()",10000); 
    	    		 }else{
    	    			 if(data.state==3){
    	    				 var payNoticeIndex = $("#payNoticeIndex").html();
        	    	    	 var payNoticeCount = $("#payNoticeCount").html();
        	    	    	 $("#payNoticeIndex").html(payNoticeIndex*1+1);
        	    	         $("#payNoticeCount").html(payNoticeCount*1+1);
        	    	         audioplaymsg();
        	    	         t4 = window.setInterval("audioplaymsg()",10000); 
    	    			 }
    	    		 }
    	    	 }
    	  }
    	  /*
    	 	 接收的消息串格式
    	  	[ { "shopid": "8a9ad8035c0c5381015c0c7461db0023", "state":"1"}]
    	  */
    	  
    	 // alert(innerHTML);
    	
    	 
    	 
    	
    	 
         // document.getElementById('message').innerHTML += innerHTML + '<br/>';
      }
       
      //关闭连接
      function closeWebSocket(){
          websocket.close();
      }
       
      //发送消息
      function send(){
          var message = document.getElementById('text').value;
          websocket.send(message);
      }
  </script>


</body>

</html>
