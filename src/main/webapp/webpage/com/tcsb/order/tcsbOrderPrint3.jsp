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
			<h1 align="center">
				<span id="shopName">${tcsbOrderPage.shopName}</span>
			</h1>
			<span>*******************************</span>
			<div class="section2">
				<label id="deskNo">桌号:${tcsbOrderPage.deskName}</label></br> <label
					id="isJustNowServing">是否上菜:${tcsbOrderPage.isJustNowServing}</label></br>
				<label id="orderNo">订单号:${tcsbOrderPage.orderNo}</label> <label
					id="startT">下单时间:<fmt:formatDate
						value='${tcsbOrderPage.createTime}' type="date"
						pattern="yyyy-MM-dd HH:mm:ss" /></label> <label id="note">备注:${tcsbOrderPage.note}</label>
			</div>
			<span>*******************************</span>
			<div class="section4">
				<div style="border-bottom: 1px solid #DADADA;">
					<c:forEach items="${tcsbOrderPage.tcsbPrinterEntities}"
						var="printItem" varStatus="psta">
						<%-- <div>打印机索引:${printItem.printIndex }打印机名称 :${printItem.name }"</div> --%>
						<table border="1px" style="width: 100%;"
							style='position: relative;' title="${psta.count}"
							name="printTable" id="table${psta.count }">
							<input type="hidden" value="${printItem.printIndex }"
								name="printIndex" />
							<input type="hidden" value="${printItem.printNum }"
								name="printNum" />
							<input type="hidden" value="${printItem.printChild }"
								name="printChild" />
							<thead>
								<tr>
									<td width="60%"></font>菜单名称</td>
									<td width="20%">数量</td>
									<td width="20%">金额</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${printItem.tcsbOrderItemPrintVOs}"
									var="orderItem" varStatus="sta">
									<tr class="rows">
										<%-- <td>${sta.count}</td> --%>
										<%-- <td   width="4%" height="25"><input type="checkbox" style ="display:none" id="CK${sta.count}" value="ON" checked onClick="ReSumMoney()"></td>
								<td> --%>
										<%-- <input type="hidden" style ="display:none" id="CK${sta.count}"  onClick="ReSumMoney()"> --%>
										<%--<td>
								 <c:if test="${fn:length(orderItem.foodName) > 5 }">
								<input type="text"  id="MC${sta.count}" value="${fn:substring(orderItem.foodName,0,5)}..." style="text-align:center;border:0px;background-color:#dae2ed" >
								</c:if>
								<c:if test="${fn:length(orderItem.foodName )  <= 5 }">
								<input type="text"  id="MC${sta.count}" value="${orderItem.foodName }" style="text-align:center;border:0px;background-color:#dae2ed" >
								</c:if> 
								</td>--%>
										<td><input type="text" id="MC${sta.count}"
											value="${orderItem.foodName }"
											style="text-align: center; border: 0px; background-color: #dae2ed"></td>
										<td><input type="text" id="SL${sta.count}"
											value="${orderItem.count }(${orderItem.unitName })"
											style="text-align: center; border: 0px; background-color: #dae2ed"></td>
										<td><input type="text" id="DJ${sta.count}"
											value="${orderItem.price }"
											style="text-align: center; border: 0px; background-color: #dae2ed"></td>
									</tr>
									<c:if test="${!empty orderItem.funFoodTaste}">
										<tr>
											<td width="60%"><label
												style="font-size: 10px; margin-left: 10px; margin-bottom: 2px;">
													<input type="text" id="GG${sta.count}"
													value="(${orderItem.funFoodTaste })"
													style="text-align: center; border: 0px; background-color: #dae2ed">
											</label></td>
										<tr>
									</c:if>
								</c:forEach>
								<tr>
									<td>
										<div class="section5">
											<div align="center">
												<a class="easyui-linkbutton" name="printPreview"
													href="javascript:MyPreview('${psta.count }','${printItem.printIndex }')"
													style="font-size: 15px;">预览打印</a>
											</div>
											<div align="center">
												<a class="easyui-linkbutton" name="print"
													href="javascript:MyPrint('${psta.count }','${printItem.printIndex }')"
													style="font-size: 15px;">直接打印</a>
											</div>
											<%-- <div align="center"><a class="easyui-linkbutton" name="print" href="javascript:MyPRINT_SETUP('${psta.count }','${printItem.printIndex }')()" style="font-size:15px;">打印维护</a></div> --%>
										</div>
									<td>
								</tr>
							</tbody>
						</table>
					</c:forEach>
				</div>
				<span>*******************************</span>
				<%-- <div class="other_fee">
				<div class="canh">
					<label class="left">总金额：</label> <label class="right">${tcsbOrderPage.totalMoney}</label>
					<div class="clearfix"></div>
				</div>
				</br>
			</div> --%>
			</div>
		</div>
	</form>
	<div class="section5"
		style='position: absolute; top: 30px; right: 30px;'>
		<!-- <div align="center"><a class="easyui-linkbutton" id="print" href="javascript:MyPreviewAll()" style="font-size:15px;">预览打印</a></div>  -->
		<div align="center">
			<a class="easyui-linkbutton" id="print"
				href="javascript:MyPrintAll()" style="font-size: 15px;">直接打印</a>
		</div>
		<!-- <div align="center"><a class="easyui-linkbutton" id="print" href="javascript:MyPRINT_SETUP()()" style="font-size:15px;">打印维护</a></div>  -->
	</div>
