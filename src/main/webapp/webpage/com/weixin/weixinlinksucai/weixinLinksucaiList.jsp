<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="weixinLinksucaiList" checkbox="true"
			fitColumns="false" title="微信链接素材"
			actionUrl="weixinLinksucaiController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="链接名称" field="name" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="外部链接" field="outerLink" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="功能描述" field="content" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="内部链接" field="innerLink" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="转换标志" field="transferSign" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="微信账户id" field="accountid" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="账号邮编" field="postCode" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="分享状态 " field="shareStatus" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="是否加密" field="isEncrypt" hidden="true"
				queryMode="group" width="120"></t:dgCol>
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
			<t:dgFunOpt funname="popMenuLink(id)" title="访问链接"></t:dgFunOpt>
			<t:dgDelOpt title="删除"
				url="weixinLinksucaiController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="weixinLinksucaiController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="weixinLinksucaiController.do?goUpdate" funname="update"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove"
				url="weixinLinksucaiController.do?doBatchDel"
				funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="weixinLinksucaiController.do?goUpdate" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/weixin/weixinlinksucai/weixinLinksucaiList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#weixinLinksucaiListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinLinksucaiListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinLinksucaiListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#weixinLinksucaiListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 /**
 *	弹出菜单链接
 */
 function popMenuLink(id){
      $.dialog({
 			content: "url:weixinLinksucaiController.do?poplink&id="+id,
 			drag :false,
 			lock : true,
 			title: '访问链接',
 			opacity : 0.3,
 			width:650,
 			height:80,drag:false,min:false,max:false
 		}).zindex();
 	}  
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'weixinLinksucaiController.do?upload', "weixinLinksucaiList");
}

//导出
function ExportXls() {
	JeecgExcelExport("weixinLinksucaiController.do?exportXls","weixinLinksucaiList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("weixinLinksucaiController.do?exportXlsByT","weixinLinksucaiList");
}
 </script>