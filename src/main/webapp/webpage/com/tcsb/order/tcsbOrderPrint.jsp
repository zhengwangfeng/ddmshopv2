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
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link media="print" rel='stylesheet'
	href="<%=basePath %>/testpay/view.css" type="text/css" />
<link rel='stylesheet' href="<%=basePath %>/testpay/view.css"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>/plug-in/jquery/jquery.jqprint-0.3.js"></script>
<script type="text/javascript">
  function printall(){
		$(".print_container").jqprint({
			 debug: true, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
		     importCSS: true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
		     printContainer: false, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
		     operaSupport: true//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
		});  
	}
	function printview(){
		document.all.WebBrowser1.ExecWB(7,1);
	}
 </script>
</head>
<body style="background-color: #fff;">
	<div class="print_container" style='position: relative;'>
		<h1 align="center">${tcsbOrderPage.shopName}</h1>
		<h3 align="center">电话:${tcsbOrderPage.phone}</h3>
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
				<table>
					<thead>
						<tr>
							<td style="font-size: 8px">品名</td>
							<td style="font-size: 8px">数量</td>
							<!-- <td  style="font-size:8px">单价</td> -->
							<td style="font-size: 8px">单位</td>
							<td style="font-size: 8px">金额</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${tcsbOrderPage.tcsbOrderItemPrintVOs}"
							var="orderItem">
							<tr>
								<%-- <c:if test="${fn:length(orderItem.foodName) > 7 }">
								<td style="font-size:8px">${orderItem.foodName }...</td>
							
							</c:if> 
							<c:if test="${fn:length(orderItem.foodName) <= 7 }">
								<td style="font-size:8px">${orderItem.foodName }</td>
							</c:if>  --%>
								<%--  <td style="font-size:8px">
								${fn:substring(orderItem.foodName,0,7)}...
								</c:if>
								<c:if test="${fn:length(orderItem.foodName )  <= 5 }">
								${orderItem.foodName }
								
								
								</td> --%>
								<%--  --%>
								<td style="font-size: 8px">${orderItem.foodName }</td>
								<td style="font-size: 8px">${orderItem.count }</td>
								<td style="font-size: 8px">${orderItem.price }</td>
								<%-- <td style="font-size:8px">${orderItem.unitName }</td> --%>
								<td style="font-size: 8px"><fmt:formatNumber type="number"
										value="${orderItem.price*orderItem.count }" pattern="0.00"
										maxFractionDigits="2" /></td>
							</tr>
							<c:if test="${!empty orderItem.funFoodTaste}">
								<tr>
									<td style="font-size: 10px"><label
										style="font-size: 8px; margin-bottom: 2px;">(${orderItem.funFoodTaste })</label>
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
					<label class="left">总金额：</label> <label class="right"><fmt:formatNumber
							type="number" value="${tcsbOrderPage.totalMoney}" pattern="0.00"
							maxFractionDigits="2" /> </label>
					<div class="clearfix"></div>
				</div>
				</br>
				<c:if test="${tcsbOrderPage.offlineDiscount!=null}">
					<div class="canh">
						<label class="left">平台打折:</label> <label class="right"><fmt:formatNumber
								type="number" value="${tcsbOrderPage.offlineDiscount}"
								pattern="0.00" maxFractionDigits="2" /></label>
						<div class="clearfix"></div>
					</div>
					</br>
				</c:if>
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
	<div class="section5"
		style='position: absolute; top: 30px; right: 30px;'>
		<div align="center">
			<a class="easyui-linkbutton" id="print" href="javascript:printall()"
				style="font-size: 15px;">打印订单</a>
		</div>
		<!-- 	<div align="center"><a class="easyui-linkbutton" id="print" href="javascript:printhandle()" style="font-size:15px;">直接打印</a></div> -->

	</div>
</body>
</html>
<script src="webpage/com/tcsb/order/tcsbOrder.js"></script>

<script>
printall();
/* function printhandle(){  
	var orderNo = $("#orderNo").val();
	var url = "printController.do?list&orderNo="+orderNo;
	createwindow("消费详情", url,200,100);
 	var orderNo = $("#orderNo").val();
    $.dialog({
        content: 'url:'+"printController.do?list&orderNo="+orderNo,  
        lock : false,  
        width:100,  
        height:100,  
        title:'正在打印',  
        opacity : 0.3,  
        cache:false,  
        cancelVal: '关闭',  
        cancel: true 
    });  
}  */

</script>


