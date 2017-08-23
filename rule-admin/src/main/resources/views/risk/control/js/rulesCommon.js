function rules_grid(id,fg){
	var url;
	if(fg == '')
		url = '/ccmis/risk/rulesAudit/list';
	else
		url = '/ccmis/risk/rules/list';
	$('#rules_grid').datagrid({
		url:url,
		queryParams:{ 
			id:id
		},
		singleSelect:true,
		 toolbar:[{  
	            text:'新增规则',  
	            iconCls:'icon-add',
	            id : 'add',
	            handler:function(){createNewRule();}  
	        }],
		columns:[[  
			        {field:'no',title:'规则编号',width:100},  
			        {field:'name',title:'规则名称',width:100},  
			        {field:'level',title:'级别',width:100,formatter: function(value,row,index){
			        	switch (value) {
						case 1:
							return '低级';
						case 5:
							return "中级";
						case 9:
							return '高级';
						default:
							return '高级';
						}
			        }},
			        {field:'createTime',title:'创建时间',width:100},  
			        {field:'createPerson',title:'创建人',width:100},  
			        {field:'endTime',title:'有效期',width:100,formatter: function(value,row,index){
			        	if (value == undefined)
							return '无限期';
			        	else
			        		return value;
			        }},
			        {field:'status',title:'规则状态',width:100,formatter: function(value,row,index){
			        	switch (value) {
						case 0:
							return '禁用';
						case 1:
							return '启用';
						default:
							return '未知';
						}

			        }},  
			        {field:'operateType',title:'操作类型',width:100,formatter:function(value,row,index){
			        	switch (value) {
						case 0:
							return '新增';
						case 1:
							return "删除";
						case 2:
							return "修改";
						default:
							return '';
						}
			        }},  
			        {field:'auditStatus',title:'审核状态',width:100,formatter:function(value,row,index){
			        	switch (value) {
						case 0:
							return '待审核';
						case 1:
							return '审核通过';
						case 2:
							return '拒绝并修改';
						default:
							return '';
						}
			        }},
			        {field:'do',title:'操作',width:100,formatter:function(value,row,index){
			        	if (row.auditStatus != undefined)
							var flag = 'draft';
			        	if(fg == ''){
			        		return'<a href="#" onclick="openRules(\'' + row.id
							+ '\',\'' + flag + '\')">查看</a>'
			        	}else{
						if (row.isowner == false)
							return'<a href="#" onclick="openRules(\'' + row.id
									+ '\',\'' + flag + '\')">查看</a>'
						else
							return '<a href="#" onclick="openRules(\'' + row.id
									+ '\',\'' + flag + '\')">编辑</a>'
			        }}
			        }
			          
			    ]] 
	});
}


