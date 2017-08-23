Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.form.*',
    'Ext.window.*'
]);
 

Ext.define('riskLogEntity', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'serviceName',type: 'string'},
        {name: 'businessType', type: 'string'},
        {name: 'bankCardNo', type: 'string'},
        {name: 'cardHolderName', type: 'string'},
        {name: 'idCardType', type: 'string'},
        {name: 'idCardNo', type: 'string'},
        {name: 'tradeAmount' , type :'string' },
        {name: 'tradeRequestId',type: 'string'},
        {name: 'extRequestSN', type: 'string'},
        {name: 'extTradeDN', type: 'string'},
        {name: 'payProduct', type: 'string'},
        {name: 'cardType', type: 'string'},
        {name: 'payWay', type: 'string'},
        {name: 'mobile' , type :'string' },
        {name: 'merchantNo',type: 'string'},
        {name: 'cardExpireDate', type: 'string'},
        {name: 'currencyType', type: 'string'},
        {name: 'accountNo', type: 'string'},
        {name: 'code', type: 'string'},
        {name: 'executeTime', type: 'string'},
        {name: 'description' , type :'string' },
        {name: 'extendInfo',type: 'string'},
        {name: 'reason', type: 'string'},
        {name: 'createDate', type: 'string'},
        {name: 'startCreateDate', type: 'string'},
        {name: 'endCreateDate', type: 'string'}
    ]
}); 

 
Ext.onReady(function() {
    var gridStore = Ext.create('Ext.data.Store', {
        model: 'riskLogEntity',
        pageSize: 20,
        proxy: {
            type: 'ajax',
            url: '/ccmis/risk/riskLog/list',
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
                Ext.getCmp("lastRow").setValue(thisStroe.getProxy().getReader().jsonData.lastRow);
            } ,
            'beforeload':function(thisStore, operation, options) {
            	if (Ext.getCmp('queryInfo')) {
            		var resultFormValues = Ext.getCmp('queryInfo').getForm().getValues(false, true);
                    thisStore.getProxy().extraParams = resultFormValues;
                }
            }
        }
    });
    
    var gridStore1 = Ext.create('Ext.data.Store', {
        model: 'riskLogEntity',
        pageSize: 20,
        proxy: {
            type: 'ajax',
            url: '/ccmis/risk/riskLog/exportExcel',
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
                Ext.getCmp("lastRow").setValue(thisStroe.getProxy().getReader().jsonData.lastRow);
            } ,
            'beforeload':function(thisStore, operation, options) {
            	if (Ext.getCmp('queryInfo')) {
            		var resultFormValues = Ext.getCmp('queryInfo').getForm().getValues(false, true);
                    thisStore.getProxy().extraParams = resultFormValues;
                }
            }
        }
    });
    
    var gridColumn = [
                      {text: "业务名称",dataIndex: 'serviceName',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
                      {text: "业务类型",dataIndex: 'businessType',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
                      {text: "银行卡号",dataIndex: 'bankCardNo',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
                      {text: "持卡人姓名",dataIndex: 'cardHolderName',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
                      {text: "证件类型",dataIndex: 'idCardType',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
                      {text: "证件编号",dataIndex: 'idCardNo',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
                      {text: "交易金额",dataIndex: 'tradeAmount',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
                      {text: "交易请求ID",dataIndex: 'tradeRequestId',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
                      {text: "外部请求流水号",dataIndex: 'extRequestSN',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
                      {text: "外部交易唯一标识",dataIndex: 'extTradeDN',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
                      {text: "支付产品",dataIndex: 'payProduct',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
                      {text: "卡类型",dataIndex: 'cardType',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
                      {text: "支付方式",dataIndex: 'payWay',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
                      {text: "电话号码",dataIndex: 'mobile',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
                      {text: "商户编号",dataIndex: 'merchantNo',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
                      {text: "卡有效期",dataIndex: 'cardExpireDate',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
                      {text: "币种",dataIndex: 'currencyType',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
                      {text: "会员号",dataIndex: 'accountNo',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
                      {text: "状态码",dataIndex: 'code',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
                      {text: "执行时间",dataIndex: 'executeTime',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
                      {text: "描述",dataIndex: 'description',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
                      {text: "扩展信息",dataIndex: 'extendInfo',sortable:true,width:Ext.getBody().getWidth() * 0.2,align:'center'},
                      {text: "原因",dataIndex: 'reason',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'},
                      {text: "创建日期",dataIndex: 'createDate',sortable:true,width:Ext.getBody().getWidth() * 0.1,align:'center'} 
                    ];

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
						    fieldLabel:'业务名称',
						    xtype:'textfield',
						    id:'serviceName',
						    name:'serviceName',
						    regex:'',
						    allowBlank:true
						
						}, 
                        {
	                        fieldLabel:'状态码',
	                        xtype:'textfield',
	                        id:'code',
	                        name:'code',
	                        regex:'',
	                        allowBlank:true
	                    },
	                    {
	                        fieldLabel: '创建日期',
	                        xtype : 'datefield',
	                        format: 'Ymd',
	                        name:'startCreateDate',
	                        id:'startCreateDate',
	                        allowBlank:true	
	                    },
	                    {
	                        fieldLabel: '至',
	                        xtype : 'datefield',
	                        format: 'Ymd',
	                        name:'endCreateDate',
	                        id:'endCreateDate',
	                        allowBlank:true	
	                    },
	                    {
	                        xtype : 'hidden',
	                        name:'lastRow',
	                        id:'lastRow',
	                        allowBlank:true	
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
                                        	if(Ext.getCmp("startCreateDate").getValue() == null ){
                                        		alert("请输入开始日期");
                                        		return;
                                        	}
                                        	if(Ext.getCmp("endCreateDate").getValue() == null ){
                                        		alert("请输入结束日期");
                                        		return;
                                        	}
                                        	
                                        	Ext.getCmp("lastRow").setValue(null);
                                        	gridStore.loadPage(1); 
                                        }
                                    },
                                    {
                                        columnWidth:.0,
                                        width:80,
                                        text: '上一页',
                                        xtype : 'button',
                                        handler:function () {
                                        	if(Ext.getCmp("startCreateDate").getValue() == null ){
                                        		alert("请输入开始日期");
                                        		return;
                                        	}
                                        	if(Ext.getCmp("endCreateDate").getValue() == null ){
                                        		alert("请输入结束日期");
                                        		return;
                                        	}
                                        	Ext.getCmp("lastRow").setValue("previous");
                                        	gridStore.loadPage(1); 
                                        }
                                    },
                                    {
                                        columnWidth:.0,
                                        width:80,
                                        text: '下一页',
                                        xtype : 'button',
                                        handler:function () {
                                        	if(Ext.getCmp("startCreateDate").getValue() == null ){
                                        		alert("请输入开始日期");
                                        		return;
                                        	}
                                        	if(Ext.getCmp("endCreateDate").getValue() == null ){
                                        		alert("请输入结束日期");
                                        		return;
                                        	}
                                        	
                                        	gridStore.loadPage(1); 
                                        }
                                    },
                                    {
                                        columnWidth:.0,
                                        width:80,
                                        text: '导出excel',
                                        xtype : 'button',
                                        handler:function () {
                                        	if(Ext.getCmp("startCreateDate").getValue() == null ){
                                        		alert("请输入开始日期");
                                        		return;
                                        	}
                                        	if(Ext.getCmp("endCreateDate").getValue() == null ){
                                        		alert("请输入结束日期");
                                        		return;
                                        	}
                                        	
                                        	location.href= "/ccmis/risk/riskLog/exportExcel?startCreateDate=" 
                                        				+ Ext.getCmp("startCreateDate").formatDate(Ext.getCmp("startCreateDate").getValue())
                                        				+ "&endCreateDate=" + Ext.getCmp("endCreateDate").formatDate(Ext.getCmp("endCreateDate").getValue())
                                        				+ "&serviceName=" + Ext.getCmp("serviceName").getValue()
                                        				+ "&code=" + Ext.getCmp("code").getValue();
                                        }
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ]
    });

    
    /**
     * Grid
     */
    var gridPanel = Ext.create('Ext.grid.Panel', {
        title:'审核日志',
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
                          
                      ]
                  }
        ]
    });

    /**
     * 面板定义
     */
    Ext.create('Ext.container.Viewport', {
        layout: 'border',
        renderTo: Ext.getBody(),
        items: [gridPanel]
    });
    
    var formWin = Ext.create('Ext.window.Window', {
        id:"formWin",
        title: '',
        closable: true,
        resizable : true,
        modal :true,
        closeAction: 'hide',
        width: 400,
        items: [formPanel]
    });
});

