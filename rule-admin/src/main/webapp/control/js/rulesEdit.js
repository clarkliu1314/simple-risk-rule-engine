function getRules(id) {
	menuId = id;
	rules_grid(id,1);

}

function openRules(rulesId, flag) {
	getDetail(rulesId, flag, 'edit');				
}

function changedFactor(id, className) {
	if (className == null)
		className = getValue($('#factor' + id));
	$
			.ajax({
				url : '/ccmis/risk/rules/getOperatorAndParams',
				type : "POST",
				dataType : 'json',
				data : 'factor=' + className,
				success : function(data) {
					var condition = data.operators;
					var params = data.params;
					var field = data.field;
					var innerDoc = '';
					$('#condition' + id).html('<select class="condition_select" name="operator"></select>');
					
					$.each(condition, function(i, val) {
						$('#condition' + id).find('select[name="operator"]').append(
								'<option value="' + val.className + '">'
										+ val.description + '</option>');
					})
					$('#condition'+id).find('select[name="operator"]').combobox({
						editable:false,
						panelHeight:'auto',
						onChange:function(){
							changeOperator(id,$('#condition'+id+' select').combobox('getValue'));}
					});
					

					$('#param' + id).html('');
					$
							.each(
									params,
									function(i, val) {
										var doc = '<span class="combo" style="width: 117px; height: 20px; line-height: 20px; "><input class="combo-text validatebox-text" name="param" comboname="param" type="text" class="input-small"  onblur="validateParam(\'factor'
												+ id
												+ '\',this.value)" placeholder="'
												+ val.name
												+ '/'
												+ val.description + '">';
										innerDoc = innerDoc + doc;
										$('#param' + id).append(innerDoc);

									})
					
					var checkValueHtml = '';
					var options = '';
					if (field.valueEnum != undefined) {
						for ( var key in field.valueEnum) {
							options += '<option value="' + field.valueEnum[key]
									+ '">' + key + '</option>'
						}
						$("#checkValue" + id).html(
								'<select class="condition_select" name="checkValue">' + options
										+ '</select>');
						$('#checkValue'+id+' select[name="checkValue"]').combobox({editable:false,panelHeight:'auto'});

					} else
						$("#checkValue" + id)
								.html(
										'<span class="combo" style="width: 117px; height: 20px; line-height: 20px; "><input class="combo-text validatebox-text" name="checkValue" comboname="checkValue" type="text" class="input-small" placeholder="判断值"  onblur="validateCheckValue(\'factor'
												+ id + '\',this.value)" /></span>');

					$('#new' + id).find('input[name="checkValue"]').attr(
							"placeholder", field.description);
					
					

				},
				error : function() {

				}

			});

}

function changeOperator(id, className) {
   var options = $('#checkValue' + id).find('select').html();
   if(options == undefined)
	   return;
   $('#checkValue' + id).html('<select name="checkValue" style="width: 119px; line-height: 20px;">'+options+'</select>');
	if (className.indexOf('Contain') > 0) {
		$('#checkValue' + id).find('select').combobox({
			editable:false,
			multiple : true,
			panelHeight : 'auto'
		})
	} else {
		$('#checkValue' + id).find('select').combobox({
			editable:false,
			panelHeight : 'auto'
		})
	}
}

function addCondition(id) {

	var condition = '<tr id="new'
			+ id
			+ '">'
			+ '<td><select class="condition_select" id="factor'
			+ id
			+ '" name="factor"><option>请选择</option></select></td><td id="field'+id+'"><a href="javascript:showFileds('+id+');">选择字段</a></td><td id="param'
			+ id
			+ '"></td>'
			+ '<td id="condition'+id+'"><select class="condition_select" name="operator"><option>请选择风险因素</option></select></td>'
			+ '<td id="checkValue'
			+ id
			+ '"><span class="combo" style="width: 117px; height: 20px; line-height: 20px; "><input name="checkValue" type="text" class="combo-text validatebox-text" placeholder="判断值"  onblur="validateCheckValue(\'factor'
			+ id
			+ '\',this.value)" /></span></td><td id="connector'+id+'"><select name="connector"><option value="and">AND</option><option value="or">OR</option></select></td>'
			+ '<td><a name="add" href="javascript:addCondition('
			+ (parseInt(id) + 1)
			+ ')"><img src="../style/css/icons/edit_add.png"></a>&nbsp;<a name="del" href="javascript:removeDoc(\'new'
			+ id
			+ '\')"><img src="../style/css/icons/edit_remove.png"></a></td>'
			+ '</tr>'

	$('#conditions').append(condition);

	removeAddoRdel($('#conditions tr'));

	$.ajax({
		url : '/ccmis/risk/rules/getFactorList',
		type : "POST",
		dataType : 'json',
		success : function(data) {
			$.each(data, function(i, val) {
				$('#factor' + id).append(
						'<option value="' + val.className + '">' + val.name
								+ '</option>');
			})
			$('#factor'+id).combobox({
				editable:false,
				panelHeight:'auto',
				onChange:function(){changedFactor(id,$('#factor'+id).combobox('getValue'));}
			})
			$('#condition'+id).find('select[name="operator"]').combobox({
				panelHeight:'auto',
				editable:false,
				onChange:function(){
					changeOperator(id,$('#condition'+id+' select').combobox('getValue'));}
			})
			
			$('#connector'+id).find('select').combobox({
				editable:false,
				panelHeight:'auto'														})

		},
		error : function() {

		}

	});

} 