function getDetail(rulesId, flag, page) {
	$('select').combobox({
		editable:false,
		panelHeight:'auto'
	})
	$
			.ajax({
				url : '/ccmis/risk/rules/factors',
				type : "POST",
				dataType : 'json',
				data : 'rulesId=' + rulesId + '&flag=' + flag,
				success : function(data) {
					command = 'edit';
					cleanDoc();
					$('#editRules').attr('style', 'display: block;');
					$('#conditions').html('');
					
					var factors = data.factors;
					var conditions = data.conditions;
					var operators = data.operators;
					var rules = data.rules;
					var handlers = data.handlers;
					var handlerList = data.handlerList;
					var selectedFactor;
					var selectedOperator;
					var id;
					ruleId = rules.id;
					ruleNo = rules.no;
					operateType = rules.operateType;
					var refuseReason = rules.refuseReason;
                   
				   if(refuseReason != undefined){
					$('#reason').html('');
					$('#reason').attr('value',refuseReason);
					}
					if(rules.auditStatus != 2){
						$('#showReason').attr('disabled',' disabled');
					}else{
						$('#showReason').removeAttr('disabled');
					}
					
					$('#rulesNo').attr('value', rules.no);
					$('#rulesName').attr('value', rules.name);
					$('#priority').attr('value', rules.priority);
					$('#rule_level').combobox('setValue',rules.level);
					$('#rule_level option[value=' + rules.level + ']').attr(
							"selected", "selected");
					$('#status').combobox('setValue',rules.status);

					$('#startTime').attr('value', rules.startTime);
					$('#endTime').attr('value', rules.endTime);
					$('#startInterval').attr('value', rules.startInterval);
					$('#endInterval').attr('value', rules.endInterval);

					var isowner = rules.isowner;
					if (isowner == false && flag == 1) {
						$('#saveBtn').attr("disabled", true);
						$('#deleteBtn').attr("disabled", true);
					} else if(isowner == true && flag == 1){
						$('#saveBtn').attr("disabled", false);
						$('#deleteBtn').attr("disabled", false);
					}

					$
							.each(
									conditions,
									function(i, val) {
										c_globalId = parseInt(c_globalId) + 1;
										id = val.id;
										var selectedFactor = 'factor' + i;
										var selectedOperator = 'condition' + i;
										var riskFactor = val.riskFactor;
										var param = val.riskFactorParam;
										var checkCondition = val.checkCondition;
										var externalParam = val.externalParam;
										var epval = '';
										var epdes = '请选择字段 ';
										if(externalParam != undefined){
										  epval = externalParam.split(":")[0];
										  epdes = externalParam.split(":")[1];
										}
											
										
										var paramHtml = '';
										if (val.containParam)
											paramHtml = '<span class="combo" style="width: 96px; height: 20px; line-height: 20px; "><input type="text" name="param'
													+ '" class="combo-text validatebox-text" onblur="validateParam(\'factor'
													+ i
													+ '\',this.value)" placeholder="参数名" value="'
													+ param + '"></span>';

										var checkValueHtml = '';

										var resultEnumValue = val.resultEnumValue;

										var options = '';
										for ( var key in resultEnumValue) {
											options += '<option value="'
													+ resultEnumValue[key]
													+ '">' + key + '</option>';

										}

										if (resultEnumValue != undefined)
											checkValueHtml = '<select name="checkValue" comboname="checkValue" class="condition_select">'
													+ options + '</select>';
										else
											checkValueHtml = '<span class="combo" style="width: 117px; height: 20px; line-height: 20px; "><input name="checkValue" comboname="checkValue" type="text" id="checkValue'
													+ i
													+ '" class="combo-text validatebox-text" placeholder="判断值"  onblur="validateCheckValue(\'factor'
													+ i
													+ '\',this.value)" value="'
													+ val.checkValue + '"/></span>'
										
													
													
										var condition = '<tr id="condition'
												+ val.idd
												+ '">'
												+ '<td><select class="condition_select" id="factor' 
												+ i
												+ '" name="factor"></select></td><td id="field'+i+'"><a name="fs" href="javascript:showFileds('+i+');" value='+epval+'>'+epdes+'</a></td><td id="param'
												+ i
												+ '">'
												+ paramHtml
												+ '</td>'
												+ '<td id="condition'+i+'"><select name="operator" comboname="operator" class="condition_select"><option value='
												+ val.checkCondition
												+ '>'
												+ val.checkConditionDescription
												+ '</option></select></td>'
												+ '<td id="checkValue'
												+ i
												+ '">'
												+ checkValueHtml
												+ '</td>'
												+ '<td id="connector'
												+ i
												+ '"><select name="connector" comboname="connector"><option value="and">与(AND)</option><option value="or">或(OR)</option></select></td>'
												+ '<td><a name="add" href="javascript:addCondition('
												+ (parseInt(c_globalId) + 1)
												+ ')"><img src="../style/css/icons/edit_add.png"></a>&nbsp;<a name="del" href="javascript:removeDoc(\'condition'
												+ val.id
												+ '\')"><img src="../style/css/icons/edit_remove.png"></a></td>'
												+ '</tr>'

										$('#conditions').append(condition);
										
										if (checkCondition.indexOf('Contain') > 0) {
											var values = val.checkValue
													.split(',');
											$('#checkValue' + i).find(
													'select[name=checkValue]')
													.combobox({
														editable:false,
														multiple : true,
														panelHeight : 'auto'
													}).combobox('setValues',
															values);
										} else {
											$('#checkValue' + i).find(
													'select[name=checkValue]')
													.combobox({
														editable:false,
														panelHeight : 'auto'
													}).combobox('setValue',
															val.checkValue);
										}
										

										$(
												'#connector' + i
														+ ' option[value="'
														+ val.connector + '"]')
												.attr("selected", "selected");
										

										$(
												'#checkValue' + i
														+ ' option[value="'
														+ val.checkValue + '"]')
												.attr('selected', 'selected');

										// operator = val.checkCondition;
										$('#' + val.riskFactor).html('');
										$
												.each(
														factors,
														function(n, factor) {
															if (factor.className == riskFactor) {
																$(
																		'#'
																				+ selectedFactor)
																		.append(
																				'<option value="'
																						+ factor.className
																						+ '" selected="selected">'
																						+ factor.name
																						+ '</option>');
															} else {
																$(
																		'#'
																				+ selectedFactor)
																		.append(
																				'<option value="'
																						+ factor.className
																						+ '">'
																						+ factor.name
																						+ '</option>');
															}

														})

										$('#' + val.checkCondition).html('');

										$('#' + selectedOperator).find('select[name="operator"]').html('');
										$
												.each(
														operators[i],
														function(m, operator) {

															if (operator.className == val.checkCondition) {
																$(
																		'#'
																				+ selectedOperator).find('select[name="operator"]')
																		.append(
																				'<option value="'
																						+ operator.className
																						+ '" selected="selected">'
																						+ operator.name
																						+ '</option>');
															} else {
																$(
																		'#'
																				+ selectedOperator).find('select[name="operator"]')
																		.append(
																				'<option value="'
																						+ operator.className
																						+ '">'
																						+ operator.name
																						+ '</option>');
															}

														})
														
														$('#factor'+i).combobox({
															editable:false,
															panelHeight:'auto',
															editable:false,
															onChange:function(){changedFactor(i,$('#factor'+i).combobox('getValue'));}
														})
														
														$('#condition'+i).find('select[name="operator"]').combobox({
															editable:false,
															panelHeight:'auto',
															onChange:function(){
																changeOperator(i,$('#condition'+i+' select').combobox('getValue'))}
														})
														
														$('#connector'+i).find('select').combobox({
															editable:false,
															panelHeight:'auto'														})

									})
									

					removeAddoRdel($('#conditions tr'));

					$('#handlers').html('');
					$ 
							.each(
									handlers,
									function(h, handler) {
										var hh = '<tr id="handler'
												+ h_globalId
												+ '"><td><select class="condition_select" id="handler'
												+ h
												+ '" name="command" comboname="command"></select></td><td><span class="combo" style="width: 96px; height: 20px; line-height: 20px; "><input type="text" class="combo-text validatebox-text" placeholder="处理命令值" onblur="validateHandler(\'handler'
												+ h
												+ '\',this.value,\'code\')" id="value'
												+ h
												+ '" name="commandValue" comboname="commandValue"/><span></td><td><span class="combo" style="width: 250px; height: 20px; line-height: 20px; "><input type="text" class="combo-text validatebox-text" placeholder="命令值描述" onblur="validateHandler(\'handler'
												+ h
												+ '\',this.value,\'description\')" id="description'
												+ h
												+ '" name="commandValue_description" comboname="commandValue_description"/></span></td>'
												+ '<td><a name="add" href="javascript:addHandler(\''
												+ parseInt(h_globalId)
												+ 1
												+ '\')"><img src="../style/css/icons/edit_add.png"></a>&nbsp;<a name="del" href="javascript:removeDoc(\'handler'
												+ h_globalId
												+ '\')"><img src="../style/css/icons/edit_remove.png"></a></td></tr>';
										$('#handlers').append(hh);
									
										h_globalId = parseInt(h_globalId) + 1;
										$
												.each(
														handlerList,
														function(l, hl) {
															if (handler.command == hl.command) {
																$(
																		'#handler'
																				+ h)
																		.append(
																				'<option value="'
																						+ hl.command
																						+ '" selected="selected">'
																						+ hl.description
																						+ '</option>');
															} else {
																$(
																		'#handler'
																				+ h)
																		.append(
																				'<option value="'
																						+ hl.command
																						+ '">'
																						+ hl.description
																						+ '</option>');
															}
														})

										var vs = handler.commandValue
												.split(',');
										$('#value' + h).attr('value', vs[0]);
										$('#description' + h).attr('value',
												vs[1]);
										
									$('#handler'+h).combobox({
										editable:false,
										panelHeight:'auto'
									});	

									})
					removeAddoRdel($('#handlers tr'));
					
					if(page == 'audit'){
						$(':input').attr('readonly','readonly');
						$('#reason').removeAttr('readonly');//将input设为只读，area也被设置为只读。
						removeAddoRdel($('#handlers tr'));
						$('.combobox-f').combobox('disable');
					}
				},
				error : function() {

				}

			});

}

