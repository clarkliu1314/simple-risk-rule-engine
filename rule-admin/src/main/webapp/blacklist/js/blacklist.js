Ext.require([ 'Ext.grid.*', 'Ext.data.*', 'Ext.util.*', 'Ext.state.*',
		'Ext.form.*', 'Ext.window.*' ]);

var typeStore = new Ext.data.SimpleStore({
	fields : [ "code", "name" ],
	proxy : {
		type : 'ajax',  
		url : '/ccmis/risk/enums/getEnum',
		extraParams : {
			'name' : 'BlackListTypeEnum',
			'type' : 'name'
		},
		reader : {
			type : 'json',
			root : 'list'
		}
	}
});
typeStore.load();

Ext.define('blackListEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'long'
	}, {
		name : 'interfaceName',
		type : 'string',
	    hidden : true
		
	}, {
		name : 'interfaceNameDescription',
		type : 'string'
	}, {
		name : 'type',
		type : 'string'
	}, {
		name : 'typeDescrption',
		type : 'string',
		hidden : true
	}, {
		name : 'value',
		type : 'string'
	}, {
		name : 'status',
		type : 'integer'
	}, {
		name : 'statusDescription',
		type : 'string'
	}, {
		name : 'createTime',
		type : 'string'
	}, {
		name : 'reason',
		type : 'string'
	}, {
		name : 'operator',
		type : 'string'
	},{
		name : 'updateTime',
		type : 'string'
	}
	]
});

//审核状态数据源
var statusStore = Ext.create('Ext.data.Store', {
	fields : [ "code", "name" ],
	proxy : {
		type : 'ajax',
		url : '/ccmis/risk/enums/getEnum',
		extraParams : {
			'name' : 'PayInterfaceStatusEnum',
			'type' : 'code'
		},
		reader : {
			type : 'json',
			root : 'list'
		}
	}
});
statusStore.load();

var interfaceEnum = Ext.create('Ext.data.Store', {
	fields : [ "code", "name" ],
	proxy : {
		type : 'ajax',
		url : '/ccmis/risk/interface/InterfaceEnum',
		reader : {
			type : 'json',
			root : 'list'
		}
	}
});
interfaceEnum.load();

