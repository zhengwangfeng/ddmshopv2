<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title></title>
<style type="text/css">
table.gridtable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}

table.gridtable th {
	width: 200px;
	border-width: 0px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}

table.gridtable td {
	text-align: center;
	border-width: 0px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
</style>
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
<link href="plug-in/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link
	href="http://cdn.bootcss.com/bootstrap-table/1.9.1/bootstrap-table.min.css"
	rel="stylesheet" />
<script src="plug-in/jquery/jquery-1.8.3.js"></script>
<script src="plug-in/bootstrap/js/bootstrap.min.js"></script>
<script
	src="http://cdn.bootcss.com/bootstrap-table/1.9.1/bootstrap-table.min.js"></script>
<script
	src="http://cdn.bootcss.com/bootstrap-table/1.9.1/locale/bootstrap-table-zh-CN.min.js"></script>

<script>
    
    function formatTableUnit(value, row, index) {
        var front_color = 'red';
        var bg_color = 'white';
        return {
            css: {
                "color":front_color,
                "background-color": bg_color
            }
        }
    }
    
    function formatTable(value, row, index) {
        var front_color = 'balck';
        var bg_color = 'red';
        return {
            css: {
                "color":front_color,
                "background-color": bg_color
            }
        }
    }
    
    function formatTable_bg_color_yellow(value, row, index) {
        var front_color = 'black';
        var bg_color = '#FFFFAA';
        return {
            css: {
                "color":front_color,
                "background-color": bg_color
            }
        }
    }
    
    function formatTable_bg_color_purple(value, row, index) {
        var front_color = 'black';
        var bg_color = 'purple';
        return {
            css: {
                "color":front_color,
                "background-color": bg_color
            }
        }
    }
    
    
    
    $(function () {
    	 
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();
 
        //2.初始化Button的点击事件
        /* var oButtonInit = new ButtonInit();
        oButtonInit.Init(); */
 
    });
 
 
    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#tradeList').bootstrapTable({
                url: 'rest/pcIncome/pcdatagrid?shopId=${shopId}',         //请求后台的URL（*）
                method: 'post',                      //请求方式（*）
                toolbar: '#toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                sortable: false,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                queryParams: oTableInit.queryParams,//传递参数（*）
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 10,                       //每页的记录行数（*）
                pageList: [10],        //可供选择的每页的行数（*）
                strictSearch: true,
                clickToSelect: true,                //是否启用点击选中行
                height: 450,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id",                     //每一行的唯一标识，一般为主键列
                cardView: false,                    //是否显示详细视图
                detailView: false,                   //是否显示父子表
                columns: [{
                    field: 'id',
                    title:'序号',
                    width:'100'
                }, {
                    field: 'createdate',
                    title: '时间',
                    width:200
                }, {
                    field: 'totalprice',
                    title: '总营业额',
                    width:200,
                    cellStyle:formatTable
                    
                }, {
                    field: 'onlineIncome',
                    title: '线上营收',
                    width:100,
                    cellStyle:formatTable
                }, {
                    field: 'specialcouponprice',
                    title: '优惠券',
                    width:200,
                    cellStyle:formatTable
                   
                }, {
                    field: 'universalcouponprice',
                    title: '店铺活动',
                    width:100,
                    cellStyle:formatTable
                }, {
                    field: 'platformSettlement',
                    title: '平台需结算',
                    cellStyle:formatTable
                },  {
                    field: 'offlineIncome',
                    title: '线下营收',
                    cellStyle:formatTable_bg_color_purple
                },  {
                    field: 'offlinediscountprice',
                    title: '折扣',
                    cellStyle:formatTable_bg_color_purple
                },  {
                    field: 'offlineDiscount',
                    title: '抹零',
                    cellStyle:formatTable_bg_color_purple
                },  {
                    field: 'offlinepaybycard',
                    title: '刷卡',
                    cellStyle:formatTable_bg_color_purple
                },  {
                    field: 'offlinepaybywechat',
                    title: '线下微信',
                    cellStyle:formatTable_bg_color_purple
                },  {
                    field: 'offlinepaybyaply',
                    title: '线下支付宝',
                    cellStyle:formatTable_bg_color_purple
                },  {
                    field: 'offlinepaybycash',
                    title: '现金',
                    cellStyle:formatTable_bg_color_purple
                },  {
                    field: 'offlinepaybycredit',
                    title: '赊账',
                    cellStyle:formatTable_bg_color_purple
                },  {
                    field: 'offlinepaybyteam',
                    title: '团购',
                    cellStyle:formatTable_bg_color_purple
                },  {
                    field: 'offlinePayment',
                    title: '线下实收',
                    cellStyle:formatTable_bg_color_purple
                },  {
                    field: 'disprice',
                    title: '总实收',
                    cellStyle:formatTable_bg_color_purple
                },  {
                    field: 'opt',
                    title: '操作'
                }]
            });
        };
 
        //得到查询的参数
      oTableInit.queryParams = function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   //页面大小
                offset: params.offset,  //页码
                sdate: 1,
                edate: $("#endTime").val(),
                sellerid: $("#sellerid").val(),
                orderid: $("#orderid").val(),
                CardNumber: $("#CardNumber").val(),
                maxrows: params.limit,
                pageindex:params.pageNumber,
                portid: $("#portid").val(),
                CardNumber: $("#CardNumber").val(),
                tradetype:$('input:radio[name="tradetype"]:checked').val(),
                success:$('input:radio[name="success"]:checked').val(),
            };
            return temp;
        };
        return oTableInit;
    };
    </script>
</head>
<body>


	<table id="tradeList">
	</table>
</body>
</html>