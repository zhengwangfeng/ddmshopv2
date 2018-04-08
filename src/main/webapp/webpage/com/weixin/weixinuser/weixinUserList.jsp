<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style>
.addbtnstyleclass {
	height: 21px;
	line-height: 21px;
	padding: 0 11px;
	background: #4ba8f7;
	border: 1px #26bbdb solid;
	border-radius: 3px;
	display: inline-block;
	text-decoration: none;
	font-size: 12px;
	outline: none;
	color: white;
}

.delbtnstyleclass {
	height: 21px;
	line-height: 21px;
	padding: 0 11px;
	background: #e60012;
	border: 1px #26bbdb solid;
	border-radius: 3px;
	display: inline-block;
	text-decoration: none;
	font-size: 12px;
	outline: none;
	color: white;
}
</style>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="weixinUserList" checkbox="true" fitColumns="false"
			title="微信用户" actionUrl="weixinUserController.do?datagrid"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<%--<t:dgCol title="userId"  field="userId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
			<t:dgCol title="用户的唯一标识" field="openid" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="用户手机号" align="center" field="mobile"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="用户昵称" align="center" field="nickname"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="备注" align="center" field="note" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="用户性别" align="center" field="sex"
				replace="男_1,女_2,保密_0,保密_3" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="省份" align="center" field="province" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="城市" align="center" field="city" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="国家" align="center" field="country" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="用户头像" align="center" field="headimgurl" image="true"
				imageSize="90,50"></t:dgCol>
			<t:dgCol title="用户特权信息" align="center" field="privilege"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="入驻时间" align="center" field="createTime"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="商家ID" align="center" field="shopId" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" align="center" field="opt" width="100"></t:dgCol>
			<%--  <t:dgDelOpt title="删除" url="weixinUserController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="weixinUserController.do?goAdd" funname="add"></t:dgToolBar>--%>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="weixinUserController.do?goUpdate" funname="update"></t:dgToolBar>
			<%-- <t:dgToolBar title="批量删除"  icon="icon-remove" url="weixinUserController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
			<t:dgFunOpt funname="sendCoupon(id)" title="发放优惠券"
				urlclass="addbtnstyleclass"></t:dgFunOpt>
			<t:dgToolBar title="查看" icon="icon-search"
				url="weixinUserController.do?goUpdate" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="批量发送优惠券" icon="icon-add"
				url="tcsbCouponController.do?goSendCoupon" funname="updateMultiple"></t:dgToolBar>
			<%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
			<%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
		<!-- 列表自定义查询条件 -->
		<div id="weixinUserListtd"
			style="padding: 1px; border: 1px; height: auto">
			<div name="searchColums"
				style="height: 45px; line-height: 45px; overflow: hidden; border: 1px">
				<input id="_sqlbuilder" name="sqlbuilder" type="hidden" />
				<form id='customDatagridListForm'>
					<%--<label class="Validform_label" style="margin-left: 15px;">用户选择：</label>--%>
					<%--<t:dictSelect field="isMember" id="isMember" type="select" datatype="*" --%>
					<%--typeGroupCode="shopCus"  hasLabel="false"  title="用户选择" defaultVal="member"></t:dictSelect> --%>
					<label class="Validform_label" style="margin-left: 15px;">手机号：</label>
					<input class="easyui-validatebox" id="mobile" type="text"
						name="mobile" style="width: 120px">
				</form>
			</div>
			<div
				style="height: 30px; line-height: 30px; overflow: hidden; border: 1px"
				class="datagrid-toolbar">
				<span style="float: right"> <a href="#"
					class="easyui-linkbutton" iconCls="icon-search"
					onclick="searchWeixinUser()">查询</a> <a href="#"
					class="easyui-linkbutton" iconCls="icon-reload"
					onclick="Refresh('weixinUserList')">重置</a>
				</span>
			</div>
		</div>
	</div>
</div>
<script src="webpage/com/weixin/weixinuser/weixinUserList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#weixinUserListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinUserListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function searchWeixinUser(){
	 	var isMember = $("#isMember").val();
	 	var mobile = $("#mobile").val();
	 		$("#weixinUserList").datagrid('load',{
	 			isMember : isMember,
	 			mobile : mobile
	     	});
	 	}
	function Refresh(){
		//刷新时将查询条件清空
		$("#isMember").val("");
		$("#mobile").val();
	 	$("#customDatagridList").datagrid('load',{});
	 }
  
 function sendCoupon(id){
		var title = '发放优惠券';
		//var url = 'tcsbCouponController.do?list';
		var url = 'tcsbCouponController.do?goSendCoupon&ids='+id
		var width = "680px";
		var height = "250px";
		createwindow(title, url,width, height); 
	}	
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'weixinUserController.do?upload', "weixinUserList");
}

//导出
function ExportXls() {
	JeecgExcelExport("weixinUserController.do?exportXls","weixinUserList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("weixinUserController.do?exportXlsByT","weixinUserList");
}


/**
 * 发送营销短信
 */
function newSendSaleSMS(title,url,gname){
	gridname=gname;
    var ids = [];
    var rowsData = $("#"+gname).datagrid('getSelections');
    /*if (rows.length > 0) {
    	 $.dialog.setting.zIndex = getzIndex(true);
    	$.dialog.confirm('你确定群发信息给以下会员用户吗?',function(r) {
		   if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
					//alert(rows[i].id);
				}
				
				$.dialog({
			        content: 'url:'+"tcsbCouponController.do?goSendCoupon&userids="+ids,  
			        lock : true,  
			        width:680,  
			        height:100,  
			        title:"发送优惠券",  
			        opacity : 0.3,  
			        cache:false,  
			        okVal: '发送优惠券',  
			        ok: function(){  
			            iframe = this.iframe.contentWindow;  
			            saveObj();  
			            return false;  
			        },  
			        cancelVal: '关闭',  
			        cancel: true /*为true等价于function(){} 
			    }); 
			}
		}); 
	}  else {
		tip("请选择需要发送信息的用户");
	} */
	
	/* for(var i in rowsData){
		ids.push(rowsData[i].id);
		createwindow(title,url,width,height);
	}
	if (!rowsData || rowsData.length==0) {
		tip('请选择编辑项目');
		return;
	} */
}
 </script>