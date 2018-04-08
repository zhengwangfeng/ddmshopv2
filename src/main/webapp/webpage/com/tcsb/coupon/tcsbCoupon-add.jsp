<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>优惠卷</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbCouponController.do?doAdd" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tcsbCouponPage.id }">
		<input id="useRebate" name="useRebate" type="hidden"
			value="${tcsbCouponPage.useRebate }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbCouponPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbCouponPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbCouponPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbCouponPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbCouponPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbCouponPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 指定用户:
				</label></td>
				<td class="value">
					<!--  <input id="userId" name="userId" type="text" style="width: 150px" class="inputxt" > -->
					<%-- <t:userSelect windowWidth="1000px" windowHeight="600px" title="用户列表"></t:userSelect> --%>
					<input name="userId" name="userId" type="hidden" value="${openid}"
					id="userId"> <input name="nickname" class="inputxt"
					value="${nickname }" id="nickname" readonly="readonly" datatype="*" />
					<t:choose hiddenName="userId" hiddenid="openid"
						url="weixinUserController.do?weixinUser" name="weixinUserList"
						icon="icon-search" title="微信会员选择" textname="nickname"
						isclear="true" isInit="true"></t:choose> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">指定用户</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 满减模版:
				</label></td>
				<td class="value">
					<!-- <input id="fullcutTemplateId" name="fullcutTemplateId" type="text" style="width: 150px" class="inputxt" > -->
					<select id="fullcutTemplateId" name="fullcutTemplateId"
					datatype="*">
						<option value="">---请选择---</option>
						<c:forEach items="${tcsbFullcutTemplateEntities}"
							var="tcsbFullcutTemplateEntitie">
							<option value="${tcsbFullcutTemplateEntitie.id }">满${tcsbFullcutTemplateEntitie.total}减${tcsbFullcutTemplateEntitie.discount}
								有效期:${tcsbFullcutTemplateEntitie.usePeriod}
								<c:if test="${tcsbFullcutTemplateEntitie.dateUnit=='day' }">天</c:if>
								<c:if test="${tcsbFullcutTemplateEntitie.dateUnit=='month' }">月</c:if>
								<c:if test="${tcsbFullcutTemplateEntitie.dateUnit=='year' }">年</c:if>
							</option>
						</c:forEach>
				</select> <span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">满减模版</label>
				</td>
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

		</table>
	</t:formvalid>
	<div id="realNameSearchColums" style="display: none;">
		<div name="searchColumsRealName">
			<t:userSelect windowWidth="1000px" windowHeight="600px" title="用户列表"></t:userSelect>
		</div>
	</div>
</body>
<script type="text/javascript">
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
<script src="webpage/com/tcsb/coupon/tcsbCoupon.js"></script>