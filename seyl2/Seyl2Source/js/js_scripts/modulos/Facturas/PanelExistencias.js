/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * author: m@rco.@ndrade
 */
//idProducto,idalmacen,descripcionProdcuto

function getDetLotes(idProducto, clvProducto, existdispo, descProducto) {
    // 1. Obtenemos el almacén seleccionado
    var cmbAlmacen = Ext.getCmp('idCmbAlmacen');
    var idAlmacen = cmbAlmacen ? cmbAlmacen.getValue() : '';
    var nomAlmacen = cmbAlmacen ? cmbAlmacen.getRawValue() : '';

    // 2. Definimos el Modelo solo si no existe previamente
    if (!Ext.ClassManager.get('ModelLotes')) {
        Ext.define('ModelLotes', {
            extend: 'Ext.data.Model',
            fields: ['racid', 'racprdid', 'raclote', 'racpedimento', 'racfeccaducidad', 'racreal', 'racreservado', 'disponible']
        });
    }

    // 3. Store con Proxy apuntando al backend (Store anónimo)
    var storeLotes = Ext.create('Ext.data.Store', {
        model: 'ModelLotes',
        buffered: true,
        pageSize: 200,
        proxy: {
            type: 'jsonp',
            url: contexto + '/FacturaMovimiento?bnd=8',
            reader: {
                root: 'recordsLotes',
                totalProperty: 'total'
            },
            simpleSortMode: true,
            listeners: {
                datachanged: function () {
                    var grdBusFact = Ext.getCmp('gridLotes');
                    if (grdBusFact) {
                        var myMask1 = grdBusFact.setLoading("Cargando...", true);
                        setTimeout(function () {
                            if (myMask1)
                                myMask1.destroy();
                        }, 1000);
                    }
                }
            }
        }
    });

    // Evento para calcular automáticamente el "Total Disponible" de los registros en pantalla
    storeLotes.on('load', function (store) {
        var total = 0;
        store.each(function (record) {
            total += parseFloat(record.get('racreal') || 0);
        });

        var txtTotal = Ext.getCmp('idTotalDisponible');
        if (txtTotal) {
            txtTotal.setValue(total);
        }
    });

    // 4. Ventana Modal
    var winLotes = Ext.create('Ext.window.Window', {
        title: 'Consulta Existencia por Lote',
        closable: true,
        closeAction: 'destroy',
        height: 480,
        width: 895,
        modal: true,
        constrain: true,
        layout: 'fit',
        resizable: true,
        items: [{
                xtype: 'form',
                id: 'idFormLotes',
                border: false,
                autoScroll: true,
                bodyPadding: 10,
                items: [{
                        xtype: 'fieldset',
                        title: 'Datos de la Consulta',
                        collapsible: true,
                        defaults: {anchor: '100%'},
                        items: [{
                                xtype: 'container',
                                layout: 'hbox',
                                defaults: {margin: '0 20 0 0'},
                                items: [{
                                        xtype: 'container',
                                        layout: 'anchor',
                                        width: 380,
                                        defaults: {xtype: 'textfield', readOnly: true, labelWidth: 80, anchor: '100%'},
                                        items: [{
                                                fieldLabel: 'Almacen',
                                                name: 'nomAlmacen',
                                                value: nomAlmacen
                                            }, {
                                                fieldLabel: 'Producto',
                                                name: 'nomProducto',
                                                value: clvProducto + (descProducto ? ' - ' + descProducto : '')
                                            }]
                                    }, {
                                        xtype: 'container',
                                        layout: 'anchor',
                                        width: 240,
                                        defaults: {xtype: 'textfield', readOnly: true, labelWidth: 110, anchor: '100%'},
                                        items: [{
                                                fieldLabel: 'Total Disponible',
                                                name: 'totalDisponible',
                                                id: 'idTotalDisponible',
                                                value: existdispo
                                            }]
                                    }]
                            }]
                    }, {
                        xtype: 'component',
                        html: '<h3 style="color: #6b8e23; margin: 15px 0 5px 0; font-size: 14px; font-weight: bold;">Lote Por Productos</h3>'
                    }, {
                        xtype: 'grid',
                        id: 'gridLotes',
                        height: 290,
                        store: storeLotes,
                        tbar: [{
                                text: 'Buscar',
                                iconCls: 'icn-busquedaDos',
                                handler: function () {
                                    fnCargarLotes();
                                }
                            }, {
                                text: 'Regresar',
                                iconCls: 'icn-back',
                                handler: function (btn) {
                                    btn.up('window').destroy();
                                }
                            }],
                        columns: [{
                                xtype: 'rownumberer',
                                width: 35,
                                text: '#'
                            }, {
                                text: 'Id Pallet',
                                dataIndex: 'racid',
                                width: 130,
                                align: 'center'
                            }, {
                                text: 'No.Lote',
                                dataIndex: 'raclote',
                                width: 120,
                                align: 'center'
                            }, {
                                text: 'Fecha Caducidad',
                                dataIndex: 'racfeccaducidad',
                                width: 130,
                                align: 'center'
                            }, {
                                text: 'Pedimento',
                                dataIndex: 'racpedimento',
                                width: 120,
                                align: 'center'
                            }, {
                                text: 'Existencia Real',
                                dataIndex: 'racreal',
                                width: 100,
                                align: 'center'
                            }, {
                                text: 'Reservado',
                                dataIndex: 'racreservado',
                                width: 100,
                                align: 'center'
                            }, {
                                text: 'Disponible',
                                dataIndex: 'disponible',
                                width: 100,
                                align: 'center'
                            }]
                    }]
            }]
    });

    winLotes.show();

    // Función interna para solicitar los datos al servidor
    function fnCargarLotes() {
        var grid = Ext.getCmp('gridLotes');
        var mask = grid ? grid.setLoading("Cargando lotes...", true) : null;

        // Persistir los filtros en el Proxy para que se envíen en cada scroll automático
        storeLotes.getProxy().extraParams = {
            'idAlmacen': idAlmacen,
            'clvProducto': idProducto
        };

        // Solicitar los primeros 200 registros
        storeLotes.load({
            callback: function () {
                if (mask) {
                    mask.destroy();
                }
            }
        });
    }

    // Carga inicial al abrir la ventana
    fnCargarLotes();
}

