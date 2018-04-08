<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>店铺管理</title>
<t:base
	type="ckfinder,ckeditor,jimmyupload,jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
.uimages {
	width: 100px;
	height: 100px;
	padding-left: 1px;
}
</style>
<script type="text/javascript">

	function openDepartmentSelect() {
		$.dialog.setting.zIndex = getzIndex(); 
		
		var orgIds = $("#orgIds").val();
		
		$.dialog({content: 'url:departController.do?departSelect&orgIds='+orgIds, zIndex: 2100, title: '组织机构列表', lock: true, width: '400px', height: '350px', opacity: 0.4, button: [
		   {name: '<t:mutiLang langKey="common.confirm"/>', callback: callbackDepartmentSelect, focus: true},
		   {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
	   ]}).zindex();
	}
	function callbackDepartmentSelect() {
		  var iframe = this.iframe.contentWindow;
		  var treeObj = iframe.$.fn.zTree.getZTreeObj("departSelect");
		  var nodes = treeObj.getCheckedNodes(true);
		  if(nodes.length>0){
		  var ids='',names='';
		  for(i=0;i<nodes.length;i++){
		     var node = nodes[i];
		     ids += node.id+',';
		    names += node.name+',';
		 }
		 $('#departname').val(names);
		 $('#departname').blur();		
		 $('#orgIds').val(ids);		
		}
	}
	
	function callbackClean(){
		$('#departname').val('');
		 $('#orgIds').val('');	
	}
	
	function setOrgIds() {}
	$(function(){
		$("#departname").prev().hide();
	});
	
	
  </script>
<script type="text/javascript">
		var index=0;//全局索引
		function doupload(){
		if($(".uimages").val()==null){
			layer.alert("请上传店铺轮播图片后再提交！ ");
			return false;
			}
		}
		//渲染回显上传的图片
		function render(srcSource,thumbs){
			 $("#images").append("<span class='tu' name='"+index+"'><img id='"+thumbs+"' class='uimages' src='${imgPath}"+thumbs+"'/><a  href='javascript:;' onclick=delimg('"+srcSource+"','"+thumbs+"',this)>删除</a><input name='imagesManagerList["+index+"].fPath' value='"+srcSource+"' type='hidden'/><input name='imagesManagerList["+index+"].fThumbPath' value='"+thumbs+"' type='hidden'/></span>");
			 index++; 
		}	
		//删除原图和缩略图
			function delimg(img,thumbs,t){
				//获取#images的SIZE
				var len= $("#images").find("span").size();
				//获取删除节点的INDEX
				var curindex=$(t).parent('span').attr("name");
				//删除节点并删除硬盘上的图片
			   	$.ajax({
						type : 'POST',
						url : '${webRoot}/uploadController.do?delImages',// 请求的action路径
						data :{
							'imgs' : img,
							'thumbs' : thumbs
						},
						dataType:'json',
						success : function(data) {
							//重新改变DOM INDEX
							 for(var i=parseInt(curindex)+1;i<=len;i++){
								//获取所删除节点后的	INDEX
								var indextemp=(i-1).toString();
								var Span = $("#images").find("span :eq('"+i+"')").attr("name",indextemp);
								var Input = $("#images").find("span :eq('"+i+"')").find("input :eq(0)").attr("name","imagesManagerList["+(i-1)+"].fPath");
								var Input2 = $("#images").find("span :eq('"+i+"')").find("input :eq(1)").attr("name","imagesManagerList["+(i-1)+"].fThumbPath");
							} 
							index--;
							$(t).parent('span').remove();
							layer.alert("图片删除成功");
						}
					});  
				
			}
</script>

<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script>
<script>
	   function init(lat,lng) {
		   var center = new qq.maps.LatLng(lat, lng);
	        //定义map变量 调用 qq.maps.Map() 构造函数   获取地图显示容器
	         var map = new qq.maps.Map(
	        	document.getElementById("container"), 
	        {
	            center: new qq.maps.LatLng(lat,lng),      // 地图的中心地理坐标。
	            zoom:12                                                 // 地图的中心地理坐标。
	        });
	         var marker = new qq.maps.Marker({
	        	    position: center,
	        	    map: map
	        	});
	        
	         qq.maps.event.addListener(
	        		    map,
	        		    'click',
	        		    function(event) {
	        		    	
	        		    	$("#lng").val(event.latLng.getLng());
	        		    	  $("#lat").val(event.latLng.getLat());
	        		    	  var latlng = event.latLng.getLat()+","+event.latLng.getLng();
	        		    	  var data={
	        		    			
	        		    		  location:latlng,
	        		    		  key:"E7JBZ-46H3U-MZUVO-2NYE3-BOQJZ-ZNBY6",
	        		    		  get_poi:0 }
	        		    		
	        		    		var url = "http://apis.map.qq.com/ws/geocoder/v1/?";
	        		    		 data.output="jsonp"; 
	        		    		  $.ajax({
	        		    		      type:"get",
	        		    		      dataType:'jsonp',
	        		    		      data:data,
	        		    		      jsonp:"callback",
	        		    		      jsonpCallback:"QQmap",
	        		    		      url:url,
	        		    		      success:function(json){
	        		    		    	  init(event.latLng.getLat(),event.latLng.getLng())
	        		    		    	  $("#address").val(json.result.formatted_addresses.recommend);
	        		    		    	 // var toStr = JSON.stringify(json);
	        		    		    	 //alert(toStr);
	        		    		    	// alert(json.result.formatted_addresses.recommend);
	        		    		      },
	        		    		      error : function(err){alert("服务端错误，请刷新浏览器后重试")}
	        		    		
	        		    		});
	        		        /* alert('您点击的位置为:[' + event.latLng.getLng() +
	        		        ',' + event.latLng.getLat() + ']'); */
	        		    }
	        		);
	    }
	
	window.onload = function(){
		//直接加载地图
		var lat = 24.606283898475176;
		var lng = 118.05219948291779;
	    //初始化地图函数  自定义函数名init
	    //调用初始化函数地图
	    init(lat,lng);
	}

function getlatlng(){
	 var Address = $("#address").val();
	//alert(123);
	var data={
			address:Address,
	  key:"E7JBZ-46H3U-MZUVO-2NYE3-BOQJZ-ZNBY6",
	  get_poi:0 }
	
	var url = "http://apis.map.qq.com/ws/geocoder/v1/?";
	 data.output="jsonp"; 
	  $.ajax({
	      type:"get",
	      dataType:'jsonp',
	      data:data,
	      jsonp:"callback",
	      jsonpCallback:"QQmap",
	      url:url,
	      success:function(json){
	    	  $("#lng").val(json.result.location.lng);
	    	  $("#lat").val(json.result.location.lat);
	    	  init(json.result.location.lat,json.result.location.lng);
	      },
	      error : function(err){alert("服务端错误，请刷新浏览器后重试")}
	
	});
}

/* 
var data={
  location:"39.984154,116.307490", */
            /*换成自己申请的key*/
 /*  key:"OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77",
  get_poi:0 }
  var url="http://apis.map.qq.com/ws/geocoder/v1/?";
  data.output="jsonp";   */




</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" beforeSubmit="doupload()"
		action="tcsbShopController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${tcsbShopPage.id }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbShopPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbShopPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbShopPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbShopPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbShopPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbShopPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 名称: </label>
				</td>
				<td class="value"><input id="name" name="name" type="text"
					style="width: 150px" class="inputxt" datatype="*"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">名称</label></td>
			</tr>
			<!-- 	<tr>
					<td align="right">
						<label class="Validform_label">
							店铺支付方式:
						</label>
					</td>
					<td class="value">
					     	<select name="isSupportPay">
					     		<option value="1">是</option>
					     		<option value="0">否</option>
					     	</select>
						</td>
				</tr> -->
			<tr>
				<td align="right"><label class="Validform_label">
						店铺支付方式: </label></td>
				<td class="value"><t:dictSelect field="payMode"
						typeGroupCode="payMode" hasLabel="false" defaultVal="0"></t:dictSelect>
				</td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label">
						是否停车位: </label></td>
				<td class="value"><t:dictSelect field="isParkingSpace"
						typeGroupCode="parking" hasLabel="false" defaultVal="1"></t:dictSelect>
				</td>
			</tr>
			
			
			

			<tr>
				<td align="right"><label class="Validform_label">
						店铺预约方式: </label></td>
				<td class="value"><t:dictSelect id="reservationMethod"
						field="reservationMethod" typeGroupCode="resMethod"
						hasLabel="false" defaultVal="0"></t:dictSelect></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 头像: </label>
				</td>
				<td class="value">
					<!-- <input id="headimg" name="headimg" type="text" style="width: 150px" class="searchbox-inputtext" > -->
					<t:ckfinder name="headimg" uploadType="Images" width="100"
						height="60" buttonClass="ui-button" buttonValue="上传图片"></t:ckfinder>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">头像</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						店铺轮播图: </label></td>
				<td colspan="2"><a href="#" onclick="selectP('店铺轮播图','shop')"
					class="easyui-linkbutton" id="btn_add" iconCls="icon-ok">上传店铺轮播图</a>
					<div id="images"></div></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 简介: </label>
				</td>
				<td class="value">
					<!-- <input id="introdution" name="introdution" type="text" style="width: 150px" class="inputxt" > -->
					<textarea id="introdution" name="introdution" cols="30" rows="3"
						datatype="*"></textarea> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">简介</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 地址: </label>

				</td>
				<td class="value"><input id="address" name="address"
					type="text" style="width: 300px" class="inputxt" datatype="*">
					<input type="button" onclick="getlatlng()" value="获取经纬度" /></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 经纬度:
				</label></td>
				<td class="value"><input id="lng" name="longitude" type="text"
					style="width: 150px" class="inputxt" datatype="*"> <input
					id="lat" name="latitude" type="text" style="width: 150px"
					class="inputxt" datatype="*"></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 地图: </label>

				</td>
				<td>
					<div id="container" style="width: 500px; height: 300px"></div>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 联系方式:
				</label></td>
				<td class="value"><input id="phone" name="phone" type="text"
					style="width: 150px" class="inputxt"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">联系方式</label></td>
			</tr>
			<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							是否入驻:
						</label>
					</td>
					<td class="value">
					     	 <!-- <input id="idDel" name="idDel" type="text" style="width: 150px" class="inputxt" > -->
					     	 <t:dictSelect field="idDel" typeGroupCode="sf_yn" hasLabel="false" defaultVal="Y"></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否入驻</label>
						</td>
					</tr> --%>
			<!-- <tr>
					<td align="right">
						<label class="Validform_label">
							星级:
						</label>
					</td>
					<td class="value">
					     	 <input id="level" name="level" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">星级</label>
						</td>
					</tr>-->
			<tr>
				<td align="right" width="25%" nowrap><label
					class="Validform_label"> <t:mutiLang
							langKey="common.username" />:
				</label></td>
				<td class="value" width="85%"><c:if test="${user.id!=null }"> ${user.userName } </c:if>
					<c:if test="${user.id==null }">
						<input id="userName" class="inputxt" name="userName"
							validType="t_s_base_user,userName,id" value="${user.userName }"
							datatype="s2-32" />
						<span class="Validform_checktip"> <t:mutiLang
								langKey="username.rang2to32" /></span>
					</c:if></td>
			</tr>
			<tr>
				<td align="right" width="10%" nowrap><label
					class="Validform_label"> <t:mutiLang
							langKey="common.real.name" />:
				</label></td>
				<td class="value" width="10%"><input id="realName"
					class="inputxt" name="realName" value="${user.realName }">
					<span class="Validform_checktip"><t:mutiLang
							langKey="fill.realname" /></span></td>
			</tr>
			<c:if test="${user.id==null }">
				<tr>
					<td align="right"><label class="Validform_label"> <t:mutiLang
								langKey="common.password" />:
					</label></td>
					<td class="value"><input type="password" class="inputxt"
						value="123456" name="password" plugin="passwordStrength"
						datatype="*6-18" errormsg="" /> <span class="passwordStrength"
						style="display: none;"> <span><t:mutiLang
									langKey="common.weak" /></span> <span><t:mutiLang
									langKey="common.middle" /></span> <span class="last"><t:mutiLang
									langKey="common.strong" /></span>
					</span> <span class="Validform_checktip"> <t:mutiLang
								langKey="password.rang6to18" /></span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label"> <t:mutiLang
								langKey="common.repeat.password" />:
					</label></td>
					<td class="value"><input id="repassword" class="inputxt"
						type="password" value="123456" recheck="password" datatype="*6-18"
						errormsg="两次输入的密码不一致！"> <span class="Validform_checktip"><t:mutiLang
								langKey="common.repeat.password" /></span></td>
				</tr>
			</c:if>
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang
							langKey="common.department" />:
				</label></td>
				<td class="value">
					<%--<select class="easyui-combobox" data-options="multiple:true, editable: false" id="orgSelect" datatype="*">--%>
					<%--<select class="easyui-combotree" data-options="url:'departController.do?getOrgTree', multiple:true, cascadeCheck:false"
                        id="orgSelect" name="orgSelect" datatype="select1">
                update-end--Author:zhangguoming  Date:20140826 for：将combobox修改为combotree
                    <c:forEach items="${departList}" var="depart">
                        <option value="${depart.id }">${depart.departname}</option>
                    </c:forEach>
                </select> --%> <%--  <t:departSelect departId="${tsDepart.id }" departName="${tsDepart.departname }"></t:departSelect>--%>

					<input id="departname" name="departname" type="text"
					readonly="readonly" class="inputxt" datatype="*"
					value="${departname}"> <input id="orgIds" name="orgIds"
					type="hidden" value="${orgIds}"> <a href="#"
					class="easyui-linkbutton" plain="true" icon="icon-search"
					id="departSearch" onclick="openDepartmentSelect()">选择</a> <a
					href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"
					id="departRedo" onclick="callbackClean()">清空</a> <span
					class="Validform_checktip"><t:mutiLang
							langKey="please.muti.department" /></span>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang
							langKey="common.role" />:
				</label></td>
				<td class="value" nowrap><input name="roleid" name="roleid"
					type="hidden" value="${id}" id="roleid"> <input
					name="roleName" class="inputxt" value="${roleName }" id="roleName"
					readonly="readonly" datatype="*" /> <t:choose hiddenName="roleid"
						hiddenid="id" url="userController.do?roles" name="roleList"
						icon="icon-search" title="common.role.list" textname="roleName"
						isclear="true" isInit="true"></t:choose> <span
					class="Validform_checktip"><t:mutiLang
							langKey="role.muti.select" /></span></td>
			</tr>
			<tr>
				<td align="right" nowrap><label class="Validform_label">
						<t:mutiLang langKey="common.phone" />:
				</label></td>
				<td class="value"><input class="inputxt" name="mobilePhone"
					value="${user.mobilePhone}" datatype="m" errormsg="手机号码不正确"
					ignore="ignore"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang
							langKey="common.tel" />:
				</label></td>
				<td class="value"><input class="inputxt" name="officePhone"
					value="${user.officePhone}" datatype="n" errormsg="办公室电话不正确"
					ignore="ignore"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang
							langKey="common.common.  " />:
				</label></td>
				<td class="value"><input class="inputxt" name="email"
					value="${user.email}" datatype="e" errormsg="邮箱格式不正确!"
					ignore="ignore"> <span class="Validform_checktip"></span></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/tcsb/shop/tcsbShop.js"></script>
<script>
  $("#reservationMethod").change(function(){
	 
	  var hitr="<tr id='trd'>"+
		"<td align='right'>"+
		"<label class='Validform_label'>所需押金:</label>"+
		"</td>"+	
	    "<td class='value'>"+
	    "<input id='reserveDeposit' name='reserveDeposit' type='text' style='width: 150px' class='inputxt' datatype='/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/' errormsg='请输入两位小数'>"+
		"<span class='Validform_checktip'></span><label class='Validform_label' style='display: none;'>所需押金</label>"+
		"</td>"+
	    "</tr>";
	  if($(this).val()=='2'){
		  $(this).parent().parent().after(hitr);
	  }
	  else{
		  $("#trd").remove();
	  }	 
  });
  </script>