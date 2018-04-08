<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="weixinAccountList" checkbox="true"
			fitColumns="false" title="微信公众号"
			actionUrl="weixinAccountController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="公众帐号名称" field="accountname" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="公众帐号TOKEN" field="accounttoken" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="公众微信号" field="accountnumber" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="公众号类型" field="accounttype" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="电子邮箱" field="accountemail" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="公众帐号描述" field="accountdesc" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="公众号at" field="accountaccesstoken" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="公众帐号APPID" field="accountappid" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="公众帐号As" field="accountappsecret" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="USERNAME" field="username" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="公众号原始ID" field="weixinAccountid" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="apiticket" field="apiticket" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="apiticketttime" field="apiticketttime"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="jsapiticket" field="jsapiticket" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="jsapitickett" field="jsapitickettime"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人名字" field="updateName" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="更新时间" field="updateDate" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除" url="weixinAccountController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="weixinAccountController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="weixinAccountController.do?goUpdate" funname="update"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="weixinAccountController.do?doBatchDel"
				funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="weixinAccountController.do?goUpdate" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/weixin/weixinaccount/weixinAccountList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#weixinAccountListtb").find("input[name='apiticketttime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinAccountListtb").find("input[name='apiticketttime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinAccountListtb").find("input[name='jsapitickettime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinAccountListtb").find("input[name='jsapitickettime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinAccountListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinAccountListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinAccountListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinAccountListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'weixinAccountController.do?upload', "weixinAccountList");
}

//导出
function ExportXls() {
	JeecgExcelExport("weixinAccountController.do?exportXls","weixinAccountList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("weixinAccountController.do?exportXlsByT","weixinAccountList");
}
 </script>