function getConsMovimientos(Producto, almacen, descAlmacen) {
    Ext.define('ModelConsMovim', {
        extend: 'Ext.data.Model',
        fields: ['elegir', 'fechaFact', 'remision',
            'referencia', 'concepto', 'cantidad',
            'saldoProd']//,
                // idProperty: 'remision'
    });
    var dia = "";
    var mes = "";
    var anio = "";
    var d = new Date();
    var fecha = "";
    var fecha1 = "";
    dia = d.getDate();
    mes = (d.getMonth() + 1);
    anio = d.getFullYear();
    fecha = "01/" + (mes < 10 ? "0" + mes : mes) + "/" + anio;
    fecha1 = (dia < 10 ? "0" + dia : dia) + "/" + (mes < 10 ? "0" + mes : mes) + "/" + anio;
    // fecha1="02/"+(mes<10?"0"+mes:mes)+"/"+anio;

    var storeConsMovim = Ext.create('Ext.data.Store', {
        id: 'storeConsMovim',
        model: 'ModelConsMovim',
        remoteSort: true,
        autoLoad: true,
        // allow the grid to interact with the paging scroller by buffering
        buffered: true,
        pageSize: 200,
        proxy: {
            // load using script tags for cross domain, if the data in on the same domain as
            // this page, an HttpProxy would be better
            type: 'jsonp',
            url: contexto + '/FacturaMovimiento?bnd=2',
            reader: {
                root: 'recordsMovimt',
                totalProperty: 'total'
            },
            // sends single sort as multi parameter
            simpleSortMode: true
        }
    });
    storeConsMovim.load({
        params: {
            'idAlmacen': almacen,
            'idProduct': "'" + Producto + "'",
            'FechIni': fecha,
            'FechFin': fecha1
        }
    });
    var formFacturas = Ext.widget('form', {
        id: 'idConsMovimientos',
        border: false,
        bodyPadding: 20,
        width: 600,
        items: [{
                xtype: 'panel',
                collapsible: false,
                layout: 'hbox',
                border: 0,
                items: [{
                        xtype: 'container',
                        msgTarget: 'side',
                        height: 35,
                        width: 303,
                        defaults: {
                            labelWidth: 60
                        },
                        // flex: 1,
                        items: [{
                                xtype: 'combobox',
                                id: "idTipAlmcenExis",
                                name: "TpoAlmcenExis",
                                fieldLabel: 'Almacen',
                                flex: 1,
                                width: 230,
                                store: createStore('idTipAlmcenExis', 'TpoAlmcenExis', 6, 9), //,5,6),
                                valueField: 'idTipAlmcenExis',
                                displayField: 'TpoAlmcenExis',
                                queryMode: 'local',
                                emptyText: 'Seleccione Almacen',
                                typeAhead: true,
                                allowBlank: true
                            }]
                    }, {
                        xtype: 'container',
                        defaultType: 'datefield',
                        msgTarget: 'side',
                        height: 35,
                        layout: 'hbox',
                        defaults: {
                            labelWidth: 30,
                            width: 130
                        },
                        items: [{
                                id: 'idFechaIniExis',
                                name: 'fechaIniExis',
                                fieldLabel: 'De',
                                allowBlank: false,
                                enableKeyEvents: true,
                                listeners: {
                                    'keypress':
                                            function (txtField, e) {
                                                if (e.keyCode == 13) {
                                                    getInfo();
                                                }
                                            }
                                }
                            }, {
                                id: 'idFechaFinExis',
                                name: 'fechaFinExis',
                                fieldLabel: '  Al',
                                allowBlank: false,
                                enableKeyEvents: true,
                                listeners: {
                                    'keypress':
                                            function (txtField, e) {
                                                if (e.keyCode == 13) {
                                                    getInfo();
                                                }
                                            }
                                }
                            }]
                    }]
            }, {
                xtype: 'panel',
                collapsible: false,
                layout: 'hbox',
                border: 0,
                items: [{
                        xtype: 'hiddenfield',
                        id: 'cvProductoExis',
                        name: 'cvProductoExis',
                        value: Producto
                    }, {
                        xtype: 'container',
                        defaultType: 'textfield',
                        msgTarget: 'side',
                        height: 35,
                        //layout: 'hbox',
                        defaults: {
                            labelWidth: 60,
                            width: 280
                        },
                        items: [{
                                id: 'idProductoExis',
                                name: 'productoExis',
                                fieldLabel: 'Producto',
                                allowBlank: false,
                                enableKeyEvents: true,
                                readOnly: true,
                                listeners: {
                                    'keypress':
                                            function (txtField, e) {
                                                if (e.keyCode == 13) {
                                                    getInfo();
                                                }
                                            }
                                }
                            }]
                    }, {
                        xtype: 'container',
                        items: [{
                                xtype: 'label',
                                html: '&nbsp;&nbsp;&nbsp;'
                                        /*
                                         },{
                                         xtype: 'button',
                                         text : 'Buscar Producto',
                                         arrowAlign :'center',
                                         handler:getBucProducto,
                                         iconCls:'icn-busquedaDos',
                                         width: 115
                                         */
                            }, {
                                xtype: 'label',
                                html: '&nbsp;&nbsp;&nbsp;'
                            }]
                    }, {
                        xtype: 'container',
                        defaultType: 'textfield',
                        msgTarget: 'side',
                        height: 35,
                        defaults: {
                            labelWidth: 70,
                            width: 150
                        },
                        items: [{
                                id: 'idSaldo',
                                name: 'saldo',
                                fieldLabel: 'Saldo Inicial',
                                allowBlank: false,
                                enableKeyEvents: true,
                                listeners: {
                                    'keypress':
                                            function (txtField, e) {
                                                if (e.keyCode == 13) {
                                                    getInfo();
                                                }
                                            }
                                }
                            }]
                    }, {
                        xtype: 'container',
                        items: [{
                                xtype: 'label',
                                html: '&nbsp;&nbsp;&nbsp;'
                            }, {
                                xtype: 'button',
                                text: 'Buscar Movimientos',
                                arrowAlign: 'center',
                                handler: getInfo,
                                iconCls: 'icn-movimiento',
                                width: 130
                            }]
                    }]
            }, Ext.create('Ext.grid.Panel', {
                id: "gridConsMovimientos",
                height: 250,
                store: storeConsMovim,
                selModel: {
                    pruneRemoved: false
                },
                multiSelect: true,
                viewConfig: {
                    trackOver: false,
                    loadMask: true
                },
                columns: [{
                        xtype: 'rownumberer',
                        width: 30,
                        sortable: false
                    }, {
                        text: "Remision",
                        dataIndex: 'elegir',
                        width: 0,
                        sortable: false
                    }, {
                        text: "Fecha",
                        dataIndex: 'fechaFact',
                        align: 'center',
                        width: 100,
                        sortable: false
                    }, {
                        id: 'remision',
                        text: "Remisión",
                        dataIndex: 'remision',
                        align: 'center',
                        width: 100,
                        sortable: false
                    }, {
                        text: "Referencia",
                        dataIndex: 'referencia',
                        align: 'center',
                        width: 120,
                        sortable: false
                    }, {
                        text: "Concepto",
                        dataIndex: 'concepto',
                        align: 'center',
                        width: 240,
                        sortable: false
                    }, {
                        text: "Cantidad",
                        dataIndex: 'cantidad',
                        align: 'center',
                        width: 90,
                        sortable: false
                    }, {
                        text: "Saldo",
                        dataIndex: 'saldoProd',
                        align: 'center',
                        width: 90,
                        sortable: false
                    }]
            })],
        buttons: [{
                text: 'Salir',
                handler: function () {
                    this.up('window').destroy();
                }
            }]
    });
    Ext.define('MyApp.PanelConsMovimientos',
            {
                extend: 'Ext.Window',
                title: 'Consulta de Movimientos Producto',
                closable: true,
                closeAction: 'destroy',
                height: 410,
                width: 890,
                maxWidth: 910,
                maxHeight: 420,
                modal: true,
                constrain: true,
                layout: 'fit',
                resizable: true,
                //autoScroll:true,
                initComponent: function () {
                    this.items = [formFacturas]
                    this.callParent(arguments);
                }
            });
    var winConsMov = Ext.create('MyApp.PanelConsMovimientos');
    winConsMov.show();
    var grdConsMov = Ext.getCmp('gridConsMovimientos');
    var myMaskBusF = grdConsMov.setLoading("Cargando...", true);
    setTimeout(function () {
        myMaskBusF.destroy();
    }, 5000);
    Ext.getCmp('idProductoExis').setValue(descAlmacen);
    loadFormulario(formFacturas, {
        url: contexto + '/FacturaMovimiento?bnd=1',
        'idalmacen': almacen,
        'fechIni': fecha,
        'fechFin': fecha1,
        'idProduct': "'" + Producto + "'",
        bnd: 1,
        msg: 'Cargando Informacíon...',
        timeEst: 50
    });
    function getInfo() {
        var idProduct = Ext.getCmp('cvProductoExis').getValue();
        var almacen = Ext.getCmp('idTipAlmcenExis').getValue();
        var fecha = Ext.getCmp('idFechaIniExis').getRawValue();
        var fecha1 = Ext.getCmp('idFechaFinExis').getRawValue();
        storeConsMovim.removeAll(true);
        loadFormulario(formFacturas, {
            url: contexto + '/FacturaMovimiento?bnd=1',
            'idalmacen': almacen,
            'fechIni': fecha,
            'fechFin': fecha1,
            'idProduct': "'" + idProduct + "'",
            bnd: 1
        });
        storeConsMovim.load({
            params: {
                'idAlmacen': almacen,
                'idProduct': "'" + idProduct + "'",
                'FechIni': fecha,
                'FechFin': fecha1
            }
        });
        var grdConsMov = Ext.getCmp('gridConsMovimientos');
        var myMaskBusF = grdConsMov.setLoading("Cargando...", true);
        setTimeout(function () {
            myMaskBusF.destroy();
        }, 5000);
    }
}
//Remision almacen
function getRemAlmacen(idRemision) {
    Ext.define('ModelRemAlmacen', {
        extend: 'Ext.data.Model',
        fields: ['productoRemi', 'descripcionRemi', 'loteRemi',
            'fechaRemi', 'ubicacionRemi', 'cantidadRemi']
                // idProperty: 'remision'
    });
    var storeRemAlmacen = Ext.create('Ext.data.Store', {
        id: 'idstoreRemAlmacen',
        model: 'ModelRemAlmacen',
        remoteSort: true,
        // autoLoad :true,
        // allow the grid to interact with the paging scroller by buffering
        buffered: true,
        pageSize: 200,

        proxy: {
            // load using script tags for cross domain, if the data in on the same domain as
            // this page, an HttpProxy would be better
            type: 'jsonp',
            url: contexto + '/FacturaMovimiento?bnd=5',
            reader: {
                root: 'records',
                totalProperty: 'total'
            },
            // sends single sort as multi parameter
            simpleSortMode: true
        },
        sorters: [{
                property: 'productoRemi'
                        //direction: 'DESC'
            }]
                //        ,listeners:{
                //            datachanged:function(){
                //                var grdRemAlm = Ext.getCmp('gridRemAlmacen');
                //                var myMask = new Ext.LoadMask(grdRemAlm.getEl(), {
                //                    msg:"Cargardo Espere..."
                //                });
                //                myMask.show();
                //                setTimeout(function(){
                //                    myMask.destroy();
                //                },4000);
                //            }
                //        }
    });

    storeRemAlmacen.load({
        params: {
            'idRemision': idRemision
        }
    });
    var FormRemAlmacen = Ext.widget('form', {
        id: 'idFormRemAlmacen',
        border: false,
        bodyPadding: 20,
        width: 550,
        //  height :650,
        items: [{
                xtype: 'panel',
                collapsible: false,
                layout: 'hbox',
                border: 0,
                height: 35,
                items: [{
                        xtype: 'container',
                        defaultType: 'textfield',
                        msgTarget: 'side',
                        //  height :35,
                        layout: 'hbox',
                        defaults: {
                            //  labelWidth: 85,
                            labelAlign: 'right'
                        },
                        items: [{
                                id: 'idremRemision',
                                name: 'remRemision',
                                fieldLabel: 'Remision',
                                readOnly: true,
                                enableKeyEvents: true,
                                labelWidth: 85,
                                width: 200,
                                listeners: {
                                    'keypress':
                                            function (txtField, e) {
                                                if (e.keyCode == 13) {

                                                }
                                            }
                                }
                            }, {
                                id: 'idRemFecha',
                                name: 'RemFecha',
                                fieldLabel: 'Fecha',
                                readOnly: true,
                                enableKeyEvents: true,
                                labelWidth: 100,
                                width: 200,
                                listeners: {
                                    'keypress':
                                            function (txtField, e) {
                                                if (e.keyCode == 13) {

                                                }
                                            }
                                }
                            }, {
                                id: 'idRemFolio',
                                name: 'RemFolio',
                                fieldLabel: 'Folio',
                                readOnly: true,
                                enableKeyEvents: true,
                                labelWidth: 85,
                                width: 200,
                                listeners: {
                                    'keypress':
                                            function (txtField, e) {
                                                if (e.keyCode == 13) {

                                                }
                                            }
                                }
                            }]
                    }]
            }, {
                xtype: 'panel',
                collapsible: false,
                layout: 'hbox',
                border: 0,
                height: 35,
                items: [{
                        xtype: 'container',
                        defaultType: 'textfield',
                        msgTarget: 'side',
                        //   height :35,
                        layout: 'hbox',
                        defaults: {
                            // labelWidth: 85,
                            labelAlign: 'right'
                        },
                        items: [{
                                id: 'idRemEmpresa',
                                name: 'RemEmpresa',
                                fieldLabel: 'Empresa',
                                readOnly: true,
                                enableKeyEvents: true,
                                labelWidth: 85,
                                width: 200,
                                listeners: {
                                    'keypress':
                                            function (txtField, e) {
                                                if (e.keyCode == 13) {
                                                    // getInfo();
                                                }
                                            }
                                }
                            }, {
                                id: 'idRemReferencia',
                                name: 'RemReferencia',
                                fieldLabel: 'Referencia',
                                readOnly: true,
                                enableKeyEvents: true,
                                width: 200,
                                labelWidth: 100,
                                listeners: {
                                    'keypress':
                                            function (txtField, e) {
                                                if (e.keyCode == 13) {
                                                    // getInfo();
                                                }
                                            }
                                }
                            }]
                    }]
            }, {
                xtype: 'panel',
                collapsible: false,
                layout: 'hbox',
                border: 0,
                height: 35,
                items: [{
                        xtype: 'container',
                        defaultType: 'textfield',
                        msgTarget: 'side',
                        //   height :35,
                        layout: 'hbox',
                        defaults: {
                            labelAlign: 'right'
                        },
                        items: [{
                                id: 'idRemAlmacen',
                                name: 'RemAlmacen',
                                fieldLabel: 'Almacen',
                                readOnly: true,
                                enableKeyEvents: true,
                                width: 200,
                                labelWidth: 85,
                                listeners: {
                                    'keypress':
                                            function (txtField, e) {
                                                if (e.keyCode == 13) {
                                                    // getInfo();
                                                }
                                            }
                                }
                            }, {
                                id: 'idremTipoMov',
                                name: 'remTipoMov',
                                fieldLabel: 'Tipo Movimiento',
                                readOnly: true,
                                enableKeyEvents: true,
                                width: 300,
                                labelWidth: 100,
                                listeners: {
                                    'keypress':
                                            function (txtField, e) {
                                                if (e.keyCode == 13) {
                                                    // getInfo();
                                                }
                                            }
                                }
                            }]
                    }
                ]
            }, {
                xtype: 'panel',
                collapsible: false,
                layout: 'hbox',
                border: 0,
                height: 35,
                items: [
                    {
                        xtype: 'container',
                        defaultType: 'textfield',
                        msgTarget: 'side',
                        // height :35,
                        layout: 'hbox',
                        defaults: {
                            labelWidth: 85,
                            labelAlign: 'right'
                        },
                        items: [{
                                id: 'idRemComentarios',
                                name: 'RemComentarios',
                                fieldLabel: 'Comentarios',
                                readOnly: true,
                                enableKeyEvents: true,
                                width: 500,
                                listeners: {
                                    'keypress':
                                            function (txtField, e) {
                                                if (e.keyCode == 13) {
                                                    // getInfo();
                                                }
                                            }
                                }
                            }]
                    }]
            },
            Ext.create('Ext.grid.Panel', {
                id: "gridRemAlmacen",
                height: 250,
                //  title: 'Facturas Encontradas',
                store: storeRemAlmacen,
                loadMask: true,
                selModel: {
                    pruneRemoved: false
                },
                multiSelect: true,
                viewConfig: {
                    trackOver: false
                },
                columns: [{
                        xtype: 'rownumberer',
                        width: 30,
                        sortable: false
                    }, {
                        text: "Producto",
                        dataIndex: 'productoRemi',
                        width: 100,
                        sortable: false
                    }, {
                        text: "Descripción",
                        dataIndex: 'descripcionRemi',
                        width: 140,
                        sortable: false
                    }, {
                        text: "Lote",
                        dataIndex: 'loteRemi',
                        align: 'center',
                        width: 90,
                        sortable: false
                    }, {
                        text: "Fecha",
                        dataIndex: 'fechaRemi',
                        align: 'center',
                        width: 90,
                        sortable: false
                    }, {
                        text: "Ubicacion",
                        dataIndex: 'ubicacionRemi',
                        align: 'center',
                        width: 70,
                        sortable: false
                    }, {
                        text: "Cantidad",
                        dataIndex: 'cantidadRemi',
                        align: 'center',
                        width: 60,
                        sortable: false
                    }]
            })],
        buttons: [{
                text: 'Salir',
                handler: function () {
                    this.up('window').destroy();
                }
            }]
    });

    Ext.define('MyApp.PanelRemAlmacen',
            {
                extend: 'Ext.Window',
                title: 'Consulta Remision Almacen',
                closable: true,
                closeAction: 'destroy',
                height: 490,
                width: 850,
                maxWidth: 900,
                maxHeight: 495,
                modal: true,
                constrain: true,
                layout: 'fit',
                resizable: true,
                //autoScroll:true,
                initComponent: function () {
                    this.items = [FormRemAlmacen]
                    this.callParent(arguments);
                }
            });
    var winRemAlm = Ext.create('MyApp.PanelRemAlmacen');
    winRemAlm.show();
    var grdRemAlm = Ext.getCmp('gridRemAlmacen');
    var myMaskRemAlm = grdRemAlm.setLoading("Cargando...", true);
    setTimeout(function () {
        myMaskRemAlm.destroy();
    }, 10000);
    loadFormulario(FormRemAlmacen, {
        url: contexto + '/FacturaMovimiento?bnd=4',
        'idRemision': idRemision,
        msg: 'Cargando Informacíon...',
        timeEst: 1000
    });
}
//Mover producto
function getMoverProd(clave, descripcionPrd, almacen, disponible) {
    var formMoverProd = Ext.widget('form', {
        id: 'idformMoverProd',
        border: false,
        autoScroll: true,
        bodyPadding: 10,
        width: 680,
        items: [{
                xtype: 'fieldset',
                title: 'Solicitar Movimiento de Producto',
                collapsible: false,
                defaults: {
                    anchor: '100%'
                },
                layout: 'fit',
                items: [{
                        xtype: 'container',
                        defaultType: 'textfield',
                        msgTarget: 'side',
                        layout: 'vbox',
                        defaults: {
                            labelWidth: 30,
                            width: 150,
                            labelAlign: 'top'
                        },
                        items: [{
                                id: 'idProducto',
                                fieldLabel: 'Clave',
                                name: 'claveProd',
                                readOnly: true
                            }, {
                                fieldLabel: 'Descripción',
                                width: 500,
                                name: 'descripcionProd',
                                readOnly: true
                            }, {
                                id: 'idAlmacen',
                                fieldLabel: 'Almacén',
                                name: 'almacenProd',
                                readOnly: true
                            }, {
                                id: 'idDisponible',
                                fieldLabel: 'Disponible',
                                name: 'disponibleProd',
                                readOnly: true
                            }, {
                                xtype: 'container',
                                defaults: {
                                    labelWidth: 30,
                                    width: 200
                                },
                                xtype: 'combobox',
                                id: "idAccion",
                                name: "cmbAccion",
                                fieldLabel: 'Acción',
                                width: 200,
                                store: createStore('idAccion', 'nomAccion', 6, 8),
                                valueField: 'idAccion',
                                displayField: 'nomAccion',
                                queryMode: 'local',
                                emptyText: 'Seleccione Acción',
                                allowBlank: false
                            }, {
                                id: 'idCantidad',
                                fieldLabel: 'Cantidad',
                                name: 'cantidadMov',
                                readOnly: false,
                                listeners: {
                                    scope: this,
                                    select: function () {
                                        var disponible = Ext.getCmp('idDisponible');
                                        var cantidad = Ext.getCmp('idCantidad')
                                        if (parseInt(disponible) < parseInt(cantidad)) {
                                            Ext.Msg.show({
                                                title: 'Datos Incorrectos',
                                                msg: 'La cantidad no puede ser mayor al disponible',
                                                buttons: Ext.MessageBox.OK,
                                                icon: Ext.MessageBox.WARNING
                                            });
                                        }

                                    }
                                }
                            }, {
                                id: 'idComentarios',
                                fieldLabel: 'Instrucciones',
                                width: 500,
                                name: 'comentariosMov',
                                readOnly: false
                            }]
                    }]
            }],
        buttons: [{
                text: 'Confirmar',
                handler: function () {
                    moverProd();
                }
            }, {
                text: 'Salir',
                handler: function () {
                    this.up('window').destroy();
                }
            }]
    });
    Ext.define('MyApp.PanelMover',
            {
                extend: 'Ext.Window',
                title: 'Mover Producto',
                closable: true,
                closeAction: 'destroy',
                // height: 400,
                width: 600,
                modal: true,
                constrain: true,
                layout: 'fit',
                resizable: true,
                autoScroll: true,
                initComponent: function () {
                    this.items = [formMoverProd]
                    this.callParent(arguments);
                }
            });
    var winuser = Ext.create('MyApp.PanelMover');
    winuser.show();
    loadFormulario(formMoverProd, {
        url: contexto + '/FacturaMovimiento?bnd=6',
        'clave': "'" + clave + "'",
        'descripcionPrd': "'" + descripcionPrd + "'",
        'almacen': "'" + almacen + "'",
        'disponible': disponible,
        msg: 'Cargando Informacíon...',
        timeEst: 1000
    });

    function moverProd() {
        var idProducto = Ext.getCmp('idProducto').getValue();
        var idAlmacen = Ext.getCmp('idAlmacen').getValue();
        var idCantidad = Ext.getCmp('idCantidad').getValue();
        var idAccion = Ext.getCmp('idAccion').getValue();
        var idComentarios = Ext.getCmp('idComentarios').getValue();
        var idDisponible = Ext.getCmp('idDisponible').getValue();

        if (parseInt(idDisponible) >= parseInt(idCantidad)) {
            Ext.Ajax.request({
                url: contexto + '/FacturaMovimiento',
                params: {
                    bnd: 7,
                    idProducto: idProducto,
                    idAlmacen: idAlmacen,
                    idCantidad: idCantidad,
                    idAccion: idAccion,
                    idComentarios: idComentarios
                },
                success: function (rsp) {
                    // var json = eval("("+rsp.responseText+")");
                    Ext.Msg.show({
                        title: 'Generar Movimiento',
                        msg: 'El movimiento se genero correctamente ',
                        buttons: Ext.MessageBox.OK,
                        icon: Ext.MessageBox.WARNING
                    });
                    winuser.destroy();
                },
                failure: function (rsp) {
                    Ext.Msg.show({
                        title: 'Datos Incorrectos',
                        msg: 'No se realizo el movimiento',
                        buttons: Ext.MessageBox.OK,
                        icon: Ext.MessageBox.WARNING
                    });
                }
            });
        } else {
            Ext.Msg.show({
                title: 'Datos Incorrectos',
                msg: 'La cantidad no puede ser mayor al disponible',
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.WARNING
            });
        }

    }
}


