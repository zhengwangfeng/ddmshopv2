<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>打印机设置</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script language="javascript" src="lodop/LodopFuncs.js"></script>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbPrinterController.do?doAdd" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tcsbPrinterPage.id }">
		<%-- <input id="printIndex" name="printIndex" type="hidden" value="${tcsbPrinterPage.printIndex }">
					<input id="name" name="name" type="hidden" value="${tcsbPrinterPage.name }"> --%>
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbPrinterPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbPrinterPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbPrinterPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbPrinterPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbPrinterPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbPrinterPage.createDate }">

		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<!-- <tr>
					<td align="right">
						<label class="Validform_label">
							打印机索引:
						</label>
					</td>
					<td class="value">
					     	 <input id=printIndex name="printIndex" type="text" style="width: 150px" class="inputxt" datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">打印机索引</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							打印机名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt" datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">打印机名称</label>
						</td>
				</tr> -->
			<%-- <input id="printIndex" name="printIndex" type="hidden" value="${tcsbPrinterPage.printIndex }">
				<input id="name" name="name" type="hidden" value="${tcsbPrinterPage.name }"> --%>
			<input type="button" name="init" id="init" value="获取打印机信息"
				onclick="getPrintInfo()" />
		</table>
	</t:formvalid>
</body>
<script type="text/javascript" src="lodop/myLodop.js"></script>
<script type="text/javascript">
  function getPrintInfo(){
	  var tr ="";
	  var num = getPrinterCount();
	 for(var i =0;i<num;i++){
		var name =getPrinterName(i);
		tr+="<tr>"+
		"<td align='right'><label class='Validform_label'>打印机索引:</label></td>"+
		"<td class='value'>"+
		"<input id='printIndex' name='printList["+parseInt(i)+"].printIndex' type='text' style='width: 150px' value='"+i+"' class='inputxt'>"+
		"<span class='Validform_checktip'></span><label class='Validform_label' style='display: none;''>打印机索引</label></td>"+
		"<td align='right'><label class='Validform_label'>打印机名称:</label></td>"+
		"<td class='value'>"+
		"<input id='name' name='printList["+parseInt(i)+"].name' type='text' style='width: 150px' value='"+name+"' class='inputxt'>"+
		"<span class='Validform_checktip'></span><label class='Validform_label' style='display: none;''>打印机名称</label></td>"+
		"</tr>";
	 }
		$("table").html(tr);
  }
</script>
<script src="webpage/com/tcsb/printer/tcsbPrinter.js"></script>