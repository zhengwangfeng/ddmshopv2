<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbSaleStatisticsList" checkbox="true"
			fitColumns="false" title="商品销售详情"
			actionUrl="tcsbSaleStatisticsController.do?detaildatagridnew&foodid=${foodid }"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="id" hidden="true" field="id" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="会员昵称" align="center" field="nickName"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="手机号" align="center" field="mobile" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="菜品名称" align="center" field="foodName"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="复购次数" align="center" field="vipSaleCount"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="最近消费时间" align="center" field="lastSaleTime"
				queryMode="group" width="120"></t:dgCol>
			<%--  <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
			<%--  <t:dgFunOpt funname="myhandle(foodId)" title="查看详情" /> --%>
			<t:dgToolBar title="发送优惠券" icon="icon-add"
				url="tcsbCouponController.do?goSendCoupon" funname="updateMultiple"></t:dgToolBar>
			<%--  <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbSaleStatisticsController.do?goUpdate" funname="myhandle"></t:dgToolBar> --%>
			<%--  <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbSaleStatisticsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbSaleStatisticsController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/salestatistics/tcsbSaleStatisticsList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			//$("#tcsbSaleStatisticsListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			//$("#tcsbSaleStatisticsListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbSaleStatisticsController.do?upload', "tcsbSaleStatisticsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbSaleStatisticsController.do?exportXls","tcsbSaleStatisticsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbSaleStatisticsController.do?exportXlsByT","tcsbSaleStatisticsList");
}



/**
 * 自定义处理事件
 * @param url
 * @param title
 * @param okVal
 */
function myhandle(foodId){
    $.dialog({
        content: 'url:'+"tcsbSaleStatisticsController.do?detaillist&openid="+openid,  
        lock : false,  
        width:1000,  
        height:700,  
        title:'会员详情',  
        opacity : 0.3,  
        cache:false,  
        cancelVal: '关闭',  
        cancel: true /*为true等价于function(){}*/  
    });  
} 



 </script>