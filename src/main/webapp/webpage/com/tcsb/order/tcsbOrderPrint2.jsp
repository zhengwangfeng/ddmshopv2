<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />
<title>订单打印</title>
<link>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css" id="style1">
* {
	padding: 0;
	margin: 0;
	font-size: 12px;
}

h1 {
	font-size: 16px;
}

h3 {
	font-size: 14px;
}

.left {
	float: left;
}

.right {
	float: right;
	padding-right: 10px;
}

.clearfix {
	clear: both;
}

ul {
	list-style: none;
}

.print_container { //
	padding: 20px;
	width: 188px;
}

.section1 {
	
}

.section2 label {
	display: block;
}

.section3 label {
	display: block;
}

.section4 {
	
}

.welcome {
	font-size: 16px;
}

.section4 .total label {
	display: block;
}

.section4 .other_fee {
	border-bottom: 1px solid #DADADA;
}

.section5 label {
	display: block;
}

.section4 table label {
	ffont-size: 10px;
}
</style>
<script language="javascript" src="lodop/LodopFuncs.js"></script>
</head>
<body style="background-color: #fff;">
	<form id="form1">
		<div class="print_container" style='position: relative;'>
			<h1 align="center">${tcsbOrderPage.shopName}</h1>
			<span>*******************************</span>
			<div class="section2">
				<label>桌号:${tcsbOrderPage.deskName}</label></br> <label>订单号:${tcsbOrderPage.orderNo}</label>
				<label>开始时间:<fmt:formatDate
						value='${tcsbOrderPage.createTime}' type="date"
						pattern="yyyy-MM-dd HH:mm:ss" /></label>
			</div>
			<span>*******************************</span>
			<div class="section4">
				<div style="border-bottom: 1px solid #DADADA;">
					<table style="width: 100%;">
						<thead>
							<tr>
								<td width="60%"></font>菜单名称</td>
								<td width="20%">数量</td>
								<td width="20%">金额</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${tcsbOrderPage.tcsbOrderItemPrintVOs}"
								var="orderItem">
								<tr>
									<td><c:if test="${fn:length(orderItem.foodName) > 5 }">
								${fn:substring(orderItem.foodName,0,5)}...
								</c:if> <c:if test="${fn:length(orderItem.foodName )  <= 5 }">
								${orderItem.foodName }
								</c:if></td>
									<td>${orderItem.count }</td>
									<td>${orderItem.price }</td>
								</tr>
								<c:if test="${!empty orderItem.funFoodTaste}">
									<tr>
										<td width="60%"><label
											style="font-size: 10px; margin-left: 10px; margin-bottom: 2px;">(${orderItem.funFoodTaste })</label>
										</td>
									<tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<span>*******************************</span>
				<div class="other_fee">
					<div class="canh">
						<label class="left">总金额：</label> <label class="right">${tcsbOrderPage.totalMoney}</label>
						<div class="clearfix"></div>
					</div>
					</br>
					<div class="canh">
						<label class="left">店铺优惠:</label> <label class="right">${tcsbOrderPage.universalCouponPrice}</label>
						<div class="clearfix"></div>
					</div>
					</br>
					<div class="canh">
						<label class="left">平台优惠:</label> <label class="right">0.0</label>
						<div class="clearfix"></div>
					</div>
					</br>
					<div class="canh">
						<label class="left">实收款:</label> <label class="right">${tcsbOrderPage.finalMoney}</label>
						<div class="clearfix"></div>
					</div>
					</br>
				</div>
				<%-- <div class="total">
				<label class="left">实际支付:</label> <label class="right">${tcsbOrderPage.finalMoney}</label>
				<div class="clearfix"></div>
			</div> --%>
				<!-- <span>*******************************</span> -->
			</div>
			<%-- <div class="section5">
			<h1 align="center" class='welcome'>欢迎关注点单么平台</h1>
			<div align="center"><img alt="点单么公众号二维码" width="150px" height="150px" src="<%=basePath%>/images/diandanme.JPG"></div>
		</div> --%>
		</div>
	</form>
	<div class="section5"
		style='position: absolute; top: 30px; right: 30px;'>
		<div align="center">
			<a class="easyui-linkbutton" id="print" href="javascript:MyPreview()"
				style="font-size: 15px;">预览打印</a>
		</div>
		<div align="center">
			<a class="easyui-linkbutton" id="print" href="javascript:MyPrint()"
				style="font-size: 15px;">直接打印</a>
		</div>
		<div align="center">
			<a class="easyui-linkbutton" id="print"
				href="javascript:MyPRINT_SETUP()()" style="font-size: 15px;">打印维护</a>
		</div>
	</div>
</body>
</html>
<script src="webpage/com/tcsb/order/tcsbOrder.js"></script>
<script type="text/javascript">
var strbodystyle = "<style>" + $("#style1").html() + "</style>";
<!--
function MyPreview() {
	AddTitle();
	LODOP.SET_PRINT_PAGESIZE(3,1385,45,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽1385mm；45表示页底空白4.5mm
	if (LODOP.SET_PRINTER_INDEX(0))
	LODOP.PREVIEW();
};
function MyPrint() {
	AddTitle();
	LODOP.SET_PRINT_PAGESIZE(3,1385,45,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽1385mm；45表示页底空白4.5mm
	if (LODOP.SET_PRINTER_INDEX(1))
	LODOP.PRINT();
};
function MyPRINT_SETUP() {
	AddTitle();
	LODOP.SET_PRINT_PAGESIZE(3,1385,45,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽1385mm；45表示页底空白4.5mm
	LODOP.PRINT_SETUP()();
};
function AddTitle(){	
	LODOP=getLodop();  
	LODOP.PRINT_INIT("订单打印发票");
	//LODOP.SET_PRINT_STYLE("FontSize",12);
	//LODOP.SET_PRINT_STYLE("Bold",1);
	var strbodyhtml = strbodystyle + $("#form1").html();
	LODOP.ADD_PRINT_HTM(15,0,150,1000,strbodyhtml);
	//LODOP.SET_PRINT_STYLE("FontSize",12);
	//LODOP.SET_PRINT_STYLE("Bold",1);
	//LODOP.ADD_PRINT_LINE(72,14,73,510,0,1);
};
//-->
</script>



