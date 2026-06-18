/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * author: m@rco.@ndrade
 */
function getDetalleFact(idfact,idRadio){
    var form = Ext.widget('form', {
        // layout:'column',
        id:'idActPacFrm',
        border: false,
        autoScroll:true,
        bodyPadding: 10,
        width: 680,
        // height :1000,
        //fieldDefaults: {labelWidth: 110},
        items: [{
            xtype:'fieldset',
            title: 'Datos Generelaes',
            collapsible: false,
            defaults: {
                anchor: '100%'
            },
            layout: 'fit',
            items :[{
                xtype: 'container',
                defaultType: 'textfield',
                msgTarget : 'side',
                layout: 'hbox',
                height: 50,
                defaults: {
                    labelWidth: 30,
                    width: 150,
                    labelAlign:'top'
                },
                items: [{
                    name: 'factura',
                    fieldLabel: 'Factura',
                    componentCls  : 'LineVerde',
                    readOnly :true
                },{
                    fieldLabel:'Referecia',
                    componentCls  : 'LineVerde',
                    name:'referencia',
                    readOnly :true
                }]
            },{
                xtype: 'container',
                defaultType: 'textfield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelWidth: 30,
                    width: 150,
                    labelAlign:'top'
                },
                items: [{
                    name: 'fchfactura',
                    fieldLabel: 'Fecha Factura',
                    readOnly :true
                },{
                    fieldLabel:'Fecha Pedido',
                    name:'fchpedido',
                    readOnly :true
                },{
                    fieldLabel:'No. Envio',
                    name:'noenvio',
                    readOnly :true
                }]
            },{
                xtype: 'container',
                defaultType: 'textfield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelWidth: 30,
                    labelAlign:'top'
                },
                items: [{
                    fieldLabel:'Cliente',
                    name:'nocliente',
                    readOnly :true,
                    width:310
                },{
                    fieldLabel:'Destino',
                    name:'destino',
                    readOnly :true,
                    width:310
                    
                }]
            },{
                xtype: 'container',
                defaultType: 'textfield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelWidth: 30,
                    width: 150,
                    labelAlign:'top'
                },
                items: [{
                    fieldLabel:'Cajas',
                    name:'nocajas',
                    readOnly :true
                },{
                    fieldLabel:'Kilos',
                    name:'kilos',
                    readOnly :true
                },{
                    fieldLabel:'Importe',
                    name:'importe',
                    readOnly :true
                }]
            },{
                xtype: 'container',
                defaultType: 'textareafield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelAlign:'top'
                },
                items: [{
                    fieldLabel:'Comentarios',
                    name:'Comentarios',
                    allowBlank: false,
                    grow      : true ,
                    width:220,
                    height:60,
                    readOnly :true
                }]
            }]
        },{
            xtype:'fieldset',
            title: 'Datos de Recibo',
            collapsible: false,
            defaults: {
                anchor: '100%'
            },
            layout: 'fit',
            items :[{
                xtype: 'container',
                defaultType: 'textfield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelWidth: 30,
                    width: 150,
                    labelAlign:'top'
                },
                items: [{
                    fieldLabel:'Folio Entrada',
                    name:'folentrada',
                    readOnly :true
                },{
                    fieldLabel:'Fecha Recibo',
                    name:'fchrecibo',
                    readOnly :true
                },{
                    fieldLabel:'Guia Empresa',
                    name:'guiaempr',
                    readOnly :true
                },{
                    fieldLabel:'Ubicación',
                    name:'ubicacion',
                    readOnly :true
                }]
            },{
                xtype: 'container',
                defaultType: 'textfield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    //labelWidth: 100,
                    labelAlign:'top'
                },
                items: [{
                    fieldLabel:'Entregar',
                    name:'entregar',
                    readOnly :true,
                    width:120
                },{
                    fieldLabel:'Fecha de Cancelación',
                    name:'fechcancel',
                    readOnly :true,
                    width:150
                }]
            }]
        },{
            xtype:'fieldset',
            title: 'Datos de Embarque',
            collapsible: false,
            defaults: {
                anchor: '100%'
            },
            layout: 'fit',
            items :[{
                xtype: 'container',
                defaultType: 'textfield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelWidth: 30,
                    labelAlign:'top'
                },
                items: [{
                    fieldLabel:'Folio Embarque',
                    name:'folembarque',
                    readOnly :true,
                    width:120
                },{
                    fieldLabel:'Fecha Embarque',
                    name:'fchembarque',
                    readOnly :true,
                    width:120
                },{
                    fieldLabel:'Chofer',
                    name:'nombchofer',
                    readOnly :true,
                    width:220
                }]
            }]
        },{
            xtype:'fieldset',
            title: 'Datos de Entrega',
            collapsible: false,
            defaults: {
                anchor: '100%'
            },
            layout: 'fit',
            items :[{
                xtype: 'container',
                defaultType: 'textfield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelWidth: 30,
                    labelAlign:'top'
                },
                items: [{
                    fieldLabel:'Fecha Entrega',
                    name:'folentrega',
                    readOnly :true,
                    width:120
                },{
                    fieldLabel:'Pzas F.O.',
                    name:'piezas',
                    readOnly :true,
                    width:120
                },{
                    fieldLabel:'A.R.',
                    name:'ar',
                    readOnly :true,
                    width:120
                },{
                    fieldLabel:'Tipo Rechazo',
                    name:'tiprechazo',
                    readOnly :true,
                    width:120
                }]
            },{
                xtype: 'container',
                defaultType: 'textfield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelWidth: 30,
                    labelAlign:'top'
                },
                items: [{
                    fieldLabel:'Tipo Rechazo',
                    name:'tiprechazo',
                    readOnly :true,
                    width:120
                },{
                    fieldLabel:'Motivo',
                    name:'motivo',
                    readOnly :true,
                    width:120
                },{
                    fieldLabel:'Accion',
                    name:'accion',
                    readOnly :true,
                    width:120
                }]
            }]
        },{
            xtype:'fieldset',
            title: 'Datos de Devolución de Documentos',
            collapsible: false,
            defaults: {
                anchor: '100%'
            },
            layout: 'fit',
            items :[{
                xtype: 'container',
                defaultType: 'textfield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelWidth: 30,
                    labelAlign:'top'
                },
                items: [{
                    fieldLabel:'Folio dev. de docs.',
                    name:'foldevdocs',
                    readOnly :true,
                    width:120
                },{
                    fieldLabel:'Fecha dev. de docs.',
                    name:'fchdevdocs',
                    readOnly :true,
                    width:120
                }]
            }]
        }],
        buttons: [{
            text: 'Salir',
            handler: function() {
                this.up('window').destroy();
            }
        }]
    });
    Ext.define('MyApp.PanelDetFactura',
    {
        extend: 'Ext.Window',
        title: 'Informacion Factura ',
        closable: true,
        closeAction: 'destroy',
        height: 500,
        width: 700,
        modal:true,
        constrain: true,
        layout: 'fit',
        resizable: true,
        autoScroll:true,
        initComponent: function() {
            this.items = [form]
            this.callParent(arguments);
        }
    });
    var win1 = Ext.create('MyApp.PanelDetFactura');
    win1.show();
    loadFormulario(form,{
        url:contexto+'/Factura',
        'idCnt':idfact,
        'bndFact':idRadio,
        bnd:1
    });
}

