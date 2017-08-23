function getQueryStringByName(name){ 
	var result = location.search.match(new RegExp("[\?\&]" + name+ "=([^\&]+)","i")); 
	if(result == null || result.length < 1){ 
		return ""; 
	} 
	return result[1]; 
} 

Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.form.*',
    'Ext.window.*'
]);

var type = getQueryStringByName("type");
var name = getQueryStringByName("name");
var host = getQueryStringByName("host");

Ext.define('interfaceEntity', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'host', type: 'string'},
        {name: 'name', type: 'string'},
        {name: 'status', type: 'string'},
        {name: 'health', type: 'integer'},
        {name: 'success', type: 'integer'},
        {name: 'failure' , type :'integer'},
        {name: 'average' , type :'integer'},
        {name: 'max' , type :'integer'}
    ]
});

Ext.onReady(function() {
    var gridStore = Ext.create('Ext.data.Store', {
        model: 'interfaceEntity',
        pageSize: 20,
        proxy: {
            type: 'ajax',
            url: '/ccmis/risk/server/status',
            reader: {
                type: 'json',
                root:'list',
                successProperty:'success',
                totalProperty: 'totalSize'
            }
        },
        listeners:{
            'load':function(thisStroe, records, success, operation, options) {
                messageHandler(thisStroe.getProxy().getReader().jsonData);
            } ,
            'beforeload':function(thisStore, operation, options) {
                thisStore.getProxy().extraParams = {type:type,host:host,name:name};
            }
        }
    });
    gridStore.load();
    
    var gridColumn = [
        {text: "服务器",dataIndex: 'host',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
        {text: "接口名",dataIndex: 'name',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
        {text: "状态",dataIndex: 'status',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
        {text: "健康值",dataIndex: 'health',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
        {text: "成功数",dataIndex: 'success',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
        {text: "失败数",dataIndex: 'failure',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
        {text: "平均响应",dataIndex: 'average',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
        {text: "最大响应",dataIndex: 'max',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'}
	];
    
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
        title:'商户代码信息',
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
                  {
                      xtype: 'toolbar',
                      layout:"hbox",
                      items: [
                          { xtype: 'button',iconCls:'add',text: '详细信息',handler:doAll},
                          { xtype: 'button',iconCls:'add',text: '服务器信息',handler:doServers},
                          { xtype: 'button',iconCls:'add',text: '此服务器',handler:doServer},
                          { xtype: 'button',iconCls:'add',text: '此接口',handler:doInterface}
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

    function doAll() {
    	document.location.href = "status.html";
    }
    
    function doServers() {
    	document.location.href = "status.html?type=server";
    }
    
    function doServer() {
    	var si = gridPanel.getSelectionModel().getSelection();
    	if (si.length != 1) {
            message("请选择需要显示的服务器.");
            return;
        }
    	document.location.href = "status.html?type=detail&host=" + si[0].get("host");
    }
    
    function doInterface() {
    	var si = gridPanel.getSelectionModel().getSelection();
    	if (si.length != 1) {
            message("请选择需要显示的接口.");
            return;
        }
    	document.location.href = "status.html?type=detail&name=" + si[0].get("name");
    }
});

