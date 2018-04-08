<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
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
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbShopList" checkbox="true" fitColumns="false"
			title="店铺管理" actionUrl="tcsbShopController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="名称" field="name" align="center" query="true"
				queryMode="single" width="180"></t:dgCol>
			<t:dgCol title="店铺支付方式" field="payMode" align="center"
				queryMode="single" width="180"></t:dgCol>
			<t:dgCol title="头像" field="headimg" image="true" align="center"
				imageSize="120,75"></t:dgCol>
			<t:dgCol title="小程序码" field="appletQrcode" image="true"
				align="center" imageSize="120,75"></t:dgCol>
			<t:dgCol title="简介" field="introdution" align="center"
				queryMode="group" width="220"></t:dgCol>
			<t:dgCol title="地址" field="address" align="center" queryMode="group"
				width="180"></t:dgCol>
			<t:dgCol title="联系方式" field="phone" align="center" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="店铺状态" field="status" align="center" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="店铺状态" field="statusName" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="店铺预约方式" field="reservationMethod"
				dictionary="resMethod" align="center" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="店铺预约金额" field="reserveDeposit" align="center"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<%-- <t:dgCol title="是否入驻"  field="idDel"   align="center" queryMode="group"  width="120"></t:dgCol> --%>
			<%--  <t:dgCol title="星级"  field="level"  align="center"  queryMode="group"  width="120"></t:dgCol> --%>
			<t:dgCol title="更新人名字" field="updateName" hidden="true"
				align="center" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新时间" field="updateDate" align="center" hidden="true"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" align="center" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" align="center"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd"
				align="center" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" hidden="false" field="opt" width="100"></t:dgCol>
			<t:dgFunOpt funname="updatestatus1(id)" title="禁用" exp="status#eq#0"
				urlclass="addbtnstyleclass" />
			<t:dgFunOpt funname="updatestatus0(id)" title="启用" exp="status#eq#1"
				urlclass="delbtnstyleclass" />
			<%-- <t:dgDelOpt title="删除" url="tcsbShopController.do?doDel&id={id}" urlclass="ace_button" operationCode="hideCD"  urlfont="fa-trash-o"/> --%>
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbShopController.do?goAdd" funname="add"
				operationCode="hideCD"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbShopController.do?goUpdate&loadState=1" funname="update"></t:dgToolBar>
			<%--   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbShopController.do?doBatchDel" funname="deleteALLSelect" operationCode="hideCD"></t:dgToolBar> --%>
			<%-- <t:dgToolBar title="查看" icon="icon-search" url="tcsbShopController.do?goUpdate&loadState=2" funname="detail"></t:dgToolBar>
    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/shop/tcsbShopList.js"></script>
<script type="text/javascript">
 function updatestatus0(id){
	var url = "tcsbShopController.do?updateStatus";
     $.ajax({
        url:url,
        type:"POST",
        dataType:"JSON",
        data:{
        	id:id,
            status:'0'
        },
        success:function(data){
        	$('#tcsbShopList').datagrid('reload'); 
        }
    }); 
} 
 function updatestatus1(id){
		var url = "tcsbShopController.do?updateStatus";
	     $.ajax({
	        url:url,
	        type:"POST",
	        dataType:"JSON",
	        data:{
	        	id:id,
	            status:'1'
	        },
	        success:function(data){
	        	$('#tcsbShopList').datagrid('reload'); 
	        }
	    }); 
	} 

</script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbShopListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbShopController.do?upload', "tcsbShopList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbShopController.do?exportXls","tcsbShopList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbShopController.do?exportXlsByT","tcsbShopList");
}
 </script>