</body>
</html>
<script src="webpage/com/tcsb/order/tcsbOrder.js"></script>
<script type="text/javascript">

var strbodystyle = "<style>" + $("#style1").html() + "</style>";
<!--
function MyPreview(obj,intPrinterIndex) {
	var shopName = $("#shopName").html();
	var deskNo = $("#deskNo").html();
	var orderNo = $("#orderNo").html();
	var startT = $("#startT").html();
	AddTitleByselect();
	var iCurLine=140;//标题行之后的数据从位置80px开始打印
	var len = $("#table"+obj).find("tr[class='rows']").length;
	var styleIndex =9;
	for(i = 1;i<len+1;i++){
		LODOP.ADD_PRINT_TEXT(iCurLine,0,100,20,$("#table"+obj).find("#MC"+i).val());
		LODOP.ADD_PRINT_TEXT(iCurLine,100,100,20,$("#table"+obj).find("#SL"+i).val());
		LODOP.ADD_PRINT_TEXT(iCurLine,150,100,20,$("#table"+obj).find("#DJ"+i).val());
		styleIndex=styleIndex+3;
		if($("#table"+obj).find("#GG"+i).val()!=undefined){
			LODOP.ADD_PRINT_TEXT(iCurLine+25,0,100,20,$("#table"+obj).find("#GG"+i).val());
			styleIndex=styleIndex+1;
			iCurLine=iCurLine+25+25;//每行占25px 
		}
		if($("#table"+obj).find("#GG"+i).val()==undefined){
			iCurLine=iCurLine+25;//每行占25px 
		}
	}
	//分页打印开始
	var printChild = $("#table"+obj).find("input[name='printChild']").val();
	if(printChild==1){
		 for(i = 1;i<len+1;i++){
			 LODOP.NewPage();
				/* LODOP.ADD_PRINT_TEXT(15,15,355,30,shopName);
				LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",13);
				LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex=styleIndex+1;
				LODOP.ADD_PRINT_LINE(40,14,40,510,0,1);
				styleIndex=styleIndex+1; */
				LODOP.ADD_PRINT_TEXT(0,15,250,20,deskNo);
				LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",12);
				LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex=styleIndex+1;
				/* LODOP.ADD_PRINT_TEXT(90,15,250,20,orderNo);
				LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",12);
				LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex=styleIndex+1;
				LODOP.ADD_PRINT_TEXT(115,15,250,20,startT);
				LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",12);
				LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex=styleIndex+1; */
				
				LODOP.ADD_PRINT_TEXT(25,15,100,20,"菜单名称");
				LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
				LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex=styleIndex+1;
				LODOP.ADD_PRINT_TEXT(25,100,100,20,"数量");
				LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
				LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex=styleIndex+1;
				LODOP.ADD_PRINT_TEXT(25,150,100,20,"金额");
				LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
				LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex=styleIndex+1;
				LODOP.ADD_PRINT_TEXT(50,0,100,20,$("#table"+obj).find("#MC"+i).val());
				LODOP.ADD_PRINT_TEXT(50,100,100,20,$("#table"+obj).find("#SL"+i).val());
				LODOP.ADD_PRINT_TEXT(50,150,100,20,$("#table"+obj).find("#DJ"+i).val());
				styleIndex=styleIndex+3;
				if($("#table"+obj).find("#GG"+i).val()!=undefined){
					LODOP.ADD_PRINT_TEXT(75,0,100,20,$("#table"+obj).find("#GG"+i).val());
				    LODOP.ADD_PRINT_LINE(100,14,100,510,0,1);
				    styleIndex=styleIndex+2;
				}else{
					LODOP.ADD_PRINT_LINE(75,14,75,510,0,1);
					styleIndex=styleIndex+1;
				}
				LODOP.SET_PRINT_PAGESIZE(3,1385,45,"");
			}
	}
	//LODOP.ADD_PRINT_LINE(iCurLine,14,iCurLine,510,0,1);
	//LODOP.ADD_PRINT_TEXT(iCurLine+5,0,300,20,"打印时间："+(new Date()).toLocaleDateString()+" "+(new Date()).toLocaleTimeString());
	LODOP.SET_PRINT_PAGESIZE(3,1385,25,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽1385mm；25表示页底空白2.5mm
	if (LODOP.SET_PRINTER_INDEX(intPrinterIndex))
	LODOP.PREVIEW();
};
function MyPrint(obj,intPrinterIndex) {   
	var shopName = $("#shopName").html();
	var deskNo = $("#deskNo").html();
	var orderNo = $("#orderNo").html();
	var startT = $("#startT").html();
	var note = $("#note").html();
	AddTitleByselect();
	var iCurLine=140;//标题行之后的数据从位置80px开始打印
	var len = $("#table"+obj).find("tr[class='rows']").length;
	var styleIndex =9;
	for(i = 1;i<len+1;i++){
		LODOP.ADD_PRINT_TEXT(iCurLine,0,100,20,$("#table"+obj).find("#MC"+i).val());
		LODOP.ADD_PRINT_TEXT(iCurLine,100,100,20,$("#table"+obj).find("#SL"+i).val());
		LODOP.ADD_PRINT_TEXT(iCurLine,150,100,20,$("#table"+obj).find("#DJ"+i).val());
		styleIndex=styleIndex+3;
		if($("#table"+obj).find("#GG"+i).val()!=undefined){
			LODOP.ADD_PRINT_TEXT(iCurLine+25,0,100,20,$("#table"+obj).find("#GG"+i).val());
			styleIndex=styleIndex+1;
			iCurLine=iCurLine+25+25;//每行占25px 
		}
		if($("#table"+obj).find("#GG"+i).val()==undefined){
			iCurLine=iCurLine+25;//每行占25px 
		}
	}
	LODOP.SET_PRINT_PAGESIZE(3,1385,25,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽1385mm；25表示页底空白2.5mm
	if (LODOP.SET_PRINTER_INDEX(intPrinterIndex))
		LODOP.PRINT();
	//主单和子单要分开来打印避免浪费纸的情况（分别创建对象来独立打印）
	LODOPchild=getLodop(); 
	styleIndex = 1;
	//分页打印开始
	var printChild = $("#table"+obj).find("input[name='printChild']").val();
	if(printChild==1){
		 for(i = 1;i<len+1;i++){
			 	LODOPchild.NewPage();
				/* LODOP.ADD_PRINT_TEXT(15,15,355,30,shopName);
				LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",13);
				LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex=styleIndex+1;
				LODOP.ADD_PRINT_LINE(40,14,40,510,0,1);
				styleIndex=styleIndex+1; */
				LODOPchild.ADD_PRINT_TEXT(0,15,250,20,deskNo);
				LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",12);
				LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex=styleIndex+1;
				/* LODOP.ADD_PRINT_TEXT(90,15,250,20,orderNo);
				LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",12);
				LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex=styleIndex+1;
				LODOP.ADD_PRINT_TEXT(115,15,250,20,startT);
				LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",12);
				LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex=styleIndex+1; */
				
				LODOPchild.ADD_PRINT_TEXT(25,15,100,20,"菜单名称");
				LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
				LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex=styleIndex+1;
				LODOPchild.ADD_PRINT_TEXT(25,100,100,20,"数量");
				LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
				LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex=styleIndex+1;
				LODOPchild.ADD_PRINT_TEXT(25,150,100,20,"金额");
				LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
				LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex=styleIndex+1;
				LODOPchild.ADD_PRINT_TEXT(50,0,100,20,$("#table"+obj).find("#MC"+i).val());
				LODOPchild.ADD_PRINT_TEXT(50,100,100,20,$("#table"+obj).find("#SL"+i).val());
				LODOPchild.ADD_PRINT_TEXT(50,150,100,20,$("#table"+obj).find("#DJ"+i).val());
				styleIndex=styleIndex+3;
				if($("#table"+obj).find("#GG"+i).val()!=undefined){
					LODOPchild.ADD_PRINT_TEXT(75,0,100,20,$("#table"+obj).find("#GG"+i).val());
				    LODOPchild.ADD_PRINT_LINE(100,14,100,510,0,1);
				    styleIndex=styleIndex+2;
				}else{
					LODOPchild.ADD_PRINT_LINE(75,14,75,510,0,1);
					styleIndex=styleIndex+1;
				}
				LODOPchild.SET_PRINT_PAGESIZE(3,1385,25,"");
			}
	}
	//LODOP.ADD_PRINT_LINE(iCurLine,14,iCurLine,510,0,1);
	//LODOP.ADD_PRINT_TEXT(iCurLine+5,0,300,20,"打印时间："+(new Date()).toLocaleDateString()+" "+(new Date()).toLocaleTimeString());
	//LODOP.SET_PRINT_PAGESIZE(3,1385,25,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽1385mm；25表示页底空白2.5mm
	if (LODOPchild.SET_PRINTER_INDEX(intPrinterIndex))
	LODOPchild.PRINT();
};
/* function MyPRINT_SETUP(obj,intPrinterIndex) {
	AddTitleByselect();
	LODOP.SET_PRINT_PAGESIZE(3,1385,45,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽1385mm；45表示页底空白4.5mm
	if (LODOP.SET_PRINTER_INDEX(intPrinterIndex))
	LODOP.PRINT_SETUP()();
}; 
function AddTitle(){	
	LODOP=getLodop();  
	LODOP.PRINT_INIT("订单打印发票");
	LODOP.SET_PRINT_STYLE("FontSize",12);
	LODOP.SET_PRINT_STYLE("Bold",1);
	//添加样式
	var strbodyhtml = strbodystyle + $("#form1").html();
	LODOP.ADD_PRINT_HTM(15,0,150,1000,strbodyhtml);
	LODOP.SET_PRINT_STYLE("FontSize",12);
	LODOP.SET_PRINT_STYLE("Bold",1);
	LODOP.ADD_PRINT_LINE(72,14,73,510,0,1);
}; */
function AddTitleByselect(){
 	var shopName = $("#shopName").html();
	var deskNo = $("#deskNo").html();
	var orderNo = $("#orderNo").html();
	var startT = $("#startT").html();  
	LODOP=getLodop();  
	//LODOP.PRINT_INIT("订单打印发票");
	LODOP.ADD_PRINT_TEXT(0,15,355,30,shopName);
	LODOP.SET_PRINT_STYLEA(1,"FontSize",13);
	LODOP.SET_PRINT_STYLEA(1,"Bold",1);
	
	LODOP.ADD_PRINT_LINE(25,14,25,510,0,1);
	LODOP.ADD_PRINT_TEXT(40,15,250,20,deskNo);
	LODOP.SET_PRINT_STYLEA(3,"FontSize",12);
	LODOP.SET_PRINT_STYLEA(3,"Bold",1);
	LODOP.ADD_PRINT_TEXT(65,15,250,20,orderNo);
	LODOP.SET_PRINT_STYLEA(4,"FontSize",10);
	LODOP.SET_PRINT_STYLEA(4,"Bold",1);
	LODOP.ADD_PRINT_TEXT(90,15,250,20,startT); 
	LODOP.SET_PRINT_STYLEA(5,"FontSize",10);
	LODOP.SET_PRINT_STYLEA(5,"Bold",1);
	
	LODOP.ADD_PRINT_TEXT(115,15,100,20,"菜单名称");
	LODOP.SET_PRINT_STYLEA(6,"FontSize",10);
	LODOP.SET_PRINT_STYLEA(6,"Bold",1);
	LODOP.ADD_PRINT_TEXT(115,100,100,20,"数量");
	LODOP.SET_PRINT_STYLEA(7,"FontSize",10);
	LODOP.SET_PRINT_STYLEA(7,"Bold",1);
	LODOP.ADD_PRINT_TEXT(115,150,100,20,"金额");
	LODOP.SET_PRINT_STYLEA(8,"FontSize",10);
	LODOP.SET_PRINT_STYLEA(8,"Bold",1);
};
function MyPreviewAll(){
	var shopName = $("#shopName").html();
	var deskNo = $("#deskNo").html();
	var orderNo = $("#orderNo").html();
	var startT = $("#startT").html(); 
	var isJustNowServing = $("#isJustNowServing").html();
	//获得所有的打印机
		$("table[name='printTable']").each(function(i){
		LODOP=getLodop();  
		LODOP.PRINT_INIT("订单打印发票");
		
		LODOP.ADD_PRINT_TEXT(5,15,355,30,shopName);
		LODOP.SET_PRINT_STYLEA(1,"FontSize",13);
		LODOP.SET_PRINT_STYLEA(1,"Bold",1);
		
		LODOP.ADD_PRINT_LINE(30,14,40,510,0,1);
		LODOP.ADD_PRINT_TEXT(55,15,250,20,deskNo);
		LODOP.SET_PRINT_STYLEA(3,"FontSize",12);
		LODOP.SET_PRINT_STYLEA(3,"Bold",1);
		LODOP.ADD_PRINT_TEXT(80,15,250,20,isJustNowServing);
		LODOP.SET_PRINT_STYLEA(4,"FontSize",12);
		LODOP.SET_PRINT_STYLEA(4,"Bold",1);
		LODOP.ADD_PRINT_TEXT(105,15,250,20,orderNo);
		LODOP.SET_PRINT_STYLEA(5,"FontSize",12);
		LODOP.SET_PRINT_STYLEA(5,"Bold",1);
		LODOP.ADD_PRINT_TEXT(130,15,250,20,startT); 
		LODOP.SET_PRINT_STYLEA(6,"FontSize",12);
		LODOP.SET_PRINT_STYLEA(6,"Bold",1);
		
		LODOP.ADD_PRINT_TEXT(155,15,100,20,"菜单名称");
		LODOP.SET_PRINT_STYLEA(7,"FontSize",10);
		LODOP.SET_PRINT_STYLEA(7,"Bold",1);
		LODOP.ADD_PRINT_TEXT(155,100,100,20,"数量");
		LODOP.SET_PRINT_STYLEA(8,"FontSize",10);
		LODOP.SET_PRINT_STYLEA(8,"Bold",1);
		LODOP.ADD_PRINT_TEXT(155,150,100,20,"金额");
		LODOP.SET_PRINT_STYLEA(9,"FontSize",10);
		LODOP.SET_PRINT_STYLEA(9,"Bold",1);
		var styleIndex =10;
		
		var intPrinterIndex = $(this).find("input[name='printIndex']").val();
		var printChild = $(this).find("input[name='printChild']").val();
		var iCurLine=180;//标题行之后的数据从位置165px开始打印
		var len = $(this).find("tr[class='rows']").length;
		 for(i = 1;i<len+1;i++){
			LODOP.ADD_PRINT_TEXT(iCurLine,0,100,20,$(this).find("#MC"+i).val());
			LODOP.ADD_PRINT_TEXT(iCurLine,100,100,20,$(this).find("#SL"+i).val());
			LODOP.ADD_PRINT_TEXT(iCurLine,150,100,20,$(this).find("#DJ"+i).val());
			styleIndex=styleIndex+3;
			if($(this).find("#GG"+i).val()!=undefined){
				LODOP.ADD_PRINT_TEXT(iCurLine+25,0,100,20,$(this).find("#GG"+i).val());
				styleIndex=styleIndex+1;
				iCurLine=iCurLine+25+25;//每行占25px 
			}
			if($(this).find("#GG"+i).val()==undefined){
				iCurLine=iCurLine+25;//每行占25px 
			}
		} 
		//LODOP.ADD_PRINT_LINE(iCurLine+25,14,iCurLine+25,510,0,1);
		//分页打印开始
		
		if(printChild==1){
			  for(i = 1;i<len+1;i++){
					LODOP.NewPage();
					/* LODOP.ADD_PRINT_TEXT(15,15,355,30,shopName);
					LODOP.SET_PRINT_STYLEA(1,"FontSize",13);
					LODOP.SET_PRINT_STYLEA(1,"Bold",1);
					
					LODOP.ADD_PRINT_LINE(40,14,40,510,0,1); */
					//styleIndex=styleIndex+2;
					LODOP.ADD_PRINT_TEXT(5,15,250,20,deskNo);
					LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",12);
					LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					styleIndex=styleIndex+1;
					//LODOP.ADD_PRINT_TEXT(90,15,250,20,orderNo);
					//LODOP.ADD_PRINT_TEXT(115,15,250,20,startT); 
					LODOP.ADD_PRINT_TEXT(40,15,100,20,"菜单名称");
					LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
					LODOP.SET_PRINT_STYLEA(2,"Bold",1);
					styleIndex=styleIndex+1;
					LODOP.ADD_PRINT_TEXT(40,100,100,20,"数量");
					LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",10);  
					LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					styleIndex=styleIndex+1;
					LODOP.ADD_PRINT_TEXT(40,150,100,20,"金额");
					LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
					LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					styleIndex=styleIndex+1;
					LODOP.ADD_PRINT_TEXT(65,0,100,20,$(this).find("#MC"+i).val());
					LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
					LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					styleIndex=styleIndex+1;
					LODOP.ADD_PRINT_TEXT(65,100,100,20,$(this).find("#SL"+i).val());
					LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
					LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					styleIndex=styleIndex+1;
					LODOP.ADD_PRINT_TEXT(65,150,100,20,$(this).find("#DJ"+i).val());
					LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
					LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					styleIndex=styleIndex+1;
					//var iCurLine=140;//标题行之后的数据从位置165px开始打印
					if($(this).find("#GG"+i).val()!=undefined){
						LODOP.ADD_PRINT_TEXT(90,0,100,20,$(this).find("#GG"+i).val());
						styleIndex=styleIndex+1;
						iCurLine=iCurLine+25+25;//每行占25px 
					}
					if($(this).find("#GG"+i).val()==undefined){
						iCurLine=iCurLine+25;//每行占25px 
					} 
					//LODOP.ADD_PRINT_LINE(190,14,190,510,0,1);
				} 
		}
		//LODOP.ADD_PRINT_LINE(iCurLine,14,iCurLine,510,0,1);
		//LODOP.ADD_PRINT_TEXT(iCurLine+5,0,300,20,"打印时间："+(new Date()).toLocaleDateString()+" "+(new Date()).toLocaleTimeString());
		LODOP.SET_PRINT_PAGESIZE(3,1385,45,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽1385mm；45表示页底空白4.5mm
		LODOP.SET_PRINTER_INDEX(intPrinterIndex);
		LODOP.PREVIEW();
	})
}
function MyPrintAll(){
	var shopName = $("#shopName").html();
	var deskNo = $("#deskNo").html();
	var orderNo = $("#orderNo").html();
	var startT = $("#startT").html(); 
	var note = $("#note").html();
	var isJustNowServing = $("#isJustNowServing").html();
	//获得所有的打印机
		$("table[name='printTable']").each(function(i){
			
			
			var intPrinterNum = $(this).find("input[name='printNum']").val();
			//打印主单的份数
			for(j=0;j<parseInt(intPrinterNum);j++){
				var temp = "LODOP"+j;
		temp=getLodop();  
		temp.PRINT_INIT("订单打印发票");
		
		temp.ADD_PRINT_TEXT(5,15,355,30,shopName);
		temp.SET_PRINT_STYLEA(1,"FontSize",16);
		temp.SET_PRINT_STYLEA(1,"Bold",1);
		
		temp.ADD_PRINT_LINE(30,14,30,510,0,1);
		temp.ADD_PRINT_TEXT(55,15,250,20,deskNo);
		temp.SET_PRINT_STYLEA(3,"FontSize",13);
		temp.SET_PRINT_STYLEA(3,"Bold",1);
		temp.ADD_PRINT_TEXT(80,15,250,20,isJustNowServing);
		temp.SET_PRINT_STYLEA(4,"FontSize",13);
		temp.SET_PRINT_STYLEA(4,"Bold",1);
		temp.ADD_PRINT_TEXT(105,15,250,20,orderNo);
		temp.SET_PRINT_STYLEA(5,"FontSize",10);
		temp.SET_PRINT_STYLEA(5,"Bold",1);
		temp.ADD_PRINT_TEXT(130,15,250,20,startT); 
		temp.SET_PRINT_STYLEA(6,"FontSize",10);
		temp.SET_PRINT_STYLEA(6,"Bold",1);
		//temp.ADD_PRINT_LINE(155,14,155,510,0,1);
		temp.ADD_PRINT_TEXT(155,15,250,20,note); 
		temp.SET_PRINT_STYLEA(7,"FontSize",10);
		temp.SET_PRINT_STYLEA(7,"Bold",1);
		temp.ADD_PRINT_LINE(180,14,180,510,0,1);
		
		temp.ADD_PRINT_TEXT(205,15,100,20,"菜单名称");
		temp.SET_PRINT_STYLEA(9,"FontSize",13);
		temp.SET_PRINT_STYLEA(9,"Bold",1);
		temp.ADD_PRINT_TEXT(205,130,100,20,"数量");
		temp.SET_PRINT_STYLEA(10,"FontSize",13);
		temp.SET_PRINT_STYLEA(10,"Bold",1);
		temp.ADD_PRINT_TEXT(205,170,100,20,""); 
		temp.SET_PRINT_STYLEA(11,"FontSize",13);
		temp.SET_PRINT_STYLEA(11,"Bold",1); 
		var styleIndex =12;
		
		var intPrinterIndex = $(this).find("input[name='printIndex']").val();
		
		var iCurLine=205;//标题行之后的数据从位置165px开始打印
		var len = $(this).find("tr[class='rows']").length;
		 for(i = 1;i<len+1;i++){
			temp.ADD_PRINT_TEXT(iCurLine,0,100,20,$(this).find("#MC"+i).val());
			temp.SET_PRINT_STYLEA(styleIndex,"FontSize",13);
			temp.SET_PRINT_STYLEA(styleIndex,"Bold",1);
			styleIndex = styleIndex+1;
			temp.ADD_PRINT_TEXT(iCurLine,120,100,20,$(this).find("#SL"+i).val());
			temp.SET_PRINT_STYLEA(styleIndex,"FontSize",13);
			temp.SET_PRINT_STYLEA(styleIndex,"Bold",1);
			styleIndex = styleIndex+1;
			/* temp.ADD_PRINT_TEXT(iCurLine,170,100,20,$(this).find("#DJ"+i).val());
			temp.SET_PRINT_STYLEA(styleIndex,"FontSize",11);
			temp.SET_PRINT_STYLEA(styleIndex,"Bold",1);
			styleIndex = styleIndex+1; */
			if($(this).find("#GG"+i).val()!=undefined){
				temp.ADD_PRINT_TEXT(iCurLine+25,0,130,20,$(this).find("#GG"+i).val());
				temp.SET_PRINT_STYLEA(styleIndex,"FontSize",13);
				temp.SET_PRINT_STYLEA(styleIndex,"Bold",1);
				styleIndex = styleIndex+1;
				iCurLine=iCurLine+25+25;//每行占25px 
			}
			if($(this).find("#GG"+i).val()==undefined){
				iCurLine=iCurLine+25;//每行占25px 
			}
		} 
		temp.SET_PRINT_PAGESIZE(3,1385,45,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽1385mm；45表示页底空白4.5mm
		temp.SET_PRINTER_INDEX(intPrinterIndex);
		temp.PRINT();
			
			
		}
		//LODOP.ADD_PRINT_LINE(iCurLine+25,14,iCurLine+25,510,0,1);
		//主单和子单要分开来打印避免浪费纸的情况（分别创建对象来独立打印）
		LODOPchild=getLodop();  
		LODOPchild.PRINT_INIT("订单打印子发票");
		styleIndex = 1;
		var printChild = $(this).find("input[name='printChild']").val();
		//分页打印开始
		if(printChild==1){
			  for(i = 1;i<len+1;i++){
					LODOPchild.NewPage();
					/* LODOPchild.ADD_PRINT_TEXT(15,15,355,30,shopName);
					LODOPchild.SET_PRINT_STYLEA(1,"FontSize",13);
					LODOPchild.SET_PRINT_STYLEA(1,"Bold",1);
					
					LODOPchild.ADD_PRINT_LINE(40,14,40,510,0,1); */
					//styleIndex=styleIndex+2;
					LODOPchild.ADD_PRINT_TEXT(0,15,250,20,deskNo);
					LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",13);
					LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					styleIndex=styleIndex+1;
					//LODOPchild.ADD_PRINT_TEXT(90,15,250,20,orderNo);
					LODOPchild.ADD_PRINT_TEXT(25,15,250,20,startT); 
					LODOP.SET_PRINT_STYLEA(styleIndex,"FontSize",10);
					LODOP.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					LODOPchild.ADD_PRINT_LINE(50,14,50,510,0,1);
					styleIndex=styleIndex+2;
				/* 	LODOPchild.ADD_PRINT_TEXT(50,15,100,20,"菜单名称");
					LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",13);
					LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					styleIndex=styleIndex+1;
					LODOPchild.ADD_PRINT_TEXT(50,100,100,20,"数量");
					LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",13);  
					LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					styleIndex=styleIndex+1;
					LODOPchild.ADD_PRINT_TEXT(50,150,100,20,"金额");
					LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",13);
					LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					styleIndex=styleIndex+1; */
					LODOPchild.ADD_PRINT_TEXT(75,0,100,20,$(this).find("#MC"+i).val());
					LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",11);
					LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					styleIndex=styleIndex+1;
					LODOPchild.ADD_PRINT_TEXT(75,150,100,20,$(this).find("#SL"+i).val());
					LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",11);
					LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					styleIndex=styleIndex+1;
					/* LODOPchild.ADD_PRINT_TEXT(75,150,100,20,$(this).find("#DJ"+i).val());
					LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",11);
					LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
					styleIndex=styleIndex+1; */
					//var iCurLine=140;//标题行之后的数据从位置165px开始打印
					if($(this).find("#GG"+i).val()!=undefined){
						LODOPchild.ADD_PRINT_TEXT(100,0,100,20,$(this).find("#GG"+i).val());
						LODOPchild.SET_PRINT_STYLEA(styleIndex,"FontSize",11);
						LODOPchild.SET_PRINT_STYLEA(styleIndex,"Bold",1);
						 LODOPchild.ADD_PRINT_LINE(125,14,115,510,0,1);
						styleIndex = styleIndex+2;
						//iCurLine=iCurLine+25+25;//每行占25px 
					}
					else{
						 LODOPchild.ADD_PRINT_LINE(100,14,100,510,0,1);
						 styleIndex = styleIndex+1;
						//iCurLine=iCurLine+25;//每行占25px 
					} 
					//LODOPchild.ADD_PRINT_LINE(190,14,190,510,0,1);
				} 
		}
		//LODOPchild.ADD_PRINT_LINE(iCurLine,14,iCurLine,510,0,1);
		//LODOPchild.ADD_PRINT_TEXT(iCurLine+5,0,300,20,"打印时间："+(new Date()).toLocaleDateString()+" "+(new Date()).toLocaleTimeString());
		LODOPchild.SET_PRINT_PAGESIZE(3,1385,45,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽1385mm；45表示页底空白4.5mm
		LODOPchild.SET_PRINTER_INDEX(intPrinterIndex);
		LODOPchild.PRINT();
	})
}
//-->
</script>



