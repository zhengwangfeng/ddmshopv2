<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>满减模版</title>
<style>
ul {
	list-style-type: none;
}

li {
	display: inline-block;
}

li {
	margin: 10px 0;
}

input.labelauty+label {
	font: 12px "Microsoft Yahei";
}
</style>
<t:base type="jquery,easyui,tools,DatePicker,Labelauty"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbFullcutTemplateController.do?doSendAdd">
		<input id="id" name="id" type="hidden"
			value="${tcsbFullcutTemplatePage.id }">
		<input id="shopId" name="shopId" type="hidden"
			value="${tcsbFullcutTemplatePage.shopId }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbFullcutTemplatePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbFullcutTemplatePage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbFullcutTemplatePage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbFullcutTemplatePage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbFullcutTemplatePage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbFullcutTemplatePage.createDate }">
		<input id="userids" name="userids" type="hidden" value="${userids }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 满额: </label>
				</td>
				<td class="value"><input id="total" name="total" type="text"
					style="width: 150px" class="inputxt" datatype="n"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">满额</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 立减: </label>
				</td>
				<td class="value"><input id="discount" name="discount"
					type="text" style="width: 150px" class="inputxt" datatype="n">
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">立减</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 使用期限:
				</label></td>
				<td class="value"><input id="usePeriod" name="usePeriod"
					type="text" style="width: 150px" class="inputxt" datatype="n">
					<t:dictSelect field="dateUnit" type="select" datatype="*"
						typeGroupCode="dateUnit" hasLabel="false" title=""></t:dictSelect>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">日期单位</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 使用范围:
				</label></td>
				<td class="value"><select id="useRange" name="useRange"
					datatype="*">
						<option value="0" selected="selected">本店铺</option>
						<option value="1">某菜品</option>
				</select> <span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">满减模版</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 发送模式:
				</label></td>
				<td class="value">
					<ul class="dowebok">
						<li><input type="radio" name="isShow" value="1"
							data-labelauty="保存并发送" checked="checked"></li>
						<li><input type="radio" name="isShow" value="0"
							data-labelauty="只发送 "></li>
						<li><input type="radio" name="isShow" value="2"
							data-labelauty="不发送只保存到模板库"></li>
					</ul>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>

<script src="webpage/com/tcsb/fullcuttemplate/tcsbFullcutTemplate.js"></script>
<script type="text/javascript">
	   $(function(){
		   $("#formobj").find("select[name='dateUnit']").css("width","100");
	   });
	  //编写自定义JS代码
	   $("select[name='useRange']").change(function(){
		if($(this).val()=='0'){
			//本店铺
			$(this).parent().parent().next().remove();
			$(this).parent().parent().next().remove();
		}else{
			//获得菜品
			var url = "tcsbFoodTypeController.do?getFoodType";
			 $.ajax({
	            url:url,
	            type:"GET",
	            dataType:"JSON",
	            success:function(data){
	                if(data.success){
	                	var d = data.obj;
	                	var trtemp = "<tr>"+
	        			"<td align='right'><label class='Validform_label'>菜品:</label></td>"+
	        			"<td class='value'><select id='foodTypeId' name='foodTypeId' datatype='*' onchange='typeChange(this)'>";
	        			var option="";
	                   for(var i in d){
	                	   option = option+"<option value="+d[i].id+">"+d[i].name+"</option>"+"";
	                   }
		                var tr = trtemp+option+
		       			"</select>"+
		       			"<span class='Validform_checktip'></span><label class='Validform_label' style='display: none;'>菜品</label>"+
		       			"</td></tr>";
		       		 	$("select[name='useRange']").parent().parent().after(tr);
		       		 		var url1 = "tcsbFoodController.do?getFoodByFoodTypeId";
			       		 	$.ajax({
			 	            url:url1,
			 	            type:"POST",
			 	            data:{
			 	        	  foodTypeId:$("#foodTypeId").val()
			 	            },
			 	            dataType:"JSON",
			 	            success:function(data){
			 	                if(data.success){
			 	                	var d = data.obj;
				                	var trtemp = "<tr>"+
				        			"<td align='right'><label class='Validform_label'>食物:</label></td>"+
				        			"<td class='value'><select id='foodId' name='foodId' datatype='*'>";
				        			var option="";
				                   for(var i in d){
				                	   option = option+"<option value="+d[i].id+">"+d[i].name+"</option>"+"";
				                   }
					                var tr = trtemp+option+
					       			"</select>"+
					       			"<span class='Validform_checktip'></span><label class='Validform_label' style='display: none;'>食物</label>"+
					       			"</td></tr>";
					       		 	$("#foodTypeId").parent().parent().after(tr);
			 	                	}
			 	            	}
			       		 	
			       		 	});
		       		 	
	                }
	            }
	        });
			
		}
 })
 function typeChange(obj){
	 $("#foodTypeId").parent().parent().next().remove();
	 var url1 = "tcsbFoodController.do?getFoodByFoodTypeId";
	 	$.ajax({
      url:url1,
      type:"POST",
      data:{
  	  foodTypeId:$(obj).val()
      },
      dataType:"JSON",
      success:function(data){
          if(data.success){
          	var d = data.obj;
         	var trtemp = "<tr>"+
 			"<td align='right'><label class='Validform_label'>食物:</label></td>"+
 			"<td class='value'><select id='foodId' name='foodId' datatype='*'>";
 			var option="";
            for(var i in d){
         	   option = option+"<option value="+d[i].id+">"+d[i].name+"</option>"+"";
            }
             var tr = trtemp+option+
    			"</select>"+
    			"<span class='Validform_checktip'></span><label class='Validform_label' style='display: none;'>食物</label>"+
    			"</td></tr>";
    		 	$("#foodTypeId").parent().parent().after(tr);
          	}
      	}
	 	
	 	});
 }
  </script>
<script>
$(function(){
	$(':input').labelauty();
});
</script>