function saveRules(){

	var no = getValue($('#rulesNo'));
	var name = getValue($('#rulesName'));
	var priority = getValue($('#priority'));
	var level = getValue($('#rule_level'));
	var status = getValue($('#status'));
	var startTime = getValue($("#startTime"));
	var endTime = getValue($("#endTime"));
	var startInterval = getValue($('#startInterval'));
	var endInterval = getValue($('#endInterval'));
	/*if (commitFlag == 0) {
		if (globalMessage == undefined)
			$.messager.show({
				title:'提示',
				msg:'规则未做任何修改!',
				timeout:1000,
				showType:'fade',
				style:{  
	                right:'',  
	                bottom:''  
	            }  
			});
		else
   		$.messager.show({
			title:'提示',
			msg:'必填项不能为空!',
			timeout:1000,
			showType:'fade',
			style:{  
                right:'',  
                bottom:''  
            }  
		});
		return;
	}
*/
	if (!compDate(startTime, endTime))
		return;

	if (!compTime(startInterval, endInterval))
		return;

	if (name == '' || priority == '' || level == '') {
		$.messager.show({
			title:'提示',
			msg:'必填项不能为空!',
			timeout:1000,
			showType:'fade',
			style:{  
                right:'',  
                bottom:''  
            }  
		});
		return;
	}

	var rules = new Rules(ruleId, no, name, priority, level, interfaceName,
			status, startTime, endTime, startInterval, endInterval);

	var conditions = new Array();
	var handlers = new Array();
	var outsidefunction = false;
	$('#conditions tr').each(
			function(i, val) {
				var factor = getValue($(val).find('select[comboname="factor"]'));
				var externalParam = $(val).find('a[name="fs"]').attr('value');
				if(externalParam == '')
					externalParam = null;//为了不将空串提到后台
				var operator = getValue($(val).find('select[comboname="operator"]'));
				var checkValue = getValue($(val).find('[comboname="checkValue"]'));
				var riskFactorParam = getValue($(val).find('input[name="param"]'));
				var connector = getValue($(val).find('select[comboname="connector"]'));
				if (factor == '请选择' || operator == '请选择风险因素' || checkValue == '' || riskFactorParam == '') {
					$.messager.show({
						title:'提示',
						msg:'必填项不能为空!',
						timeout:1000,
						showType:'fade',
						style:{  
			                right:'',  
			                bottom:''  
			            }  
					});
					outsidefunction = true;
					return;
				}
				var condition = new Condition(factor, externalParam, operator, checkValue,
						riskFactorParam, connector);
				conditions.push(condition);
			})

	if (outsidefunction)
		return; 
 
	$('#handlers tr').each(
			function(i, handler) {
				var command = getValue($(handler).find('select[comboname="command"]'));
				var commandValue = getValue($(handler)
						.find('input[name="commandValue"]'));
				var commandValueDescription = getValue($(handler).find(
						'input[name="commandValue_description"]'));
				if (commandValue == '' || commandValueDescription == '') {
					$.messager.show({
						title:'提示',
						msg:'必填项不能为空!',
						timeout:1000,
						showType:'fade',
						style:{  
			                right:'',  
			                bottom:''  
			            }  
					});
					outsidefunction = true;
					return;
				}
				var hl = new Handler(command, commandValue + ','
						+ commandValueDescription);
				handlers.push(hl);
				commitFlag = 1;
			})

	if (outsidefunction)
		return;

	if (conditions.length == 0 || handlers.length == 0) {
		$.messager.show({
			title:'提示',
			msg:'风险条件与处理命令不能为空!',
			timeout:1000,
			showType:'fade',
			style:{  
                right:'',  
                bottom:''  
            }  
		});
		return;
	}
	
	$.ajax({
		url : '/ccmis/risk/rules/saveRules',
		type : "POST",
		dataType : 'json',
		data : 'rules=' + $.toJSON(rules) + '&conditions='
				+ $.toJSON(conditions) + '&handlers=' + $.toJSON(handlers)
				+ '&command=' + command,
		success : function(data) {
			if (data == "success") {
				getRules(menuId);
			} else {
				$.messager.alert('提示','服务器错误:' + data,'info');
				return;
			}
		},
		error : function() {
			getRules(menuId);
		}

	});
	$('#editRules').attr('style', 'display: none;');


}