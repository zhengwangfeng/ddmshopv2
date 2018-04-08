<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!--360浏览器优先以webkit内核解析-->


    <title>点单么美食平台</title>

    <link rel="shortcut icon" href="images/favicon.ico">
    <link href="plug-in-ui/hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="plug-in-ui/hplus/css/animate.css" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/style.css?v=4.1.0" rel="stylesheet">
<script src="plug-in-ui/hplus/js/jquery.min.js?v=2.1.4"></script>
<style>

.incomediv{
	width: 200px;
	height: 50px;
	float: left;
	line-height: 50px;
	padding-left: 5px;
}

.incomediv:hover{
	border: 1px solid #61af58;
	background-color: #9fe084;
	font-size:16px;
	color: black;
}

</style>
</head>

<body class="gray-bg">
<input type="hidden" id="shopshow" value="${shopshow }">
<div class="row  border-bottom white-bg dashboard-header">
    <!-- <div class="col-sm-12">
        <blockquote class="text-warning" style="font-size:14px">您是否需要一款企业级J2EE快速开发平台，提高开发效率，缩短项目周期…
            <br>您是否一直在苦苦寻找一款强大的代码生成器，节省码农的繁琐重复工作…
            <br>您是否想拥有移动报表能力、自定义表单设计能力、插件开发能力(可插拔)、工作流配置能力…
            <br>…………
            <h4 class="text-danger">那么，现在Jeecg来了</h4>
        </blockquote>
        <hr>
    </div> -->
<!--     <div class="col-sm-3">
        <h2>Hello,Guest</h2>
        <small>移动设备访问请扫描以下二维码：</small>
        <br>
        <br>
        <img src="plug-in/login/images/jeecg.jpg" width="100%" style="max-width:264px;">
        <img src="#" width="100%" style="max-width:264px;">
        <br>
    </div> -->
    <!-- <div class="col-sm-5">
        <h2>
            Jeecg 微云快速开发平台
        </h2>
        <p>JEECG是一款基于代码生成器的J2EE快速开发平台，开源界“小普元”超越传统商业企业级开发平台。引领新的开发模式(Online Coding模式(自定义表单)->代码生成器模式->手工MERGE智能开发)， 可以帮助解决Java项目60%的重复工作，让开发更多关注业务逻辑。既能快速提高开发效率，帮助公司节省人力成本，同时又不失灵活性。她可以用于所有的Web应用程序，如:<b>MIS</b>，<b>CRM</b>，<b>OA</b>，<b>ERP</b>，<b>CMS</b>，<b>网站后台</b>，<b>微信管家</b>，等等，当然，您也可以对她进行深度定制，以做出更强系统。</p>
        <p>
            <b>当前版本：</b>v3.6.6
        </p>
        <p>
            <span class="label label-warning">开源     &nbsp; | &nbsp; 免费  | &nbsp; 更多插件</span>
        </p>
        <br>
        <p>
        	<a class="btn btn-success btn-outline" href="http://yun.jeecg.org" target="_blank">
                <i class="fa fa-cloud"></i> 云应用中心
            </a>
            <a class="btn btn-white btn-bitbucket" href="http://www.jeecg.org/" target="_blank">
                <i class="fa fa-qq"> </i> 联系我们
            </a>
            <a class="btn btn-white btn-bitbucket" href="http://blog.csdn.net/zhangdaiscott" target="_blank">
                <i class="fa fa-home"></i> 官方博客
            </a>
        </p>
    </div> -->
   <!--  <div class="col-sm-4">
        <h4>Jeecg具有以下特点：</h4>
        <ol>
            <li>采用主流J2EE框架，容易上手;</li>
            <li>强大的代码生成器，一键生成</li>
            <li>提供5套不同风格首页</li>
            <li>开发效率很高，节省80%重复工作</li>
            <li>使用最流行的的扁平化设计</li>
            <li>在线开发能力，通过在线配置实现功能，零代码</li>
            <li>在线报表配置能力，一次配置七种报表风格，支持移动报表</li>
            <li>移动平台支持，采用Bootstrap技术，移动OA，移动报表</li>
            <li>强大数据权限，访问级，按钮级、数据行级，列级，字段级</li>
            <li>国际化能力，支持多语言</li>
            <li>多数据源，跨数据源操作，便捷集成第三方系统</li>
            <li>简易Excel、Word 导入导出,满足企业需求</li>
            <li>插件开发，可插拔开发模式，集成第三方组件</li>
            <li>流程定义，在线画流程，流程挂表单，符合国情流程</li>
            <li>自定义表单，可视化拖拽布局，自定义表单风格</li>
            <li>更多……</li>
        </ol>
    </div> -->

