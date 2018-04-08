<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script language="javascript" src="lodop/LodopFuncs.js"></script>
<script type="text/javascript">
	//layer扩展使用prompt
	$(function(){
		layer.use('extend/layer.ext.js', function(){
		    layer.ext = function(){
		        layer.prompt({})
		    };
		});
	})
	function toDecimal(x) {    
	    var val = Number(x)   
	   if(!isNaN(parseFloat(val))) {    
	      val = val.toFixed(1);    
	   }    
	   return  val;     
	}  
	
	$('#addTcsbOrderItemBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delTcsbOrderItemBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#retreatTcsbOrderItemBtn').linkbutton({   
	    iconCls: 'icon-redo'  
	});
	var paystate = $("#paystatus").val();
	if(paystate == 1){
		$('#addTcsbOrderItemBtn').linkbutton('disable');
		$('#delTcsbOrderItemBtn').linkbutton('disable');
		$('#retreatTcsbOrderItemBtn').linkbutton('disable');
	}else{
		$('#addTcsbOrderItemBtn').bind('click', function(){
				 var tr =  $("#add_tcsbOrderItem_table_template tr").clone();
			 	 $("#add_tcsbOrderItem_table").append(tr);
			 	 //update
			 	 var price = parseFloat(tr.find('td:eq(6) input').val());//5-6
			 	 var totalPrice =parseFloat($("#totalPrice").val())+price;
			 	
		    	  $("#totalPrice").val(toDecimal(totalPrice));
		    	  $("#offlinePrice").val(toDecimal(totalPrice));
			 	 
			 	 resetTrNum('add_tcsbOrderItem_table');
			 	 return false;	
	    });  
		$('#delTcsbOrderItemBtn').bind('click', function(){
			
			//alert($(":checkbox","#add_tcsbOrderItem_table").length);
			
				if($(":checkbox:checked","#add_tcsbOrderItem_table").length==0){
					tip('请至少选择一个项目');
					return ;
				}
				
				if($(":checkbox:checked","#add_tcsbOrderItem_table").length == $(":checkbox","#add_tcsbOrderItem_table").length){
					tip('订单菜品不能清空');
					return ;
				}
				
				$(":checkbox:checked","#add_tcsbOrderItem_table").each(
					function(){
						var trObj = $(this).parent().parent();
						var price =trObj.find("td:eq(6) input").val();//5-6
						var num =trObj.find("td:eq(4) input").val();//3-4
						var finalPrice =parseFloat(price)*num;
						var em =parseFloat($("#totalPrice").val())-parseFloat(finalPrice)
						$("#totalPrice").val(toDecimal(em));
						$("#offlinePrice").val(toDecimal(em));
					}		
				);
		      	$("#add_tcsbOrderItem_table").find("input:checked").parent().parent().remove();   
		        resetTrNum('add_tcsbOrderItem_table'); 
		        return false;
	    }); 
		$('#retreatTcsbOrderItemBtn').bind('click', function(){  
			if($(":checkbox:checked","#add_tcsbOrderItem_table").length!=1){
				tip('请选择一个项目');
				return ;
			}
			var trObj = $(":checkbox:checked","#add_tcsbOrderItem_table").parent().parent();
			//var price =trObj.find("td:eq(5) input").val();
			var numOld =trObj.find("td:eq(4) input").val();//3-4
			var foodName = trObj.find("select:eq(1) option:selected").text();
			var foodId = trObj.find("select:eq(1)").val();
			//prompt层
			layer.prompt({
			  formType: 0,
			  value: numOld,
			  title: '请输入退菜的数量'
			}, function(value, index, elem){
			  //alert(value); //得到value
			  if((value*1)>(numOld*1)){
				  tip('数字要小于原始值');
			  }else{
				  var numFinall =toDecimal(parseFloat(numOld)-parseFloat(value));
				  //数量为零时移除
				  trObj.find("td:eq(4) input").val(numFinall);//3-4
				 var orderItemId = trObj.find("td:eq(1)").next().val();
				  if(numFinall==0){
					  trObj.remove();
				  }
				  
				  var price =trObj.find("td:eq(6) input").val();//5-6
				 var disPrice =parseFloat(price)*value;
				
				 var em =parseFloat($("#totalPrice").val())-parseFloat(disPrice)
				  $("#totalPrice").val(toDecimal(em));
				  $("#offlinePrice").val(toDecimal(em));
				  layer.close(index);
				 	var deskName = $("#deskName").val();
				 	//获取食物所属的所有打印机
				 	var url = "tcsbPrinterController.do?getPrinterByFoodId";
			        $.ajax({
			            url:url,
			            type:"GET",
			            dataType:"JSON",
			            data:{
			                foodId:foodId
			            },
			            success:function(data){
			                if(data.success){
			                	var da = data.obj;
			                	for(var i in da){
			                		 LODOP=getLodop();  
			    					LODOP.PRINT_INIT("退单打印发票");
			    					LODOP.ADD_PRINT_TEXT(0,100,355,30,"退菜");
			    					LODOP.SET_PRINT_STYLEA(1,"FontSize",13);
			    					LODOP.SET_PRINT_STYLEA(1,"Bold",1);
			    					LODOP.ADD_PRINT_LINE(25,14,25,510,0,1);
			    					LODOP.ADD_PRINT_TEXT(50,15,355,30,deskName);
			    					LODOP.SET_PRINT_STYLEA(3,"FontSize",13);
			    					LODOP.SET_PRINT_STYLEA(3,"Bold",1);
			    					LODOP.ADD_PRINT_LINE(75,14,75,510,0,1);
			    					LODOP.ADD_PRINT_TEXT(100,0,100,100,"名称");
			    					LODOP.SET_PRINT_STYLEA(5,"FontSize",13);
			    					LODOP.SET_PRINT_STYLEA(5,"Bold",1);
			    					LODOP.ADD_PRINT_TEXT(100,100,100,100,"数量");
			    					LODOP.SET_PRINT_STYLEA(6,"FontSize",13);
			    					LODOP.SET_PRINT_STYLEA(6,"Bold",1);
			    					
			    					LODOP.ADD_PRINT_TEXT(125,0,100,100,foodName);
			    					LODOP.SET_PRINT_STYLEA(7,"FontSize",13);
			    					LODOP.SET_PRINT_STYLEA(7,"Bold",1);
			    					LODOP.ADD_PRINT_TEXT(125,100,100,100,value);
			    					LODOP.SET_PRINT_STYLEA(8,"FontSize",13);
			    					LODOP.SET_PRINT_STYLEA(8,"Bold",1);
			    					LODOP.SET_PRINT_PAGESIZE(3,1385,25,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽1385mm；25表示页底空白2.5mm
			    					if (LODOP.SET_PRINTER_INDEX(da[i].print_index))
			    					LODOP.PRINT();  
			                	} 
			                	//更新数据库
			                	var url = "tcsbOrderController.do?retreatOrder";
			                	var orderId = $("#id").val();
			                     $.ajax({
			                         url:url,
			                         type:"GET",
			                         dataType:"JSON",
			                         data:{
			                        	 orderId:orderId,
			                        	 totalPrice:$("#totalPrice").val(),
			                        	 offlinePrice:$("#offlinePrice").val(),
			                        	 orderItemId:orderItemId,
			                        	 orderItemnum:numFinall
			                         },
			                         success:function(data){
			                             if(data.success){
			                            	 
			                             }
			                         }
			                     });  
			                }
			            }
			        });
			  }
			});  
			 resetTrNum('add_tcsbOrderItem_table'); 
		        return false;
	    }); 
	}
	
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tcsbOrderItem_table").createhftable({
	    	height:'300px',
			width:'auto',
			fixFooter:false
			});
    });
    
    $(document).ready(function(){
    	var paystate = $("#paystatus").val();
		if(paystate == 1){
				$('input').attr("readonly","readonly")//将input元素设置为readonly 
		}
    });