function addHandler(id) {

	var hh = '<tr id="new'
			+ id
			+ '"><td><select class="condition_select" id="handler'
			+ id
			+ '" name="command"></select></td><td><span class="combo" style="width: 96px; height: 20px; line-height: 20px; "><input type="text" class="combo-text validatebox-text" placeholder="处理命令值" onblur="validateHandler(\'handler'
			+ id
			+ '\',this.value,\'code\')" id="value'
			+ id
			+ '" name="commandValue"/></span></td><td><span class="combo" style="width: 250px; height: 20px; line-height: 20px; "><input type="text" class="combo-text validatebox-text" placeholder="命令值描述" onblur="validateHandler(\'handler'
			+ id
			+ '\',this.value,\'description\')" id="description'
			+ id
			+ '" name="commandValue_description"/></span></td>'
			+ '<td><a name="add" href="javascript:addHandler(\''
			+ parseInt(id, 10)
			+ 1
			+ '\')"><img src="../style/css/icons/edit_add.png"></a>&nbsp;<a name="del" href="javascript:removeDoc(\'new'
			+ id
			+ '\')"><img src="../style/css/icons/edit_remove.png"></a></td></tr>';
	$('#handlers').append(hh);

	removeAddoRdel($('#handlers tr'));

	h_globalId = parseInt(id, 10) + 1;

	$.ajax({
		url : '/ccmis/risk/rules/getHandlerList',
		type : "POST",
		dataType : 'json',
		success : function(data) {
			$.each(data, function(i, val) {
				$('#handler' + id).append(
						'<option value="' + val.command + '">'
								+ val.description + '</option>');
			})

			$('#handler' + id).combobox({
				editable:false,
				panelHeight : 'auto'
			});
		},
		error : function() {

		}

	});

}

function removeDoc(id) {
	$('#' + id).remove();
	c_globalId = c_globalId - 1;
	removeAddoRdel($('#conditions tr'));
	removeAddoRdel($('#handlers tr'));
}

function save() {
	saveRules();
}

function cleanDoc() {

	$('#rulesNo').attr('value', '');
	$('#rulesName').attr('value', '');
	$('#conditions').html('');
	$('#handlers').html('');
	$('#startTime').attr('value', '');
	$('#endTime').attr('value', '');
	$('#startInterval').attr('value', '');
	$('#endInterval').attr('value', '');
}

function createNewRule() {
	ruleId = '';
	command = 'add';
	if (interfaceName == '' || interfaceName == undefined) {
		$.messager.alert('提示',"请选择接口信息",'info');
		return;
	}
	cleanDoc();
	$('#saveBtn').attr("disabled", false);
	$('#deleteBtn').attr("disabled", false);
	$('#editRules').attr('style', 'display: block;');
	addCondition(0);
	addHandler(0);
}

// 删除规则 
function deleteRules() {
	if (!window.confirm('确定要删除该条规则吗？')) {
		return;
	}
	if (ruleNo == null || ruleNo == undefined) {
		$.messager.alert('提示',"只能再编辑模式下删除!",'info');
		return;
	}
	$.ajax({
		url : '/ccmis/risk/rules/removeRules',
		type : "POST",
		dataType : 'json',
		data : 'ruleNo=' + ruleNo,
		success : function(data) {
			getRules(menuId);
			$('#editRules').attr('style', 'display: none;');
			cleanDoc();

		},
		error : function() {

		}

	});

}

// 删除新增行
function removeAddoRdel(doc) {
	var size = doc.size();
	$(doc).each(function(i, val) {
		if (i == 0)
			$(val).find('a[name="del"]').attr('style', 'display:none');
		if (i < size - 1)
			$(val).find('a[name="add"]').attr('style', 'display:none');
		if (i == size - 1)
			$(val).find('a[name="add"]').attr('style', '');

	})
}