</div>
<div class="wrapper wrapper-content">
    <div class="row">
        <!-- <div class="col-sm-4">

            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>商业支持</h5>
                </div>
                <div class="ibox-content">
                    <p>我们提供基于Jeecg的二次开发服务，具体费用请联系官方。</p>
                    <p>同时，我们也提供以下服务：</p>
                    <ol>
                        <li>Jeecg工作流开发平台 (商业版)</li>
                        <li>Jeewx微信管家 (商业版)</li>
                        <li>OA办公系统 (商业版)</li>
                        <li>专业H5活动开发</li>
                    </ol>
                </div>
            </div> -->
         
             
            
             <div id="incomeibox" class="ibox float-e-margins" style="display: none;">
             <h5 style="margin-left: 30px;">短信剩余条数:<span id="smscount"></span>条</h5>
                <div class="ibox-title">
                    <h5>营业额统计</h5>

                </div>
                <div class="ibox-content">
                 <div class="incomediv"><i class="fa fa-credit-card"></i> 今日新增会员：<span id="todayJoinVip"></span>人
                 </div>
                 <div style="clear: both;"></div>
                    <div class="incomediv"><i class="fa fa-credit-card"></i> 日均营业额：<span id="avgDataIncome"></span>元
                    </div>
                    <div class="incomediv"><i class="fa fa-credit-card"></i> 月均营业额：<span id="monthAvgIncme"></span>元
                    </div>
                    <div class="incomediv"><i class="fa fa-credit-card"></i> 总营业额：<span id="totalIncme"></span>元
                    </div>
                    <div style="clear: both;"></div>
                     <div class="incomediv"><i class="fa fa-credit-card"></i> 今日营业收入：<span id="todayIncome"></span>元
                    </div>
                     <div class="incomediv"><i class="fa fa-credit-card"></i> 线上支付：<span id="onlineIncome"></span>元
                    </div>
                     <div class="incomediv"><i class="fa fa-credit-card"></i> 线下支付：<span id="offlineIncome"></span>元
                    </div>
                </div>
                <div style="clear: both;"></div>
                <script>
	                $(document).ready(function(){
						var shopshow =$("#shopshow").val();				
						if(shopshow == "0"){
							$("#incomeibox").hide();
						}else{
							var url = "tcsbInComeController.do?getInComeStatistic";
							$.post(
								url,
								function(data){
									data = jQuery.parseJSON(data);
									$("#incomeibox").show();
									$("#avgDataIncome").html(data.avgDataIncome);
									$("#monthAvgIncme").html(data.monthAvgIncme);
									$("#totalIncme").html(data.totalIncme);
									$("#todayIncome").html(data.todayIncome);
									$("#onlineIncome").html(data.onlineIncome);
									$("#offlineIncome").html(data.offlineIncome);
									$("#todayJoinVip").html(data.todayJoinVip);
								}
							);
						}
	
					});
                </script>
                
					<script>
						$(document).ready(function(){
							var shopshow =$("#shopshow").val();
							if(shopshow == "0"){
								$("#smscountibox").hide();
							}else{
								var url = "tcsbAssociatorStatisticsController.do?getSmsCount";
								$.post(
									url,
									function(data){
										$("#smscountibox").show();
										$("#smscount").html(data);
									}
								);
							}

						});
					</script>
            </div>
            
      <!--       <div id="smscountibox" class="ibox float-e-margins" style="display: none;">
                <div class="ibox-title">
                    
                </div>
            </div> -->
            
            
            
            
            
            
       <!--  </div> -->
         <div class="col-sm-4">
            <div class="ibox float-e-margins" >
                <div class="ibox-title">
                    <h5>公告</h5>
                </div>
                <div class="ibox-content no-padding">
                    <div class="panel-body">
                        <div class="panel-group" id="version">
                        
                        <c:forEach var="notice" items="${tSNoticeList }">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h5 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#version" href="#v50">${notice.noticeTitle }</a><code class="pull-right"><fmt:formatDate value="${notice.createTime }" pattern="yyyy-MM-dd"/> </code>
                                    </h5>
                                </div>   
                        	</div>
                        </c:forEach>
                        
                     
                        
                          
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h5 class="panel-title">
                                    
                                       <a class="" href="javascript:goAllNotice();">
                                        <i class="fa fa-envelope"></i> <strong> 更多...</strong>
                                    </a>
                                    
                                    
                                       <%--  <a data-toggle="collapse" data-parent="#version" href="javascript:goAllNotice();">更多...</a><code class="pull-right"></code> --%>
                                    </h5>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
               
        </div>
        
        <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>联系信息</h5>

                </div>
                <div class="ibox-content">
                    <p><i class="fa fa-send-o"></i> 官网：<a href="http://www.diandanme.xyz/shop" target="_blank">www.diandanme.com</a>
                    </p>
                   
                    <p><i class="fa fa-weixin"></i>微信公众号：<a href="javascript:;">微信搜索”点单么“公众号</a>
                    </p>
                    <p><i class="fa fa-credit-card"></i> 邮箱：<a href="javascript:;" class="邮箱">support@diandanme.com</a>
                    </p>
                </div>
            </div>
       <!--  <div class="col-sm-4">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>JEECG 适用范围</h5>
                </div>
                <div class="ibox-content">
                    <p>为什么选择jeecg？</p>
                    <ol>
                        <li>采用主流框架，容易上手，学习成本低；</li>
                        <li>强大代码生成器和在线配置能力，提高开发效率，大大缩短项目周期；</li>
                        <li>开源免费，万人活跃社区；</li>
                        <li>封装基础用户权限，报表等功能模块，无需再次开发；</li>
                        <li>采用SpringMVC + Hibernate + Minidao+ Easyui+Jquery+Boostrap等基础架构</li>
                        <li>支持多浏览器，多数据库；</li>
                        <li>支持移动开发，可以完美兼容电脑、手机、pad等多平台；</li>
                        <li>……</li>
                    </ol>
                    <hr>
                    <div class="alert alert-warning">JEECG智能开发平台，可以应用在任何J2EE项目的开发中，尤其适合企业信息管理系统（MIS）、内部办公系统（OA）、企业资源计划系统（ERP） 、客户关系管理系统（CRM）等，其半智能手工Merge的开发方式，可以显著提高开发效率60%以上，极大降低开发成本。
                    </div>
                </div>
            </div>
        </div> -->
    </div>
</div>

<!-- 全局js -->
<script src="plug-in-ui/hplus/js/jquery.min.js?v=2.1.4"></script>
<script src="plug-in-ui/hplus/js/bootstrap.min.js?v=3.3.6"></script>
<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>

<script src="plug-in-ui/hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="plug-in-ui/hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>


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


<!-- 自定义js -->
<script src="plug-in-ui/hplus/js/content.js"></script>
<script type="text/javascript">

function goAllNotice(){
    var addurl = "noticeController.do?noticeList";
    createdetailwindow("公告", addurl, 800, 400);
}


</script>
</body>

</html>
