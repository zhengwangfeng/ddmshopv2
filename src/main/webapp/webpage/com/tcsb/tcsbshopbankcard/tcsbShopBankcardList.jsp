<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbShopBankcardList" checkbox="true"
			fitColumns="false" title="店铺关联银行卡"
			actionUrl="tcsbShopBankcardController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="收款方账户名称" field="beneficiaryName" align="center"
				queryMode="group" width="160"></t:dgCol>
			<t:dgCol title="收款方客户账号" field="beneficiaryAccount" align="center"
				queryMode="group" width="140"></t:dgCol>
			<t:dgCol title="收款方开户行名称" field="beneficiaryOpeningBankName"
				align="center" queryMode="group" width="200"></t:dgCol>
			<t:dgCol title="收款方行别代码" field="beneficiaryBankcode"
				replace="本行_01,国内他行_02" align="center" queryMode="group" width="120"></t:dgCol>
			<%-- <t:dgCol title="收款方联行号"  field="beneficiaryLineNumber"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="客户方流水号"  field="clientDerialNumber"    queryMode="group"  width="120"></t:dgCol> --%>
			<%--  <t:dgCol title="更新人名字"  field="updateName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人"  field="updateBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名字"  field="createName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"    queryMode="group"  width="120"></t:dgCol> --%>
			<%--  <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol> --%>
			<%-- <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
			<%-- <t:dgDelOpt title="删除" url="tcsbShopBankcardController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tcsbShopBankcardController.do?goAdd" funname="add"></t:dgToolBar> --%>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbShopBankcardController.do?goUpdate" funname="update"></t:dgToolBar>
			<%-- <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbShopBankcardController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbShopBankcardController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/tcsbshopbankcard/tcsbShopBankcardList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbShopBankcardListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopBankcardListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopBankcardListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopBankcardListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbShopBankcardController.do?upload', "tcsbShopBankcardList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbShopBankcardController.do?exportXls","tcsbShopBankcardList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbShopBankcardController.do?exportXlsByT","tcsbShopBankcardList");
}
 </script>