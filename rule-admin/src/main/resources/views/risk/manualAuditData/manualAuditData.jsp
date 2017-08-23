<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>人工审核</title>
    <link rel="stylesheet" type="text/css" href="/style/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/style/themes/icon.css">
    <script type="text/javascript" src="/style/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/style/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/style/locale/easyui-lang-zh_CN.js"></script>
</head>
<body style="width:100%;height:100%">

 <table id="grid"></table>
	<!--  <table id="grid" 
	class="easyui-datagrid" style="height:530px"
		data-options="title:'规则人工审核',rownumbers:true,singleSelect:true,pagination:true,pageSize:20,toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'rowKey',width:100,align:'center',hidden:true">rowKey</th>
				<th data-options="field:'interfaceName',width:100,align:'center'">接口名称</th>
				<th data-options="field:'ruleNumber',width:100,align:'center'">规则编号</th>
				<th data-options="field:'ruleTrack',width:100,align:'center'">规则执行信息</th>
				<th data-options="field:'auditStatus',width:100,align:'center',hidden:true">审核状态</th>
				<th data-options="field:'auditStatusDisplayValue',width:100,align:'center'">审核状态</th>
				<th data-options="field:'operate',width:100,align:'center',formatter:function(value,rec,index){return gridformatter(value,rec,index);}">操作</th>
				<th data-options="field:'auditBackup',width:100,align:'center'">审核备注</th>
				<th data-options="field:'operator',width:100,align:'center'">操作人</th>
				<th data-options="field:'manualAuditDate',width:100,align:'center'">创建时间</th>
			 可选字段 
				<th data-options="field:'username',width:100,align:'center'">用户名</th>
				<th data-options="field:'tel',width:100,align:'center'">电话号码</th>
				
			 	<th data-options="field:'requestId',width:80,align:'center'">支付请求号</th>
				<th
					data-options="field:'merchantPayAccount',width:80,align:'center'">商户支付账号</th>
				<th data-options="field:'memberBankAccount',width:80,align:'center'">会员银行账号</th>
				<th data-options="field:'memberPayAccount',width:80,align:'center'">会员支付账号</th>
				<th data-options="field:'memberIdCardType',width:80,align:'center'">会员证件类型</th>
				<th data-options="field:'memberIdCardNo',width:80,align:'center'">会员会员证件号</th>
				<th
					data-options="field:'memberCardHolderName',width:80,align:'center'">会员持卡人姓名</th>
				<th data-options="field:'memberMobile',width:80,align:'center'">会员手机号</th>
				<th data-options="field:'memberBankName',width:80,align:'center'">会员开户银行名</th>
				<th data-options="field:'keyVersion',width:80,align:'center'">加密版本</th>
				<th data-options="field:'reportTime',width:80,align:'center'">上报时间</th>
				<th data-options="field:'ruleId',width:80,align:'center'">规则ID</th>
				<th data-options="field:'tradeIp',width:80,align:'center'">交易IP</th>
				<th data-options="field:'excludeReason',width:80,align:'center'">排除可疑原因</th> 
				<th data-options="field:'do',width:250,align:'center',formatter:function(value,rec,index){return gridformatter(value,rec,index);}">操作</th>

			</tr>
		</thead>
	</table> -->
 	<div id="tb" style="padding: 5px; height: auto">
	
		<div style="margin-bottom: 15px">
			接口名称: <input class="easyui-combobox" id="interfaceName" style="width: 200px" data-options="required:true,editable:false"/>
			命中规则日期: <input class="easyui-datebox" id="manualAuditDate" style="width: 100px" data-options="editable:false,required:true"/> 
			审核状态: <input id="auditStatus" class="easyui-combobox"
			 data-options="editable:false,required:true" style="width: 100px">
			<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
		</div>
	</div>
	
	<div id="manualAuditDialog" class="easyui-dialog"
		style="width: 300px; height: 250px; padding: 10px 20px" closed="true" modal="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<div>
				<label for="auditStatus">审核状态</label> 
				<input class="easyui-combobox" type="text" name="auditStatus" style="height: 20px; width: 180px" data-options="url:'/ccmis/risk/enums/getEnumOfEasyui?name=RiskAuditStatusEnum&type=code',valueField:'code',textField:'name',editable:'false',panelHeight:'auto',required:true" />
			</div>
			<div>
				<label for="auditBackup">审核备注</label> 
				<textarea rows="4" cols="25" class="easyui-validatebox" name="auditBackup" style="height: 80px; width: 180px" data-options="validType:'length[0,200]',required:true"></textarea>
				<!-- <input class="easyui-validatebox"
					type="text" name="auditBackup" style="height: 20px; width: 200px" data-options="required:true" /> -->
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="submit()">确定</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#manualAuditDialog').dialog('close')">取消</a>
	</div>
	
<script type="text/javascript">

$("#interfaceName").combobox({
	url : "/ccmis/risk/interface/listAll",
	valueField : "interfaceName",
	textField : "interfaceName",
	panelHeight:'auto',
	onLoadSuccess:function(data){
		if(data!=null && data!=""){
			$('#interfaceName').combobox('select',data[0].interfaceName);
		}
	}
});

