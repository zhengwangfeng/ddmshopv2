<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbWeixinUserList" checkbox="false"
			fitColumns="false" title="会员管理"
			actionUrl="tcsbWeixinUserController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<%--   <t:dgCol title="用户的唯一标识"  field="openid"    queryMode="group"  width="120"></t:dgCol> --%>
			<t:dgCol title="用户昵称" field="nickname" query="true" width="120"></t:dgCol>
			<t:dgCol title="会员手机号" field="mobile" query="true" width="120"></t:dgCol>
			<%--    <t:dgCol title="用户性别"  field="sex"    queryMode="group"  width="120"></t:dgCol> --%>
			<t:dgCol title="省份" field="province" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="城市" field="city" query="true" width="120"></t:dgCol>

			<t:dgCol title="消费总次数" field="saleCount" sortable="false" width="120"></t:dgCol>
			<t:dgCol title="消费店铺数" field="saleShopCount" sortable="false"
				width="120"></t:dgCol>
			<t:dgCol title="消费金额" field="saleMoney" sortable="false" width="120"></t:dgCol>
			<t:dgCol title="均消费金额" field="avgSaleMoney" sortable="false"
				width="120"></t:dgCol>

			<%--    <t:dgCol title="国家"  field="country"    queryMode="group"  width="120"></t:dgCol> --%>
			<%--    <t:dgCol title="用户头像"  field="headimgurl"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="用户特权信息"  field="privilege"    queryMode="group"  width="120"></t:dgCol> --%>
			<t:dgCol title="注册时间" field="createTime" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<%-- <t:dgCol title="商家ID"  field="shopId"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="smscode"  field="smscode"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发送时间"  field="sendtime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="用户是否消费"  field="issale"    queryMode="group"  width="120"></t:dgCol> --%>
			<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tcsbWeixinUserController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tcsbWeixinUserController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbWeixinUserController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbWeixinUserController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbWeixinUserController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/tcsbweixinuser/tcsbWeixinUserList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbWeixinUserListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbWeixinUserListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbWeixinUserListtb").find("input[name='sendtime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbWeixinUserListtb").find("input[name='sendtime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbWeixinUserController.do?upload', "tcsbWeixinUserList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbWeixinUserController.do?exportXls","tcsbWeixinUserList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbWeixinUserController.do?exportXlsByT","tcsbWeixinUserList");
}
 </script>