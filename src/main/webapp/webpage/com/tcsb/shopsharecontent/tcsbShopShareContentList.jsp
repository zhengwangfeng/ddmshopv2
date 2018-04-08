<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tcsbShopShareContentList" checkbox="true" fitColumns="false" title="分享内容管理" actionUrl="tcsbShopShareContentController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属店铺"  field="shopId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="分享二维码"  field="qrCode"   image="true" align="center" imageSize="120,75"></t:dgCol>
   <t:dgCol title="分享获取比例"  field="shareProportion"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="分享标题"  field="shareTittle"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="分享描述"  field="shareDescript"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="分享logo"  field="shareLogo"   image="true" align="center" imageSize="120,75"></t:dgCol>
   <t:dgCol title="更新人名字"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名字"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <%-- <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
   <%-- <t:dgDelOpt title="删除" url="tcsbShopShareContentController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tcsbShopShareContentController.do?goAdd" funname="add"></t:dgToolBar> --%>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbShopShareContentController.do?goUpdate" funname="update"></t:dgToolBar>
  <%--  <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbShopShareContentController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbShopShareContentController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/tcsb/shopsharecontent/tcsbShopShareContentList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbShopShareContentListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopShareContentListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopShareContentListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopShareContentListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbShopShareContentController.do?upload', "tcsbShopShareContentList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbShopShareContentController.do?exportXls","tcsbShopShareContentList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbShopShareContentController.do?exportXlsByT","tcsbShopShareContentList");
}
 </script>