$("#auditStatus").combobox({
	url : "/ccmis/risk/enums/getEnumOfEasyui?name=RiskAuditStatusEnum&type=code",
	valueField : "code",
	textField : "name",
	panelHeight:'auto',
	onLoadSuccess:function(data){
		if(data!=null && data!=""){
			$('#auditStatus').combobox('select',data[0].code);
		}
	}
});


//格式化操作
function gridformatter(value,rec,index){
	//audit operate
	if (rec.auditStatus == "100001"){
		return "<a href='javascript:void(0);' onclick='showManualAudit()'>审核</a>";
	}else{
		return "";
	}
}

function dateformatter(value,row,index){
	if (getLength(row.manualAuditDate) == 8){
		var d  = row.manualAuditDate;
		return d.substr(0, 4) + "-" + d.substr(4, 2) + "-" + d.substr(6, 2);
	}else{
		return row.manualAuditDate;
	}
};

//显示审核对话框
function showManualAudit(){
	$('#manualAuditDialog').dialog('open').dialog('setTitle', '审核');
	$('#fm').form('clear');
}

//审核操作
function manualAudit(){
	var row = $('#grid').datagrid('getSelected');
	
}

//搜索按钮事件
$("#search").click(function () {
	var interfaceName = $("#interfaceName").combobox('getValue');
	var manualAuditDate = $("#manualAuditDate").datebox('getValue');
	var auditStatus = $("#auditStatus").combobox('getValue');
	
	if(getLength($.trim(interfaceName)) == 0){
		$.messager.alert('提示','接口名称不能为空!','info');
		return;
	}
	if(getLength($.trim(manualAuditDate)) == 0){
		$.messager.alert('提示','命中规则日期不能为空!','info');
		return;
	}
	if(getLength($.trim(auditStatus)) == 0){
		$.messager.alert('提示','审核状态不能为空!','info');
		return;
	}		

	$('#grid').datagrid('load',{
			interfaceName:$("#interfaceName").combobox('getValue'), 
			manualAuditDate:$("#manualAuditDate").datebox('getValue'),
			auditStatus:$("#auditStatus").combobox('getValue')
	});
	
});

$(function(){
	//设置默认时间
	$("#manualAuditDate").datebox("setValue", getDate(0)); 
	
	//表格
	$('#grid').datagrid({
		queryParams:{
			interfaceName:$("#interfaceName").combobox('getValue'), 
			manualAuditDate:$("#manualAuditDate").datebox('getValue'),
			auditStatus:$("#auditStatus").combobox('getValue')
		},
		url:'/ccmis/risk/manualAuditData/selectPaging',
		title:'规则人工审核',
		rownumbers:true,
		singleSelect:true,
		pagination:true,
		pageSize:20,
		toolbar:'#tb',
		height:530,
		columns:[[  
		          {field:'rowKey',width:100,align:'center',hidden:true,title:'rowKey'},  
		          {field:'interfaceName',width:100,align:'center',title:'接口名称'},  
		          {field:'ruleNumber',width:100,align:'center',title:'规则编号'},
		          {field:'ruleTrack',width:100,align:'center',title:'规则执行信息'},  
		          {field:'auditStatus',width:100,align:'center',hidden:true,title:'审核状态'},  
		          {field:'auditStatusDisplayValue',width:100,align:'center',title:'审核状态'},  
		          {field:'operate',width:100,align:'center',title:'操作',formatter:function(value,rec,index){return gridformatter(value,rec,index);}},  
		          {field:'auditBackup',width:100,align:'center',title:'审核备注'},  
		          {field:'operator',width:100,align:'center',title:'操作人'},
		          {field:'manualAuditDate',width:100,align:'center',title:'命中规则日期',formatter: function(value,row,index){return dateformatter(value,row,index);}},
		           
		          //可选属性
		          {field:'username',width:100,align:'center',title:'用户名'},
		          {field:'tel',width:100,align:'center',title:'电话'}
		]]
	});
	
	// 分页
	$('#grid').datagrid('getPager').pagination({
		beforePageText:'第',
		afterPageText:'页',
		displayMsg:'显示{from}到{to}条记录',
	});
});

function getDate(flag){
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	if(getLength(month) < 2){
		month = "0" + month;
	}
	var day = date.getDate() + flag;
	strDate = year + "-" + month + "-" + day;   
	return strDate;
} 

//提交表单
function submit() {
	$('#fm').form('submit', {
		url : "/ccmis/risk/manualAuditData/manualAudit",
		onSubmit : function(param) {
			var row = $('#grid').datagrid('getSelected');
			param.rowKey = row.rowKey; 
			return $(this).form('validate');
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success == false) {
				$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			} else if (result.success == true){
				$('#manualAuditDialog').dialog('close');
				$('#grid').datagrid('reload');
			}
		}
	});
};

function getLength(str){
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) realLength += 1;
        else realLength += 2;
    }
    return realLength;
}	
</script>
</body>
</html>