function getImagenProd(imagen){
    var form = Ext.widget('form', {
        // layout:'column',
        id:'idImagenProd',
        border: false,
        autoScroll:true,
        bodyPadding: 10,
        width: 680,
        // height: 800,
        // fieldDefaults: {labelWidth: 110},
        items: [{
            xtype:'fieldset',
            title: 'Producto '+imagen,
            collapsible: false,
            defaults: {
                anchor: '100%'
            },
            layout: 'fit',
            items :[{
                xtype: 'container',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelAlign:'top'
                },
                items: [{
                    xtype: 'component',
                    //html: '<img src=\"'+contexto+'/img/Argo2.jpg\" height="250" width="250">'
                  html: '<img src=\"'+contexto+'/img/'+imagen+'.jpg\" height="250" width="250">'
                }]
            }]
        }],
        buttons: [{
            text: 'Salir',
            handler: function() {
                this.up('window').destroy();
            }
        }]
    });
    Ext.define('MyApp.PanelProdImagen',
    {
        extend: 'Ext.Window',
        title: 'Imagen Producto',
        closable: true,
        closeAction: 'destroy',
        height: 400,
        width: 400,
        modal:true,
        constrain: true,
        layout: 'fit',
        resizable: true,
        autoScroll:true,
        initComponent: function() {
            this.items = [form]
            this.callParent(arguments);
        }
    });
    var win1 = Ext.create('MyApp.PanelProdImagen');
    win1.show();
