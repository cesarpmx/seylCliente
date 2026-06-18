/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * author: m@rco.@ndrade
 */
/*
function getMoverProd(clave,descripcion,almacen,existencia){
    Ext.define('ModelMoverProd', {
        extend: 'Ext.data.Model',
        fields: ['clave','descripcion','almacen','existencia','accion',
            'cantidad','comentarios']
        //  idProperty: 'elegir'
    });
    var storeMoverProd = Ext.create('Ext.data.Store', {
        id: 'storeMoverProd',
        model: 'ModelMoverProd',
        remoteSort: true,
        autoLoad :true,
        // allow the grid to interact with the paging scroller by buffering
        buffered: true,
        pageSize: 200,
        proxy: {
            // load using script tags for cross domain, if the data in on the same domain as
            // this page, an HttpProxy would be better
            type: 'jsonp',
            url: contexto+'/servlet/MoverProductos?bnd=1',
            reader: {
                root: 'records',
                totalProperty: 'total'
            },
            // sends single sort as multi parameter
            simpleSortMode: true
        }
    });
    storeMoverProd.load();
    var formUsuarios = Ext.widget('form', {
        id:'idusuarios',
        border: false,
        bodyPadding: 20,
        width:700,
        items: [{
                xtype:'panel',
                collapsible: false,
                layout: 'hbox',
                border : 0,
                items :[{
                        xtype: 'container',
                        defaultType: 'textfield',
                        msgTarget : 'side',
                        height :35,
                        defaults: {
                            labelWidth: 70,
                            width: 300
                        },
                        items: [{
                                id:'idUsuario',
                                name: 'Usuario',
                                fieldLabel: 'Nombre Usuario',
                                allowBlank: false,
                                enableKeyEvents:true,
                                listeners:{
                                    'keypress':
                                        function(txtField,e){
                                        if(e.keyCode==13){
                                            getInfo();
                                        }
                                    },
                                    'keyup' : function(elem, e){
                                        elem.setValue(elem.getValue().toUpperCase());
                                    }
                                }
                            }]
                    },{
                        xtype: 'container',
                        items: [{
                                xtype:'label',
                                html:'&nbsp;&nbsp;&nbsp;'
                            },{
                                xtype: 'button',
                                text : 'Buscar Usuarios',
                                arrowAlign :'center',
                                handler:getInfo,
                                iconCls:'icn-movimiento',
                                width: 115
                            }]
                    }]
            },Ext.create('Ext.grid.Panel', {
                id: "gridUsuariosAux",
                height: 250,
                store: storeUsuarios,
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
                        text: "Elegir",
                        dataIndex: 'elegir',
                        width: 50,
                        sortable: false
                    },{
                        text: "Nombre Completo",
                        dataIndex: 'nombre',
                        width: 350,
                        sortable: false
                    },{
                        text: "Usuario",
                        dataIndex: 'login',
                        align: 'center',
                        width: 150,
                        sortable: false
                    },{
                        text: "Contraseña web",
                        dataIndex: 'contrasena',
                        align: 'center',
                        width: 150,
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
    Ext.define('MyApp.PanelUsuarios',
    {
        extend: 'Ext.Window',
        title: 'Consulta de Usuarios',
        closable: true,
        closeAction: 'destroy',
        height: 410,
        width: 890,
        maxWidth:910,
        maxHeight:420,
        modal:true,
        constrain: true,
        layout: 'fit',
        resizable: true,
        //autoScroll:true,
        initComponent: function() {
            this.items = [formUsuarios]
            this.callParent(arguments);
        }
    });
    var winConsMov = Ext.create('MyApp.PanelUsuarios');
    winConsMov.show();
    function getInfo(){
        var idUsuario=Ext.getCmp('idUsuario').getValue();
        storeUsuarios.load({
            params:{
                'idUsuario':"'"+idUsuario+"'"
            }
        });
        var grdUsuario = Ext.getCmp('idusuarios');
        var myMaskUsua=grdUsuario.setLoading("Cargando...",true);
        setTimeout(function(){
            myMaskUsua.destroy();
        },1000);
    }
}
*/
function getMoverProd(idusuario,clave,descripcion,almacen,existencia){
    var formMover = Ext.widget('form', {
        id:'idfrmMover',
        border: false,
        autoScroll:true,
        bodyPadding: 10,
        width: 680,
        items: [{
                xtype:'fieldset',
                title: 'Solicitar Movimiento de Producto',
                collapsible: false,
                defaults: {
                    anchor: '100%'
                },
                layout: 'fit',
                items :[{
                        xtype: 'hiddenfield',
                        id:'idProducto',
                        name: 'idProductoMov'
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
                                fieldLabel: 'Clave',
                                name: 'claveProd',
                                readOnly :true
                            },{
                                fieldLabel:'Descripción',
                                name:'descripcionProd',
                                readOnly :true
                            },{
                                fieldLabel:'Almacén',
                                name:'almacenProd',
                                readOnly :true
                            },{
                                fieldLabel:'Disponible',
                                name:'disponibleProd',
                                readOnly :true
                            },{
                                id:'idAccion',
                                fieldLabel:'Acción',
                                name:'accionMov',
                                readOnly :false
                            },{
                                id:'idCantidad',
                                fieldLabel:'Cantidad',
                                name:'cantidadMov',
                                readOnly :false
                            },{
                                id:'idComentarios',
                                fieldLabel:'Comentarios',
                                name:'comentariosMov',
                                readOnly :false
                            }]
                    }]
            }],
        buttons: [{
                text: 'Confirmar',
                handler: function() {
                    cambiar();
                }
            },{
                text: 'Salir',
                handler: function() {
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
        height: 400,
        width: 700,
        modal:true,
        constrain: true,
        layout: 'fit',
        resizable: true,
        autoScroll:true,
        initComponent: function() {
            this.items = [formMover]
            this.callParent(arguments);
        }
    });
    var winuser = Ext.create('MyApp.PanelMover');
    winuser.show();
    loadFormulario(formMover,{
        url:contexto+'/servlet/MoverProducto?bnd=2&idUsuario='+idUsuario
    });
    function cambiar(){
        var Password=Ext.getCmp('idcontrsena').getValue();
        var idUser=Ext.getCmp('iduser').getValue();
        Ext.Ajax.request({
            url : contexto+'/servlet/MoverProducto',
            params:{
                bnd:3,
                producto:idProducto,
                almacen:idAlmacen,
                accion:idAccion,
                cantidad:idAccion,
                comentarios:idComentarios,
                idUser:idUser
            },
            success:function(rsp){
                // var json = eval("("+rsp.responseText+")");
                Ext.Msg.show({
                    title: 'Cambio de password',
                    msg: 'Se guardo correctamente',
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.WARNING
                });
                winuser.destroy();
            },
            failure:function(rsp){
                Ext.Msg.show({
                    title: 'Datos Incorrectos',
                    msg: 'No se realizo el cambio',
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.WARNING
                });
            }
        });
    }
}
