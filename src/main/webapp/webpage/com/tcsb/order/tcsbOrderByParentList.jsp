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
		<t:datagrid name="tcsbOrderList" checkbox="true" fitColumns="false"
			title="订单管理"
			actionUrl="tcsbOrderController.do?datagridByParent&orderParentId=${ orderParentId}"
			idField="id" fit="true" queryMode="group" sortName="createTime"
			sortOrder="desc">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="orderParentId" field="orderParentId" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="订单编号" field="orderNo" align="center" query="true"
				queryMode="single" width="150"></t:dgCol>
			<c:if test="${isAdmin==true }">
				<t:dgCol title="所属商家" field="shopId" align="center" query="true"
					queryMode="single" replace="${shopReplace}" width="150"></t:dgCol>
			</c:if>
			<t:dgCol title="桌位号" field="deskId" align="center" query="true"
				queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="下单方式" field="method" align="center"
				replace="扫码下单_0,线上下单_1,手动下单_2" query="true" queryMode="single"
				width="80"></t:dgCol>
			<%--  <t:dgCol title="用户ID"  field="userId"  align="center"  queryMode="group"  width="120"></t:dgCol> --%>
			<%--  <t:dgCol title="所属店铺ID"  field="shopId" align="center"  query="true" queryMode="single"  width="120"></t:dgCol> --%>
			<t:dgCol title="下单时间" field="createTime" align="center"
				formatter="yyyy-MM-dd hh:mm" query="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="订单状态" hidden="true" align="center" field="status"
				replace="未消费_0,已消费_1" query="true" queryMode="single" width="120"></t:dgCol>

			<t:dgCol title="实际消费" field="totalPrice" align="center"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="线上支付金额" field="onlinePrice" align="center"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="线下支付金额" field="offlinePrice" align="center"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="优惠方式" field="dMethod" hidden="true" align="center"
				replace="平台优惠_0,专用券_1,通用券_2" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="支付状态" field="payStatus" align="center"
				replace="未支付_0,已支付_1" query="true" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="支付方式" field="payMethod" align="center"
				replace="线上支付_0,线下支付_1" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="用餐时间" field="eatTime" align="center" hidden="true"
				formatter="yyyy-MM-dd" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="用餐人数" field="people" align="center" queryMode="group"
				width="80"></t:dgCol>
			<t:dgCol title="特殊说明" field="note" align="center" queryMode="group"
				width="80"></t:dgCol>
			<%--  <t:dgCol title="是否包间"  field="isRoom" replace="是_Y,否_N" queryMode="single"  width="120"></t:dgCol> --%>
			<t:dgCol title="更新人名字" field="updateName" align="center"
				hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="更新时间" field="updateDate" align="center"
				formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" align="center" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" align="center"
				hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" align="center" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" align="center"
				formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="150"></t:dgCol>
			<t:dgFunOpt funname="send(id)" title="发放优惠卷"
				urlclass="addbtnstyleclass" />
			<c:if test="${isAdmin==true }">
				<t:dgDelOpt title="删除" url="tcsbOrderController.do?doDel&id={id}"
					urlclass="ace_button" urlfont="fa-trash-o" />
			</c:if>
			<%-- <t:dgConfOpt title="接单" url="tcsbOrderController.do?receiveOrder&id={id}" urlclass="ace_button"  urlfont="fa-toggle-on"  message="确认接单"/> --%>
			<%-- <t:dgConfOpt title="取消接单" url="cgformFtlController.do?cancleActive&id={id}&formId=${formid}"  urlclass="ace_button"  urlfont="fa-toggle-off"  exp="ftlStatus#eq#1" message="确认取消激活"/> --%>
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbOrderController.do?goAdd" funname="add" width="1000"
				height="500"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbOrderController.do?goUpdate" funname="update" width="1000"
				height="500"></t:dgToolBar>
			<%--  <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbOrderController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
			<t:dgToolBar title="打印" icon="icon-print"
				url="tcsbOrderController.do?print" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbOrderController.do?goUpdate" funname="detail" width="1000"
				height="500"></t:dgToolBar>
			<%--  <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/order/tcsbOrderList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbOrderListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			//$("#tcsbOrderListtb").find("input[name='eatTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			//$("#tcsbOrderListtb").find("input[name='eatTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			//$("#tcsbOrderListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			//$("#tcsbOrderListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			//$("#tcsbOrderListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			//$("#tcsbOrderListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 
 			var mydate = new Date();
 		   /* 	var str = "" + mydate.getFullYear() + "-";
 		   	if(mydate.getMonth()+1>9){
 		   		str += (mydate.getMonth()+1) + "-";
 		   	}else{
 		   		str += "0"+(mydate.getMonth()+1) + "-";
 		   	} */
 		   	var str1 = AddDays(mydate,-1);
 		   	var str2 = AddDays(mydate,1);
 		    //str1 =str+ (mydate.getDate()-1);
 		   	//str2 =str+ (mydate.getDate()+1);
 			$("#tcsbOrderListtb").find("input[name='createTime_begin']").val(str1);
 			$("#tcsbOrderListtb").find("input[name='createTime_end']").val(str2);
 			
 			
 			//$('.l-btn-left').click();
 			$("#tcsbOrderListtb").find("input[name='createTime_end']").attr("onchange","sss()");
 			window.setTimeout("tcsbOrderListsearch()",200);
 			
 });
 
 
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
	 tcsbOrderListsearch();
 }
 
 function send(id){
	 var url ="weixinUserController.do?weixinUserByOrder&orderId="+id;
	 createwindow("发放优惠卷",url,'100%','100%');
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbOrderController.do?upload', "tcsbOrderList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbOrderController.do?exportXls","tcsbOrderList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbOrderController.do?exportXlsByT","tcsbOrderList");
}
 </script>