//    loadFormulario(form,{
//        url:contexto+'/Factura',
//        'idCnt1':idfact,
//        bnd:10
//    });
}

function getDetListEmpaque(idfact){
    Ext.define('ModelDetMovimiento', {
        extend: 'Ext.data.Model',
        fields: ['ver','producto','descripcion',
        'almacen','cantidad'],
        idProperty: 'producto'
    });
    var storeDetMovimiento = Ext.create('Ext.data.Store', {
        id: 'storeDetMovimiento',
        model: 'ModelDetMovimiento',
        remoteSort: true,
        autoLoad :true,
        // allow the grid to interact with the paging scroller by buffering
        buffered: true,
        pageSize: 100,
        proxy: {
            // load using script tags for cross domain, if the data in on the same domain as
            // this page, an HttpProxy would be better
            type: 'jsonp',
            url: contexto+'/Factura?bnd=8&idCnt1='+idfact,
            reader: {
                root: 'records1',
                totalProperty: 'total'
            },
            // sends single sort as multi parameter
            simpleSortMode: true
        }
    });
    var form = Ext.widget('form', {
        // layout:'column',
        id:'idListEmpaque',
        border: false,
        autoScroll:true,
        bodyPadding: 10,
        width: 500,
        height: 300,
        //fieldDefaults: {labelWidth: 110},
        items: [{
            xtype:'fieldset',
            title: 'Datos del Envio',
            collapsible: false,
            defaults: {
                anchor: '100%'
            },
            layout: 'fit',
            items :[{
                xtype: 'container',
                defaultType: 'textfield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelWidth: 30,
                    //width: 150,
                    labelAlign:'top'
                },
                items: [{
                    name: 'lstRemision',
                    fieldLabel: 'Lista de Empaque',
                    width:150,
                    readOnly :true
                },{
                    fieldLabel:'Empresa',
                    name:'lsEmpresa',
                    width:200,
                    readOnly :true
                },{
                    fieldLabel:'Cliente',
                    name:'lscliente',
                    width:250,
                    readOnly :true
                }]
            },{
                xtype: 'container',
                defaultType: 'textfield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelWidth: 30,
                    labelAlign:'top'
                },
                items: [{
                    fieldLabel:'Almacen',
                    name:'lsAlmacen',
                    readOnly :true,
                    width:150
                },{
                    fieldLabel:'Tipo Movimiento',
                    name:'lsTipoMov',
                    readOnly :true,
                    width:200

                },{
                    fieldLabel:'Estatus',
                    name:'estatusEnvio',
                    readOnly :true,
                    width:200

                }]
            },{
                xtype: 'container',
                defaultType: 'textfield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelWidth: 30,
                    width: 120,
                    labelAlign:'top'
                },
                items: [{
                    fieldLabel:'Factura',
                    name:'lsfactura',
                    readOnly :true
                },{
                    fieldLabel:'Referencia',
                    name:'lsReferencia',
                    readOnly :true
                },{
                    fieldLabel:'Fecha Docto.',
                    name:'lsFechDoc',
                    readOnly :true
                },{
                    fieldLabel:'Fecha Embarque',
                    name:'lsFechEmb',
                    readOnly :true
                },{
                    fieldLabel:'Fecha Entrega',
                    name:'lsFechEnt',
                    readOnly :true
                }]
            },{
                xtype: 'container',
                defaultType: 'textfield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelWidth: 30,
                    width: 120,
                    labelAlign:'top'
                },
                items: [{
                    fieldLabel:'Fecha Envío TPF',
                    name:'lsFecEnvioTpf',
                    readOnly :true
                },{
                    fieldLabel:'Referencia',
                    name:'lsReferenciaTpf',
                    readOnly :true
                },{
                    fieldLabel:'Costo',
                    name:'lsCostoTpf',
                    readOnly :true
                },{
                    fieldLabel:'Proveedor TPF',
                    name:'lsProveedorTpf',
                    readOnly :true,
                    width : 240
                }]
            },{
                xtype: 'container',
                defaultType: 'textareafield',
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    labelAlign:'top'
                },
                items: [{
                    fieldLabel:'Comentarios',
                    name:'Comentarios',
                    allowBlank: false,
                    grow      : true ,
                    width:500,
                    height:60,
                    readOnly :true
                }]
            }]
        },Ext.create('Ext.grid.Panel', {
            id: "gridDetMovimientos",
            height: 170,
            store: storeDetMovimiento,
            selModel: {
                pruneRemoved: false
            },
            multiSelect: true,
            viewConfig: {
                trackOver: false,
                loadMask:true
            },
            columns:[{
                xtype: 'rownumberer',
                width: 30,
                sortable: false
            },{
                text: "Imagen",
                dataIndex: 'ver',
                width: 50,
                sortable: false
            },{
                text: "Clave",
                dataIndex: 'producto',
                align: 'center',
                width: 120,
                sortable: false
            },{
                text: "Descripcion",
                dataIndex: 'descripcion',
                align: 'center',
                width: 300,
                sortable: false
            },{
                text: "Almacen",
                dataIndex: 'almacen',
                align: 'center',
                width: 60,
                sortable: false
            },{
                text: "Cantidad",
                dataIndex: 'cantidad',
                align: 'center',
                width: 70,
                sortable: false
            }]
        })],
        buttons: [{
            text: 'Salir',
            handler: function() {
                this.up('window').destroy();
            }
        }]
    });
    Ext.define('MyApp.PanelListEmpaque',
    {
        extend: 'Ext.Window',
        title: 'Consulta por Envío',
        closable: true,
        closeAction: 'destroy',
        height: 550,
        width: 700,
        modal:true,
        constrain: true,
        layout: 'fit',
        resizable: true,
        autoScroll:true,
        initComponent: function() {
            this.items = [form]
            this.callParent(arguments);
        }
    });
    var win1 = Ext.create('MyApp.PanelListEmpaque');
    win1.show();
    loadFormulario(form,{
        url:contexto+'/Factura',
        'idCnt1':idfact,
        bnd:7
    });     
}

