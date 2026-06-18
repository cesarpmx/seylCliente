/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * author: m@rco.@ndrade
 */
function getModuloFacturas(){
    
    Ext.define('ModelFacturasEst', {
        extend: 'Ext.data.Model',
        fields: ['consultar','estatus','factura',
        'referencia','envio','numCliente','nombCliente',
        'destino','numCajas','importe','fechFactura',
        'fechIngreso','cita','fechEmbarq','fechEntreg','comentarios'],
        idProperty: 'factura'       
    });

    // create the Data Store
    var Facturas = Ext.create('Ext.data.Store', {
        id: 'store',
        model: 'ModelFacturasEst',
        remoteSort: true,
        // allow the grid to interact with the paging scroller by buffering
        buffered: true,
        pageSize: 200,
        proxy: {
            // load using script tags for cross domain, if the data in on the same domain as
            // this page, an HttpProxy would be better
            type: 'jsonp',
            url: contexto+'/Factura?bnd=2',
            reader: {
                root: 'records',
                totalProperty: 'total'
            },
            // sends single sort as multi parameter
            simpleSortMode: true
        },
        //groupField: 'estatus','referencia',
        sorters: [{
            property: 'factura',
            direction: 'DESC'
        }],
        listeners:{
            datachanged:function(){
                var grdBusFact = Ext.getCmp('gridBuscadorFactura');
                var myMask1=grdBusFact.setLoading("Cargando...",true);
                setTimeout(function(){
                    myMask1.destroy();
                },1000);
            }
        }
    });

    function clickBtnBusqEstatus(){
        var date1="dd/mm/yyyy";
        var date2="dd/mm/yyyy";
        var estatus="";
        var IdStatus="";
        var Porteo="";
        var idPorteo="";
        var param;
      
            if(Ext.getCmp('idFechaIni').getRawValue()!='')date1=Ext.getCmp('idFechaIni').getRawValue();//.format('d/m/Y');
            if(Ext.getCmp('idFechaFin').getRawValue()!='')date2=Ext.getCmp('idFechaFin').getRawValue();//.format('d/m/Y');
            if(Ext.getCmp('idCmbEstatus').getRawValue()!='') estatus=Ext.getCmp('idCmbEstatus').getRawValue();
            IdStatus=Ext.getCmp('idCmbEstatus').getValue();
            if(Ext.getCmp('idCmbPorteo').getRawValue()!='') Porteo=Ext.getCmp('idCmbPorteo').getRawValue();
            idPorteo=Ext.getCmp('idCmbPorteo').getValue();

                param={
                    'fechaIni':"'"+date1+"'",
                    'fechaFin':"'"+date2+"'",
                    'estatus':"'"+estatus+"'",
                    'idestatus':"'"+IdStatus+"'",                    
                    'Porteo':"'"+Porteo+"'",
                    'idPorteo':"'"+idPorteo+"'",
                    'busqBnd':'1'
                };
                BuscarFacturas1(param);           
    }
    function clickBtnExcel(){
        var date1="dd/mm/yyyy";
        var date2="dd/mm/yyyy";
        var estatus="";
        var IdStatus="";
        var Porteo="";
        var idPorteo="";
     
            if(Ext.getCmp('idFechaIni').getRawValue()!='')date1=Ext.getCmp('idFechaIni').getRawValue();
            if(Ext.getCmp('idFechaFin').getRawValue()!='')date2=Ext.getCmp('idFechaFin').getRawValue();
            if(Ext.getCmp('idCmbEstatus').getRawValue()!='') estatus=Ext.getCmp('idCmbEstatus').getRawValue();
            IdStatus=Ext.getCmp('idCmbEstatus').getValue();
            if(Ext.getCmp('idCmbPorteo').getRawValue()!='') Porteo=Ext.getCmp('idCmbPorteo').getRawValue();
            idPorteo=Ext.getCmp('idCmbPorteo').getValue();
               document.frmExportExcel.fInicio.value =date1;
               document.frmExportExcel.fFin.value =date2;
               document.frmExportExcel.status.value =estatus;
               document.frmExportExcel.idStatus.value =IdStatus;                
               document.frmExportExcel.idPorteo.value =idPorteo;
               document.frmExportExcel.submit();
       
    }
    function BuscarFacturas1(prm){
        var gridBuscFact = Ext.getCmp('gridBuscadorFactura');
        var store = gridBuscFact.getStore();
        store.removeAll(true);
        store.load({
            // url: contexto+'/Factura?bnd=2',
            params:prm
        });
        var myMaskBuscFact=gridBuscFact.setLoading("Cargando...",true);
        setTimeout(function(){
            myMaskBuscFact.destroy();
        },3000);
    }
    function clickBtnLimpiarParts(){
        Ext.getCmp('idFechaIni').setValue("");
        Ext.getCmp('idFechaFin').setValue("");
        Ext.getCmp('idCmbEstatus').setValue("");
        var storeGridBuscFact= Ext.getCmp('gridBuscadorFactura').getStore();
        storeGridBuscFact.load({
            url: contexto+'/Factura?bnd=6'
        });
    }
    //--------------------------------------------------------------------------funciones Existencias Prodcutos Almacen------------------------------------------------------------------------------------------------//
    
    Ext.define('ModelExistenciasProd', {
        extend: 'Ext.data.Model',
        fields: ['kardex','imagen','idProducto','clvProducto','decripcion',
        'linea','familia','uniAlmacenado','existReal',
        'existReservada','existDisponible','almacen','prdcosto', 'mover']
        // idProperty: 'clvProducto'
    });

    // create the Data Store
    var storeExistProd = Ext.create('Ext.data.Store', {
        id: 'storeExistProd',
        model: 'ModelExistenciasProd',
        remoteSort: true,
        // leadingBufferZone: 300,
        // allow the grid to interact with the paging scroller by buffering
        buffered: true,
        pageSize: 300,
        proxy: {
            // load using script tags for cross domain, if the data in on the same domain as
            // this page, an HttpProxy would be better
            type: 'jsonp',
            url: contexto+'/Factura?bnd=3',
            reader: {
                root: 'records',
                totalProperty: 'total'
            },
            // sends single sort as multi parameter
            simpleSortMode: true
        },
        sorters: [{
            property: 'decripcion'
            // direction: 'DESC'
        }],
        listeners:{
            datachanged:function(){
                var grdBusExis = Ext.getCmp('gridBuscadorExistencias');
                var myMask=grdBusExis.setLoading("Cargando...",true);
                setTimeout(function(){
                    myMask.destroy();
                },3000);
            }        
        }
    });
    function BtnBusqExistncs(){
        var idProducts="";
        var idalmacen="";
        var idLine="";
        var Line="";
        var Famili="";
        var idFamili="";
        var tipoProd="";
        var idTipoProd="";
        var sinExist="";
        var param;

            if(Ext.getCmp('idCvProducto').getValue()!='')idProducts=Ext.getCmp('idCvProducto').getValue();
            if(Ext.getCmp('idCmbAlmacen').getValue()!='')idalmacen=Ext.getCmp('idCmbAlmacen').getValue();
            if(Ext.getCmp('idCmbFamili').getRawValue()!='')Famili=Ext.getCmp('idCmbFamili').getRawValue();
            idFamili=Ext.getCmp('idCmbFamili').getValue();
            if(Ext.getCmp('idCmbLine').getRawValue()!='')Line=Ext.getCmp('idCmbLine').getRawValue();
            idLine=Ext.getCmp('idCmbLine').getValue();
            if(Ext.getCmp('sinExist').getValue()!='')sinExist=Ext.getCmp('sinExist').getValue();
            
            param={
                'idProducts':"'"+idProducts+"'",
                'idAlmacen':"'"+idalmacen+"'",
                'idLine':"'"+idLine+"'",
                'Line':"'"+Line+"'",
                'Famili':"'"+Famili+"'",
                'idFamili':"'"+idFamili+"'",
                'tipoProd':"'"+tipoProd+"'",
                'idTipoProd':"'"+idTipoProd+"'",
                'sinExist':""+sinExist+"'",
                'busqBnd':1
            };
            BuscarExistencias(param);
       
    }
    function BuscarExistencias(prm){
        var grd = Ext.getCmp('gridBuscadorExistencias');
        var store = grd.getStore();
        store.removeAll(true);
        store.load({
            params:prm
        });
        var myMaskgrd=grd.setLoading("Cargando...",true);
        setTimeout(function(){
            myMaskgrd.destroy();
        },10000);
    }
    function BtnLimpBuscExist(){
        Ext.getCmp('idCvProducto').setValue("");
        Ext.getCmp('idCmbAlmacen').setValue("");
        Ext.getCmp('idCmbFamili').setValue("");
        Ext.getCmp('idCmbLine').setValue("");
        Ext.getCmp('idCmbTipoProd').setValue("");
        var storeGridBuscEx= Ext.getCmp('gridBuscadorExistencias').getStore();
        storeGridBuscEx.load({
            url: contexto+'/Factura?bnd=6'
        });
    }
    function BtnBusqExistncsExcel(){
        var idProducts="";
        var nomProduct="";
        var idalmacen="";
        var nomAlmacen="";
        var idLine="";
        var Line="";
        var Famili="";
        var idFamili="";
        var tipoProd="";
        var idTipoProd="";
       
            if(Ext.getCmp('idCvProducto').getValue()!='')idProducts=Ext.getCmp('idCvProducto').getValue();
            nomProduct=Ext.getCmp('idCvProducto').getRawValue();
            if(Ext.getCmp('idCmbAlmacen').getValue()!='')idalmacen=Ext.getCmp('idCmbAlmacen').getValue();
            nomAlmacen=Ext.getCmp('idCmbAlmacen').getRawValue();
            if(Ext.getCmp('idCmbFamili').getRawValue()!=''){Famili=Ext.getCmp('idCmbFamili').getRawValue();
            idFamili=Ext.getCmp('idCmbFamili').getValue()};
            if(Ext.getCmp('idCmbLine').getRawValue()!='')Line=Ext.getCmp('idCmbLine').getRawValue();
            idLine=Ext.getCmp('idCmbLine').getValue();
            document.frmExpExlExist.idProducts.value =idProducts;
            document.frmExpExlExist.nomProduct.value =nomProduct;
            document.frmExpExlExist.idalmacen.value =idalmacen;
            document.frmExpExlExist.nomAlmacen.value =nomAlmacen;
            document.frmExpExlExist.idFamili.value =idFamili;
            document.frmExpExlExist.idLine.value =idLine;
            document.frmExpExlExist.submit();
        
    }
    return {
        //Panel Inicio
        start: {
            id: 'start-panel',
            deferredRender: false,
            autoScroll:true,
            activeTab: 0,     // first tab initially active
            html:'<table align=\"center\" style=\"width:100%;height:100%\">'+
            '<tr><td align=\"center\" valign=\"top\">&nbsp;</td></tr>' +
            '<tr><td align=\"center\">'+
            '</td></tr>'+
            '<tr><td align=\"center\" valign=\"top\">'+
            '</td></tr>'+
            '</table>'
        },
        
        
        //Panel Consulta por Factura
        
        form: {
            xtype: 'form', // since we are not using the default 'panel' xtype, we must specify it
            id: 'idMenu156',
            labelWidth: 75,
            title: 'Consulta De Envios Por Estatus',
            bodyStyle: 'padding:15px',
            width: 700,
            labelPad: 20,
            autoScroll :true,
            items: [{
                xtype:'fieldset',
                title: 'Parametros de Busqueda',
                collapsible: false,
                defaults: {
                    anchor: '100%'
                },
                layout: 'fit',
                items :[ {
                    xtype: 'panel',
                    border: false,
                    layout: {
                        type: 'vbox',
                        align: 'stretch'
                    },
                    items: [{
                        xtype: 'container',
                        defaultType: 'datefield',
                        msgTarget : 'side',
                        height :35,
                        layout: 'hbox',
                        defaults: {
                            labelWidth: 30,
                            width: 130
                            //flex: 1
                        },
                        items: [{
                            id:'idFechaIni',
                            name: 'fechaIni',
                            fieldLabel: 'De',
                            allowBlank: false,
                            enableKeyEvents:true,
                            listeners:{
                                'keypress':
                                function(txtField,e){
                                    if(e.keyCode==13){
                                }
                                }
                            }
                        },{
                            id:'idFechaFin',
                            name: 'fechaFin',
                            fieldLabel: 'Al',
                            allowBlank: false,
                            enableKeyEvents:true,
                            listeners:{
                                'keypress':
                                function(txtField,e){
                                    if(e.keyCode==13){
                                }
                                }
                            }
                        }]
                        /*
                        items: [{
                            xtype: 'combobox',
                            id:"idcmbCicl",
                            name:"cmbCicl",
                            fieldLabel: 'No Ciclo',
                            flex: 1,
                            width: 300,
                            store: createStore('idTCicl','tipCicl',6,7),
                            valueField: 'idTCicl',
                            displayField: 'tipCicl',
                            queryMode: 'local',
                            emptyText: 'Seleccione Ciclo',
                            // typeAhead: true,
                            allowBlank: true
                        }]*/
                    },{
                        xtype: 'container',
                        msgTarget : 'side',
                        height :35,
                        defaults: {
                            labelWidth: 70,
                            flex: 2
                        },
                        items: [{
                            xtype: 'combobox',
                            id:"idCmbEstatus",
                            name:"cmbEstatus",
                            fieldLabel: 'Estatus',
                            flex: 1,
                            width: 300,
                            // store: createStore('idEstatus','tipEstatus',5,7),
                            store: createStore('idEstatus','tipEstatus',6,11),
                            valueField: 'idEstatus',
                            displayField: 'tipEstatus',
                            queryMode: 'local',
                            emptyText: 'Seleccione Estatus',
                            typeAhead: true,
                            allowBlank: true
                        }]
                    },{
                        xtype: 'container',
                        msgTarget : 'side',
                        height :35,
                        defaults: {
                            labelWidth: 70,
                            flex: 1
                        },
                        items: [{
                            xtype: 'combobox',
                            id:"idCmbPorteo",
                            name:"cmbPorteo",
                            fieldLabel: 'Almacen',
                            flex: 1,
                            width: 300,
                            //store: createStore('idTPorteo','tipTPorteo',6,5),
                            store: createStore('idTPorteo','tipTPorteo',6,10),
                            valueField: 'idTPorteo',
                            displayField: 'tipTPorteo',
                            queryMode: 'local',
                            emptyText: 'Seleccione Almacen',
                            typeAhead: true,
                            allowBlank: true
                        }]
                    }]
                }]
            },{
                xtype:"panel",
                msgTarget : 'side',
                border:false,
                height :35,
                layout: {
                    type: 'vbox',
                    align: 'center'
                },
                items:[{
                    xtype: 'container',
                    items: [{
                        xtype: 'button',
                        text : 'Regresar',
                        iconCls:'icn-back',
                        width: 90,
                        handler:function(){
                            habilitar();
                        }
                    },{
                        xtype:'label',
                        html:'&nbsp;&nbsp;&nbsp;'
                    },{
                        xtype: 'button',
                        text : 'Buscar',
                        arrowAlign :'center',
                        iconCls:'icn-busquedaDos',
                        handler:clickBtnBusqEstatus,
                        width: 90
                    },{
                        xtype:'label',
                        html:'&nbsp;&nbsp;&nbsp;'
                    },{
                        xtype: 'button',
                        text : 'Exportar a Excel',
                        arrowAlign :'center',
                        iconCls:'icn-excel',
                        handler:clickBtnExcel,
                        width: 115
                    },{
                        xtype:'label',
                        html:'&nbsp;&nbsp;&nbsp;'
                    },{
                        xtype: 'button',
                        text : 'Limpiar',
                        arrowAlign :'center',
                        iconCls:'icn-limpiarBusqueda',
                        handler:clickBtnLimpiarParts,
                        width: 90
                    }]
                }]
            },
            Ext.create('Ext.grid.Panel', {
                id: "gridBuscadorFactura",
                height: 350,
                title: ' Envíos Encontrados',
                store: Ext.data.StoreManager.lookup('store'),
                clearRemovedOnLoad:false,
                
                selModel: {
                    pruneRemoved: false
                },
                multiSelect: true,
                viewConfig: {
                    loadMask: false,
                    trackOver: false
                },
                features: [{
                    ftype: 'grouping',
                    groupByText :'Agrupar',
                    showGroupsText:'ver en grupos'
                }/*,{
                    ftype: 'filters',
                    encode: true,
                    filters: [{
                        type: 'string',
                        dataIndex: 'nombCliente'
                    }]
                }*/],
                // grid columns
                columns:[{
                    xtype: 'rownumberer',
                    width: 30,
                    sortable: false
                },{
                    id:'consultar',
                    text: "Ver Detalle",
                    dataIndex: 'consultar',
                    width: 70,
                    sortable: false,
                    groupable : false
                },{
                    text: "Estatus",
                    dataIndex: 'estatus',
                    align: 'center',
                    width: 85,
                    sortable: true
                },{
                    text: "Factura",
                    dataIndex: 'factura',
                    align: 'center',
                    width: 70,
                    sortable: true
                },{
                    text: "Referencia",
                    dataIndex: 'referencia',
                    align: 'center',
                    width: 70,
                    sortable: true
                },{
                    text: "No Envio",
                    dataIndex: 'envio',
                    align: 'center',
                    width: 60,
                    sortable: true
                },{
                    text: "No Cliente",
                    dataIndex: 'numCliente',
                    align: 'center',
                    width: 70,
                    sortable: true
                },{
                    text: "Cliente",
                    dataIndex: 'nombCliente',
                    // align: 'center',
                    width: 200,
                    sortable: true,
                    // filterable: true,
                    // filter: {type:'string'}
                },{
                    text: "Destino",
                    dataIndex: 'destino',
                    //   align: 'center',
                    width: 100,
                    sortable: true
                },{
                    text: "No Cajas",
                    dataIndex: 'numCajas',
                    align: 'center',
                    width: 60,
                    sortable: true
                },{
                    text: "Importe",
                    dataIndex: 'importe',
                    align: 'right',
                    width: 85,
                    sortable: true
                },{
                    text: "Fecha Factura",
                    dataIndex: 'fechFactura',
                    align: 'center',
                    width: 90,
                    sortable: true
                },{
                    text: "Fecha Ingreso",
                    dataIndex: 'fechIngreso',
                    align: 'center',
                    width: 90,
                    sortable: true
                },{
                    text: "Fecha Cita",
                    dataIndex: 'cita',
                    align: 'center',
                    width: 100,
                    sortable: true
                },{
                    text: "Fecha Embarque",
                    dataIndex: 'fechEmbarq',
                    align: 'center',
                    width: 90,
                    sortable: true
                },{
                    text: "Fecha Entrega",
                    dataIndex: 'fechEntreg',
                    align: 'center',
                    width: 90,
                    sortable: true
                },{
                    text: "Comentarios",
                    dataIndex: 'comentarios',
                    width: 250,
                    sortable: true,
                    groupable : false
                }]
            })
            ]
        },
        
        //panel Consulta de existencia de productos
        formPnlConEst: {
            xtype: 'form', // since we are not using the default 'panel' xtype, we must specify it
            id: 'idMenu157',
            labelWidth: 75,
            title: 'Consulta de Existencias',
            bodyStyle: 'padding:15px',
            width: 350,
            labelPad: 20,
            autoScroll :true,
            items: [{
                xtype:'fieldset',
                title: 'Parametros de Busqueda',
                collapsible: false,
                defaults: {
                    anchor: '100%'
                },
                layout: 'fit',
                items :[ {
                    xtype: 'panel',
                    msgTarget: 'side',
                    border: false,
                    layout: {
                        type: 'vbox',
                        align: 'stretch'
                        // align: 'left'
                    },
                    items: [{
                        xtype: 'container',
                        defaultType: 'textfield',
                        msgTarget : 'side',
                        height :35,
                        layout: 'hbox',
                        defaults: {
                            labelWidth: 70,
                            width: 200
                        },
                        items: [{
                            id:'idCvProducto',
                            name: 'namProducto',
                            fieldLabel: 'Producto',
                            allowBlank: true,
                            maxWidth :250,
                            enableKeyEvents:true,
                            listeners:{
                                'keypress':
                                function(txtField,e){
                                    if(e.keyCode==13){
                                }
                                }
                            }
                        }]
                    },{
                        xtype: 'container',
                        // height: 35,
                        msgTarget : 'side', 
                        defaults: {
                             labelWidth: 70,
                             flex: 1
                        },
                        items: [{
                                xtype: 'combobox',
                                id:"idCmbAlmacen",
                                name:"cmbAlmacen",
                                fieldLabel: 'Almacén',
                                flex: 1,
                                width: 250,
                                store: createStore('idTAlmacen','tipAlmacen',6,9),
                                valueField: 'idTAlmacen',
                                displayField: 'tipAlmacen',
                                queryMode: 'local',
                                emptyText: 'Seleccione Almacen',
                                typeAhead: true,
                                allowBlank: true,
                                listeners: {
                                    scope: this,
                                    select: function (){
                                        var dirAlmacen = Ext.getCmp('ideDirecAlmacen');
                                        dirAlmacen.enable();
                                        dirAlmacen.reset();
                                        dirAlmacen.store.removeAll();
                                        dirAlmacen.lastQuery = null;
                                        dirAlmacen.bindStore(createStoreLinea('idDirecAlmacen','DirecAlmacen',43,10,Ext.getCmp('idCmbAlmacen').getValue()));
                                        dirAlmacen.setValue(Ext.getCmp('idCmbAlmacen').getValue());
                                    }
                                }
                            },{
                                xtype: 'combobox',
                                id:"ideDirecAlmacen",
                                name:"cmbDirecAlmacen",
                                fieldLabel: 'Dirección',
                                flex: 1,
                                width: 700,
                                store: createStoreLinea('idDirecAlmacen','DirecAlmacen',43,10,null),
                                valueField: 'idDirecAlmacen',
                                displayField: 'DirecAlmacen',
                                queryMode: 'local',
                                emptyText: 'Direccion del almacen',
                                // typeAhead: true,
                                readOnly: true,
                                allowBlank: true     
                        }]
                    },{
                        xtype: 'container',
                        msgTarget : 'side',
                        height :35,
                        defaults: {
                            labelWidth: 70,
                            flex: 1
                        },
                        items: [{
                            xtype: 'combobox',
                            id:"idCmbFamili",
                            name:"cmbFamili",
                            fieldLabel: 'Linea',
                            flex: 1,
                            width: 250,
                            store: createStore('idFamili','tipFamili',6,1),
                            valueField: 'idFamili',
                            displayField: 'tipFamili',
                            queryMode: 'local',
                            emptyText: 'Linea de producto',
                            typeAhead: true,
                            allowBlank: true,
                            listeners: {
                                scope: this,
                                select: function (){
                                        var cmbLinea = Ext.getCmp('idCmbLine');
                                        cmbLinea.enable();
                                        cmbLinea.reset();
                                        cmbLinea.store.removeAll();
                                        cmbLinea.lastQuery = null;
                                        cmbLinea.bindStore(createStoreLinea("idLine","tipLine",42,2,Ext.getCmp('idCmbFamili').getValue()));
                                    }
                            }
                        }]
                    },{
                        xtype: 'container',
                        msgTarget : 'side',
                        height :35,
                        defaults: {
                            labelWidth: 70,
                            flex: 1
                        },
                        items: [{
                            xtype: 'combobox',
                            id:"idCmbLine",
                            name:"cmbLine",
                            fieldLabel: 'Familia',
                            flex: 1,
                            width: 250,
                            store: createStoreLinea('idLine','tipLine',42,2,"null"),
                            valueField: 'idLine',
                            displayField: 'tipLine',
                            queryMode: 'local',
                            emptyText: 'Familia de producto',
                            typeAhead: true,
                            allowBlank: true
                        }]
                    },{
                        xtype: 'container',
                        msgTarget : 'side',
                        height :35,
                        defaults: {
                            labelWidth: 70,
                            flex: 1
                        },
                         items: [{
                           xtype: 'checkboxgroup',
                            fieldLabel: 'Consultar',
                            // arrange Checkboxes into 3 columns
                            // columns: 2,
                            allowBlank: true,
                            itemId: 'chkboxexistencias',
                            items: [
                            {
                                xtype: 'checkbox',
                                checked: false,
                                labelWidth: 200,
                                boxLabel: 'Sin existencias',
                                name: 'sinExist',
                                id: 'sinExist',
                                inputValue: '1'
                            }]
                        }]
                        
                    }]
                }]
            },{
                xtype:"panel",
                msgTarget : 'side',
                border:false,
                height :35,
                layout: {
                    type: 'vbox',
                    align: 'center'
                },
                items:[{
                    xtype: 'container',
                    items: [{
                        xtype: 'button',
                        text : 'Regresar',
                        iconCls:'icn-back',
                        width: 90,
                        handler:function(){
                            habilitar();
                        }
                    },{
                        xtype:'label',
                        html:'&nbsp;&nbsp;&nbsp;'
                    },{
                        xtype: 'button',
                        text : 'Buscar',
                        arrowAlign :'center',
                        iconCls:'icn-busquedaDos',
                        handler:BtnBusqExistncs,
                        width: 90
                    },{
                        xtype:'label',
                        html:'&nbsp;&nbsp;&nbsp;'
                    },{
                        xtype: 'button',
                        text : 'Exportar a Excel',
                        arrowAlign :'center',
                        iconCls:'icn-excel',
                        handler:BtnBusqExistncsExcel,
                        width: 115
                    },{
                        xtype:'label',
                        html:'&nbsp;&nbsp;&nbsp;'
                    },{
                        xtype: 'button',
                        text : 'Limpiar',
                        arrowAlign :'center',
                        iconCls:'icn-limpiarBusqueda',
                        handler:BtnLimpBuscExist,
                        width: 90
                    }]
                }]
            },Ext.create('Ext.grid.Panel', {
                id: "gridBuscadorExistencias",
                height: 350,
                title: 'Productos Encontrados',
                store: storeExistProd,
                selModel: {
                    pruneRemoved: false
                },
                multiSelect: true,
                viewConfig: {
                    loadMask: false,
                    trackOver: false                  
                },               
                // grid columns
                columns:[{
                    xtype: 'rownumberer',
                    width: 50,
                    sortable: false
                },{
                    text: "Kardex",
                    dataIndex: 'kardex',
                    width: 70,
                    sortable: false
                },{
                    text: "Imagen",
                    dataIndex: 'imagen',
                    width: 70,
                    sortable: false
                },{
                    text: "Id Prod",
                    dataIndex: 'idProducto',
                    align: 'center',
                    width: 0,
                    sortable: false
                },{
                    text: "Clave",
                    dataIndex: 'clvProducto',
                    align: 'center',
                    width: 90,
                    sortable: true
                },{
                    text: "Decripción",
                    dataIndex: 'decripcion',
                    width: 250,
                    sortable: true
                },{
                    text: "Linea",
                    dataIndex: 'familia',
                    align: 'center',
                    width: 80,
                    sortable: true
                },{
                    text: "Familia",
                    dataIndex: 'linea',
                    align: 'center',
                    width: 80,
                    sortable: true
                },{
                    text: "Unidad",
                    dataIndex: 'uniAlmacenado',
                    align: 'center',
                    width: 105,
                    sortable: true
                },{
                    text: "Existencia Real",
                    dataIndex: 'existReal',
                    align: 'center',
                    width: 95,
                    sortable: true
                },{
                    text: "Existencia Reservada",
                    dataIndex: 'existReservada',
                    align: 'center',
                    width: 115,
                    sortable: true
                },{
                    text: "Existencia Disponible",
                    dataIndex: 'existDisponible',
                    align: 'center',
                    width: 115,
                    sortable: true
                },{
                    text: "Almacen",
                    dataIndex: 'almacen',
                    width: 150,
                    sortable: true
                },{
                    text: "Precio",
                    dataIndex: 'prdcosto',
                    width: 0,
                    sortable: true
                },{
                    text: "Mover",
                    dataIndex: 'mover',
                    width: 70,
                    sortable: false
                }]
            })]
        }
    };
}