//http://localhost:8080/jeecgos/tcsbServiceController.do?datagrid&field=id,name,logo,updateName,updateDate,updateBy,createName,createBy,createDate,
$(function(){
	alert("123");
	$('#tcsbServiceList').datagrid({
		idField: 'id',
		title: '操作',
		url:'tcsbServiceController.do?datagrid&field=id,name,logo,updateName,updateDate,updateBy,createName,createBy,createDate,',
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
					{field:'id',title:'编号',hidden:true,sortable:true},
					{field:'name',title:'用户名'},
					{field:'logo',title:'组织机构',sortable:true},
					{field:'updateName',title:'真实姓名',sortable:true},
					{field:'updateBy',title:'角色',sortable:true},
					{field:'createName',title:'状态',sortable:true,formatter:function(value,rec,index){
						var valArray = value.split(",");
						if(valArray.length > 1){
							var checkboxValue = "";
							for(var k=0; k<valArray.length; k++){
								if(valArray[k] == '1'){ 
									checkboxValue = checkboxValue + '激活' + ','
								}if(valArray[k] == '0'){
									checkboxValue = checkboxValue + '未激活' + ','
								}if(valArray[k] == '-1'){ 
									checkboxValue = checkboxValue + '超级管理员' + ','
								}
							}
							return checkboxValue.substring(0,checkboxValue.length-1);
						}else{
								if(value=='1'){
									return '激活'
								}if(value=='0'){
									return '未激活'
								}if(value=='-1'){
									return '超级管理员'
								}else{
									return value
								}
							}
						}
					},
					{field:'opt',title:'?操作',formatter:function(value,rec,index){
						if(!rec.id){
							return '';
						}
						var href='';
						href+="[<a href='#' onclick=delObj('userController.do?del&id="+rec.id +"&userName="+rec.userName +"','userList')>";href+="删除</a>]";return href;}
						}
					]
				],
		onLoadSuccess:function(data){
			$("#userList").datagrid("clearSelections");
		},
		onClickRow:function(rowIndex,rowData){
			rowid=rowData.id;gridname='userList';
		}
	});
	$('#userList').datagrid('getPager').pagination({
		beforePageText:'',
		afterPageText:'/{pages}',
		displayMsg:'{from}-{to}共 {total}条',
		showPageList:true,showRefresh:true
	});
	$('#userList').datagrid('getPager').pagination({
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
function getuserListSelections(field){
	var ids = [];
	var rows = $('#userList').datagrid('getSelections');
	for(var i=0;i<rows.length;i++){
		ids.push(rows[i][field]);
	}
	ids.join(',');
	return ids
};
function getSelectRows(){	
	return $('#userList').datagrid('getChecked');
}
function userListsearch(){
	var queryParams=$('#userList').datagrid('options').queryParams;
	$('#userListtb').find('*').each(function(){
		queryParams[$(this).attr('name')]=$(this).val();
	});
	$('#userList').datagrid({
		url:'userController.do?getUserList&departId=&field=id,userName,orgName,realName,roleName,status,',
		pageNumber:1
	});
}
function dosearch(params){
	var jsonparams=$.parseJSON(params);
	$('#userList').datagrid({
		url:'userController.do?getUserList&departId=&field=id,userName,orgName,realName,roleName,status,',
		queryParams:jsonparams
	});
}
function userListsearchbox(value,name){
	var queryParams=$('#userList').datagrid('options').queryParams;
	queryParams[name]=value;
	queryParams.searchfield=name;
	$('#userList').datagrid('reload');
}
$('#userListsearchbox').searchbox({
	searcher:function(value,name){
		userListsearchbox(value,name);
	},
	menu:'#userListmm',
	prompt:'请输入查询关键字'
});
function EnterPress(e){
	var e = e || window.event;
	if(e.keyCode == 13){ 
		userListsearch();
	}
}
function searchReset(name){ 
	$("#"+name+"tb").find(":input").val("");
	userListsearch();
}