</script>
<div style="padding: 3px; height: 25px; width: auto;"
	class="datagrid-toolbar">
	<a id="addTcsbOrderItemBtn" href="#">添加</a> <a id="delTcsbOrderItemBtn"
		href="#">删除</a> <a id="retreatTcsbOrderItemBtn" href="#">退菜</a>
</div>

<table border="0" cellpadding="2" cellspacing="0"
	id="tcsbOrderItem_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE" style="width: 25px;">序号</td>
		<td align="center" bgcolor="#EEEEEE" style="width: 35px;">操作</td>
		<td align="center" bgcolor="#EEEEEE" style="width: 126px;">食品分类</td>
		<td align="center" bgcolor="#EEEEEE" style="width: 126px;">食物</td>
		<td align="center" bgcolor="#EEEEEE" style="width: 126px;">数量</td>
		<td align="center" bgcolor="#EEEEEE" style="width: 126px;">单位</td>

		<td align="center" bgcolor="#EEEEEE" style="width: 126px;">单价</td>
		<td align="center" bgcolor="#EEEEEE" style="width: 126px;">菜品规格</td>

		<!--   <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						菜品ID
				  </td> -->
		<!--   <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						创建时间
				  </td> -->
	</tr>
	<tbody id="add_tcsbOrderItem_table">

		<c:if test="${fn:length(tcsbOrderItemList)  > 0 }">
			<c:forEach items="${tcsbOrderItemList}" var="poVal" varStatus="stuts">
				<tr>
					<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
					<td align="center"><input style="width: 20px;" type="checkbox"
						name="ck" /></td>
					<input name="tcsbOrderItemList[${stuts.index }].id" type="hidden"
						value="${poVal.id }" />
					<input name="tcsbOrderItemList[${stuts.index }].orderId"
						type="hidden" value="${poVal.orderId }" />
					<input name="tcsbOrderItemList[${stuts.index }].updateName"
						type="hidden" value="${poVal.updateName }" />
					<input name="tcsbOrderItemList[${stuts.index }].updateDate"
						type="hidden" value="${poVal.updateDate }" />
					<input name="tcsbOrderItemList[${stuts.index }].updateBy"
						type="hidden" value="${poVal.updateBy }" />
					<input name="tcsbOrderItemList[${stuts.index }].createName"
						type="hidden" value="${poVal.createName }" />
					<input name="tcsbOrderItemList[${stuts.index }].createBy"
						type="hidden" value="${poVal.createBy }" />

					<td align="left">
						<%-- <t:dictSelect field="tcsbOrderItemList[${stuts.index }].foodId" type="checkbox"
										typeGroupCode="" defaultVal="${poVal.foodId }" hasLabel="false"  title="食物ID"></t:dictSelect>   --%>
						<select id="foodTypeId"
						name="tcsbOrderItemList[${stuts.index }].foodTypeId" datatype="*"
						disabled="disabled">
							<c:forEach items="${tcsbFoodTypeEntity}" var="tcsbFoodTypeEntity">
								<option value="${tcsbFoodTypeEntity.id }"
									<c:if test="${poVal.foodTypeId==tcsbFoodTypeEntity.id}">selected="selected"</c:if>>${tcsbFoodTypeEntity.name }</option>
							</c:forEach>
					</select> <%-- <select id="foodId" name="tcsbOrderItemList[${stuts.index }].foodId" datatype="*" disabled="disabled">
								<c:forEach items="${tcsbFoodEntities}" var="tcsbFoodEntitie">
									<option value="${tcsbFoodEntitie.id }" <c:if test="${poVal.foodId==tcsbFoodEntitie.id}">selected="selected"</c:if>>${tcsbFoodEntitie.name }</option>
								</c:forEach>  
							</select>	 --%> <label class="Validform_label"
						style="display: none;">食物</label>
					</td>
					<td align="left">
						<%-- <t:dictSelect field="tcsbOrderItemList[${stuts.index }].foodId" type="checkbox"
										typeGroupCode="" defaultVal="${poVal.foodId }" hasLabel="false"  title="食物ID"></t:dictSelect>   --%>
						<!-- 	 --> <select id="foodId"
						name="tcsbOrderItemList[${stuts.index }].foodId" datatype="*"
						disabled="disabled">
							<c:forEach items="${tcsbFoodEntities}" var="tcsbFoodEntitie">
								<option value="${tcsbFoodEntitie.id }"
									<c:if test="${poVal.foodId==tcsbFoodEntitie.id}">selected="selected"</c:if>>${tcsbFoodEntitie.name }</option>
							</c:forEach>
					</select> <label class="Validform_label" style="display: none;">食物</label>
					</td>
					<td align="left"><c:if test="${poVal.isFloat==1 }">
							<input name="tcsbOrderItemList[${stuts.index }].count"
								maxlength="10" type="text" class="inputxt" style="width: 120px;"
								value="${poVal.count }" onblur="changNum(this)"
								datatype="/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/">

						</c:if> <c:if test="${poVal.isFloat==0 }">
							<input name="tcsbOrderItemList[${stuts.index }].count"
								maxlength="10" type="text" class="inputxt" style="width: 120px;"
								value="<fmt:formatNumber type="number" value="${poVal.count }" maxFractionDigits="0"/>"
								onblur="changNum(this)" datatype="n">
						</c:if> <%-- <input name="tcsbOrderItemList[${stuts.index }].count" maxlength="10" 
					  		type="text" class="inputxt"  style="width:120px;"  value="${poVal.count }" onblur="changNum(this)" datatype="n"> --%>
						<input type="hidden" class="oldNum" value="${poVal.count }">
						<label class="Validform_label" style="display: none;">数量</label></td>
					<td align="left"><input
						name="tcsbOrderItemList[${stuts.index }].unitName" maxlength="22"
						type="text" class="inputxt" style="width: 80px;"
						value="${poVal.unitName }" readonly="true"> <label
						class="Validform_label" style="display: none;">单位</label></td>

					<td align="left"><input
						name="tcsbOrderItemList[${stuts.index }].price" maxlength="22"
						type="text" class="inputxt" style="width: 80px;"
						value="${poVal.price }" readonly="true"> <label
						class="Validform_label" style="display: none;">单价</label></td>

					<td align="left"><input
						name="tcsbOrderItemList[${stuts.index }].foodTasteFun"
						maxlength="22" type="text" class="inputxt"
						style="width: 120px; cursor: pointer;" title="编辑菜品规格" alt="编辑菜品规格"
						value="${poVal.foodTasteFun }" onclick="getfoodtastefun(this)">
						<label class="Validform_label" style="display: none;">菜品规格</label>
					</td>


					<%--  <td align="left">
							<t:dictSelect field="tcsbOrderItemList[${stuts.index }].foodTypeId" type="checkbox"
										typeGroupCode="" defaultVal="${poVal.foodTypeId }" hasLabel="false"  title="菜品ID"></t:dictSelect>     
					  <label class="Validform_label" style="display: none;">菜品ID</label>
				   </td> --%>
					<%--   <td align="left">
							<input name="tcsbOrderItemList[${stuts.index }].createDate" maxlength="0" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"    value="<fmt:formatDate value='${poVal.createDate}' type="date" pattern="yyyy-MM-dd"/>">
					  <label class="Validform_label" style="display: none;">创建时间</label>
				   </td> --%>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
