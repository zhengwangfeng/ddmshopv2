<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style>
@charset "utf-8";

.gover_search {
	position: relative;
}

.gover_search .search_t {
	float: left;
	line-height: 26px;
	color: #666666;
	margin-left: 15px;
}

.gover_search .input_search_key {
	float: left;
	width: 200px;
	height: 28px;
	padding: 3px;
	margin-left: 15px;
	margin-right: 15px;
	border: 1px solid #cccccc;
	line-height: 18px;
	background: #FFFFFF;
}

.gover_search .search_suggest {
	position: absolute;
	z-index: 999;
	left: 81px;
	top: 21px;
	width: 220px;
	border: 1px solid #999999;
	display: none;
}

.gover_search .search_suggest li {
	height: 24px;
	overflow: hidden;
	padding-left: 3px;
	line-height: 24px;
	background: #FFFFFF;
	cursor: default;
}

.gover_search .search_suggest li.hover {
	background: #DDDDDD;
}
</style>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<input type="hidden" id="startTime" value="${startTime }" /> <input
			type="hidden" id="endTime" value="${endTime }" />
		<t:datagrid name="tcsbIncomeStatisticsPlaList" checkbox="false"
			isShowSearch="true" fitColumns="false" title="商品销量统计"
			actionUrl="tcsbInComeController.do?adminincomedatagrid" idField="id"
			fit="true" queryMode="group">
			<%-- <t:dgCol title="店铺名称"  field="foodTypeId"  query="true" replace="${foodTypeReplace}" hidden="true"  queryMode="single"  width="120"></t:dgCol> --%>
			<%-- <t:dgCol title="会员数" field="id"  queryMode="group" width="120"></t:dgCol> --%>
			<%-- <t:dgCol title="选择时间段"  hidden="true" field="searchTime" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol> --%>
			<t:dgCol title="时间" field="createdate" formatter="yyyy-MM-dd"
				queryMode="single" width="200"></t:dgCol>


			<t:dgCol title="营业额" field="totalprice" queryMode="group" width="120"></t:dgCol>

			<t:dgCol title="线上支付" field="onlinePayment" queryMode="single"
				width="120"></t:dgCol>
			<t:dgCol title="线下支付" field="offlinePayment" queryMode="single"
				width="120"></t:dgCol>
			<t:dgCol title="店铺优惠券" field="universalcouponprice"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="店铺折扣" field="specialcouponprice" queryMode="single"
				width="120"></t:dgCol>
			<t:dgCol title="平台优惠" field="platformdiscountprice"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="实收" field="disprice" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="平台需结算" field="platformSettlement" queryMode="single"
				width="120"></t:dgCol>

			<%-- <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
			<%--  <t:dgDelOpt title="删除" url="tcsbIncomeStatisticsController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tcsbIncomeStatisticsController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbIncomeStatisticsController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbIncomeStatisticsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
			<%-- <t:dgToolBar title="查看" icon="icon-search" url="tcsbIncomeStatisticsController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
		</t:datagrid>
		<div style="margin-bottom: 10px;">
			<div class="gover_search" style="float: left;">
				<div class="gover_search_form clearfix">
					<span class="search_t">店铺名称:</span> <input type="text"
						name="foodTypeId" class="input_search_key" id="gover_search_key"
						placeholder="请输入关键词直接搜索" />
					<div class="search_suggest" id="gov_search_suggest">
						<ul>
						</ul>
					</div>
				</div>
			</div>

			选择时间段:<input type="text" id="searchTime_begin"
				name="searchTime_begin" style="width: 94px" class="Wdate">~
			<input type="text" id="searchTime_end" name="searchTime_end"
				style="width: 94px" class="Wdate"> <input type="button"
				style="width: 94px" value="查询" onclick="sss()">
		</div>
	</div>

</div>

<script
	src="webpage/com/tcsb/incomestatistics/tcsbIncomeStatisticsPlaList.js"></script>


<script type="text/javascript">  
  