Ext.onReady(function() {
	var gridStore = Ext.create('Ext.data.Store', {
		model : 'blackListEntity',
		pageSize : 20,
		proxy : {
			type : 'ajax',
			url : '/ccmis/risk/blacklist/list',
			reader : {
				type : 'json',
				root : 'list',
				successProperty : 'success',
				totalProperty : 'totalSize'
			}
		},
		listeners : {
			'load' : function(thisStroe, records, success, operation, options) {
				messageHandler(thisStroe.getProxy().getReader().jsonData);
			},
			'beforeload' : function(thisStore, operation, options) {
				if (Ext.getCmp('queryInfo')) {
					var resultFormValues = Ext.getCmp('queryInfo').getForm()
							.getValues(false, true);
					thisStore.getProxy().extraParams = resultFormValues;
				}
			}
		}
	});
	gridStore.load();
	var gridColumn = [ {
		text : "ID",
		dataIndex : 'id',
		sortable : true,
		width : Ext.getBody().getWidth() * 0.1,
		align : 'center'
	}, {
		text : "接口名",
		dataIndex : 'interfaceName',
		sortable : true,
		width : Ext.getBody().getWidth() * 0.2,
		align : 'center',
		hidden : true
		
	}, {

		text : "接口名",
		dataIndex : 'interfaceNameDescription',
		sortable : true,
		width : Ext.getBody().getWidth() * 0.2,
		align : 'center',
	}, {
		text : "类型",
		dataIndex : 'type',
		sortable : true,
		width : Ext.getBody().getWidth() * 0.1,
		align : 'center',
		hidden : true

	}, {
		text : "类型",
		dataIndex : 'typeDescrption',
		sortable : true,
		width : Ext.getBody().getWidth() * 0.1,
		align : 'center'
	}, {
		text : "值",
		dataIndex : 'value',
		sortable : true,
		width : Ext.getBody().getWidth() * 0.3,
		align : 'center'
	}, {
		text : "状态值",
		dataIndex : 'status',
		sortable : true,
		width : Ext.getBody().getWidth() * 0.1,
		align : 'center',
		hidden : true
	}, {
		text : "状态",
		dataIndex : 'statusDescription',
		sortable : true,
		width : Ext.getBody().getWidth() * 0.1,
		align : 'center'
	}, {

		text : "创建时间",
		dataIndex : 'createTime',
		sortable : true,
		width : Ext.getBody().getWidth() * 0.1,
		align : 'center'
	}, {
		text : "原因",
		dataIndex : 'reason',
		sortable : true,
		width : Ext.getBody().getWidth() * 0.1,
		align : 'center'
	}, {
		text : "创建人",
		dataIndex : 'operator',
		sortable : true,
		width : Ext.getBody().getWidth() * 0.1,
		align : 'center'
	},{

		text : "更新时间",
		dataIndex : 'updateTime',
		sortable : true,
		width : Ext.getBody().getWidth() * 0.1,
		align : 'center'
	}

	];

	var gridForm = Ext.create('Ext.form.Panel', {
		frame : true,
		id : 'queryInfo',
		layout : 'column',
		defaults : {
			layout : 'column',
			columnWidth : 1,
			xtype : 'container',
			defaults : {
				labelStyle : 'padding-left:5px;',
				columnWidth : .33
			}
		},
		items : [ {
			items : [ {
				fieldLabel : '接口名',
				xtype : 'combobox',
				name : 'interfaceName',
				store : interfaceEnum,
				valueField : 'code',
				displayField : 'name',
				regex:'',
				editable: false,
				typeAhead : true,
				queryMode : 'local',
				emptyText : '全部'
			}, {
				fieldLabel : '类型',
				xtype : 'combobox',
				name : 'type',
				editable: false,
				store : typeStore,
				valueField : 'code',
				displayField : 'name',
				typeAhead : true,
				queryMode : 'local',
				emptyText : '全部'

			}, {
				fieldLabel : '状态',
				xtype : 'combobox',
				name : 'status',
				editable: false,
				store : statusStore,
				valueField : 'code',
				displayField : 'name',
				typeAhead : true,
				queryMode : 'local',
				emptyText : '全部'
			},

			{
				layout : 'column',
				xtype : 'container',
				items : [ {
					columnWidth : 1,
					xtype : 'label',
					html : "&nbsp;"
				}, {
					columnWidth : .0,
					width : 80,
					text : '搜索',
					xtype : 'button',
					handler : function() {
						gridStore.loadPage(1);
					}
				} ]
			} ]
		} ]
	});

	/**
	 * 页脚分页相关信息
	 */
	var gridFootBar = Ext.create('Ext.PagingToolbar', {
		store : gridStore,
		displayInfo : true,
		displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		emptyMsg : "没有记录"
	});

	/**
	 * Grid
	 */
	var gridPanel = Ext.create('Ext.grid.Panel', {
		title : '黑名单列表',
		id : 'gridPanel',
		region : 'center',
		forceFit : false,
		store : gridStore,
		columns : gridColumn,
		frame : true,//制定框
		stripeRows : true,// 是否隔行显示不同的背景颜色!
		loadMask : {
			msg : "数据加载中，请稍等..."
		},

		dockedItems : [ gridForm, {
			xtype : 'toolbar',
			layout : "hbox",
			items : [ {
				xtype : 'button',
				iconCls : 'add',
				text : '添加',
				handler : doAdd
			}, {
				xtype : 'button',
				iconCls : 'option',
				text : '修改',
				handler : doEdit
			}, {
				xtype : 'button',
				iconCls : 'option',
				text : '查看原始值',
				handler : doQueryOriginalValue
			} ]
		} ],
		bbar : gridFootBar
	});

	/**
	 * 解密值并显示
	 */
	function doQueryOriginalValue() {
		var selections = gridPanel.getSelectionModel().getSelection();
		if (selections.length != 1) {
			message("请选中一条记录.");
			return;
		}

		var id = selections[0].get('id');
		Ext.Ajax.request({
			url : '/ccmis/risk/blacklist/doQueryOriginalValue',
			params : {
				id : id
			},
			success : function(response) {
				messageHandler(response.responseText);
			},
			failure : function(response) {
				messageHandler(response.responseText);
			}
		});
	}
	/**
	 * 面板定义
	 */
	Ext.create('Ext.container.Viewport', {
		layout : 'border',
		renderTo : Ext.getBody(),
		items : [ gridPanel ]
	});
	var modifyId;
	var formPanel = Ext.create('Ext.form.Panel', {
		frame : true,
		id : 'formPanel',
		layout : 'column',
		defaults : {
			layout : 'column',
			columnWidth : 1,
			xtype : 'container',
			defaults : {
				labelStyle : 'padding-left:5px;',
				columnWidth : .99,
				allowBlank : false
			}
		},
		items : [ {
			items : [ {
				fieldLabel : '接口名',
				xtype : 'combobox',
				id : 'interfaceName',
				name : 'interfaceName',
				store : interfaceEnum,
				editable: false,
				valueField : 'code',
				displayField : 'name',
				typeAhead : true,
				queryMode : 'local',
				regex:'',
				allowBlank : false,
				emptyText : '请选择接口名称'

			}, {

				fieldLabel : '类型',
				xtype : 'combobox',
				name : 'type',
				id : 'type',
				store : typeStore,
				valueField : 'code',
				displayField : 'name',
				typeAhead : true,
				editable: false,
				queryMode : 'local',
				allowBlank : false,
				emptyText : '请选择类型'
			}, {
				fieldLabel : '值',
				id : 'value',
				name : "value",
				xtype : 'textfield',
				regex : /^[\s\S]*[^ ]+$/,
				maxLength : 200
			}, {
				xtype : "textarea",
				id : 'reason',
				fieldLabel : "原因",
				name : "reason",
				regex : /^[\s\S]*[^ ]+$/,
				maxLength : 200
			}, {
				xtype : 'fieldcontainer',
				fieldLabel : '状态',
				name : 'savestatus',
				defaultType : 'radiofield',
				defaults : {
					flex : 1,
					name : 'status'
				},
				layout : 'hbox',
				items : [ {
					boxLabel : '可用',
					checked : true,
					inputValue : '1'
				}, {
					boxLabel : '禁用',
					inputValue : '0'
				} ]
			} ]
		} ],
		buttons : [ {
			text : '提交',
			handler : function() {
				if (formPanel.form.isValid()) {
					var url = '/ccmis/risk/blacklist/save';
					formPanel.submit({
						url : url,
						params : {
							'id' : modifyId
						},
						success : function(form, action) {
							if (action.result.success) {
								formWin.hide();
								gridStore.load();
							}
							messageHandler(action.result);
						},
						failure : function(form, action) {
							messageHandler(action.result);
						}
					});
				}
			}
		} ]
	});

	var formWin = Ext.create('Ext.window.Window', {
		id : "formWin",
		title : '',
		closable : true,
		resizable : true,
		modal : true,
		closeAction : 'hide',
		width : 400,
		items : [ formPanel ]
	});

	function doAdd() {
		formWin.setTitle('添加黑名单');
		modifyId = '';
		formPanel.form.reset();
		Ext.getCmp('interfaceName').setReadOnly(false);
		Ext.getCmp('type').setReadOnly(false);
		Ext.getCmp('value').setReadOnly(false);
		Ext.getCmp('reason').setReadOnly(false);
		formWin.show();
	}

	function doEdit() {
		var selections = gridPanel.getSelectionModel().getSelection();
		if (selections.length != 1) {
			message("请选中一条记录.");
			return;
		}
		formWin.setTitle('修改黑名单');
		formPanel.form.reset();
		var obj = selections[0];
		modifyId = obj.get("id");
		formPanel.form.setValues(obj.data);
		Ext.getCmp('interfaceName').setReadOnly(true);
		Ext.getCmp('type').setReadOnly(true);
		Ext.getCmp('value').setReadOnly(true);
		Ext.getCmp('reason').setReadOnly(true);
		formWin.show();

	}
});
