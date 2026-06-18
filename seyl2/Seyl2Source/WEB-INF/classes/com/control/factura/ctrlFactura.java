package com.control.factura;

import com.dao.EventManager;
import com.dao.jdbc.DaoHelper;
import com.dao.util.HibernateUtil;
import com.entity.Lialmacenes;
import com.entity.Lichoferes;
import com.entity.Liembarques;
import com.entity.Limotrechazos;
import com.entity.Liusuarios;
import com.sql.SQLFacturas;
import com.util.Fecha;
import com.util.Utilities;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ctrlFactura
  extends HttpServlet
{
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html;charset=ISO-8859-1");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0L);
    PrintWriter out = response.getWriter();
    try
    {
      String bnd = Utilities.obtenParametro(request, "bnd");
      switch (Integer.parseInt(bnd))
      {
      case 1:
        out.print(getFactura(request));
        break;
      case 2:
        out.print(buscarFactura(request));
        break;
      case 3:
        out.print(buscarExistencia(request));
        break;
      case 4:
        out.print(buscarFacturas(request));
        break;
      case 5:
        out.print(getNoFact(request));
        break;
      case 6:
        out.print(Utilities.getGridRemoveColum(request));
        break;
      case 7:
        out.print(getListaEmpaque(request));
        break;
      case 8:
        out.print(getListaEmpaqueDetalle(request));
      }
    }
    finally
    {
      out.close();
    }
  }

  public String getListaEmpaqueDetalle(HttpServletRequest request)
  {
    String respuesta = "";
    String callback = Utilities.obtenParametro(request, "callback").replaceAll("'", "");
    int start = Integer.parseInt(Utilities.obtenParametro(request, "start").equals("") ? "0" : Utilities.obtenParametro(request, "start"));
    int limit = Integer.parseInt(Utilities.obtenParametro(request, "limit").equals("") ? "0" : Utilities.obtenParametro(request, "limit"));
    respuesta = callback + "({total:0,records1:[]});";
    Liusuarios usr = Utilities.ReactivarSession(request);
    try
    {
      ArrayList arrayEmpaqueD = new ArrayList(0);
      arrayEmpaqueD.clear();
      DaoHelper daoHelper = new DaoHelper();
      if (usr != null)
      {
        String noFactura = Utilities.obtenParametro(request, "idCnt1");
        arrayEmpaqueD = daoHelper.getResultTable(SQLFacturas.obtenerEmpaqueDetalle(noFactura));
        if (arrayEmpaqueD != null)
        {
          if (!arrayEmpaqueD.isEmpty())
          {
            respuesta = "";
            respuesta = callback + "({total:" + arrayEmpaqueD.size() + ",records1:[";
            for (int i = start; (i < start + limit) && (i < arrayEmpaqueD.size()); i++)
            {
              Hashtable hashAuxEmpaqD = new Hashtable();
              hashAuxEmpaqD.clear();
              hashAuxEmpaqD = (Hashtable)arrayEmpaqueD.get(i);
              respuesta = respuesta + "{";
              respuesta = respuesta + "ver:\"<u><a href=# onClick=getImagenProd('" + ((String)hashAuxEmpaqD.get("PRDCLAVE")).trim() + "') style='color:#1CA4D7; cursor:pointer;'>Imagen</a></u> \" ,";
              respuesta = respuesta + "producto:'" + (String)hashAuxEmpaqD.get("PRDCLAVE") + "',";
              respuesta = respuesta + "descripcion:'" + (String)hashAuxEmpaqD.get("PRDNOMBRE") + "',";
              respuesta = respuesta + "almacen:'" + (String)hashAuxEmpaqD.get("DPRALOID") + "',";
              respuesta = respuesta + "cantidad:'" + (String)hashAuxEmpaqD.get("DPRCANTIDAD") + "'";
              respuesta = respuesta + "},";
            }
            respuesta = respuesta.substring(0, respuesta.length() - 1) + "]});";
          }
        }
        else {
          respuesta = callback + "({total:0,records1:[]});";
        }
      }
    }
    catch (Exception e)
    {
      respuesta = callback + "({total:0,records1:[]});";
      e.printStackTrace();
    }
    return respuesta;
  }

  public String getListaEmpaque(HttpServletRequest request)
  {
    String respuesta = "";
    Liusuarios usr = Utilities.ReactivarSession(request);
    try
    {
      ArrayList arrayEmpaque = new ArrayList(0);
      arrayEmpaque.clear();
      DaoHelper daoHelper = new DaoHelper();
      if (usr != null)
      {
        String noFactura = Utilities.obtenParametro(request, "idCnt1");
        arrayEmpaque = daoHelper.getResultTable(SQLFacturas.obtenerEmpaqueEncabezado(noFactura, String.valueOf(usr.getUsuempresa())));
        if ((arrayEmpaque != null) &&
          (!arrayEmpaque.isEmpty())) {
          for (int i = 0; i < arrayEmpaque.size(); i++)
          {
            Hashtable hashAuxEmpaque = new Hashtable();
            hashAuxEmpaque.clear();
            hashAuxEmpaque = (Hashtable)arrayEmpaque.get(i);
            respuesta = "{success:true,data:{lstRemision:'" + (String)hashAuxEmpaque.get("PREID") + "'," + "lsEmpresa:'" + (String)hashAuxEmpaque.get("EMPRESA") + "'," + "lscliente:'" + (String)hashAuxEmpaque.get("CLIENTE") + "',";
            respuesta = respuesta + "lsAlmacen:'" + (String)hashAuxEmpaque.get("ALMACEN") + "'," + "lsTipoMov:'" + (String)hashAuxEmpaque.get("MOVIMIENTO");
            //respuesta = respuesta + "'," + "estatusEnvio:'" + (String)hashAuxEmpaque.get("EMBESTATUS");
            respuesta = respuesta + "'," + "estatusEnvio:'" + status(hashAuxEmpaque.get("EMBESTATUS").equals("") ? null : (String)hashAuxEmpaque.get("EMBESTATUS"), hashAuxEmpaque.get("RECTIPORECHAZO").equals("") ? null : (String)hashAuxEmpaque.get("RECTIPORECHAZO"), hashAuxEmpaque.get("EMBFECENT").equals("") ? null : (String)hashAuxEmpaque.get("EMBFECENT"));
            respuesta = respuesta + "'," + "lsfactura:'" + (String)hashAuxEmpaque.get("PREREF1") + "'," + "lsReferencia:'" + (String)hashAuxEmpaque.get("EMBREF1") + "'," + "lsFechDoc:'" + (String)hashAuxEmpaque.get("PREFECHA") + "'," + "lsFechEmb:'" + (String)hashAuxEmpaque.get("EMBFECEMB");
            respuesta = respuesta + "'," + "lsFechEnt:'" + (String)hashAuxEmpaque.get("EMBFECENT");
            respuesta = respuesta + "'," + "lsFecEnvioTpf:'" + (String)hashAuxEmpaque.get("EVEFECHA") + "'," + "lsReferenciaTpf:'" + (String)hashAuxEmpaque.get("EVEREFERENCIA") + "'," + "lsCostoTpf:'" + (String)hashAuxEmpaque.get("EVECOSTO") + "'," + "lsProveedorTpf:'" + (String)hashAuxEmpaque.get("EVEPROVEEDOR");
            respuesta = respuesta + "'," + "Comentarios:'" + (String)hashAuxEmpaque.get("EMBCOMS") + "'";
            respuesta = respuesta + "}}";
          }
        }
      }
    }
    catch (Exception e)
    {
      e = e;
      respuesta = "{success: false,msg: 'Error en el servidor',accion1:'false',wnd:'idTabFactura'}";
    }
    finally {}
    return respuesta;
  }

  public String getFactura(HttpServletRequest request)
  {
    String respuesta = "";String motivo = "";String chofer = "";String sqlBusq = "";
    Liusuarios usr = Utilities.ReactivarSession(request);
    if (usr != null)
    {
      EntityManager em = HibernateUtil.getEntityManager();
      if (!em.getTransaction().isActive()) {
        em.getTransaction().begin();
      }
      try
      {
        String noFactura = Utilities.obtenParametro(request, "idCnt");
        String bndFact = Utilities.obtenParametro(request, "bndFact");
        Vector vecParams = new Vector();
        vecParams.clear();
        vecParams.add(usr.getUsuempresa());
        if (bndFact.equals("")) {
          sqlBusq = " and (le.embfolio='" + noFactura + "')";
        } else if (bndFact.equals("true")) {
          sqlBusq = " and (le.embfolio='" + noFactura + "')";
        } else if (bndFact.equals("false")) {
          sqlBusq = " and (le.embref='" + noFactura + "')";
        } else if (bndFact.equals("Factura")) {
          sqlBusq = " and (le.embclave=" + noFactura + ")";
        }
        List listFactura = EventManager.getArrayParameter(SQLFacturas.obtFactura(sqlBusq), vecParams);
        if ((listFactura != null) && (!listFactura.isEmpty()))
        {
          Object[] obj = (Object[])(Object[])listFactura.get(0);
          respuesta = "{success:true,data:{fchfactura:'" + (obj[0] == null ? "NA" : Fecha.getDateExtJSSQL(obj[0].toString(), "-", "/")) + "'," + "fchpedido:'" + (obj[1] == null ? "NA" : Fecha.getDateExtJSSQL(obj[1].toString(), "-", "/")) + "'," + "factura:'" + (obj[28] == null ? "NA" : obj[28]) + "'," + "referencia:'" + (obj[2] == null ? "NA" : obj[2]) + "'," + "noenvio:'" + (obj[3] == null ? "NA" : obj[3]) + "'," + "nocliente:'" + (obj[4] == null ? "NA" : obj[4]) + " " + (obj[5] == null ? "NA" : obj[5].toString().replaceAll("'", "")) + "'," + "destino:'" + (obj[6] == null ? "NA" : obj[6]) + " " + (obj[7] == null ? "NA" : obj[7].toString().replaceAll("'", "")) + "'," + "nocajas:'" + (obj[8] == null ? "NA" : obj[8]) + "'," + "kilos:'" + (obj[9] == null ? "NA" : obj[9]) + "',";
          NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
          respuesta = respuesta + "importe:'" + (obj[10] == null ? "NA" : currencyFormatter.format(Double.parseDouble(obj[10].toString()))) + "',";
          respuesta = respuesta + "Comentarios:'" + (obj[11] == null ? "NA" : obj[11].toString().replaceAll("'", "")) + "'," + "folentrada:'" + (obj[12] == null ? "NA" : obj[12]) + "'," + "fchrecibo:'" + (obj[13] == null ? "NA" : Fecha.getDateExtJSSQL(obj[13].toString(), "-", "/")) + "'," + "guiaempr:'" + (obj[14] == null ? "NA" : obj[14]) + "'," + "ubicacion:'" + (obj[15] == null ? "NA" : obj[15]) + "'," + "entregar:'" + (obj[16] == null ? "NA" : Fecha.getDateExtJSSQL(obj[16].toString(), "-", "/")) + "'," + "fechcancel:'" + (obj[17] == null ? "NA" : Fecha.getDateExtJSSQL(obj[17].toString(), "-", "/")) + "'," + "folembarque:'" + (obj[18] == null ? "NA" : obj[18]) + "'," + "fchembarque:'" + (obj[19] == null ? "NA" : Fecha.getDateExtJSSQL(obj[19].toString(), "-", "/")) + "',";
          if (obj[24] != null)
          {
            Liembarques objEmbarque = (Liembarques)EventManager.getSingleList(Liembarques.class, Integer.valueOf(Integer.parseInt(obj[24].toString())));
            if (objEmbarque.getEmbchofer() != null) {
              chofer = objEmbarque.getEmbchofer().getChoclave() + " " + objEmbarque.getEmbchofer().getChonombre() + " " + objEmbarque.getEmbchofer().getChopaterno() + " " + objEmbarque.getEmbchofer().getChomaterno();
            } else {
              chofer = "NA";
            }
          }
          respuesta = respuesta + "nombchofer:'" + chofer + "'," + "folentrega:'" + (obj[20] == null ? "NA" : obj[20]) + "'," + "piezas:'" + (obj[21] == null ? "NA" : obj[21]) + "'," + "ar:'" + (obj[22] == null ? "NA" : Fecha.getDateExtJSSQL(obj[22].toString(), "-", "/")) + "',";
          respuesta = respuesta + "tiprechazo:'" + (obj[23].toString().equals("T") ? "Rechazo Total" : obj[23].toString().equals("P") == true ? "Rechazo Parcial" : obj[23].toString().equals("O") ? "Otro" : obj[23] == null ? "NA" : "NA") + "',";
          if (obj[24] != null)
          {
            Liembarques objEmbarque = (Liembarques)EventManager.getSingleList(Liembarques.class, Integer.valueOf(Integer.parseInt(obj[24].toString())));
            if (objEmbarque.getEmbconcrechazo() != null) {
              motivo = objEmbarque.getEmbconcrechazo().getMredescripcion();
            } else {
              motivo = "NA";
            }
          }
          respuesta = respuesta + "motivo:'" + (obj[24] == null ? "NA" : motivo) + "',";
          respuesta = respuesta + "accion:'" + (obj[25] == null ? "NA" : getAccion(obj[25].toString())) + "',";
          respuesta = respuesta + "foldevdocs:'" + (obj[26] == null ? "NA" : obj[26]) + "',";
          respuesta = respuesta + "fchdevdocs:'" + (obj[27] == null ? "NA" : Fecha.getDateExtJSSQL(obj[27].toString(), "-", "/")) + "'";
          respuesta = respuesta + "}}";
        }
        else
        {
          respuesta = "{success: false,msg: 'No Existe la Factura',accion1:'false',wnd:'idTabFactura'}";
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        em.getTransaction().rollback();
        respuesta = "{success: false,msg: 'Error en el servidor',accion1:'false',wnd:'idTabFactura'}";
      }
      finally
      {
        HibernateUtil.closeSession();
      }
    }
    return respuesta;
  }

  private String buscarFactura(HttpServletRequest request)
  {
    String callback = Utilities.obtenParametro(request, "callback").replaceAll("'", "");
    String record = callback + "({total:0,records:[]});";
    String query1 = "";
    String query2 = "";
    String query3 = "";
    String fechaIni = "";
    String fechaFin = "";
    String IdStatus = "";
    String ordernBy = "";
    String dir = "";
    String idporteo = "";
    Liusuarios usr = Utilities.ReactivarSession(request);
    HttpSession s = request.getSession(true);
    if (usr != null)
    {
      EntityManager em = HibernateUtil.getEntityManager();
      if (!em.getTransaction().isActive()) {
        em.getTransaction().begin();
      }
      try
      {
        ArrayList arrayFacturas = new ArrayList(0);
        arrayFacturas.clear();
        DaoHelper daoHelper = new DaoHelper();
        if (!Utilities.obtenParametro(request, "busqBnd").equals(""))
        {
          s.removeAttribute("fechaIni");
          s.removeAttribute("fechaFin");
          s.removeAttribute("IdStatus");
          s.removeAttribute("idporteo");
          fechaIni = Utilities.obtenParametro(request, "fechaIni").replaceAll("'", "");
          fechaFin = Utilities.obtenParametro(request, "fechaFin").replaceAll("'", "");
          IdStatus = Utilities.obtenParametro(request, "idestatus").replaceAll("'", "");
          idporteo = Utilities.obtenParametro(request, "idPorteo").replaceAll("'", "");
          if (idporteo.equals("")) {
            idporteo = "";
          } else if (idporteo.equals("null")) {
            idporteo = "";
          }
          idporteo = !idporteo.equals("") ? " and le.embalmid='" + idporteo + "' " : "";
          if (fechaIni.equals("")) {
            fechaIni = "";
          } else if (fechaIni.equals("null")) {
            fechaIni = "";
          } else if (fechaIni.equals("dd/mm/yyyy")) {
            fechaIni = "";
          }
          if (fechaFin.equals("")) {
            fechaFin = "";
          } else if (fechaFin.equals("null")) {
            fechaFin = "";
          } else if (fechaFin.equals("dd/mm/yyyy")) {
            fechaFin = "";
          }
          s.setAttribute("fechaIni", fechaIni);
          s.setAttribute("fechaFin", fechaFin);
          s.setAttribute("IdStatus", IdStatus);
          s.setAttribute("idporteo", idporteo);
        }
        else
        {
          fechaIni = s.getAttribute("fechaIni") != null ? s.getAttribute("fechaIni").toString() : "";
          fechaFin = s.getAttribute("fechaFin") != null ? s.getAttribute("fechaFin").toString() : "";
          IdStatus = s.getAttribute("IdStatus") != null ? s.getAttribute("IdStatus").toString() : "";
          idporteo = s.getAttribute("idporteo") != null ? s.getAttribute("idporteo").toString() : "";
        }
        ordernBy = Utilities.obtenParametro(request, "sort");
        dir = Utilities.obtenParametro(request, "dir");
        int start = Integer.parseInt(Utilities.obtenParametro(request, "start").equals("") ? "0" : Utilities.obtenParametro(request, "start"));
        int limit = Integer.parseInt(Utilities.obtenParametro(request, "limit").equals("") ? "0" : Utilities.obtenParametro(request, "limit"));
        if ((!fechaIni.equals("")) && (!fechaFin.equals("")))
        {
          query1 = " and (trunc(le.embfecfac) between trunc(to_date('" + fechaIni + "', 'DD/MM/YYYY')) and trunc(to_date('" + fechaFin + "', 'DD/MM/YYYY'))) ";
          if (!IdStatus.equals("")) {
            query3 = getSqlStatus(IdStatus);
          }
          query3 = query3 + idporteo;
          arrayFacturas = daoHelper.getResultTable(SQLFacturas.BuscarFacturas(query1, query2, query3, usr.getUsuempresa().intValue(), CtrlUtilFactura.getOrdSqlBusFac(ordernBy, dir)));
        }
        if (arrayFacturas != null)
        {
          if (!arrayFacturas.isEmpty())
          {
            record = callback + "({total:" + arrayFacturas.size() + ",records:[";
            for (int i = start; (i < start + limit) && (i < arrayFacturas.size()); i++)
            {
              Hashtable hashAuxFactura = new Hashtable();
              hashAuxFactura.clear();
              hashAuxFactura = (Hashtable)arrayFacturas.get(i);
              record = record + "{";
              // record = record + "elegir:\"<u><a href=# onClick=getDetalleFact(" + hashAuxFactura.get("EMBID") + ",'Factura') style='color:#1CA4D7; cursor:pointer;'>Elegir</a></u> \" ,";
              record = record + "consultar:\"<u><a href=# onClick=getDetListEmpaque(" + hashAuxFactura.get("EMBID").toString().trim() + ") style='color:#1CA4D7; cursor:pointer;'>Ver Detalle</a></u> \" ,";
              record = record + "estatus:'" + status(hashAuxFactura.get("EMBESTATUS").equals("") ? null : (String)hashAuxFactura.get("EMBESTATUS"), hashAuxFactura.get("RECTIPORECHAZO").equals("") ? null : (String)hashAuxFactura.get("RECTIPORECHAZO"), hashAuxFactura.get("EMBFECENT").equals("") ? null : (String)hashAuxFactura.get("EMBFECENT")) + "',";
              record = record + "factura:'" + (hashAuxFactura.get("EMBFOLIO").equals("") ? "NA" : hashAuxFactura.get("EMBFOLIO")) + "',";
              record = record + "referencia:'" + (hashAuxFactura.get("EMBREF1").equals("") ? "NA" : hashAuxFactura.get("EMBREF1")) + "',";
              record = record + "envio:'" + (hashAuxFactura.get("EMBENVIO").equals("") ? "NA" : hashAuxFactura.get("EMBENVIO")) + "',";
              record = record + "numCliente:'" + (hashAuxFactura.get("CLICLAVE").equals("") ? "NA" : hashAuxFactura.get("CLICLAVE")) + "',";
              record = record + "nombCliente:'" + (hashAuxFactura.get("CLINOMBRE").equals("") ? "NA" : hashAuxFactura.get("CLINOMBRE").toString().replaceAll("'", "")) + "',";
              record = record + "destino:'" + (hashAuxFactura.get("POBNOMBRE").equals("") ? "NA" : hashAuxFactura.get("POBNOMBRE")) + "',";
              record = record + "numCajas:'" + (hashAuxFactura.get("EMBCAJAS").equals("") ? "NA" : hashAuxFactura.get("EMBCAJAS")) + "',";
              record = record + "importe:'" + (hashAuxFactura.get("EMBVALOR").equals("") ? "NA" : hashAuxFactura.get("EMBVALOR")) + "',";
              record = record + "fechFactura:'" + (hashAuxFactura.get("EMBFECFAC").equals("") ? "NA" : (String)hashAuxFactura.get("EMBFECFAC")) + "',";
              record = record + "fechIngreso:'" + (hashAuxFactura.get("EMBFECREC").equals("") ? "NA" : (String)hashAuxFactura.get("EMBFECREC")) + "',";
              record = record + "cita:'" + (hashAuxFactura.get("EMBFECCITA").equals("") ? "NA" : (String)hashAuxFactura.get("EMBFECCITA")) + "',";
              record = record + "fechEmbarq:'" + (hashAuxFactura.get("EMBFECEMB").equals("") ? "NA" : (String)hashAuxFactura.get("EMBFECEMB")) + "',";
              record = record + "fechEntreg:'" + (hashAuxFactura.get("EMBFECENT").equals("") ? "NA" : (String)hashAuxFactura.get("EMBFECENT")) + "',";
              record = record + "comentarios:'" + (hashAuxFactura.get("EMBCOMS").equals("") ? "NA" : hashAuxFactura.get("EMBCOMS").toString().replaceAll("'", "")) + "'";
              record = record + "},";
            }
            record = record.substring(0, record.length() - 1) + "]});";
          }
          else
          {
            record = callback + "({total:0,records:[]});";
          }
        }
        else {
          record = callback + "({total:0,records:[]});";
        }
      }
      catch (Exception e)
      {
        record = callback + "{total:0,records:[{}]}";
        e.printStackTrace();
        if (em.getTransaction().isActive()) {
          em.getTransaction().rollback();
        }
      }
      finally
      {
        HibernateUtil.closeSession();
      }
    }
    else
    {
      record = callback + "{total:0,records:[{}]}";
    }
    return record;
  }

  private String buscarExistencia(HttpServletRequest request)
  {
    String callback = Utilities.obtenParametro(request, "callback").replaceAll("'", "");
    String record = callback + "({total:0,records:[]});";
    Liusuarios usr = Utilities.ReactivarSession(request);
    HttpSession s = request.getSession(true);
    if (usr != null)
    {
      EntityManager em = HibernateUtil.getEntityManager();
      if (!em.getTransaction().isActive()) {
        em.getTransaction().begin();
      }
      try
      {
        String idProducts = "";
        String idAlmacen = "";
        String nomAlmacen = "";
        String orderBy = "";
        String Famili = "";
        String Line = "";
        String tipoProd = "";
        String dir = "";
        String empresa = "";
        String almacen = "";
        String sinexistencias = "";
        String sinExist = "";
        
        ArrayList arrayExistencia = new ArrayList(0);
        arrayExistencia.clear();
        orderBy = Utilities.obtenParametro(request, "sort");
        dir = Utilities.obtenParametro(request, "dir");
        int start = Integer.parseInt(Utilities.obtenParametro(request, "start").equals("") ? "0" : Utilities.obtenParametro(request, "start"));
        int limit = Integer.parseInt(Utilities.obtenParametro(request, "limit").equals("") ? "0" : Utilities.obtenParametro(request, "limit"));
        DaoHelper daoHelper = new DaoHelper();
        Vector vecParams = new Vector();
        vecParams.clear();
        if (!Utilities.obtenParametro(request, "busqBnd").equals(""))
        {
          s.removeAttribute("idProducts");
          s.removeAttribute("idAlmacen");
          s.removeAttribute("Famili");
          s.removeAttribute("Line");
          s.removeAttribute("tipoProd");
          s.removeAttribute("sinExist");
          
          idProducts = Utilities.obtenParametro(request, "idProducts").replaceAll("'", "");
          idAlmacen = idAlmacen = Utilities.obtenParametro(request, "idAlmacen").replaceAll("'", "");
     //     Famili = Utilities.obtenParametro(request, "idFamili").replaceAll("'", "").trim();
     //     Famili = !Famili.equals("") ? " and lp.prdfamilia = '" + Famili + "'" : "";
          
          Famili = Utilities.obtenParametro(request, "idFamili").replaceAll("'", "");
          if (Famili.equals("")) {
            Famili = "";
          } else if (Famili.equals("null")) {
            Famili = "";
          }
          Famili = !Famili.equals("") ? " and lp.prdlinid = '" + Famili + "' " : "";
    
          Line = Utilities.obtenParametro(request, "idLine").replaceAll("'", "");
          if (Line.equals("")) {
            Line = "";
          } else if (Line.equals("null")) {
            Line = "";
          }
          Line = !Line.equals("") ? " and lp.prdfamid = '" + Line + "' " : "";
         
          tipoProd = Utilities.obtenParametro(request, "idTipoProd").replaceAll("'", "");
          if (tipoProd.equals("")) {
            tipoProd = "";
          } else if (tipoProd.equals("null")) {
            tipoProd = "";
          }
          tipoProd = !tipoProd.equals("") ? " and lp.prdtprid = '" + tipoProd + "' " : "";
          
          sinExist = Utilities.obtenParametro(request, "sinExist").replaceAll("'","");
          
          if (sinExist.equals("true")){
               sinexistencias = "";
           }
           else {
              sinexistencias = " and li.invreal > 0 ";
           }
            
          s.setAttribute("Famili", Famili);
          s.setAttribute("Line", Line);
          s.setAttribute("tipoProd", tipoProd);
          s.setAttribute("idProducts", idProducts);
          s.setAttribute("idAlmacen", idAlmacen);
          s.setAttribute("sinExist", sinExist);
        }
        else
        {
          sinExist = s.getAttribute("sinExist") != null ? s.getAttribute("sinExist").toString() : "";          
          idProducts = s.getAttribute("idProducts") != null ? s.getAttribute("idProducts").toString() : "";
          idAlmacen = s.getAttribute("idAlmacen") != null ? s.getAttribute("idAlmacen").toString() : "";
          Famili = s.getAttribute("Famili") != null ? s.getAttribute("Famili").toString() : "";
          Line = s.getAttribute("Line") != null ? s.getAttribute("Line").toString() : "";
          tipoProd = s.getAttribute("tipoProd") != null ? s.getAttribute("tipoProd").toString() : "";
        }
        if (!idAlmacen.equals(""))
        {
          empresa = usr.getUsuempresa().toString();
          almacen = idAlmacen;
          if (almacen.equals("")) {
            almacen = "";
          } else if (almacen.equals("null")) {
            almacen = "";
          }
          if (!almacen.equals("")) {
            almacen = " and li.invaloid='" + almacen + "'";
          }
          if (!idProducts.equals("")) {
            idProducts = " and lp.prdclave like ('%" + idProducts + "%') ";
          }
          idProducts = idProducts + Famili;
          idProducts = idProducts + Line;
          idProducts = idProducts + tipoProd;
          idProducts = idProducts + sinexistencias;

          arrayExistencia = daoHelper.getResultTable(SQLFacturas.BuscarExistencias(idProducts, empresa, almacen, CtrlUtilFactura.getOrdSqlBusExis(orderBy, dir)));
        }
        if (arrayExistencia != null)
        {
          if (!arrayExistencia.isEmpty())
          {
            record = callback + "({total:" + arrayExistencia.size() + ",records:[";
            for (int i = start; (i < start + limit) && (i < arrayExistencia.size()); i++)
            {
              Hashtable hashAuxExistencia = new Hashtable();
              hashAuxExistencia.clear();
              hashAuxExistencia = (Hashtable)arrayExistencia.get(i);
              String almacen_logico;
              almacen_logico = "'"+hashAuxExistencia.get("INVALOID")+"'";

              record = record + "{";
              record = record + "kardex:\"<u><a href=# onClick=getConsMovimientos(Ext.getCmp('gridBuscadorExistencias').getSelectionModel().getLastSelected().get('idProducto')," + almacen_logico + ",Ext.getCmp('gridBuscadorExistencias').getSelectionModel().getLastSelected().get('decripcion')) style='color:#1CA4D7; cursor:pointer;'>Kardex</a></u> \" ,";
              record = record + "imagen:\"<u><a href=# onClick=getImagenProd('" + ((String)hashAuxExistencia.get("PRDCLAVE")).trim() + "') style='color:#1CA4D7; cursor:pointer;'>Imagen</a></u> \" ,";
              record = record + "idProducto:'" + (String)hashAuxExistencia.get("PRDID") + "',";
              record = record + "clvProducto:'" + (String)hashAuxExistencia.get("PRDCLAVE") + "',";
              record = record + "decripcion:'" + hashAuxExistencia.get("PRDNOMBRE").toString().replaceAll("'", "") + "',";
              record = record + "linea:'" + (String)hashAuxExistencia.get("FAMNOMBRE") + "',";
              record = record + "familia:'" + (String)hashAuxExistencia.get("LINNOMBRE") + "',";
              record = record + "uniAlmacenado:'" + (String)hashAuxExistencia.get("PRDUALID") + "',";
              record = record + "existReal:'" + (String)hashAuxExistencia.get("INVREAL") + "',";
              record = record + "existReservada:'" + (String)hashAuxExistencia.get("INVRESERVADO") + "',";
              record = record + "existDisponible:'" + calExDisponible(hashAuxExistencia.get("INVREAL") != null ? (String)hashAuxExistencia.get("INVREAL") : "0", (String)hashAuxExistencia.get("INVRESERVADO") != null ? (String)hashAuxExistencia.get("INVRESERVADO") : "0") + "',";
              record = record + "almacen:'" + (String)hashAuxExistencia.get("ALONOMBRE") + "',";
              record = record + "prdcosto:'" + (String)hashAuxExistencia.get("PRDCOSTO") + "',";
              record = record + "mover:\"<u><a href=# onClick=getMoverProd(Ext.getCmp('gridBuscadorExistencias').getSelectionModel().getLastSelected().get('clvProducto'),Ext.getCmp('gridBuscadorExistencias').getSelectionModel().getLastSelected().get('decripcion')," + almacen_logico + ",Ext.getCmp('gridBuscadorExistencias').getSelectionModel().getLastSelected().get('existDisponible')) style='color:#1CA4D7; cursor:pointer;'>Mover</a></u> \"";
              record = record + "},";
            }
            record = record.substring(0, record.length() - 1) + "]});";
          }
          else
          {
            record = callback + "({total:0,records:[]});";
          }
        }
        else {
          record = callback + "({total:0,records:[]});";
        }
      }
      catch (Exception e)
      {
        record = callback + "({total:0,records:[]});";
        e.printStackTrace();
        if (em.getTransaction().isActive()) {
          em.getTransaction().rollback();
        }
      }
      finally
      {
        HibernateUtil.closeSession();
      }
    }
    else
    {
      record = callback + "({total:0,records:[]});";
    }
    return record;
  }

  public String buscarFacturas(HttpServletRequest request)
  {
    String record = "";
    String sqlBusq = "";
    EntityManager em = HibernateUtil.getEntityManager();
    Liusuarios usr = Utilities.ReactivarSession(request);
    DaoHelper daoHelper = new DaoHelper();
    ArrayList arrayExistencia = new ArrayList(0);
    arrayExistencia.clear();
    if (usr != null) {
      try
      {
        if (!em.getTransaction().isActive()) {
          em.getTransaction().begin();
        }
        String noFactura = Utilities.obtenParametro(request, "idFact");
        String bndFact = Utilities.obtenParametro(request, "bndFact");

        //Vector vecParams = new Vector();
        //vecParams.clear();
        //vecParams.add(usr.getUsuempresa());
        if (bndFact.equals("true")) {
          sqlBusq = " and le.embfolio='" + noFactura + "'";
        } else if (bndFact.equals("false")) {
          sqlBusq = " and le.embref1='" + noFactura + "'";
        }
        arrayExistencia = daoHelper.getResultTable(SQLFacturas.ObtenerFacturas(sqlBusq,usr.getUsuempresa().toString()));
        if ((arrayExistencia != null) && (!arrayExistencia.isEmpty()))
        {
          record = "{records:[";
          for (int i = 0; i < arrayExistencia.size(); i++)
          {
            Hashtable hashAuxExistencia = new Hashtable();
            hashAuxExistencia.clear();
            hashAuxExistencia = (Hashtable)arrayExistencia.get(i);
            record = record + "{";
            // record = record + "elegir:\"<u><a href=# onClick=getDetalleFact(" + hashAuxExistencia.get("EMBCLAVE") + ",'Factura') style='color:#1CA4D7; cursor:pointer;'>Elegir</a></u> \" ,";
            record = record + "elegir:\"<u><a href=# onClick=getDetListEmpaque(" + hashAuxExistencia.get("EMBID") + ") style='color:#1CA4D7; cursor:pointer;'>Elegir</a></u> \" ,";
            record = record + "numFactura:'" + (String)hashAuxExistencia.get("EMBFOLIO") + "',";
            record = record + "fechFactura:'" + (String)hashAuxExistencia.get("EMBFECFAC") + "',";
            record = record + "numEnvio:'" + (String)hashAuxExistencia.get("EMBENVIO") + "'";
            record = record + "},";
          }
          record = record.substring(0, record.length() - 1) + "]}";
        }
        else
        {
          record = "{records:[]}";
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        em.getTransaction().rollback();
        record = record = "{records:[]}";
      }
      finally
      {
        HibernateUtil.closeSession();
      }
    }
    return record;
  }

  public String getNoFact(HttpServletRequest request)
  {
    String json = "";
    String sqlBusq = "";
    EntityManager em = HibernateUtil.getEntityManager();
    Liusuarios usr = Utilities.ReactivarSession(request);
    
    ArrayList listFactura = new ArrayList(0);
    listFactura.clear();
    DaoHelper daoHelper = new DaoHelper();
    
    if (usr != null) {
      try
      {
        if (!em.getTransaction().isActive()) {
          em.getTransaction().begin();
        }
        String noFactura = Utilities.obtenParametro(request, "idFact");
        String bndFact = Utilities.obtenParametro(request, "bndFact");
        String idEmpresa = (usr.getUsuempresa().toString());
        //Vector vecParams = new Vector();
        //vecParams.clear();
        //vecParams.add(usr.getUsuempresa());
        if (bndFact.equals("")) {
          sqlBusq = " and le.embfolio='" + noFactura + "' ";
        } else if (bndFact.equals("true")) {
          sqlBusq = " and le.embfolio='" + noFactura + "' ";
        } else if (bndFact.equals("false")) {
          sqlBusq = " and le.embref1='" + noFactura + "' ";
        }
        // List listFactura = EventManager.getArrayParameter(SQLFacturas.ContFacturas(sqlBusq), vecParams);
        listFactura = daoHelper.getResultTable(SQLFacturas.ContFacturas(sqlBusq, idEmpresa));
        if ((listFactura != null) && (!listFactura.isEmpty()))
        {
          Hashtable hashlistaFactura = new Hashtable();
          hashlistaFactura.clear();
          hashlistaFactura = (Hashtable)listFactura.get(0);

          json = "{success:true,";
          json = json + "data:{";
          json = json + "nomFactura:" + (String)hashlistaFactura.get("NOMFACT") + "}}";
        }
        else
        {
          json = "{success:false,data:{nomFactura:0}}";
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        em.getTransaction().rollback();
        json = "{success:false,data:{nomFactura:0}}";
      }
      finally
      {
        HibernateUtil.closeSession();
      }
    }
    return json;
  }

  public String getSqlStatus(String tipo)
  {
    String sql = "";
    tipo = tipo.replace("'", "");
    if (tipo.equals("S")) {
      sql = " and le.embestatus = 'S' ";
    } else if (tipo.equals("P")) {
      sql = " and le.embestatus = 'P' ";
    } else if (tipo.equals("T")) {
      sql = " and le.embestatus = 'T' ";
    } else if (tipo.equals("A")) {
      sql = " and le.embestatus in ('A', 'R') ";
    } else if (tipo.equals("E")) {
      sql = " and le.embestatus = 'E' and lr.rectiporechazo is null ";
    } else if (tipo.equals("EP")) {
      sql = " and le.embestatus = 'E' and lr.rectiporechazo(+)='P' ";
    } else if (tipo.equals("RT")) {
      sql = " and le.embestatus = 'E' and lr.rectiporechazo(+)='T' ";
    } else {
      sql = "";
    }
    return sql;
  }

  public String status(String statusF, String tRechazo, String fechEntrega)
  {
    String estatus = "";
    if (statusF.equals("A")) {
      estatus = "Por Embarcar";
    } else if (statusF.equals("S")) {
      estatus = "Por Surtir";
    } else if (statusF.equals("P")) {
      estatus = "En Surtido";
    } else if (statusF.equals("R")) {
      estatus = "Por Embarcar";
    } else if (statusF.equals("T"))
    {
      estatus = "En Transito";
    }
    else if (statusF.equals("E"))
    {
      if (tRechazo == null) {
        estatus = "Entregado";
      } else if (tRechazo.equals("P")) {
        estatus = "Entregado Parcial";
      } else if (tRechazo.equals("T")) {
        estatus = "Rechazado";
      } else {
        estatus = "Entregado";
      }
    }
    else {
      estatus = "Pendiente";
    }
    return estatus;
  }

  public String getAccion(String Accion)
  {
    String lAccion = "";
    if (Accion.equals("E")) {
      lAccion = "EN ESPERA";
    } else if (Accion.equals("R")) {
      lAccion = "REENVIO";
    } else if (Accion.equals("X")) {
      lAccion = "REEMBARQUE";
    } else if (Accion.equals("C")) {
      lAccion = "CANCELADO";
    } else if (Accion.equals("E")) {
      lAccion = "NA";
    }
    return lAccion;
  }

  public Integer calExDisponible(String real, String reservada)
  {
    int disp = 0;int exReal = 0;int exReserd = 0;
    try
    {
      exReal = Integer.parseInt(real);
      exReserd = Integer.parseInt(reservada);
      disp = exReal - exReserd;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      disp = 0;
    }
    return Integer.valueOf(disp);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    processRequest(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    processRequest(request, response);
  }

  public String getServletInfo()
  {
    return "Short description";
  }
}
