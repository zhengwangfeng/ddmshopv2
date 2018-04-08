<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="memberUserVOVOs" checkbox="true" fitColumns="false"
			title="会员信息" actionUrl="tcsbMemberUserController.do?datagrid"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="卡号" field="cardNo" align="center" query="true"
				width="120"></t:dgCol>
			<t:dgCol title="会员级别ID" field="membershipLevelId" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="会员级别" field="membershipLevelName" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="账户余额" field="balance" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="用户的唯一标识" field="openid" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="用户昵称" field="nickname" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="用户名" field="name" align="center" query="true"
				width="120"></t:dgCol>
			<t:dgCol title="性别" field="sex" dictionary="sex" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="出生日期" field="birthOfDate" align="center"
				formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="省份" field="province" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="城市" field="city" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="国家" field="country" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="用户头像" field="headimgurl" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="用户特权信息" field="privilege" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createTime"
				formatter="yyyy-MM-dd hh:mm:ss" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="商家ID" field="shopId" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="所属商家" field="shopName" align="center"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="联系方式" field="mobile" align="center" query="true"
				width="120"></t:dgCol>
			<t:dgCol title="备注" field="note" align="center" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="更新人名字" field="updateName" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新时间" field="updateTime"
				formatter="yyyy-MM-dd hh:mm:ss" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="更新人" field="updateBy" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="创建人名字" field="createName" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="tcsbMemberUserController.do?doDel&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add"
				url="tcsbMemberUserController.do?goAdd" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit"
				url="tcsbMemberUserController.do?goUpdate" funname="update"></t:dgToolBar>
			<%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbMemberUserController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tcsbMemberUserController.do?goUpdate" funname="detail"></t:dgToolBar>
			<%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
			<%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
			<%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/tcsb/memberuser/tcsbMemberUserList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //给时间控件加上样式
        $("#tcsbMemberUserListtb").find("input[name='birthOfDate_begin']").attr("class", "Wdate").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tcsbMemberUserListtb").find("input[name='birthOfDate_end']").attr("class", "Wdate").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tcsbMemberUserListtb").find("input[name='createTime_begin']").attr("class", "Wdate").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tcsbMemberUserListtb").find("input[name='createTime_end']").attr("class", "Wdate").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tcsbMemberUserListtb").find("input[name='updateTime_begin']").attr("class", "Wdate").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tcsbMemberUserListtb").find("input[name='updateTime_end']").attr("class", "Wdate").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
    });


    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tcsbMemberUserController.do?upload', "tcsbMemberUserList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tcsbMemberUserController.do?exportXls", "tcsbMemberUserList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tcsbMemberUserController.do?exportXlsByT", "tcsbMemberUserList");
    }
</script>