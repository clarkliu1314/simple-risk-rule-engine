Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.form.*',
    'Ext.window.*'
]);




var cerficicationData = [
 {
	'code' :'DEBUG', 'name' :'DEBUG'
}, {
	'code' :'INFO', 'name' :'INFO'
}, {
	'code' :'WARN', 'name' :'WARN'
}, {
	'code' :'ERROR', 'name' :'ERROR'
}
, {
	'code' :'FATAL', 'name' :'FATAL'
}
];
var statusStore = new Ext.data.SimpleStore( {
	fields : [ { name :'code', mapping :'code' }, { name :'name', mapping :'name' } ],
	data : cerficicationData
});


Ext.onReady(function() {
  
    

    var textForm = Ext.create('Ext.form.Panel', {
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
						    fieldLabel:'关键字',
						    xtype:'textfield',
						    id:'key',
						    name:'key',
						    regex:'',
						    allowBlank:true
						
						}, 
                       
                
                    {
                        fieldLabel:'最大行数',
                        xtype:'textfield',
                        id:'maxline',
                        name:'maxline',
                        value:'100',
                        regex:'',
                        allowBlank:true
                    },
                    {
                        fieldLabel: '日志级别',
                        xtype: 'combobox',
                        name:'level',
                        id:'level',
                        store: statusStore,
                        valueField: 'code',
                        displayField: 'name',
                        typeAhead: true,
                        queryMode: 'local',
                        editable:false,
                        emptyText: '请选择日志级别'
                    },
                    
                    {
                        fieldLabel: '日期',
                        xtype : 'datefield',
                        format: 'Y-m-d',
                        name:'date'
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
                                text: '查找',
                                xtype : 'button',
                                handler:function () {
                                	findLog();
                                	
                                }
                            }
                        ]
                    }
                ]
            }
        ]
    });

    Ext.getCmp('level').setRawValue('ERROR');

    /**
     * Grid
     */
    var textPanel = Ext.create('Ext.Panel', {
        title:'日志信息',
        id:'textPanel',
        region:'center',
        bodyStyle: 'background:white; padding:10px;',
        autoScroll : true,
        html:'',
        loadMask : {
            msg : "数据加载中，请稍等..."
        },
        
        dockedItems: [
                  textForm
        ]
    });

    /**
     * 面板定义
     */
    Ext.create('Ext.container.Viewport', {
        layout: 'border',
        renderTo: Ext.getBody(),
        items: [textPanel]
    });

    function findLog(){
    	
    	  Ext.Ajax.request({
              url: '/ccmis/risk/interface/findLog',
              params: Ext.getCmp('queryInfo').getForm().getValues(false, true),
              success: function(response) {
              	Ext.getCmp('textPanel').body.update(response.responseText);
              },
              failure : function(response) {
                  messageHandler(response.responseText);
              }
          });
    
      
    	
    }
  
  
});