<script>
function changNum(obj){
	var oldVal=$(obj).siblings().val();
	var newVal=$(obj).val();
	var trObj = $(obj).parent().parent();
	var price = trObj.find('td:eq(6) input').val();//5-6
	var finalPrice = 0;
	var totalPrice = 0;
	if(oldVal>newVal){
		finalPrice =(oldVal-newVal)*parseFloat(price);
		totalPrice = parseFloat($("#totalPrice").val())-parseFloat(finalPrice);
	}else{
		finalPrice =(newVal-oldVal)*parseFloat(price);
		totalPrice = parseFloat($("#totalPrice").val())+parseFloat(finalPrice);
	}
	 $("#totalPrice").val(toDecimal(totalPrice));
	 $("#offlinePrice").val(toDecimal(totalPrice));
	 $(obj).siblings().val(newVal)
}


function getfoodtastefun(obj){
	  //var foodid = $(obj).val();
	  //alert(foodid);
	  var trObj = $(obj).parent().parent();
	  var serialnumber = trObj.find('td:eq(0) div').html();
	  var foodid = trObj.find('td:eq(3) select').val();//2-3
	  //检测食品是否有规格可选
	  var checkurl = "tcsbFoodController.do?checkfoodTasteFun";
	  $.post(
			checkurl,
			 {foodId:foodid},
			  function(data){
				  if(data == 1){
					  //ajax请求获取食品规格
					  var url = "tcsbFoodController.do?shopgetfoodTasteFun";
					  $.post(
							  url,
							  {foodId:foodid},
							  function(data){
								  data = jQuery.parseJSON(data);
								  $("#funtastetable").html("");
								  var htmllist = "";
								  for(var i=0;i<data.length;i++){
									  htmllist =htmllist + "<tr>"+
										"<td align='center' class='value' style='width:30%;'>"+
										"<label class='Validform_label'>"+data[i].firstFun+"</label>"+
										"</td>"+
										"<td class='value'>"+
										"<ul class='dowebok'>";
									  var foodTasteVo = data[i].foodTasteVo;
									  for(var j=0;j<foodTasteVo.length;j++){
										  htmllist = htmllist +"<li>"+
											"<input type='radio' name='"+data[i].firstFun+"' value='"+foodTasteVo[j].tasteName+"' data-labelauty='"+foodTasteVo[j].tasteName+"'>"
											"</li>";
									  }
									  htmllist = htmllist + "</ul></td></tr>";
									 
								  }
								  $("#funtastetable").append(htmllist);
								  $('#funtastetable input').labelauty();
								  $("#tcsbOrderItemListView").css("z-index",-1);
								  $("#funtasteselect").css("z-index",1);
								  $("#objhidden").val(serialnumber);
							  }
					  );
				  }else{
					  alert("该菜品没有规格可选");
				  }
			  }
	  );  
} 
</script>
