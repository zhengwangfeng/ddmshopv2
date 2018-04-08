<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style>
.addbtnstyleclass {
	height: 21px;
	line-height: 21px;
	padding: 0 11px;
	background: #4ba8f7;
	border: 1px #26bbdb solid;
	border-radius: 3px;
	display: inline-block;
	text-decoration: none;
	font-size: 12px;
	outline: none;
	color: white;
}

.delbtnstyleclass {
	height: 21px;
	line-height: 21px;
	padding: 0 11px;
	background: #9D9D9D;
	border: 1px #9D9D9D solid;
	border-radius: 3px;
	display: inline-block;
	text-decoration: none;
	font-size: 12px;
	outline: none;
	color: white;
}
</style>
<%--<t:datagrid name="userList" title="user.manage" actionUrl="roleController.do?roleUserDatagrid&roleId=${roleId}" fit="true" fitColumns="true" idField="id">--%>
<%--	<t:dgCol title="common.id" field="id" hidden="true" ></t:dgCol>--%>
<%--	<t:dgCol title="common.username" sortable="false" field="userName" width="5"></t:dgCol>--%>
<%--	<t:dgCol title="common.real.name" field="realName" width="5"></t:dgCol>--%>
<%--</t:datagrid>--%>
<t:datagrid name="foodStandardList" title="规格设置"
	actionUrl="tcsbFoodStandardController.do?foodStandardDatagrid&foodId=${foodId}"
	fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="名称" sortable="false" field="name" query="true"></t:dgCol>
	<t:dgCol title="价格" field="price"></t:dgCol>
	<t:dgCol title="状态" field="statusName"></t:dgCol>
	<t:dgCol title="状态" field="status" hidden="true"></t:dgCol>
	<%-- <t:dgCol title="common.status" sortable="true" field="status" replace="common.active_1,common.inactive_0,super.admin_-1"></t:dgCol> --%>
	<t:dgCol title="common.operation" field="opt"></t:dgCol>
	<%-- <t:dgDelOpt title="停用" url="tcsbFoodStandardController.do?delUserRole&standardId={id}&foodId=${foodId }" urlclass="ace_button"  urlfont="fa-trash-o"/>  --%>
	<t:dgFunOpt funname="updatestatus1(id)" title="启用" exp="status#eq#0"
		urlclass="addbtnstyleclass" />
	<t:dgFunOpt funname="updatestatus0(id)" title="停用" exp="status#eq#1"
		urlclass="delbtnstyleclass" />
	<t:dgToolBar title="添加规格" icon="icon-add"
		url="tcsbFoodStandardController.do?goAdd&foodId=${foodId}"
		funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑规格" icon="icon-edit"
		url="tcsbFoodStandardController.do?goUpdate&foodId=${foodId}"
		funname="update"></t:dgToolBar>
</t:datagrid>

<script type="text/javascript">
 function updatestatus0(id){
	var url = "tcsbFoodStandardController.do?updateStatus";
     $.ajax({
        url:url,
        type:"POST",
        dataType:"JSON",
        data:{
        	id:id,
            status:0
        },
        success:function(data){
        	$('#foodStandardList').datagrid('reload'); 
        }
    }); 
} 
 function updatestatus1(id){
		var url = "tcsbFoodStandardController.do?updateStatus";
	     $.ajax({
	        url:url,
	        type:"POST",
	        dataType:"JSON",
	        data:{
	        	id:id,
	            status:1
	        },
	        success:function(data){
	        	$('#foodStandardList').datagrid('reload'); 
	        }
	    }); 
	} 

</script>

