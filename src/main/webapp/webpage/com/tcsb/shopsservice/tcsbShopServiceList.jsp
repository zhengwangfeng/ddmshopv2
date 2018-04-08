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
	background: #e60012;
	border: 1px #26bbdb solid;
	border-radius: 3px;
	display: inline-block;
	text-decoration: none;
	font-size: 12px;
	outline: none;
	color: white;
}
</style>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="tcsbServiceList" checkbox="false" fitColumns="false"
			title="店铺评价" actionUrl="tcsbShopServiceController.do?datagrid2"
			idField="id" fit="true" queryMode="group">
			<%--  <t:dgCol title="ID"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="图标" align="center" field="logo" image="true" imageSize="50,50"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="服务名称" align="center" field="name"    queryMode="group"  width="130"></t:dgCol>
   <t:dgCol title="state" align="center" field="state"  hidden="true"  queryMode="group"  width="130"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
   <t:dgFunOpt funname="addObj(id)" title="添加服务"  exp="state#eq#0" urlclass="addbtnstyleclass"/>
   <t:dgFunOpt funname="delObj(id)" title="取消服务"  exp="state#eq#1" urlclass="delbtnstyleclass"/>  --%>
			<t:dgToolBar title="规格设置" icon="icon-add"
				url="tcsbShopEvaluateController.do?goAdd" funname="setfunbyrole()"></t:dgToolBar>
			<%--  <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbShopEvaluateController.do?goUpdate" funname="update"></t:dgToolBar> --%>
			<%--  <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbShopEvaluateController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbShopEvaluateController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
			<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>

<div id="foodtaste" region="east" style="width: 500px; display: none;"
	split="true">
	<div tools="#tt" class="easyui-panel" title='服务' style="padding: 10px;"
		fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>

<script src="webpage/com/tcsb/shopevaluate/tcsbShopEvaluateList.js"></script>
<script type="text/javascript">
 
 
 function setfunbyrole() {
	 $("#foodtaste").show();
		$("#function-panel").panel(
			{
				title :'食品规格',
				href:"tcsbShopServiceController.do?fun"
			}
		);
	}
 
  setfunbyrole();
 
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbShopEvaluateListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbShopEvaluateListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
	function delObj(id){
		var url = "tcsbShopServiceController.do?doDel";
		$.post(
				url,
				{serviceId:id},
				function(data){
					$('#tcsbServiceList').datagrid('reload');	
				}
		);
	}
	
	function addObj(id){
		var url = "tcsbShopServiceController.do?doAdd";
		$.post(
				url,
				{serviceId:id},
				function(data){
					$('#tcsbServiceList').datagrid('reload');	
				}
		);
		
	}
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbShopEvaluateController.do?upload', "tcsbShopEvaluateList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbShopEvaluateController.do?exportXls","tcsbShopEvaluateList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbShopEvaluateController.do?exportXlsByT","tcsbShopEvaluateList");
}
 </script>





<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <script type="text/javascript">
  $(function(){
		$('#tcsbServiceList').datagrid({
			idField: 'id',
			title: '操作',
			url:'tcsbShopServiceController.do?datagrid&field=id,name,logo,state',
			fit:true,
			loadMsg: '数据加载中...',
			pageSize: 10,
			pagination:false,
			pageList:[10,20,30],
			sortOrder:'asc',
			rownumbers:true,
			singleSelect:true,
			fitColumns:true,
			showFooter:true,
			frozenColumns:[[]],
			columns:[[
						
						{field:'logo',title:'图标',width:'200',formatter:function(value,rec,index){
								var href='';
								href+="<img style='display:block;margin:0 auto;' width='90' height='50' border='0' src='"+rec.logo+"'>";
								return href;
							
							}},
						{field:'name',title:'服务名称',width:'220'},
						{field:'opt',title:'操作',width:'120',sortable:true,formatter:function(value,rec,index){
							if(rec.state == 1){
								var href='';
								href+="<span style='margin-left:30px;'>[<a style='color:red;' href='#' onclick=delObj('"+rec.id+"')>";
								href+="取消服务</a></span>]";
								return href;
							}else{
								var href='';
								href+="<span style='margin-left:30px;'>[<a style='color:green' href='#' onclick=addObj('"+rec.id+"')>";
								href+="添加服务</a></span>]";
								return href;
							}
							}
						}]
					],
			onLoadSuccess:function(data){
				$("#tcsbServiceList").datagrid("clearSelections");
			},
			onClickRow:function(rowIndex,rowData){
				rowid=rowData.id;gridname='tcsbServiceList';
			}
		});
		$('#tcsbServiceList').datagrid('getPager').pagination({
			beforePageText:'',
			afterPageText:'/{pages}',
			displayMsg:'{from}-{to}共 {total}条',
			showPageList:true,showRefresh:true
		});
		$('#tcsbServiceList').datagrid('getPager').pagination({
			onBeforeRefresh:function(pageNumber, pageSize){ 
				$(this).pagination('loading');
				$(this).pagination('loaded'); 
			}});
		});
	function reloadTable(){
		try{	
			$('#'+gridname).datagrid('reload');	
			$('#'+gridname).treegrid('reload');
		}catch(ex){
			
		}
	}
	function reloaduserList(){
		$('#userList').datagrid('reload');
	}
	function getuserListSelected(field){
		return getSelected(field);
	}
	function getSelected(field){
		var row = $('#'+gridname).datagrid('getSelected');
		if(row!=null){
			value= row[field];
		}else{
			value='';
		}
		return value;
	}
	
	function delObj(id){
		var url = "tcsbShopServiceController.do?doDel";
		$.post(
				url,
				{serviceId:id},
				function(data){
					$('#tcsbServiceList').datagrid('reload');	
				}
		);
	}
	
	function addObj(id){
		var url = "tcsbShopServiceController.do?doAdd";
		$.post(
				url,
				{serviceId:id},
				function(data){
					$('#tcsbServiceList').datagrid('reload');	
				}
		);
		
	}
</script>

	<table id="tcsbServiceList"></table>

</div>
</div>

 --%>