/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * Autor: m@rco.@ndrade
 */
Ext.require(['*']);
Ext.onReady(function() {
    //var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
    var form = Ext.widget('form', {
        layout:'column',
        border: false,
        bodyPadding: 10,
        fieldDefaults: {
            // labelAlign: 'right',
            labelWidth: 110
        //msgTarget: 'qtip'
        },
        items: [{
            xtype: 'container',
            columnWidth: 0.5,
            // defaultType: 'numberfield',
            defaultType: 'textfield',
            items: [{
                id:'IdFchfactura',
                name: 'fchfactura',
                fieldLabel: 'Clave',
                allowBlank:false,
                emptyText:'Referencia',
                //maxWidth :200,
                //cls: 'LineVerde',
                anchor: '95%',
                // minValue:0,
                enableKeyEvents:true,
                hideTrigger: true,
                keyNavEnabled: false,
                mouseWheelEnabled: false,
                listeners:{
                    'keypress':
                    function(txtField,e){
                        if(e.keyCode==13){
                    //     accesar();
                    }
                    }
                }
            }]
        },{
            xtype: 'container',
            columnWidth:0.5,
            items: [{
                xtype: 'radiogroup',
                anchor: 'none',
                layout: {
                    autoFlex: false
                },
                defaults: {
                    name: 'ccType',
                    style: 'margin-right:15px'
                },
                items: [{
                    name:'vFactura',
                    id:'idFact',
                    inputValue: 'factura',
                    boxLabel: 'Factura',
                    checked: true
                }, {
                    name:'vFactura',
                    id:'idRefe',
                    inputValue: 'referencia',
                    boxLabel: 'Referencia'
                }]
            }]
        }],
        buttons: [{
            text: 'Buscar',
            handler: function() {
                if (this.up('form').getForm().isValid()) {
                    var band=0;
                    var frm=this.up('form').getForm();
                    var idfact=frm.findField('IdFchfactura').getValue();
                    var idRadio=frm.findField('vFactura').getValue();
                    //this.up('window').hide();
                    Ext.Ajax.request({
                        url : contexto+'/Factura',
                        params:{
                            bnd:5,
                            idFact:idfact,
                            bndFact:idRadio
                        },
                        success:function(rsp){
                            var json = eval("("+rsp.responseText+")");
                            band=json.data.nomFactura;
                            if(band==0){
                                Ext.MessageBox.show({
                                    title: 'Información',
                                    msg: 'No existe el envío',
                                    buttons: Ext.MessageBox.OK,
                                    icon: Ext.MessageBox.INFO
                                });
                            }else{
                                if(band==1){
                                    // getDetalleFact(idfact,idRadio);
                                    // getDetListEmpaque(idfact);
                                    getGridDetFact(idfact,idRadio);
                                }else{
                                    getGridDetFact(idfact,idRadio);
                                    // getDetListEmpaque(idfact);
                                }
                            }
                        },
                        failure:function(rsp){

                        }
                    });                
                }
            }
        },{
            text: 'Cancel',
            handler: function() {
                this.up('form').getForm().reset();
                this.up('window').hide();
            }
        }]
    });
    Ext.define('MyApp.Consultas',
    {
        extend: 'Ext.Window',
        title: 'Consulta Envíos',
        closable: true,
        closeAction: 'hide',//if use method destroy Invoking window brand mistake
        height: 120,
        width: 570,
        modal:true,
        constrain: true,
        layout: 'fit',
        resizable: false,
        initComponent: function() {
            this.items = [form],
            this.callParent(arguments);
        }
    });
});

