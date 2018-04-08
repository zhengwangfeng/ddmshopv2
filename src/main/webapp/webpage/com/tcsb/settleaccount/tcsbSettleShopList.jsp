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
		<t:datagrid name="tcsbShopList" checkbox="false" fitColumns="false"
			title="店铺管理" actionUrl="tcsbSettleAccountController.do?shopdatagrid"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="店铺名称" field="name" align="center" query="true"
				queryMode="single" width="180"></t:dgCol>
			<t:dgCol title="总营业额" style="background-color: #80FFFF;"
				align="center" field="totalprice" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="线上营收" style="background-color: #79FF79;"
				align="center" field="onlineIncome" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="优惠券" style="background-color: #79FF79;"
				align="center" field="specialcouponprice" queryMode="single"
				width="100"></t:dgCol>
			<t:dgCol title="店铺活动" style="background-color: #79FF79;"
				align="center" field="universalcouponprice" queryMode="single"
				width="80"></t:dgCol>
			<t:dgCol title="平台需结算" style="background-color: #79FF79;"
				align="center" field="platformSettlement" queryMode="single"
				width="100"></t:dgCol>
			<!--  -->
			<t:dgCol title="线下营收" style="background-color: #FFFFAA;"
				align="center" field="offlineIncome" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="抹零" style="background-color: #FFFFAA;" align="center"
				field="offlineDiscount" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="线下实收" style="background-color: #FFFFAA;"
				align="center" field="offlinePayment" queryMode="single" width="80"></t:dgCol>
			<!--  -->
			<t:dgCol title="总实收" align="center"
				style="background-color: #FFBB77;" field="disprice"
				queryMode="single" width="80"></t:dgCol>

			<t:dgCol title="操作" field="opt" width="200"></t:dgCol>
			<t:dgFunOpt funname="incomehandle(id)" title="查看营收详情"
				urlclass="addbtnstyleclass" />
			<t:dgFunOpt funname="settlehandle(id)" title="查看结算详情"
				urlclass="delbtnstyleclass" />
			<t:dgToolBar title="待结算列表"
				url="tcsbSettleAccountController.do?createSettle"
				funname="myhandle()"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/shop/tcsbShopList.js"></script>
<script type="text/javascript">
 
 
 
 function incomehandle(shopId){  
	 var url = "tcsbInComeController.do?income&shopId="+shopId;
		createwindow("营业收入统计", url,1000,600);
	} 
 
 function settlehandle(shopId){  
	 var url = "tcsbSettleAccountController.do?list&shopId="+shopId;
		createwindow("账单结算", url,900,500);
	} 
 
 function myhandle(){  
	 var url = "tcsbSettleAccountController.do?createSettle";
		createwindow("未结算账单", url,900,500);
	} 
 
 
 
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