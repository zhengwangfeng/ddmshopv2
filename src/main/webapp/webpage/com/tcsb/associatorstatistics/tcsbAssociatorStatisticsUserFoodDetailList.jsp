<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbAssociatorStatisticsList" checkbox="false"
			fitColumns="false" title="会员统计已消费"
			actionUrl="tcsbAssociatorStatisticsController.do?userfooddetaildatagrid&openid=${openid }"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<%--   <t:dgCol title="orderid"  field="orderid" hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
			<t:dgCol title="商品名称" align="center" field="foodname"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="消费次数" align="center" field="saleCount"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="购买次数" align="center" field="buyCount"
				queryMode="group" width="150"></t:dgCol>
			<t:dgCol title="平均消费次数" align="center" field="avgSaleCount"
				queryMode="group" width="120"></t:dgCol>
			<%--   <t:dgCol title="消费的菜品"  field="foodList"    queryMode="group"  width="120"></t:dgCol> --%>
			<%--   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
	<t:dgFunOpt funname="myhandle(orderid)" title="消费的菜品详情"/> --%>
			<%--    <t:dgToolBar title="发送营销短信" icon="icon-add" url="tcsbAssociatorStatisticsController.do?goAdd" funname="sendSaleSMS"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbAssociatorStatisticsController.do?goUpdate" funname="myhandle"></t:dgToolBar> --%>
			<%--  <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbAssociatorStatisticsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbAssociatorStatisticsController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
		<%--<div id="tcsbAssociatorStatisticsList" style="padding:1px;border:1px; height:auto">--%>
		<%--<div name="searchColums" style=" height:70px;line-height:70px;overflow:hidden;border:1px">--%>
		<%--会员昵称:${weixinUserEntity.nickname}--%>
		<%--</div>--%>
		<%--</div>--%>
	</div>
</div>
<script
	src="webpage/com/tcsb/associatorstatistics/tcsbAssociatorStatisticsList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbAssociatorStatisticsListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbAssociatorStatisticsListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbAssociatorStatisticsController.do?upload', "tcsbAssociatorStatisticsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbAssociatorStatisticsController.do?exportXls","tcsbAssociatorStatisticsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbAssociatorStatisticsController.do?exportXlsByT","tcsbAssociatorStatisticsList");
}



/**
 * 自定义处理事件
 * @param url
 * @param title
 * @param okVal
 */
function myhandle(orderid){  
    $.dialog({
        content: 'url:'+"tcsbAssociatorStatisticsController.do?foodlist&orderid="+orderid,  
        lock : true,  
        width:600,  
        height:500,  
        title:'会员详情',  
        opacity : 0.3,  
        cache:false,  
        cancelVal: '关闭',  
        cancel: true /*为true等价于function(){}*/  
    });  
} 

 </script>