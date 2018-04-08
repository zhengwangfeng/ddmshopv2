<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>会员级别权益</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
        //编写自定义JS代码
    </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tcsbMemberLevelEquityController.do?doUpdate">
		<input id="id" name="id" type="hidden"
			value="${tcsbMemberLevelEquityPage.id }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tcsbMemberLevelEquityPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tcsbMemberLevelEquityPage.updateDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tcsbMemberLevelEquityPage.updateBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tcsbMemberLevelEquityPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tcsbMemberLevelEquityPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tcsbMemberLevelEquityPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">


			<tr>
				<td align="right"><label class="Validform_label"> 名称: </label>
				</td>
				<!-- <input id="fullcutTemplateId" name="fullcutTemplateId" type="text" style="width: 150px" class="inputxt" > -->
				<td class="value"><input id="name" name="name" type="text"
					style="width: 150px" class="inputxt" datatype="*"
					value='${tcsbMemberLevelEquityPage.name }'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 是否打折:
				</label></td>
				<td class="value"><t:dictSelect field="isDiscount"
						type="select" typeGroupCode="sf_10"
						defaultVal="${tcsbMemberLevelEquityPage.isDiscount}"
						hasLabel="false" title="是否打折" datatype="*"></t:dictSelect> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">是否打折</label></td>
			</tr>
			<%--<tr>--%>
			<%--<td align="right">--%>
			<%--<label class="Validform_label">--%>
			<%--打折:--%>
			<%--</label>--%>
			<%--</td>--%>
			<%--<td class="value">--%>
			<%--<t:dictSelect field="discount" type="list"--%>
			<%--typeGroupCode="" defaultVal="${tcsbMemberLevelEquityPage.discount}" hasLabel="false"  title="打折"></t:dictSelect>     --%>
			<%--<span class="Validform_checktip"></span>--%>
			<%--<label class="Validform_label" style="display: none;">打折</label>--%>
			<%--</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
			<%--<td align="right">--%>
			<%--<label class="Validform_label">--%>
			<%--是否赠送卡卷:--%>
			<%--</label>--%>
			<%--</td>--%>
			<%--<td class="value">--%>
			<%--<t:dictSelect field="isGiveCard" type="radio"--%>
			<%--typeGroupCode="" defaultVal="${tcsbMemberLevelEquityPage.isGiveCard}" hasLabel="false"  title="是否赠送卡卷"></t:dictSelect>     --%>
			<%--<span class="Validform_checktip"></span>--%>
			<%--<label class="Validform_label" style="display: none;">是否赠送卡卷</label>--%>
			<%--</td>--%>
			<%--<tr>--%>
			<%--<td align="right">--%>
			<%--<label class="Validform_label">--%>
			<%--卡卷ID:--%>
			<%--</label>--%>
			<%--</td>--%>
			<%--<td class="value">--%>
			<%--<input id="cardId" name="cardId" type="text" style="width: 150px" class="inputxt"  value='${tcsbMemberLevelEquityPage.cardId}'>--%>
			<%--<span class="Validform_checktip"></span>--%>
			<%--<label class="Validform_label" style="display: none;">卡卷ID</label>--%>
			<%--</td>--%>
			<%--</tr>--%>
		</table>
	</t:formvalid>
</body>
<script>
    $(function () {
        var html = "<tr id='tr1'>" +
            "<td align='right'>" +
            "<label class='Validform_label'>" +
            "折扣:" +
            "</label>" +
            "</td>" +

            "<td class='value'>" +
            "<input id='discount' name='discount' type='text' value='${tcsbMemberLevelEquityPage.discount }' style='width: 150px' class='inputxt' datatype='/^(0\\.([1-9]|\\d[1-9])|[1-9](\\.\\d{1,2})?)$/' errormsg='请输入正确的折扣0到10之间'>" +
            "<span class='Validform_checktip'></span>" +
            "<label class='Validform_label' style='display: none;'>折扣</label>" +
            "</td>";
        if ($("select[name='isDiscount']").val() == "1") {

            $("select[name='isDiscount']").parent().parent().after(html);
        }
        $("select[name='isDiscount']").change(function () {
            if ($(this).val() == "1") {
                $(this).parent().parent().after(html);
            } else {
                $("#tr1").remove();
            }
        })
    })

</script>
<script
	src="webpage/com/tcsb/memberlevelequity/tcsbMemberLevelEquity.js"></script>