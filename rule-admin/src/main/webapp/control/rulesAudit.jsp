<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>规则规则审核</title>
<link rel="stylesheet" href="../style/css/default/easyui.css">
<link rel="stylesheet" href="../style/css/bootstrap.css">
<link rel="stylesheet" href="../style/css/bootstrap-responsive.css">
<link rel="stylesheet" href="../style/css/icon.css">
<link rel="stylesheet" href="js/css/zTreeStyle/zTreeStyle.css">

<script type="text/javascript" src="../style/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="../style/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/js/jquery.json-2.4.min.js"></script>
<script type="text/javascript" src="js/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="js/initParam.js"></script>
<script type="text/javascript" src="js/menu.js"></script>
<script type="text/javascript" src="js/rulesEntity.js"></script>
<script type="text/javascript" src="js/rulesCommon.js"></script>
<script type="text/javascript" src="js/rulesAudit.js"></script>
<script type="text/javascript" src="js/WdatePicker.js"></script>
<style type="text/css">
/*解决easyui与bootstrap的样式冲突*/ 
.combo-panel{ 
 line-height: 20px;
}
select{
  width: 70px;
} 

.condition_select{
  width: 120px;
}
</style>
</head>
<body>
	<div id="cc" class="easyui-layout" style="width:1200px;height:800px">  
    <div data-options="region:'north',title:'规则列表',split:true" style="height:200px;">
     <table id="rules_grid"></table>
    </div>  
    <div data-options="region:'west',title:'接口列表',split:true" style="width:150px;">
    <ul id='rootlist' class='ztree'></ul>
    </div>  
    <div data-options="region:'center',title:'规则编辑'" style="padding:5px;">
    	<div id="editRules" style="display: none;line-height: ">
						<span id="rule_des" style="font-size: 10px"> 规则编号 &nbsp;<span class="combo" style="width: 96px; height: 20px; line-height: 20px; "><input type="text"
							class="combo-text validatebox-text" id="rulesNo" readonly="readonly"
							placeholder="自动生成"></span>&nbsp;&nbsp;规则名称 &nbsp;<span class="combo" style="width: 96px; height: 20px; line-height: 20px; "><input
							type="text" class="combo-text validatebox-text" id="rulesName" maxlength="20"></span>&nbsp;&nbsp;规则优先级
							&nbsp;<select id="priority" class="easyui-combobox">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							</select>&nbsp;&nbsp;规则等级&nbsp;<select
						    id="rule_level" class="easyui-combobox">
								<option value="9">高级</option>
								<option value="5">中级</option>
								<option value="1">低级</option>
						</select> &nbsp;规则状态&nbsp; <select id="status" class="easyui-combobox">
								<option value="1">启用</option>
								<option value="0">禁用</option>
						</select>
						</span> <hr> 规则条件:<hr>
						<table class="table" id="conditions">

						</table>

						<hr>

						规则处理命令:<hr>
						<table class="table" id="handlers">

						</table>
						<hr>
						<span>有效期:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="combo" style="width: 150px; height: 20px; line-height: 20px; "><input type="text"
							readonly="readonly" class="combo-text validatebox-text" 
							id="startTime" style="width: 150px"></span><img
							src="js/skin/datePicker.gif" width="20px" height="40px"
							align="absmiddle">至<span class="combo" style="width: 150px; height: 20px; line-height: 20px; "><input type="text" readonly="readonly"
							class="combo-text validatebox-text"  id="endTime"
							style="width: 150px"></span><img
							src="js/skin/datePicker.gif" width="20px" height="40px"
							align="absmiddle"><br>生效时段: <span class="combo" style="width: 150px; height: 20px; line-height: 20px; "><input type="text"
							id="startInterval" readonly="readonly" class="combo-text validatebox-text"
						    style="width: 150px"></span><img
							src="js/skin/datePicker.gif" width="20px" height="40px"
							align="absmiddle">至<span class="combo" style="width: 150px; height: 20px; line-height: 20px; "><input type="text" class="combo-text validatebox-text"
							id="endInterval" readonly="readonly"
							style="width: 150px" /></span><img
							src="js/skin/datePicker.gif" width="20px" height="40px"
							align="absmiddle"> &nbsp;&nbsp;&nbsp;&nbsp;
							<div class="btn-group">
								<button class="btn" id="saveBtn" onclick="auditThrough()">通过</button>
								<button class="btn" id="deleteBtn" onclick="auditRefuse()">拒绝</button>
							    <button class="btn" id="deleteBtn" onclick="$('#dlg').dialog('open')">拒绝并修改</button>
							</div>
						</span>
					</div> 
    </div>  
</div>

		<hr>
		<!-- 
		<footer>
		<p>规则规则</p>
		</footer> -->

	</div>
	<!--/.fluid-container-->

    <div id="dlg" class="easyui-dialog" title="拒绝原因" style="width:400px;height:200px;padding:10px"  
            data-options="  
                iconCls: 'icon-save',    
                buttons: [{  
                    text:'Ok',  
                    iconCls:'icon-ok',  
                    handler:saveReason
                },{  
                    text:'Cancel',  
                    handler:reasonCancel
                }]  
            ">  
        <textarea id="reason" style="width:330px;height:75px;padding:10px"></textarea>
    </div>  
</body>
</html>