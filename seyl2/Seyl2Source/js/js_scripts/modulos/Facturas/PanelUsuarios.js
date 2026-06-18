/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * author: m@rco.@ndrade
 */
function getUsuarios(){
    Ext.define('ModelUsuarios', {
        extend: 'Ext.data.Model',
        fields: ['elegir','nombre','estatus',
            'login','contrasena']
        //  idProperty: 'elegir'
    });
    var storeUsuarios = Ext.create('Ext.data.Store', {
        id: 'storeUsuarios',
        model: 'ModelUsuarios',
        remoteSort: true,
        autoLoad :true,
        // allow the grid to interact with the paging scroller by buffering
        buffered: true,
        pageSize: 200,
        proxy: {
            // load using script tags for cross domain, if the data in on the same domain as
            // this page, an HttpProxy would be better
            type: 'jsonp',
            url: contexto+'/servlet/ListaUsuario?bnd=1',
            reader: {
                root: 'records',
                totalProperty: 'total'
            },
            // sends single sort as multi parameter
            simpleSortMode: true
        }
    });
    storeUsuarios.load();
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
function getDetalleUsuario(idUsuario){
    var formUser = Ext.widget('form', {
        id:'idfrmUsuario',
        border: false,
        autoScroll:true,
        bodyPadding: 10,
        width: 680,
        items: [{
                xtype:'fieldset',
                title: 'Datos Generelaes',
                collapsible: false,
                defaults: {
                    anchor: '100%'
                },
                layout: 'fit',
                items :[{
                        xtype: 'hiddenfield',
                        id:'iduser',
                        name: 'idUsuarioPas'
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
                                fieldLabel: 'Nombre Usuario',
                                name: 'nombre',
                                readOnly :true
                            },{
                                fieldLabel:'Usuario',
                                name:'usuario',
                                readOnly :true
                            },{
                                id:'idcontrsena',
                                fieldLabel:'Contraseña Web',
                                name:'contrasena',
                                readOnly :false
                            }]
                    }/*,{
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
            }*/]
            }],
        buttons: [{
                text: 'Cambiar Contraseña',
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
    Ext.define('MyApp.PanelUser',
    {
        extend: 'Ext.Window',
        title: 'Informacion Usuario ',
        closable: true,
        closeAction: 'destroy',
        height: 200,
        width: 700,
        modal:true,
        constrain: true,
        layout: 'fit',
        resizable: true,
        autoScroll:true,
        initComponent: function() {
            this.items = [formUser]
            this.callParent(arguments);
        }
    });
    var winuser = Ext.create('MyApp.PanelUser');
    winuser.show();
    loadFormulario(formUser,{
        url:contexto+'/servlet/ListaUsuario?bnd=2&idUsuario='+idUsuario
    });
    function cambiar(){
        var Password=Ext.getCmp('idcontrsena').getValue();
        var idUser=Ext.getCmp('iduser').getValue();
        Ext.Ajax.request({
            url : contexto+'/servlet/ListaUsuario',
            params:{
                bnd:3,
                password:Password,
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
