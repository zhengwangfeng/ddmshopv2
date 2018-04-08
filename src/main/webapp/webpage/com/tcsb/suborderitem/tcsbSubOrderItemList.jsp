<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addTcsbSubOrderItemBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delTcsbSubOrderItemBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addTcsbSubOrderItemBtn').bind('click', function(){   
 		 var tr =  $("#add_tcsbSubOrderItem_table_template tr").clone();
	 	 $("#add_tcsbSubOrderItem_table").append(tr);
	 	 resetTrNum('add_tcsbOrderItem_table');
	 	 return false;
    });  
	$('#delTcsbSubOrderItemBtn').bind('click', function(){   
      	$("#add_tcsbSubOrderItem_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_tcsbOrderItem_table'); 
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tcsbSubOrderItem_table").createhftable({
	    	height:'300px',
			width:'auto',
			fixFooter:false
			});
    });
</script>
<div style="padding: 3px; height: 25px; width: auto;"
	class="datagrid-toolbar">
	<a id="addTcsbSubOrderItemBtn" href="#">添加</a> <a
		id="delTcsbSubOrderItemBtn" href="#">删除</a>
</div>
<table border="0" cellpadding="2" cellspacing="0"
	id="tcsbSubOrderItem_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE" style="width: 25px;">序号</td>
		<td align="center" bgcolor="#EEEEEE" style="width: 25px;">操作</td>
		<td align="left" bgcolor="#EEEEEE" style="width: 126px;">食物</td>
		<td align="left" bgcolor="#EEEEEE" style="width: 126px;">数量</td>
		<td align="left" bgcolor="#EEEEEE" style="width: 126px;">单价</td>
		<!--   <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						菜品ID
				  </td> -->
		<!--   <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						创建时间
				  </td> -->
	</tr>
	<tbody id="add_tcsbSubOrderItem_table">
		<c:if test="${fn:length(tcsbSubOrderItemList)  > 0 }">
			<c:forEach items="${tcsbSubOrderItemList}" var="poVal"
				varStatus="stuts">
				<tr>
					<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
					<td align="center"><input style="width: 20px;" type="checkbox"
						name="ck" /></td>
					<input name="tcsbSubOrderItemList[${stuts.index }].id"
						type="hidden" value="${poVal.id }" />
					<input name="tcsbSubOrderItemList[${stuts.index }].orderId"
						type="hidden" value="${poVal.orderId }" />
					<input name="tcsbSubOrderItemList[${stuts.index }].updateName"
						type="hidden" value="${poVal.updateName }" />
					<input name="tcsbSubOrderItemList[${stuts.index }].updateDate"
						type="hidden" value="${poVal.updateDate }" />
					<input name="tcsbSubOrderItemList[${stuts.index }].updateBy"
						type="hidden" value="${poVal.updateBy }" />
					<input name="tcsbSubOrderItemList[${stuts.index }].createName"
						type="hidden" value="${poVal.createName }" />
					<input name="tcsbSubOrderItemList[${stuts.index }].createBy"
						type="hidden" value="${poVal.createBy }" />
					<td align="left">
						<%-- <t:dictSelect field="tcsbOrderItemList[${stuts.index }].foodId" type="checkbox"
										typeGroupCode="" defaultVal="${poVal.foodId }" hasLabel="false"  title="食物ID"></t:dictSelect>   --%>
						<select id="foodId"
						name="tcsbSubOrderItemList[${stuts.index }].foodId" datatype="*">
							<c:forEach items="${tcsbFoodEntities}" var="tcsbFoodEntitie">
								<option value="${tcsbFoodEntitie.id }"
									<c:if test="${poVal.foodId==tcsbFoodEntitie.id}">selected="selected"</c:if>>${tcsbFoodEntitie.name }</option>
							</c:forEach>
					</select> <label class="Validform_label" style="display: none;">食物</label>
					</td>
					<td align="left"><input
						name="tcsbSubOrderItemList[${stuts.index }].count" maxlength="10"
						type="text" class="inputxt" style="width: 120px;"
						value="${poVal.count }"> <label class="Validform_label"
						style="display: none;">数量</label></td>
					<td align="left"><input
						name="tcsbSubOrderItemList[${stuts.index }].price" maxlength="22"
						type="text" class="inputxt" style="width: 120px;"
						value="${poVal.price }"> <label class="Validform_label"
						style="display: none;">单价</label></td>
					<%--  <td align="left">
							<t:dictSelect field="tcsbSubOrderItemList[${stuts.index }].foodTypeId" type="checkbox"
										typeGroupCode="" defaultVal="${poVal.foodTypeId }" hasLabel="false"  title="菜品ID"></t:dictSelect>     
					  <label class="Validform_label" style="display: none;">菜品ID</label>
				   </td> --%>
					<%--   <td align="left">
							<input name="tcsbSubOrderItemList[${stuts.index }].createDate" maxlength="0" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"    value="<fmt:formatDate value='${poVal.createDate}' type="date" pattern="yyyy-MM-dd"/>">
					  <label class="Validform_label" style="display: none;">创建时间</label>
				   </td> --%>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
<script>

</script>
