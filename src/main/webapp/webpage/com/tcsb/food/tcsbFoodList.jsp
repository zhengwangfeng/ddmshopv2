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
		<t:datagrid name="tcsbFoodList" checkbox="true" fitColumns="false"
			title="美食" actionUrl="tcsbFoodController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="名称" field="name" query="true" align="center"
				queryMode="single" width="130"></t:dgCol>
			<t:dgCol title="价格" field="price" queryMode="group" align="center"
				width="80"></t:dgCol>
			<t:dgCol title="是否时价" field="isCurrentPrice" dictionary="sf_yn"
				queryMode="group" align="center" width="80"></t:dgCol>
			<t:dgCol title="单位" field="unitName" queryMode="group" align="center"
				width="60"></t:dgCol>
			<t:dgCol title="菜品类型" field="foodTypeId" align="center" query="true"
				replace="${foodTypeReplace}" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="图片" field="img" image="true" align="center"
				imageSize="100,60"></t:dgCol>
			<t:dgCol title="折扣价" field="discountPrice" align="center"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="是否支持外卖" align="center" field="istakeout"
				hidden="true" replace="是_1,否_0" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="是否是特色" align="center" field="ischara" hidden="true"
				replace="是_1,否_0" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="顺序 " align="center" field="orders" queryMode="group"
				width="80"></t:dgCol>
			<t:dgCol title="菜状态" align="center" field="status"
				replace="上架_1,下架_0" query="true" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="更新人名字" align="center" field="updateName"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新时间" align="center" field="updateDate" hidden="true"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" align="center" field="updateBy" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人名字" align="center" field="createName"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" align="center" field="createBy" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" align="center" field="createDate"
				formatter="yyyy-MM-dd" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="套餐" align="center" field="isSetMeal"
				replace="是_1,否_0" queryMode="group" width="60"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="350" align="center"></t:dgCol>
			<%--  <t:dgDefOpt url="tcsbFoodController.do?stateUpdate&id={id}&status=1" title="上架" exp="status#eq#0"/>
   <t:dgDefOpt url="tcsbFoodController.do?stateUpdate&id={id}&status=0" title="下架" exp="status#eq#1"/> --%>
			<t:dgFunOpt funname="onsale(id)" title="上架" exp="status#eq#0"
				urlclass="addbtnstyleclass" />
			<t:dgFunOpt funname="offsale(id)" title="下架" exp="status#eq#1"
				urlclass="delbtnstyleclass" />
			<t:dgFunOpt funname="setfoodtaste(id)" title="口味设置"
				urlclass="addbtnstyleclass"></t:dgFunOpt>
			<t:dgFunOpt funname="setStandard(id)" title="规格设置"
				urlclass="addbtnstyleclass"></t:dgFunOpt>
			<t:dgFunOpt funname="setMeal(id)" title="套餐设置" exp="isSetMeal#eq#1"
				urlclass="delbtnstyleclass" />
			<t:dgDelOpt title="删除" url="tcsbFoodController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" operationCode="foodCUD" />
			<%--   <t:dgDelOpt title="删除" url="tcsbFoodController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o" operationCode="foodCUD"/> --%>
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbFoodController.do?goAdd" funname="add"
				operationCode="foodCUD"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbFoodController.do?goUpdate" funname="update"
				operationCode="foodCUD"></t:dgToolBar>
			<%--  <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbFoodController.do?doBatchDel" funname="deleteALLSelect" operationCode="foodCUD"></t:dgToolBar> --%>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbFoodController.do?goUpdate" funname="detail"></t:dgToolBar>
			<%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>

</div>
<div id="foodtaste" region="east" style="width: 420px;" split="true">
	<div tools="#tt" class="easyui-panel" title='口味偏好'
		style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>
<script src="webpage/com/tcsb/food/tcsbFoodList.js"></script>
<script type="text/javascript">
 
 
 function setMeal(id) {
		$("#function-panel").panel(
			{
				title :'套餐设置',
				//TODO
				href:"tcsbFoodController.do?foodMealFun&foodid=" + id
			}
		);
		//$('#function-panel').panel("refresh" );
	}
 
 
 function setfoodtaste(id) {
		$("#function-panel").panel(
			{
				title :'口味偏好设置',
				href:"tcsbFoodTasteFunctionController.do?fun&roleId=" + id
			}
		);
	}
 
 function setStandard(id) {
	
	 $("#function-panel").panel(
				{
					title :'规格设置',
					href:"tcsbFoodController.do?standardList&foodId=" + id
				}
			);
	}
 
 function onsale(foodid){
	 var url = "tcsbFoodController.do?stateUpdate";
	  $.post(
			url,
			{id:foodid,status:1},
			function(data){
				  $('#tcsbFoodList').datagrid('reload');
			}
	  );
	} 
 
 function offsale(foodid){ 
	 var url = "tcsbFoodController.do?stateUpdate";
	 $.post(
				url,
				{id:foodid,status:0},
				function(data){
					  $('#tcsbFoodList').datagrid('reload');
				}
		  );
	} 
 
 
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbFoodListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFoodListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFoodListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFoodListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbFoodController.do?upload', "tcsbFoodList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbFoodController.do?exportXls","tcsbFoodList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbFoodController.do?exportXlsByT","tcsbFoodList");
}
 </script>