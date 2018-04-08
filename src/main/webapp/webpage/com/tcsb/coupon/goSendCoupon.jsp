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
		layout="table" action="tcsbCouponController.do?sendCoupon" tiptype="1">
		<input type="hidden" name="ids" value="${ids }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
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
				</select> <a href="#" class="easyui-linkbutton l-btn l-btn-plain"
					plain="true"
					onclick="addTemplate('录入满减模版','tcsbFullcutTemplateController.do?goSendAdd&userids=${ids}','tcsbFullcutTemplateList',null,null)"><span
						class="l-btn-left"><span
							class="l-btn-text icon-add l-btn-icon-left">录入满减模版</span></span></a> <!-- <span class="l-btn-left"><span class="l-btn-text icon-add l-btn-icon-left">录入</span></span> -->
					<span class="Validform_checktip"></span> <label
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

 function addTemplate(title,addurl,gname,width,height) {
		gridname=gname;
		createTemplatewindow(title, addurl,width,height);
	}
 function createTemplatewindow(title, addurl,width,height) {
 	width = width?width:700;
 	height = height?height:400;
 	if(width=="100%" || height=="100%"){
 		width = window.top.document.body.offsetWidth;
 		height =window.top.document.body.offsetHeight-100;
 	}
 	if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+addurl,
				lock : true,
				//zIndex:1990,
				width:width,
				height:height,
				title:title,
				opacity : 0.3,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	saveObj1(iframe);
					return false;
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}else{
			W.$.dialog({
				content: 'url:'+addurl,
				lock : true,
				width:width,
				//zIndex:1990,
				height:height,
				parent:windowapi,
				title:title,
				opacity : 0.3,
				cache:false,
			    ok: function(data){
			    	iframe = this.iframe.contentWindow;
			    	saveTemplate(iframe);
					return true;
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
	}
 
 function saveObj1() {
	 	$('#btn_sub', iframe.document).click();
	 }
 /**
  * 执行保存
  * 
  * @param url
  * @param gridname
  */
 function saveTemplate(s) {
	//var fullcutTemplateIdVal = $("#fullcutTemplateId", s.document).val();
	//保存满减模版
 	$('#btn_sub', iframe.document).click();
	//关闭父窗口
	windowapi.close();
 	//回显
	/* $("#fullcutTemplateId option").remove();
    var url = "tcsbFullcutTemplateController.do?getFullcutTemplate";
      $.ajax({
         url:url,
         type:"GET",
         dataType:"JSON",
         success:function(data){
             if(data.success){
            	 var d = data.obj;
            	 $("#fullcutTemplateId").append("<option value=''>---请选择---</option>");
            	 for(var i =0;i<d.length; i++){
            		 
            		 var dateUnit = "";
            		 if(d[i].dateUnit == 'day'){
            			 dateUnit = "天";
            		 }
					if(d[i].dateUnit == 'month'){
						dateUnit = "月";   			 
					 }
					if(d[i].dateUnit == 'year'){
						dateUnit = "年";
					 }
            	   $("#fullcutTemplateId").append("<option value="+d[i].id+">满"+d[i].total+"减"+d[i].discount+" 有效期:"+d[i].usePeriod+dateUnit+"</option>")
            	 }
             }
         }
     });  */
	
	//$("#fullcutTemplateId").append("<option value="${tcsbFullcutTemplateEntitie.id }">满${tcsbFullcutTemplateEntitie.total}减${tcsbFullcutTemplateEntitie.discount}</option>")
 }
 
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