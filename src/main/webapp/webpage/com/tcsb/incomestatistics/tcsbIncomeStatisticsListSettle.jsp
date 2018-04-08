<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<input type="hidden" id="startTime" value="${startTime }" /> <input
			type="hidden" id="endTime" value="${endTime }" />
		<t:datagrid name="tcsbIncomeStatisticsList" checkbox="false"
			fitColumns="false" title="商品销量统计"
			actionUrl="tcsbInComeController.do?incomedatagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="统计" field="id" queryMode="group" width="50"
				align="center"></t:dgCol>
			<t:dgCol title="选择时间段" align="center" hidden="true"
				field="searchTime" formatter="yyyy-MM-dd" query="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="时间" align="center" field="createdate"
				formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="线上营业额" align="center" field="onlineIncome"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="店铺优惠券" align="center" field="universalcouponprice"
				queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="店铺折扣" align="center" field="specialcouponprice"
				queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="平台需结算" align="center" field="platformSettlement"
				queryMode="single" width="100"></t:dgCol>

			<%-- <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
			<%--  <t:dgDelOpt title="删除" url="tcsbIncomeStatisticsController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tcsbIncomeStatisticsController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbIncomeStatisticsController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbIncomeStatisticsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
			<%-- <t:dgToolBar title="查看" icon="icon-search" url="tcsbIncomeStatisticsController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>

</div>

<script
	src="webpage/com/tcsb/incomestatistics/tcsbIncomeStatisticsList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbIncomeStatisticsListtb").find("input[name='searchTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbIncomeStatisticsListtb").find("input[name='searchTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			//新增的时间控制
 			//$("#tcsbIncomeStatisticsListtb").find("input[name='createTime']").attr("class","Wdate").attr("style","width:150px").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH-mm-ss'});});
 			
 			// $('#tcsbCallServiceList').datagrid('reload');
 			var startTime = $("#startTime").val();
 			if(startTime == null || startTime==''){
 				
 				var mydate = new Date();
 	 		   	var str1 = AddDays(mydate,0);
 	 		   	var str2 = AddDays(mydate,0);
 				$("#tcsbIncomeStatisticsListtb").find("input[name='searchTime_begin']").val(str1);
 	 			$("#tcsbIncomeStatisticsListtb").find("input[name='searchTime_end']").val(str2);
 				
 			}else{
 				$("#tcsbIncomeStatisticsListtb").find("input[name='searchTime_begin']").val($("#startTime").val());
 	 			$("#tcsbIncomeStatisticsListtb").find("input[name='searchTime_end']").val($("#endTime").val());
 			
 			}
 			
 			
 			tcsbIncomeStatisticsListsearch();
 			
 			$("#tcsbIncomeStatisticsListtb").find("input[name='searchTime_end']").attr("onchange","sss()")
 			
 });
 
 
 function getFirdtDays(date){
	 var nd = new Date(date);
	 var y = nd.getFullYear();
	 var m = nd.getMonth()+1; 
	 if(m <= 9) m = "0"+m;
	 var cdate = y+"-"+m+"-01";
	 return cdate;
	 }
 
 
 function getLastDay(date) {  
	 var nd = new Date(date);
	 var y = nd.getFullYear();
	 var m = nd.getMonth()+1;     
     var new_date = new Date(y,m,1);                //取当年当月中的第一天          
     return y+"-"+m+"-"+(new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月最后一天日期          
} 
 
 
 
 function AddDays(date,days){
	 var nd = new Date(date);
	    nd = nd.valueOf();
	    nd = nd + days * 24 * 60 * 60 * 1000;
	    nd = new Date(nd);
	    //alert(nd.getFullYear() + "年" + (nd.getMonth() + 1) + "月" + nd.getDate() + "日");
	 var y = nd.getFullYear();
	 var m = nd.getMonth()+1;
	 var d = nd.getDate();
	 if(m <= 9) m = "0"+m;
	 if(d <= 9) d = "0"+d; 
	 var cdate = y+"-"+m+"-"+d;
	 return cdate;
	 }
 
 
 function sss(){
	 tcsbIncomeStatisticsListsearch();
 }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbIncomeStatisticsController.do?upload', "tcsbIncomeStatisticsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbIncomeStatisticsController.do?exportXls","tcsbIncomeStatisticsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbIncomeStatisticsController.do?exportXlsByT","tcsbIncomeStatisticsList");
}
 </script>