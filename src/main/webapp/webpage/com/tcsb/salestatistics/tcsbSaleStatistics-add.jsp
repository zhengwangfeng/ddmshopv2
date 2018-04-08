<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>系统服务</title>
<t:base type="jquery,easyui,tools,DatePicker,ckfinder,ckeditor"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbSaleStatisticsController.do?send"
		tiptype="1">
		<input id="mobile" name="mobile" type="hidden" value="${mobile }">
		<table style="width: 500px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 短息内容:
				</label></td>
				<td class="value"><textarea id="area" name="content"
						placeholder="请输入文本内容" rows="10" cols="50"></textarea>
					<p>
						剩余字数<span id="text-count">65</span>/65
					</p> <script type="text/javascript">  
					    /*字数限制*/  
					    $("#area").on("input propertychange", function() {  
					        var $this = $(this),  
					            _val = $this.val(),  
					            count = "";  
					        if (_val.length > 65) {  
					            $this.val(_val.substring(0, 65));  
					        }  
					        count = 65 - $this.val().length;  
					        $("#text-count").text(count);  
					    });  
					</script> <!-- <textarea id="content" name="content" rows="10" cols="50"></textarea>
					     	 <input id="content" name="content" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span> --></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/service/tcsbService.js"></script>