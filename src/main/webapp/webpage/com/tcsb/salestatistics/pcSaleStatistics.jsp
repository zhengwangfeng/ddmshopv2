<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<script type="text/javascript">
  $(function(){
		$('#tcsbSaleStatisticsList').datagrid({
			idField: 'id',
			title: '操作',
			url:'tcsbSaleStatisticsController.do?datagrid',
			fit:true,
			loadMsg: '数据加载中...',
			pageSize: 10,
			pagination:true,
			pageList:[10,20,30],
			sortOrder:'asc',
			rownumbers:true,
			singleSelect:true,
			fitColumns:true,
			showFooter:true,
			frozenColumns:[[]],
			columns:[[
						{field:'foodName',title:'商品名称',width:'220'},
						{field:'allSaleCount',title:'总销量',width:'220'},
						{field:'onSaleTime',title:'上架时间',width:'220',formatter:function(value,rec,index){
							return new Date(parseInt(rec.onSaleTime)).toLocaleString().replace(/:\d{1,2}$/,' '); ;
						}},
						{field:'vipSaleCount',title:'会员复购数',width:'220'},
						{field:'opt',title:'操作',width:'120',sortable:true,formatter:function(value,rec,index){
								var href='';
								href+="<span style='margin-left:30px;'>[<a style='color:red;' href='#' onclick=foodDetail('"+rec.foodId+"')>";
								href+="查看详情</a></span>]";
								return href;
							
							}
						}]
					],
			onLoadSuccess:function(data){
				$("#tcsbSaleStatisticsList").datagrid("clearSelections");
			},
			onClickRow:function(rowIndex,rowData){
				rowid=rowData.id;gridname='tcsbSaleStatisticsList';
			}
		});
		$('#tcsbSaleStatisticsList').datagrid('getPager').pagination({
			beforePageText:'',
			afterPageText:'/{pages}',
			displayMsg:'{from}-{to}共 {total}条',
			showPageList:true,showRefresh:true
		});
		$('#tcsbSaleStatisticsList').datagrid('getPager').pagination({
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
		$('#tcsbSaleStatisticsList').datagrid('reload');
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
	
	function foodDetail(id){
		//alert(id);
		window.location.href = "tcsbSaleStatisticsController.do?detaillist&foodid="+id;
	}
	

</script>

		<table id="tcsbSaleStatisticsList"></table>

	</div>
</div>






<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tcsbSaleStatisticsList" checkbox="true" fitColumns="false" title="商品销量统计" actionUrl="tcsbSaleStatisticsController.do?datagrid" idField="foodId" fit="true" queryMode="group">
   <t:dgCol title="ID"  field="foodId"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="商品名称"  field="foodName"    queryMode="group"  width="220"></t:dgCol>
   <t:dgCol title="总销量"  field="allSaleCount"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="上架时间"  field="onSaleTime"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group"  width="180"></t:dgCol>
   <t:dgCol title="会员复购数"  field="vipSaleCount"    queryMode="group"  width="120"></t:dgCol>
  <t:dgCol title="操作" field="opt" width="100"></t:dgCol> 
   <t:dgDelOpt title="删除" url="tcsbDeskController.do?doDel&id={foodId}" urlclass="ace_button" urlfont="fa-trash-o"/>
    <t:dgToolBar title="录入" icon="icon-add" url="tcsbSaleStatisticsController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbSaleStatisticsController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbSaleStatisticsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看详情" icon="icon-search" url="tcsbSaleStatisticsController.do?goUpdate" funname="tcsbfinddetail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/tcsb/salestatistics/tcsbSaleStatisticsList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbSaleStatisticsListtb").find("input[name='createTime_begin']").attr("class","Wdate").attr("style","width:150px").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
 			$("#tcsbSaleStatisticsListtb").find("input[name='createTime_end']").attr("class","Wdate").attr("style","width:150px").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss' });});
 });
 
   
 function  test(value,row,index){
		
	}
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbSaleStatisticsController.do?upload', "tcsbSaleStatisticsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbSaleStatisticsController.do?exportXls","tcsbSaleStatisticsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbSaleStatisticsController.do?exportXlsByT","tcsbSaleStatisticsList");
}
 </script> --%>