Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.form.*',
    'Ext.window.*'
]);
var addFlag,modifyId;

Ext.define('interfaceEntity', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id',type: 'long'},
        {name: 'systemName', type: 'string'},
        {name: 'interfaceName', type: 'string'},
        {name: 'description', type: 'string'},
        {name: 'eventDescription', type: 'string'},
        {name: 'createTime', type: 'string'},
        {name: 'status' , type :'integer' },
        {name: 'updateTime', type: 'string'}
    ]
});

Ext.onReady(function() {
    //状态数据源
    var statusStore = Ext.create('Ext.data.Store', {
        fields:["code","name"],
        proxy: {
            type: 'ajax',
            url: '/ccmis/enum/getEnum',
            extraParams:{'name':'EnabledEnum','type':'code'},
            reader: {
                type: 'json',
                root:'list'
            }
        }
    });
    statusStore.load();

    var systemNameStore =  Ext.create('Ext.data.Store', {
        fields:["systemName","description","status"],
        proxy: {
            type: 'ajax',
            url: '/ccmis/risk/system/selectList',
            extraParams:{'name':'','type':''},
            reader: {
                type: 'json',
                root:'list'
            }
        }
    });
    systemNameStore.load();

    var gridStore = Ext.create('Ext.data.Store', {
        model: 'interfaceEntity',
        pageSize: 20,
        proxy: {
            type: 'ajax',
            url: '/ccmis/risk/interface/list',
            reader: {
                type: 'json',
                root:'list',
                successProperty:'success',
                totalProperty: 'totalSize'
            }
        },
        listeners:{
            'load':function(thisStore, records, success, operation, options) {
                messageHandler(thisStore.getProxy().getReader().jsonData);
            } ,
            'beforeload':function(thisStore, operation, options) {
                if (Ext.getCmp('queryInfo')) {
                    var resultFormValues = Ext.getCmp('queryInfo').getForm().getValues(false, true);
                    thisStore.getProxy().extraParams = resultFormValues;
                }
            }
        }
    });
    gridStore.load();

    var gridColumn = [
        {text: "ID",dataIndex: 'id',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
        {text: "子系统名",dataIndex: 'systemName',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center',renderer:rendererSystemName},
        {text: "接口名",dataIndex: 'interfaceName',sortable:true,width:Ext.getBody().getWidth() * 0.15,align:'center'},
        {text: "描述",dataIndex: 'description',sortable:true,width:Ext.getBody().getWidth() * 0.15,align:'center'},
        {text: "事件描述",dataIndex: 'eventDescription',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
        {text: "创建时间",dataIndex: 'createTime',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
        {text: "状态",dataIndex: 'status',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center',renderer:renderStatus},
        {text: "更新时间",dataIndex: 'updateTime',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'}

    ];  

    function rendererSystemName(v){
        systemNameStore.each(function(record) {
            var code = record.get('systemName');
            if(v == code){
                v =  record.get('description');
            }
        });
        return v;
    }

    var gridForm = Ext.create('Ext.form.Panel', {
        frame:true,
        id:'queryInfo',
        layout:'column',
        defaults:{
            layout:'column',
            columnWidth:1,
            xtype:'container',
            defaults:{
                labelStyle: 'padding-left:5px;',
                columnWidth:.33
            }
        },
        items:[
            {
                items:[
                    {
                        fieldLabel:'子系统名',
                        xtype:'combobox',
                        name:'systemName',
                        store : systemNameStore,
                        valueField : 'systemName',
                        displayField : 'description',
                        typeAhead : true,
                        queryMode : 'local',
                        regex:'',
                        emptyText : '全部',
                        allowBlank:true
                    },
                    {
                        fieldLabel:'接口名',
                        xtype:'textfield',
                        name:'interfaceName',
                        regex:'',
                        //    regexText:number619RegexText,
                        allowBlank:true
                    },
                    {
                        fieldLabel: '状态',
                        xtype: 'combobox',
                        name:'status',
                        store: statusStore,
                        valueField: 'code',
                        displayField: 'name',
                        typeAhead: true,
                        queryMode: 'local',
                        editable:true,
                        emptyText: '全部'
                    },
                    {
                        layout:'column',
                        xtype : 'container',
                        items:[
                            {
                                columnWidth:1,
                                xtype : 'label',
                                html:"&nbsp;"
                            },
                            {
                                columnWidth:.0,
                                width:80,
                                text: '搜索',
                                xtype : 'button',
                                handler:function () {
                                    gridStore.loadPage(1);
                                }
                            }
                        ]
                    }
                ]
            }
        ]
    });

    /**
     * 页脚分页相关信息
     */
    var gridFootBar = Ext.create('Ext.PagingToolbar', {
        store: gridStore,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });

    /**
     * Grid
     */
    var gridPanel = Ext.create('Ext.grid.Panel', {
        title:'规则接口信息',
        id:'gridPanel',
        region:'center',
        forceFit:false,
        store : gridStore,
        columns : gridColumn,
        frame: true,//制定框
        stripeRows : true,// 是否隔行显示不同的背景颜色!
        loadMask : {
            msg : "数据加载中，请稍等..."
        },

        dockedItems: [
            gridForm,
            {
                xtype: 'toolbar',
                layout:"hbox",
                items: [
                    { xtype: 'button',iconCls:'add',text: '添加',handler:doAdd},
                    { xtype: 'button',iconCls:'option',text: '修改',handler:doModify},
                    { xtype: 'button',iconCls:'add',text: '导入',handler:doImport}
//                    { xtype: 'button',iconCls:'add',text: '通知规则改变',handler:doNotify}
                ]
            }
        ],
        bbar : gridFootBar
    });

    /**
     * 面板定义
     */
    Ext.create('Ext.container.Viewport', {
        layout: 'border',
        renderTo: Ext.getBody(),
        items: [gridPanel]
    });

    /**
     * Form定义
     */
    var formPanel = Ext.create('Ext.form.Panel', {
        frame:true,
        id : 'formPanel',
        layout:'column',
        defaults:{
            layout:'column',
            columnWidth:1,
            xtype:'container',
            defaults:{
                labelStyle: 'padding-left:5px;',
                columnWidth:.99,
                allowBlank : false
            }
        },
        items: [
            {
                items:[
                    {
                        fieldLabel:'子系统名',
                        xtype: 'combobox',
                        name:'systemName',
                        store: systemNameStore,
                        valueField: 'systemName',
                        displayField: 'description',
                        typeAhead: true,
                        queryMode: 'local',
                        editable:false,
                        emptyText: '请选择状态'
                    }
                ]
            },
            {
                items:[
                    {
                        fieldLabel: '接口名称',
                        xtype : 'textfield',
                        name:"interfaceName",
                        maxLength:100,
                        regex:/^[\s\S]*[^ ]+$/
                    }
                ]
            },
            {
                items:[
                    {
                        fieldLabel: '描述',
                        xtype : 'textfield',
                        name:"description",
                        regex:/^[\s\S]*[^ ]+$/,
                        maxLength:200
                    }
                ]
            },
            {
                items:[
                    {
                        fieldLabel: '事件描述',
                        xtype : 'textfield',
                        name:"eventDescription",
                        maxLength:200
                    }
                ]
            },
            {
                items:[
                    {
                        fieldLabel: '状态',
                        xtype : 'combobox',
                        store: statusStore,
                        queryMode: 'local',
                        name : 'status',
                        id:'status',
                        displayField: 'name',
                        valueField: 'code',
                        editable : false,
                        allowBlank:false
                    }
                ]
            }
        ],
        buttons: [
            {
                text: '提交',
                handler:function() {
                    if (formPanel.form.isValid()) {
                        this.setDisabled(true);
                        var url = '/ccmis/risk/interface/modify';
                        if (addFlag) {
                            url = "/ccmis/risk/interface/add";
                            modifyId = '';
                        }

                        formPanel.submit({
                            url: url,
                            params: {'id':modifyId},
                            scope: this,
                            success: function(form, action) {
                                if (action.result.success) {
                                    formWin.hide();
                                    gridStore.load();
                                }
                                this.setDisabled(false);
                                messageHandler(action.result);
                            },
                            failure : function(form, action) {
                                this.setDisabled(false);
                                messageHandler(action.result);
                            }
                        });
                    }
                }
            }
        ]
    });

    /**
     * 定义Form的Window
     */
    var formWin = Ext.create('Ext.window.Window', {
        id:"formWin",
        title: '',
        closable: true,
        resizable : true,
        modal :true,
        closeAction: 'hide',
        draggable:false,
        width: 500,
        items: [formPanel]
    });

    function doAdd() {
        addFlag = true;
        formWin.setTitle('添加规则接口');
        formPanel.form.reset();
        formWin.show();
    }

    /**
     * 修改规则接口
     */
    function doModify() {
        var selections = gridPanel.getSelectionModel().getSelection();
        if (selections.length != 1) {
            message("请选中一条记录.");
            return;
        }
        addFlag = false;
        formWin.setTitle('修改规则接口');
        formPanel.form.reset();

        var obj = selections[0];
        modifyId = obj.get("id");
        formPanel.form.setValues(obj.data);

        statusStore.each(function(record) {
            var code = record.get('code');
            if(obj.get('status') == code){
                Ext.getCmp('status').setValue(record.get('code'));
            }
        });
      formWin.show();
    }

    function doImport() {
        Ext.Ajax.request({
            url: '/ccmis/risk/interface/import',
            success: function(response) {
                messageHandler(response.responseText);
                gridStore.load();

            },
            failure : function(response) {
                messageHandler(response.responseText);
            }
        });
    }

    function doNotify() {
        var si = gridPanel.getSelectionModel().getSelection();
        if (si.length != 1) {
            message("请选择需要通知的接口.");
            return;
        }
        Ext.Ajax.request({
            url: '/ccmis/risk/interface/notify',
            params: {name:si[0].get("interfaceName")},
            success: function(response) {
                if (response.responseText == "0") {
                    alert("通知成功。");
                }
                else {
                    alert("通知失败，" + response.responseText);
                }
            },
            failure : function(response) {
                messageHandler(response.responseText);
            }
        });
    }

    function renderStatus(v){
        statusStore.each(function(record) {
            var code = record.get('code');
            if(v == code){
                v =  record.get('name');
            }
        });
        return v
    }
});