//实现搜索输入框的输入提示js类  
function oSearchSuggest(searchFuc){  
    var input = $('#gover_search_key');  
    var suggestWrap = $('#gov_search_suggest');  
    var key = "";  
    var init = function(){  
        input.bind('keyup',sendKeyWord);  
        input.bind('blur',function(){setTimeout(hideSuggest,100);})  
    }  
    var hideSuggest = function(){  
        suggestWrap.hide();  
    }  
      
    //发送请求，根据关键字到后台查询  
    var sendKeyWord = function(event){  
          
        //键盘选择下拉项  
        if(suggestWrap.css('display')=='block'&&event.keyCode == 38||event.keyCode == 40){  
            var current = suggestWrap.find('li.hover');  
            if(event.keyCode == 38){  
                if(current.length>0){  
                    var prevLi = current.removeClass('hover').prev();  
                    if(prevLi.length>0){  
                        prevLi.addClass('hover');  
                        input.val(prevLi.html());  
                    }  
                }else{  
                    var last = suggestWrap.find('li:last');  
                    last.addClass('hover');  
                    input.val(last.html());  
                }  
                  
            }else if(event.keyCode == 40){  
                if(current.length>0){  
                    var nextLi = current.removeClass('hover').next();  
                    if(nextLi.length>0){  
                        nextLi.addClass('hover');  
                        input.val(nextLi.html());  
                    }  
                }else{  
                    var first = suggestWrap.find('li:first');  
                    first.addClass('hover');  
                    input.val(first.html());  
                }  
            }  
              
        //输入字符  
        }else{   
            var valText = $.trim(input.val());  
            if(valText ==''||valText==key){  
                return;  
            }  
            searchFuc(valText);  
            key = valText;  
        }             
          
    }  
    //请求返回后，执行数据展示  
    this.dataDisplay = function(data){  
        if(data.length<=0){  
            suggestWrap.hide();  
            return;  
        }  
          
        //往搜索框下拉建议显示栏中添加条目并显示  
        var li;  
        var tmpFrag = document.createDocumentFragment();  
        suggestWrap.find('ul').html('');  
        for(var i=0; i<data.length; i++){  
            li = document.createElement('LI');  
            li.innerHTML = data[i];  
            tmpFrag.appendChild(li);  
        }  
        suggestWrap.find('ul').append(tmpFrag);  
        suggestWrap.show();  
          
        //为下拉选项绑定鼠标事件  
        suggestWrap.find('li').hover(function(){  
                suggestWrap.find('li').removeClass('hover');  
                $(this).addClass('hover');  
          
            },function(){  
                $(this).removeClass('hover');  
        }).bind('click',function(){  
            input.val(this.innerHTML);  
            suggestWrap.hide();  
        });  
    }  
    init();  
};  
  
//实例化输入提示的JS,参数为进行查询操作时要调用的函数名  
var searchSuggest =  new oSearchSuggest(sendKeyWordToBack);  
  
//这是一个模似函数，实现向后台发送ajax查询请求，并返回一个查询结果数据，传递给前台的JS,再由前台JS来展示数据。本函数由程序员进行修改实现查询的请求  
//参数为一个字符串，是搜索输入框中当前的内容  
function sendKeyWordToBack(keyword){  
        var obj = {  
                "keyword" : keyword  
             };  
             $.ajax({  
                       type: "POST",  
                       url: "tcsbInComeController.do?getshopName",  
                       async:false,  
                       data: obj,  
                       success: function(data){ 
                    	   var aData = [];  
                    	   data = jQuery.parseJSON(data);
                    	   for(var i =0;i<data.length;i++){
                    		   aData.push(data[i].name); 
                    	   }
                         //var json = eval("("+data+")");  
                         /* var key=data.split(",");  
                         var aData = [];  
                         for(var i=0;i<key.length;i++){  
                                //以下为根据输入返回搜索结果的模拟效果代码,实际数据由后台返回  
                            if(key[i]!=""){  
                                  aData.push(key[i]);  
                            }  
                         }   */
                        //将返回的数据传递给实现搜索输入框的输入提示js类  
                         searchSuggest.dataDisplay(aData);  
                       }  
         });       
               
                //以下为根据输入返回搜索结果的模拟效果代码,实际数据由后台返回  
               /*  var aData = [];  
                aData.push(keyword+'返回数据1');  
                aData.push(keyword+'返回数据2');  
                aData.push(keyword+'不是有的人天生吃素的');  
                aData.push(keyword+'不是有的人天生吃素的');  
                aData.push(keyword+'2012是真的');  
                aData.push(keyword+'2012是假的');   */
                //将返回的数据传递给实现搜索输入框的输入提示js类  
               // searchSuggest.dataDisplay(aData);  
      
}  
  
</script>


<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			//$("#tcsbIncomeStatisticsPlaListtb").find("input[name='searchTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			//$("#tcsbIncomeStatisticsPlaListtb").find("input[name='searchTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#searchTime_begin").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#searchTime_end").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			//新增的时间控制
 			//$("#tcsbIncomeStatisticsPlaListtb").find("input[name='createTime']").attr("class","Wdate").attr("style","width:150px").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH-mm-ss'});});
 			
 			// $('#tcsbCallServiceList').datagrid('reload');
 		
 			//$("#tcsbIncomeStatisticsPlaListtb").find("input[name='searchTime_begin']").val($("#startTime").val());
 			//$("#tcsbIncomeStatisticsPlaListtb").find("input[name='searchTime_end']").val($("#endTime").val());
 			
 			tcsbIncomeStatisticsPlaListsearch();
 			
 			$("#tcsbIncomeStatisticsPlaListtb").find("input[name='searchTime_end']").attr("onchange","sss()")
 			
 });
 
 function sss(){
	 tcsbIncomeStatisticsPlaListsearch();
 }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbIncomeStatisticsController.do?upload', "tcsbIncomeStatisticsPlaList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbIncomeStatisticsController.do?exportXls","tcsbIncomeStatisticsPlaList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbIncomeStatisticsController.do?exportXlsByT","tcsbIncomeStatisticsPlaList");
}
 </script>