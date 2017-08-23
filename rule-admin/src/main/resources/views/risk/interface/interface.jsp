<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">

    <link rel="stylesheet" type="text/css" href="/style/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/style/themes/icon.css">
	<script type="text/javascript" src="/style/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="/style/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/style/locale/easyui-lang-zh_CN.js"></script>
	
	<script type="text/javascript" src="/style/datagrid-detailview.js"></script> 

<title>接口管理</title> 
</head>
<body>  
<!-- 主界面构造 -->
<div class="easyui-panel" title="接口管理">	
	<div id="tb" style="padding: 5px; height: auto">
		<a id="addMenu" href="#" class="easyui-linkbutton"   iconCls="icon-add"  onclick="javascript:doAddMenu()">新增菜单</a>
		<a id="addInterface" href="#" class="easyui-linkbutton"   iconCls="icon-add"  onclick="javascript:doAddInterface()">新增接口</a>
	</div>
	
	<table id="tt" class="easyui-treegrid" style="height:auto;"  toolbar="#tb" data-options=" url: '/ccmis/risk/interface/getInterfaceTreeList', rownumbers: false, idField: 'id', treeField: 'code' ">
		<thead>
			<tr>
				<th data-options="field:'id'" width="40">ID</th>
				<th data-options="field:'code'" width="200">编码</th>
				<th data-options="field:'name'" width="100">名称</th>
				<th data-options="field:'interfaceName'"  width="200">接口名称</th>
				<th data-options="field:'description'" width="200">描述</th>
				<th data-options="field:'eventDescription'" width="200">事件描述</th>
				<th data-options="field:'status', formatter:function(value,rec,index){return statusformatter(value,rec,index);}" width="30">状态</th>
				<th data-options="field:'createTime'" width="120">创建时间</th>
				<th data-options="field:'updateTime'" width="120">更新时间</th>
				<th data-options="field:'operate',formatter:function(value,rec,index){return operateformatter(value,rec,index);}" width="200" align="center">操作</th>
				<th data-options="field:'interfaceId' ,hidden:true" >接口ID</th>
			</tr>
		</thead>
	</table>
</div>

<!-- 新增修改对话框构造 -->        
    <div id="menuDlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px ; top:20px"   closed="true" buttons="#menuDlg-buttons" >  
        	<div class="ftitle">规则接口菜单</div>  
        	<form id="menuEditForm" method="post" novalidate> 
        	    <div class="fitem"  style="display:none">  
                	<label>菜单ID:</label>  
                	<input type="text" name="id" class="easyui-validatebox" ></input> 
            	</div>  
            	<div class="fitem">  
                	<label>菜单编码:</label>  
                	<input type="text" name="code" class="easyui-validatebox"   data-options="required:true"></input> 
            	</div>  
            	<div class="fitem">  
                	<label>菜单名称:</label>  
                	<input type="text" name="name" class="easyui-validatebox"  data-options="required:true"></input> 
            	</div>  
            	<div class="fitem">  
                	<label>上级菜单:</label>  
                	<input type="text" name="parentId"   id="menuParent"  class="easyui-validatebox" ></input>  
           	 </div>  
        </form>  
    </div>  
    <div id="menuDlg-buttons">  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveMenu()">保存</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#menuDlg').dialog('close')">取消</a>  
    </div> 
    
     <div id="interfaceDlg" class="easyui-dialog" style="width:400px;height:350px;padding:10px 20px ; top:20px"   closed="true" buttons="#interfaceDlg-buttons">  
        	<div class="ftitle">规则接口</div>  
        	<form id="interfaceEditForm" method="post" novalidate> 
        		<div class="fitem"  style="display:none">  
                	<label>菜单ID:</label>  
                	<input type="text" name="id" class="easyui-validatebox" ></input> 
            	</div> 
        	   <div class="fitem"  style="display:none">  
                	<label>接口ID:</label>  
                	<input type="text" name="interfaceId" class="easyui-validatebox" ></input> 
            	</div>
            	<div class="fitem">  
                	<label>菜单编码:</label>  
                	<input type="text" name="code" class="easyui-validatebox" data-options="required:true"></input> 
            	</div>  
            	<div class="fitem">  
                	<label>菜单名称:</label>  
                	<input type="text" name="name" class="easyui-validatebox"   data-options="required:true"></input> 
            	</div>
            	<div class="fitem">  
                	<label>接口名称:</label>  
                	<input type="text" name="interfaceName" class="easyui-validatebox"   data-options="required:true"></input>  
           	 	</div>  
            	<div class="fitem">  
                	<label>上级菜单:</label>  
                	<input type="text" name="parentId"  id="interfaceParent"  class="easyui-validatebox" ></input>  
           	 </div>  
           	 <div class="fitem">  
                	<label>接口描述:</label>  
                	<input type="text" name="description" class="easyui-validatebox" data-options="required:true"></input>  
           	 </div>
           	 <div class="fitem">  
                	<label>事件描述:</label>  
                	<input type="text" name="eventDescription" class="easyui-validatebox"   data-options="required:true"></input>  
           	 </div>
           	 <div class="fitem">  
                	<label>接口状态:</label>  
                	<select  name="status" class="easyui-combobox" panelHeight="auto" style="width:160px" 
													data-options="editable:false,required:true,valueField:'code',textField:'name',url:'/ccmis//risk/interface/getInterfaceStateEnum?name=InterfaceStatusEnum&type=code'">
											<option value="">默认项</option>
					</select>
           	 </div>
        </form>  
    </div>  
    <div id="interfaceDlg-buttons">  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveInterface()">保存</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#interfaceDlg').dialog('close')">取消</a>  
    </div>
