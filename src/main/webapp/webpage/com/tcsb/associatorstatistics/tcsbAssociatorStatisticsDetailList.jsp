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
		<t:datagrid name="tcsbAssociatorBigdataList" checkbox="false"
			fitColumns="false" title="会员消费统计"
			actionUrl="tcsbAssociatorStatisticsController.do?detaildatagrid&openid=${openid }"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="orderParentId" field="orderParentId" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="消费时间" align="center" field="saleTime"
				queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150"></t:dgCol>
			<t:dgCol title="消费金额" align="center" field="allSaleMoney"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="总购买数" align="center" field="count" hidden="true"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="消费桌位" align="center" field="deskName"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="150" align="center"></t:dgCol>
			<t:dgFunOpt funname="myhandle(orderParentId,count,allSaleMoney)"
				title="消费的菜品详情" urlclass="addbtnstyleclass" />
		</t:datagrid>
		<%--<div id="tcsbAssociatorStatisticsList" style="padding:1px;border:1px; height:auto">--%>
		<%--<div name="searchColums" style=" height:70px;line-height:70px;overflow:hidden;border:1px">--%>
		<%--会员昵称:${weixinUserEntity.nickname}--%>
		<%--</div>--%>
		<%--</div>--%>
	</div>
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
function myhandle(orderParentId,count,allSaleMoney){
    $.dialog({
        content: 'url:'+"tcsbAssociatorStatisticsController.do?foodlist&orderParentId="+orderParentId+"&count="+count+"&allSaleMoney="+allSaleMoney+"",
        lock : true,  
        width:500,  
        height:500, 
        zIndex: getzIndex(),
        title:'会员详情',  
        opacity : 0.3,  
        cache:false,  
        cancelVal: '关闭',  
        cancel: true /*为true等价于function(){}*/  
    });  
} 

 </script>