function validateParam(factorId, param) {
	if (factorId == '' || factorId == undefined) {
		$.messager.show({
			title:'提示',
			msg:'请选择风险因素',
			timeout:2000,
			showType:'fade',
			style:{  
                right:'',  
                bottom:''  
            }  
		});
		return;
	}
	if (param == '') {
		$.messager.show({
			title:'提示',
			msg:'参数不能为空!',
			timeout:1000,
			showType:'fade',
			style:{  
                right:'',  
                bottom:''  
            }  
		});
		return;
	}
	var factor = getValue($('#' + factorId));
	$.ajax({
		url : '/ccmis/risk/rules/validateParam',
		type : "POST",
		dataType : 'json',
		data : 'param=' + param + '&factor=' + factor,
		success : function(data) {
			var code = data.code;
			var message = data.message;
			if (code == 0) {
				commitFlag = 0;
				globalMessage = message;
				$.messager.show({
					title:'提示',
					msg:message,
					timeout:1000,
					showType:'fade',
					style:{  
		                right:'',  
		                bottom:''  
		            }  
				});
				return;
			}
			commitFlag = 1;
		},
		error : function() {

		}

	});

}

function validateCheckValue(factorId, checkValue) {
	if (factorId == '' || factorId == undefined) {
		$.messager.show({
			title:'提示',
			msg:'请选择风险因素!',
			timeout:1000,
			showType:'fade',
			style:{  
                right:'',  
                bottom:''  
            }  
		});
		return;
	}
	if (checkValue == '') {
		$.messager.show({
			title:'提示',
			msg:'参数不能为空!',
			timeout:1000,
			showType:'fade',
			style:{  
                right:'',  
                bottom:''  
            }  
		});
		return;
	}
	var factor = getValue($('#' + factorId));

	$.ajax({
		url : '/ccmis/risk/rules/validateCheckValue',
		type : "POST",
		dataType : 'json',
		data : 'checkValue=' + checkValue + '&factor=' + factor,
		success : function(data) {
			var code = data.code;
			var message = data.message;
			if (code == 0) {
				commitFlag = 0;
				globalMessage = message;
				$.messager.show({
					title:'提示',
					msg:message,
					timeout:1000,
					showType:'fade',
					style:{  
		                right:'',  
		                bottom:''  
		            }  
				});
				return;
			}
			commitFlag = 1;

		},
		error : function() {

		}
  
	});

}

function validateHandler(handlerId, value, type) {
	if (value == '' || value == undefined) {
		$.messager.show({
			title:'提示',
			msg:'该项为必填项!',
			timeout:1000,
			showType:'fade',
			style:{  
                right:'',  
                bottom:''  
            }  
		});
		return;
	}
	var handler = getValue($('#' + handlerId));
	$.ajax({
		url : '/ccmis/risk/rules/validateHandler',
		type : "POST",
		dataType : 'json',
		data : 'value=' + value + '&handler=' + handler + '&type=' + type,
		success : function(data) {
			var code = data.code;
			var message = data.message;
			if (code == 0) {
				commitFlag = 0;
				globalMessage = message;
				$.messager.show({
					title:'提示',
					msg:message,
					timeout:1000,
					showType:'fade',
					style:{  
		                right:'',  
		                bottom:''  
		            }  
				});
				return;
			}
			commitFlag = 1;

		},
		error : function() {

		}

	});

}


function getValue(selector){
	try{
		var value = selector.combobox('getValue');
		if(value == undefined)
		value = selecotr.val();
		return value;
	}catch(e){
		return selector.val();
	}
	
} 

function showFileds(id){
   var factor = $('#factor'+id).combobox('getValue');
   var fieldIds = $('#field'+id+' a').attr('value');
   $.ajax({
		url : '/ccmis/risk/rules/getExternalArgumentsSize',
		type : "GET",
		dataType : 'json',
		data : 'factor='+factor,
		success : function(data) {
			$('#fields').html('');
			for(var i = 0;i < parseInt(data);i++){
			$('#fields').append('<tr><td>字段'+parseInt(i+1)+':</td><td><input id="f'+i+'" style="width:200px;"></td></tr>');
			$('#f'+i).combotree({  
			    url: '/ccmis/risk/ruleFied/queryAllOnCategory',  
			    required: true  
			}); 
			if(fieldIds != undefined)
			$('#f'+i).combotree('setValue',fieldIds.split(',')[i]);
			}
			$('#selectField').dialog({  
			    title: '选择字段',  
			    width: 400,  
			    height: 200,  
			    closed: false,  
			    cache: false,  
			    modal: true,
			    buttons: [{  
		            text:'Ok',  
		            iconCls:'icon-ok',  
		            handler:function(){ 
		            	var text = '';
		            	var value ='';
		            		$.each($('#fields input[class="combo-value"]'),function(i,val){
		            		text += $('#f'+i).combotree('getText')+',';
		            		value += $('#f'+i).combotree('getValue')+',';
		            	})
		         $('#field'+id).html('<a name="fs" href="javascript:showFileds('+id+');" value="'+value.substring(0,value.length-1)+'">'+text.substring(0,text.length-1)+'</a>');
		            	$('#selectField').dialog('close');
		            }  
		        },{  
		            text:'Cancel',  
		            handler:function(){  
		            	$('#selectField').dialog('close');
		            }  
		        }] 
			});  

			$('#selectField').dialog('open'); 
		
		
		
		},
		error : function() {

		}

	});
   
}
$(function (){
	$('#dlg').dialog('close');
})