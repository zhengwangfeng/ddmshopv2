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
	background: #9D9D9D;
	border: 1px #9D9D9D solid;
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
			title="订单管理" actionUrl="tcsbSubOrderController.do?datagridAccept"
			idField="id" fit="true" queryMode="group" sortName="createDate"
			sortOrder="asc">
			<t:dgCol title="ID" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="订单编号" field="orderNo" query="true" queryMode="single"
				width="120"></t:dgCol>
			<t:dgCol title="下单方式" field="method" align="center"
				replace="扫码下单_0,线上下单_1,手动下单_2" query="true" queryMode="single"
				width="80"></t:dgCol>
			<%--  <t:dgCol title="用户ID"  field="userId"    queryMode="group"  width="120"></t:dgCol> --%>
			<%--  <t:dgCol title="所属店铺ID"  field="shopId"   query="true" queryMode="single"  width="120"></t:dgCol> --%>
			<t:dgCol title="下单时间" field="createTime" align="center"
				formatter="yyyy-MM-dd hh:mm" query="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="订单状态" field="status" hidden="true"
				replace="未消费_0,已消费_1" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="桌位号" field="deskId" align="center" queryMode="single"
				width="60"></t:dgCol>
			<t:dgCol title="实际消费" field="totalPrice" align="center"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="优惠价" field="disPrice" hidden="true" queryMode="group"
				width="80"></t:dgCol>
			<t:dgCol title="优惠方式" field="dMethod" hidden="true"
				replace="平台优惠_0,专用券_1,通用券_2" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="支付状态" field="payStatus" hidden="true"
				replace="未支付_0,已支付_1" query="true" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="支付方式" field="payMethod" hidden="true"
				replace="线上支付_0,线下支付_1" query="true" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="用餐时间" field="eatTime" hidden="true"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="用餐人数" field="people" align="center" queryMode="group"
				width="80"></t:dgCol>
			<t:dgCol title="特殊说明" field="note" align="center" queryMode="group"
				width="80"></t:dgCol>
			<t:dgCol title="是否包间" field="isRoom" align="center" replace="是_Y,否_N"
				query="true" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="更新人名字" field="updateName" hidden="true"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="更新时间" field="updateDate" formatter="yyyy-MM-dd"
				hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" hidden="true" queryMode="group"
				width="80"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" hidden="true"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group"
				width="80"></t:dgCol>
			<t:dgCol title="是否接单" field="orderIstake" hidden="true"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd"
				hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="80" align="center"></t:dgCol>
			<t:dgFunOpt funname="receiveOrder(id)" title="接单"
				exp="orderIstake#eq#N" urlclass="addbtnstyleclass" />
			<t:dgFunOpt funname="receiveOrders(id)" title="已接单"
				exp="orderIstake#eq#Y" urlclass="delbtnstyleclass" />
			<%-- <t:dgDelOpt title="删除" url="tcsbOrderController.do?doDel&id={id}"  urlclass="ace_button" urlfont="fa-trash-o"/> --%>
			<%-- <t:dgConfOpt title="接单" url="tcsbSubOrderController.do?receiveOrder&id={id}" urlclass="ace_button"  urlfont="fa-toggle-on"  message="确认接单"/> --%>
			<%-- <t:dgConfOpt title="取消接单" url="cgformFtlController.do?cancleActive&id={id}&formId=${formid}"  urlclass="ace_button"  urlfont="fa-toggle-off"  exp="ftlStatus#eq#1" message="确认取消激活"/> --%>
			<%-- <t:dgToolBar title="录入" icon="icon-add" url="tcsbSubOrderController.do?goAdd" funname="add" width="100%" height="100%"></t:dgToolBar> --%>
			<t:dgToolBar title="打印" icon="icon-print"
				url="tcsbSubOrderController.do?print3" funname="detail"></t:dgToolBar>
			<%-- <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbOrderController.do?goUpdate" funname="update" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbOrderController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbSubOrderController.do?goUpdate" funname="detail"
				width="1000" height="500"></t:dgToolBar>

			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/order/tcsbOrderList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbOrderListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='eatTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='eatTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
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


function receiveOrder(Id){
	
	//检测是否有时价类菜品
	var checkurl = "tcsbSubOrderController.do?checkIsflatOrderFood";
	$.post(
			checkurl,
			{id:Id},
			function(data){
				if(data == 1){
					var redirecturl = "tcsbSubOrderController.do?goIsflatOrderFood&id="+Id;
					createwindows("时价类菜品编辑", redirecturl,480,500);
				}else{
					var url = "tcsbSubOrderController.do?receiveOrder";
					$.post(
							url,
							{id:Id},
							function(data){
								$('#tcsbOrderList').datagrid('reload');	
							}
					);
				}
			}
	);
}

function receiveOrders(Id){
}


function createwindows(title, addurl,width,height) {
	width = width?width:700;
	height = height?height:400;
	if(width=="100%" || height=="100%"){
		width = window.top.document.body.offsetWidth;
		height =window.top.document.body.offsetHeight-100;
	}
   
		W.$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			zIndex:getzIndex(),
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
				saveObjs();
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		});

    //--author：JueYue---------date：20140427---------for：弹出bug修改,设置了zindex()函数
	
}

function saveObjs() {
	$('#btn_sub', iframe.document).click();
	var aaa = window.setTimeout("refash()",2000);
	
}
function refash(){
	$('#tcsbOrderList').datagrid('reload');	
}

 </script>