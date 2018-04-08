<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>微信菜单</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  $(function() {
	   var msgtype = "${weixinMenuPage.msgtype}";
	   var type="${weixinMenuPage.type}";
	   var templateid = "${weixinMenuPage.templateid}";
	   //alert("templateid="+templateid);
	   //var supMenuId="${fatherName}";
	   if(templateid){
	   		var templateObj = $("#templateid");
	 		templateObj.html("");
	 		$.ajax({
	 			url:"weixinMenuController.do?gettemplate",
	 			data:{"msgtype":msgtype},
	 			dataType:"JSON",
	 			type:"POST",
	 			success:function(data){
	 				var obj = data.obj;
	 				var msg="";
	 				for(var i=0;i<obj.length;i++){
	 				   
	 				    if(templateid == obj[i].id){
	 				    	/* if(msgtype=="expand"){
	 				    		msg += "<option value='"+data[i].id+"' selected='selected'>"+data[i].name+"</option>";
	 				    	}else{ */
	 				    		msg += "<option value='"+obj[i].id+"' selected='selected'>"+obj[i].templatename+"</option>";
	 				    	/* } */
	 				    }else{
		 				   /*  if(msgtype=="expand"){
		 				    	msg += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
		 				    }else{ */
		 				    	msg += "<option value='"+obj[i].id+"'>"+obj[i].templatename+"</option>";
		 				   /*  } */
	 				    }
	 				}
	 				templateObj.html(msg);
	 			}
	 		});
	   }
	   
		$("#type").change(function(){
			var selectValue = $(this).children('option:selected').val();
			
			if("click"==selectValue){
				$("#url").attr("disabled","disabled");
				$("#trurl").attr("style","display:none");
				
				$("#xxtr").removeAttr("style");
				var inputAttr = $("input[name='msgtype']");
				for(var i=0;i<inputAttr.length;i++){
					$(inputAttr[i]).removeAttr("disabled");
					$(inputAttr[i]).attr("datatype","*");
				}
				
				$("#templateTr").removeAttr("style");
				$("#templateid").removeAttr("disabled");
				
				//设置消息类型和验证
				$("#msgtype").attr("datatype","*");
				$("#templateid").attr("datatype","*");
			}else{
				$("#url").removeAttr("disabled");
				$("#trurl").removeAttr("style");
				
				$("#xxtr").attr("style","display:none");
				var inputAttr = $("input[name='msgtype']");
				for(var i=0;i<inputAttr.length;i++){
					$(inputAttr[i]).attr("disabled","disabled");
					$(inputAttr[i]).removeAttr("datatype");
				}
				
				$("#templateTr").attr("style","display:none");
				$("#templateid").attr("disabled","disabled");
				//取消验证。防止无法提交
				$("#msgtype").removeAttr("datatype");
				$("#templateid").removeAttr("datatype");
			}
		});
		
		var inputAttr = $("input[name='msgtype']");
		for(var i=0;i<inputAttr.length;i++){
			$(inputAttr[i]).click(function(){
			   var textVal = $(this).val();
			   if("text"==textVal){
			   		getTemplates("text");
			   }/* else if("expand"==textVal){
			   		getTemplates("expand");
			   } */else if("news"==textVal){
			   		getTemplates("news");
			   }
			});
		}
	
		//获取动作设置选中的项,用于初始化依赖dom元素
		var typeVal = $("#type").val(); // 动作设置选中项的值
		if("click" == typeVal){
			$("#xxtr").show();
			$("#trurl").hide();
			$("#templateTr").show();
			//设置消息类型和验证
			$("#msgtype").attr("datatype","*");
			$("#templateid").attr("datatype","*");
		}else if("view" == typeVal){
			$("#xxtr").hide();
			$("#trurl").show();
			$("#templateTr").hide();
			$("#msgtype").removeAttr("datatype");
			$("#templateid").removeAttr("datatype");
		}
		
		/* if (typeof(fatherId) == "undefined"){  
			$("#msgtype").removeAttr("datatype");
			$("#templateid").removeAttr("datatype");
		} */
	  	
		$('#cc').combotree({
			url : 'weixinMenuController.do?setPWeixinMenu&selfId=${weixinMenuPage.id}',

			panelHeight: 200,
			width: 157,
			onClick: function(node){
				$("#weixinMenuEntityId").val(node.id);
			}
		});
		
		if($('#menuLevel').val()=='1'){
			$('#pfun').show();
		}else{
			$('#pfun').hide();
		}
		
		
		$('#menuLevel').change(function(){
			if($(this).val()=='1'){
				$('#pfun').show();
				var t = $('#cc').combotree('tree');
				var nodes = t.tree('getRoots');
				if(nodes.length>0){
					$('#cc').combotree('setValue', nodes[0].id);
					$("#weixinMenuEntityId").val(nodes[0].id);
				}
			}else{
				var t = $('#cc').combotree('tree');
				var node = t.tree('getSelected');
				if(node){
					$('#cc').combotree('setValue', null);
				}
                $("#weixinMenuEntityId").val(null);
				$('#pfun').hide();
			}
		});
		
	});
  
  	function getTemplates(msgtype){
		var templateObj = $("#templateid");
		templateObj.html("");
		$.ajax({
			url:"weixinMenuController.do?gettemplate",
			data:{"msgtype":msgtype},
			dataType:"JSON",
			type:"POST",
			success:function(data){
				var obj=data.obj;
				var msg="";
				msg += "<option value=''>---请选择---</option>";
				for(var i=0;i<obj.length;i++){
					/* if(msgtype=="expand"){
					 	msg += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
					}else{ */
					 	msg += "<option value='"+obj[i].id+"'>"+obj[i].templatename+"</option>";
					/* } */
				}
				templateObj.html(msg);
			}
		});
		
	}
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="weixinMenuController.do?saveWeixinMenu">
		<input id="id" name="id" type="hidden" value="${weixinMenuPage.id }">
		<input id="accountid" name="accountid" type="hidden"
			value="${weixinMenuPage.accountid }">
		<input id="updateName" name="updateName" type="hidden"
			value="${weixinMenuPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${weixinMenuPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${weixinMenuPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${weixinMenuPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${weixinMenuPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${weixinMenuPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 名称: </label>
				</td>
				<td class="value"><input id="name" name="name" type="text"
					style="width: 150px" class="inputxt" value="${weixinMenuPage.name}"
					datatype="*2-50"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">菜单名称</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang
							langKey="menu.level" />:
				</label></td>
				<td class="value"><select name="menuLevel" id="menuLevel"
					datatype="*">
						<option value="0"
							<c:if test="${weixinMenuPage.menuLevel eq 0}">selected="selected"</c:if>>
							<t:mutiLang langKey="main.function" />
						</option>
						<option value="1"
							<c:if test="${weixinMenuPage.menuLevel>0}"> selected="selected"</c:if>>
							<t:mutiLang langKey="sub.function" />
						</option>
				</select> <span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;"><t:mutiLang
							langKey="menu.level" /></label></td>
			</tr>
			<tr id="pfun">
				<td align="right"><label class="Validform_label"> <t:mutiLang
							langKey="parent.function" />:
				</label></td>
				<td class="value"><input id="cc"
					<c:if test="${weixinMenuPage.weixinMenuEntity.menuLevel eq 0}"> value="${weixinMenuPage.weixinMenuEntity.id}"</c:if>
					<c:if test="${weixinMenuPage.weixinMenuEntity.menuLevel > 0}"> value="${weixinMenuPage.weixinMenuEntity.name}"</c:if>>
					<input id="weixinMenuEntityId" name="weixinMenuEntity.id"
					style="display: none;"
					value="${weixinMenuPage.weixinMenuEntity.id}"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;"><t:mutiLang
							langKey="parent.function" /></label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 菜单标识:
				</label></td>
				<td class="value"><input id="menukey" name="menukey"
					type="text" style="width: 150px" class="inputxt"
					value="${weixinMenuPage.menukey}" datatype="*"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;"> 菜单标识</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 动作设置:
				</label></td>
				<%-- <td class="value">
					     	 <input id="type" name="type" type="text" style="width: 150px" class="inputxt" value="${weixinMenuPage.type}"  datatype="*"> --%>
				<td colspan="3" class="value"><select name="type" id="type"
					datatype="*">
						<option value="click"
							<c:if test="${weixinMenuPage.type=='click'}">selected="selected"</c:if>>消息触发类</option>
						<option value="view"
							<c:if test="${weixinMenuPage.type=='view'}">selected="selected"</c:if>>网页链接类</option>
				</select> <span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">动作设置</label></td>
			</tr>
			<tr id="xxtr"">
				<td align="right"><label class="Validform_label"> 消息类型:
				</label></td>
				<%-- <td class="value">
					     	 <input id="msgtype" name="msgtype" type="text" style="width: 150px" class="inputxt" value="${weixinMenuPage.msgtype}"  datatype="*"> --%>
				<td class="value" colspan="3"><input type="radio" value="text"
					name="msgtype" id="msgtype" datatype="*"
					<c:if test="${weixinMenuPage.msgtype=='text'}">checked="checked"</c:if> />文本
					<input type="radio" value="news" name="msgtype"
					<c:if test="${weixinMenuPage.msgtype=='news'}">checked="checked"</c:if> />图文
					<%-- <input type="radio" value="expand" name="msgtype"  <c:if test="${msgtype=='expand'}">checked="checked"</c:if>/>扩展 --%>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">消息类型</label></td>
			</tr>
			<tr id="templateTr">
				<td align="right"><label class="Validform_label"> 消息模版
						: </label></td>
				<td class="value">
					<%-- <input id="templateid" name="templateid" type="text" style="width: 150px" class="inputxt" value="${weixinMenuPage.templateid}"  datatype="*"> --%>
					<select name="templateid" id="templateid" datatype="*">

				</select> <span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">消息模版</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 顺序: </label>
				</td>
				<td class="value"><input id="orders" name="orders" type="text"
					style="width: 150px" class="inputxt"
					value="${weixinMenuPage.orders}" datatype="*"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">顺序</label></td>
			</tr>

			<tr id="trurl">
				<td align="right"><label class="Validform_label"> 链接地址:
				</label></td>
				<td class="value"><input id="url" name="url" type="text"
					style="width: 150px" class="inputxt" value="${weixinMenuPage.url}">
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">填写链接地址，格式需要以http开头</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/weixin/weixinmenu/weixinMenu.js"></script>