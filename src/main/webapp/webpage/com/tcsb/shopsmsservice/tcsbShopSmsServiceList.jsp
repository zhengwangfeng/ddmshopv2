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
		<t:datagrid name="tcsbShopSmsServiceList" checkbox="true"
			fitColumns="false" title="商户短信服务"
			actionUrl="tcsbShopSmsServiceController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="所属店铺" field="shopName" align="center" query="true"
				width="160"></t:dgCol>
			<t:dgCol title="店铺地址" field="shopAddress" queryMode="group"
				width="220"></t:dgCol>
			<t:dgCol title="店铺ID" field="shopId" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="真实姓名" field="realName" hidden="true" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="短信条数" field="smsCount" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人名字" field="updateName" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新时间" field="updateDate" formatter="yyyy-MM-dd"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
			<t:dgFunOpt funname="myhandle(shopId)" title="短信充值"
				urlclass="addbtnstyleclass" />
			<%-- <t:dgToolBar title="录入" icon="icon-add" url="tcsbShopSmsServiceController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbShopSmsServiceController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbShopSmsServiceController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbShopSmsServiceController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/shopsmsservice/tcsbShopSmsServiceList.js"></script>
<script type="text/javascript">
 
 
 
 /**
  * 自定义处理事件
  * @param url
  * @param title
  * @param okVal
  */
 function myhandle(shopId){  
     $.dialog({
         content: 'url:'+"tcsbShopSmsServiceController.do?SMSrechargeList&shopId="+shopId,  
         lock : false,  
         width:600,  
         height:200,  
         title:'短信充值',  
         opacity : 0.3,  
         cache:false,  
         okVal: '确定',  
	        ok: function(){  
	            iframe = this.iframe.contentWindow;  
	            saveObj();  
	            return false;  
	        },  
         cancelVal: '关闭',  
         cancel: true /*为true等价于function(){}*/  
     });  
 } 
 
 
 
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbShopSmsServiceListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopSmsServiceListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopSmsServiceListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopSmsServiceListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbShopSmsServiceController.do?upload', "tcsbShopSmsServiceList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbShopSmsServiceController.do?exportXls","tcsbShopSmsServiceList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbShopSmsServiceController.do?exportXlsByT","tcsbShopSmsServiceList");
}
 </script>