</body>
<script type="text/javascript">
	var url;
	function doAddMenu(){
   		$('#menuEditForm').form('clear');
   		
		var row = $('#tt').treegrid('getSelected');
		if(row){
			if(row.interfaceName){
				$.messager.alert('警告','接口下不能增加下级菜单!', 'warning'); 
				return;
			}
			
			$('#menuParent').val(row.id);			
		}
		
   		url = '/ccmis/risk/interface/addMenu';
   		
   		$('#menuDlg').dialog('open').dialog('setTitle','新增菜单'); 
	}
	
	function doAddInterface(){
   		$('#interfaceEditForm').form('clear');
   		
		var row = $('#tt').treegrid('getSelected');
		if(row){
			if(row.interfaceName){
				$.messager.alert('警告','接口下不能增加下级接口!', 'warning'); 
				return;
			}
			
			$('#interfaceParent').val(row.id);
		} 
		
		url = '/ccmis/risk/interface/addInterface';
		
		$('#interfaceDlg').dialog('open').dialog('setTitle','新增接口'); 
	}
	
	function doModify(){   		
   		var row = $('#tt').treegrid('getSelected');
	   	if(row){
	   		if(row.interfaceName){
	   			$('#interfaceDlg').dialog('open').dialog('setTitle','修改接口'); 
	   			$('#interfaceEditForm').form('load',row);
	   			
	   			url = '/ccmis/risk/interface/updateInterface';
	   		}else{
	   			$('#menuDlg').dialog('open').dialog('setTitle','修改菜单'); 
	   			$('#menuEditForm').form('load',row);
	   			
	   			url = '/ccmis/risk/interface/updateMenu';
	   		}
	   	}
	}
	
	function saveMenu(){
   		$('#menuEditForm').form('submit',{
       		url:url,
       		onSubmit:function(){
           		return $(this).form('validate');
			},
   			success:function(result){
   				var resultJson = JSON.parse(result);
   				if(resultJson.success){
   					$.messager.show({
                   		title:'提示',
						msg:resultJson.message
               		});
   					$('#menuDlg').dialog('close');
               		$('#tt').treegrid('reload');
   				}else{
   					$.messager.show({
                   		title:'错误',
						msg:resultJson.message
               		});
   				}
       	   }
   		});
	}
	
	function saveInterface(){			
   		$('#interfaceEditForm').form('submit',{
       		url:url,
       		onSubmit:function(){
           		return $(this).form('validate');
			},
   			success:function(result){
   				var resultJson = JSON.parse(result);
   				if(resultJson.success){
   					$.messager.show({title : '操作成功',msg : resultJson.message});
   					$('#interfaceDlg').dialog('close');
               		$('#tt').treegrid('reload');
   				}else{
   					$.messager.show({title:'错误',	msg:resultJson.message});   					
   				}
       	   }
   		});
	}
	
	function deleteData(id,children) {
		$.messager.confirm("是否删除","是否删除 ?",function(result){  
            if (result){  
        		$.post("/ccmis/risk/interface/delete", 
        			{ "id": id},
        		    function(result){
        				if(result.success){
        			    	 $.messager.show({title : '操作成功',msg : "删除成功"});
        			    	 $('#tt').treegrid('reload');
        			     }else{
        			    	 $.messager.show({title : '操作失败',msg : result.message});
        			     }
        			  }, "json");
            }  
        });		
	}

	function statusformatter(value,rec,index){
		if (rec.status == "0")
			return "停用";
		if (rec.status == "1")
			return "启用";
		return "";
	}

	function operateformatter(value,rec,index){
		var str = "<a href='javascript:void(0);' onclick='doModify()'>修改</a>&nbsp;|&nbsp;<a href='javascript:void(0);' onclick='deleteData("+rec.id+")']>删除</a>";

		return str;
	}
</script>

<style type="text/css"> 
		.ftitle{ 
					font-size:14px; 
					font-weight:bold; 
					color:#666; 
					padding:5px 0; 
					margin-bottom:10px; 
					border-bottom:1px solid #ccc; 
		} 
		.fitem{ 
					margin-bottom:5px; 
		} 
		.fitem label{ 
					display:inline-block; 
					width:80px; 
		} 
	</style>
</html>