function getGridDetFact(idfact,idRadio){
    Ext.define('ModelGridFactura', {
        extend: 'Ext.data.Model',
        fields: ['elegir','numFactura','fechFactura',
        'numEnvio']
    //,idProperty: 'numFactura'
    });
    var storeGridFactura = Ext.create('Ext.data.JsonStore', {
        id: 'storeGridFactura',
        model: 'ModelGridFactura',
        autoLoad: true,
        proxy: {
            type: 'ajax',
            url: contexto+'/Factura?bnd=4&idFact='+idfact+'&bndFact='+idRadio,
            reader: {
                type: 'json',
                root: 'records'
            }
        }
    });
    //  storeGridFactura.load();
    var formFacturas = Ext.widget('form', {
        id:'idFormFacturas',
        border: false,
        //autoScroll:true,
        bodyPadding: 10,
        // width: 680,
        items: [Ext.create('Ext.grid.Panel', {
            id: "gridFacturas",
            height: 200,
            //  title: 'Facturas Encontradas',
            store: storeGridFactura,
            loadMask: true,
            selModel: {
                pruneRemoved: false
            },
            multiSelect: true,
            viewConfig: {
                trackOver: false
            },
            columns:[{
                xtype: 'rownumberer',
                width: 30,
                sortable: false
            },{                
                text: "elegir",
                dataIndex: 'elegir',
                width: 50,
                sortable: false
            },{
                id:'numFactura',
                text: "Clave de Envío",
                dataIndex: 'numFactura',
                align: 'center',
                width: 130,
                sortable: true
            },{
                text: "Fecha de Envío",
                dataIndex: 'fechFactura',
                align: 'center',
                width: 120,
                sortable: true
            },{
                text: "No. Envío",
                dataIndex: 'numEnvio',
                align: 'center',
                width: 120,
                sortable: true
            }]
        })]
    });
    Ext.define('MyApp.PanelGridFactura',
    {
        extend: 'Ext.Window',
        title: 'Envíos encontrados',
        closable: true,
        closeAction: 'destroy',
        height: 250,
        width: 500,
        modal:true,
        constrain: true,
        layout: 'fit',
        resizable: false,
        //autoScroll:true,
        initComponent: function() {
            this.items = [formFacturas]
            this.callParent(arguments);
        }
    });
    var wingf = Ext.create('MyApp.PanelGridFactura');
    wingf.show();
}
