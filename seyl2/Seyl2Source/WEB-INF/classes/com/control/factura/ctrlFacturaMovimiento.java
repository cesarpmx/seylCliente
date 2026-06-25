package com.control.factura;

import com.dao.EventManager;
import com.dao.jdbc.DaoHelper;
import com.dao.util.HibernateUtil;
import com.entity.Lialmacenes;
import com.entity.Liempresas;
import com.entity.Limovalmacen;
import com.entity.Seremisiones;
import com.entity.Liusuarios;
import com.entity.Liprdmover;
import com.sql.SQLFacturas;
import com.util.EncriptadoSimple;
import com.util.Fecha;
import com.util.Utilities;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ctrlFacturaMovimiento
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
        out.print(getNoFact(request));
        break;
      case 2:
        out.print(getMoviAlmacPrad(request));
        break;
      case 3:
        out.print(getProductos(request));
        break;
      case 4:
        out.print(getDatsGnrlsRemision(request));
        break;
      case 5:
        out.print(getDetalleMvimts(request));
        // break;
      case 6:
        out.print(getMoverProducto(request));
      case 7:
        out.print(getMover(request));    
      }
    }
    finally
    {
      out.close();
    }
  }

  public String getNoFact(HttpServletRequest request)
  {
    String json = "";
    EntityManager em = HibernateUtil.getEntityManager();
    Liusuarios usr = Utilities.ReactivarSession(request);
    if (usr != null) {
      try
      {
        if (!em.getTransaction().isActive()) {
          em.getTransaction().begin();
        }
        Integer idProducto = Integer.valueOf(Utilities.obtenParametro(request, "idProduct").replaceAll("'", ""));
        String idAlmacen = Utilities.obtenParametro(request, "idalmacen").replaceAll("'", "");
        String fecha = Utilities.obtenParametro(request, "fechIni").replaceAll("'", "");
        String fechFin = Utilities.obtenParametro(request, "fechFin").replaceAll("'", "");
        DaoHelper daoHelper = new DaoHelper();
        ArrayList arraySaldo = new ArrayList(0);
        arraySaldo.clear();
        arraySaldo = daoHelper.getResultTable(SQLFacturas.getSaldoMov(usr.getUsuempresa(), idProducto, idAlmacen, fecha));
        if ((arraySaldo != null) && (!arraySaldo.isEmpty()))
        {
          Hashtable hashAuxExistencia = new Hashtable();
          hashAuxExistencia.clear();
          hashAuxExistencia = (Hashtable)arraySaldo.get(0);
          json = "{success:true,";
          json = json + "data:{idTipAlmcenExis:'" + idAlmacen + "',idFechaIniExis:'" + fecha + "',idFechaFinExis:'" + fechFin + "'";
          json = json + ",saldo:" + (String)hashAuxExistencia.get("SALDO") + "}}";
        }
        else
        {
          json = "{success:true,data:{idTipAlmcenExis:'" + idAlmacen + "',idFechaIniExis:'" + fecha + "',idFechaFinExis:'" + fechFin + "',saldo:0}}";
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        em.getTransaction().rollback();
        json = "{success:false,data:{saldo:0}}";
      }
      finally
      {
        HibernateUtil.closeSession();
      }
    }
    return json;
  }

  public static String getMoviAlmacPrad(HttpServletRequest request)
  {
    String callback = Utilities.obtenParametro(request, "callback").replaceAll("'", "");
    String record = callback + "(total:0,recordsMovimt:[]});";
    int saldo = 0;int saloAux = 0;int saloAuxs = 0;int saloAuxt = 0;
    Liusuarios usr = Utilities.ReactivarSession(request);
    if (usr != null)
    {
      EntityManager em = HibernateUtil.getEntityManager();
      if (!em.getTransaction().isActive()) {
        em.getTransaction().begin();
      }
      try
      {
        List lst = null;
        ArrayList arrayMovimtos = new ArrayList(0);
        arrayMovimtos.clear();
        int start = Integer.parseInt(Utilities.obtenParametro(request, "start").equals("") ? "0" : Utilities.obtenParametro(request, "start"));
        int limit = Integer.parseInt(Utilities.obtenParametro(request, "limit").equals("") ? "0" : Utilities.obtenParametro(request, "limit"));
        String idAlmacen = Utilities.obtenParametro(request, "idAlmacen").replaceAll("'", "");
        Integer idProduct = Integer.valueOf(Utilities.obtenParametro(request, "idProduct").replaceAll("'", ""));
        String FechIni = Utilities.obtenParametro(request, "FechIni").replaceAll("'", "");
        String FechFin = Utilities.obtenParametro(request, "FechFin").replaceAll("'", "");
        DaoHelper daoHelper = new DaoHelper();
        if (!idAlmacen.equals(""))
        {
          ArrayList arraySaldo = new ArrayList(0);
          arraySaldo.clear();
          arraySaldo = daoHelper.getResultTable(SQLFacturas.getSaldoMov(usr.getUsuempresa(), idProduct, idAlmacen, FechIni));
          if ((arraySaldo != null) && (!arraySaldo.isEmpty()))
          {
            Hashtable hashAuxSaldo = new Hashtable();
            hashAuxSaldo.clear();
            hashAuxSaldo = (Hashtable)arraySaldo.get(0);
            saldo = Integer.parseInt((String)hashAuxSaldo.get("SALDO"));
          }
        }
        
        if (!idAlmacen.equals(""))
        {
          // Vector vecParams = new Vector();
          // vecParams.clear();
          // vecParams.add(usr.getUsuempresa());
          // vecParams.add(idAlmacen);
          arrayMovimtos = daoHelper.getResultTable(SQLFacturas.getConMovProd(FechIni, FechFin, idProduct, idAlmacen));
        }
        
        if (arrayMovimtos != null)
        {
          if (!arrayMovimtos.isEmpty())
          {
            record = callback + "({total:" + arrayMovimtos.size() + ",recordsMovimt:[";
            for (int i = start; (i < start + limit) && (i < arrayMovimtos.size()); i++)
            {
              Hashtable hashAuxMovimtos = new Hashtable();
              hashAuxMovimtos.clear();
              hashAuxMovimtos = (Hashtable)arrayMovimtos.get(i);
 
              
              record = record + "{";
              record = record + "elegir:\"<u><a href=# onClick=getRemAlmacen(" + (String)hashAuxMovimtos.get("DREREMISION") + ") style='color:#1CA4D7; cursor:pointer;'>Ver</a></u> \" ,";
              record = record + "fechaFact:'" + (String)hashAuxMovimtos.get("DREFECHA") + "',";
              record = record + "remision:'" + (String)hashAuxMovimtos.get("DREREMISION") + "',";
              record = record + "referencia:'" + (String)hashAuxMovimtos.get("REMREFERENCIA") + "',";
              record = record + "concepto:'" + (String)hashAuxMovimtos.get("MOVDESCRIPCION") + "',";
              record = record + "cantidad:'" + (String)hashAuxMovimtos.get("DRECANTIDAD") + "',";
              if (i == 0)
              {
                saloAux = saldo + Integer.parseInt((String)hashAuxMovimtos.get("DRECANTIDAD"));
                saloAuxt = saloAux;
              }
              else
              {
                saloAuxt += Integer.parseInt((String)hashAuxMovimtos.get("DRECANTIDAD"));
              }
              record = record + "saldoProd:'" + saloAuxt + "'";
              record = record + "},";
            }
            record = record.substring(0, record.length() - 1) + "]});";
          }
          else
          {
            record = callback + "({total:0,recordsMovimt:[]});";
          }
        }
        else {
          record = callback + "({total:0,recordsMovimt:[]});";
        }
      }
      catch (Exception e)
      {
        record = callback + "({total:0,recordsMovimt:[]);";
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
      record = callback + "({total:getSession(),recordsMovimt:[]});";
    }
    return record;
  }

  public String getProductos(HttpServletRequest request)
  {
    String callback = Utilities.obtenParametro(request, "callback").replaceAll("'", "");
    String record = callback + "({total:0,records:[]});";
    String sqlProdctLike = "";
    Liusuarios usr = Utilities.ReactivarSession(request);
    if (usr != null)
    {
      EntityManager em = HibernateUtil.getEntityManager();
      if (!em.getTransaction().isActive()) {
        em.getTransaction().begin();
      }
      try
      {
        ArrayList arrayProductos = new ArrayList(0);
        arrayProductos.clear();
        int start = Integer.parseInt(Utilities.obtenParametro(request, "start").equals("") ? "0" : Utilities.obtenParametro(request, "start"));
        int limit = Integer.parseInt(Utilities.obtenParametro(request, "limit").equals("") ? "0" : Utilities.obtenParametro(request, "limit"));
        String cvProduct = Utilities.obtenParametro(request, "cvProduct").replaceAll("'", "");
        String tipoBuq = Utilities.obtenParametro(request, "tipoBuq").replaceAll("'", "");
        if (!cvProduct.equals("")) {
          if (tipoBuq.equals("true")) {
            sqlProdctLike = " and prdclave like '" + cvProduct + "%' ";
          } else if (tipoBuq.equals("false")) {
            sqlProdctLike = " and prddescripcion like '" + cvProduct + "%' ";
          }
        }
        DaoHelper daoHelper = new DaoHelper();
        Vector vecParams = new Vector();
        vecParams.clear();
        vecParams.add(usr.getUsuempresa());
        arrayProductos = daoHelper.getResultTable(SQLFacturas.getProductosEmpresa(sqlProdctLike), vecParams);
        if (arrayProductos != null)
        {
          if (!arrayProductos.isEmpty())
          {
            record = callback + "({total:" + arrayProductos.size() + ",records:[";
            for (int i = start; (i < start + limit) && (i < arrayProductos.size()); i++)
            {
              Hashtable hashAuxProductos = new Hashtable();
              hashAuxProductos.clear();
              hashAuxProductos = (Hashtable)arrayProductos.get(i);
              record = record + "{";
              record = record + "cvProducto:'" + (String)hashAuxProductos.get("PRDCLAVE") + "',";
              record = record + "descripcion:'" + hashAuxProductos.get("PRDDESCRIPCION").toString().replaceAll("'", "") + "'";
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

  public String getDatsGnrlsRemision(HttpServletRequest request)
  {
    String json = "";
    EntityManager em = HibernateUtil.getEntityManager();
    Liusuarios usr = Utilities.ReactivarSession(request);
    if (usr != null) {
      try
      {
        if (!em.getTransaction().isActive()) {
          em.getTransaction().begin();
        }
        String idRemision = Utilities.obtenParametro(request, "idRemision").replaceAll("'", "");
        Seremisiones ListRemisiones = (Seremisiones)EventManager.getSingleList(Seremisiones.class, Integer.valueOf(Integer.parseInt(idRemision)));
        if (ListRemisiones != null)
        {
          json = "{success:true,";
          json = json + "data:{";
          // json = json + "idremRemision:'" + ListRemisiones.getRemfolio() + "'," + "idRemFecha:'" + (ListRemisiones.getRemfecha() == null ? "NA" : Fecha.getDateExtJSSQL(ListRemisiones.getRemfecha().toString(), "-", "/")) + "'," + "idRemFolio:'" + ListRemisiones.getRemreferencia() + "'," + "idRemEmpresa:'" + ListRemisiones.getRemempresa().getEmpclave() + " " + ListRemisiones.getRemempresa().getEmpnombre() + "'," + "idRemReferencia:'" + ListRemisiones.getRemreferencia() + "'," + "idRemAlmacen:'" + ListRemisiones.getRemalmacen().getAlmclave() + " " + ListRemisiones.getRemalmacen().getAlmnombre() + "',";
          json = json + "idremRemision:'" + ListRemisiones.getRemfolio() + "'," + "idRemFecha:'" + (ListRemisiones.getRemfecha() == null ? "NA" : Fecha.getDateExtJSSQL(ListRemisiones.getRemfecha().toString(), "-", "/")) + "'," + "idRemFolio:'" + ListRemisiones.getRemreferencia() + "'," + "idRemEmpresa:'" + "AGENCIAS DE PROMOTORIA NESTLE" + "'," + "idRemReferencia:'" + ListRemisiones.getRemreferencia() + "'," + "idRemAlmacen:'" +Utilities.obtenParametro(request, "idAlmacen")+ "',";
          Limovalmacen objMovalmacen = (Limovalmacen)EventManager.getSingleList(Limovalmacen.class, ListRemisiones.getRemtipo());
          json = json + "idremTipoMov:'" + objMovalmacen.getMovclave() + " " + objMovalmacen.getMovdescripcion() + "'," + "idRemComentarios:'" + (ListRemisiones.getRemcoms() == null ? "NA" : ListRemisiones.getRemcoms()) + "'}}";
        }
        else
        {
          json = "{success:false,data:{idremRemision:'',idRemFecha:'',idRemFolio:'',idRemEmpresa:'',idRemReferencia:'',idRemAlmacen:'',idremTipoMov:'',idRemComentarios:''}}";
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        em.getTransaction().rollback();
        json = "{success:false,data:{idremRemision:'',idRemFecha:'',idRemFolio:'',idRemEmpresa:'',idRemReferencia:'',idRemAlmacen:'',idremTipoMov:'',idRemComentarios:''}}";
      }
      finally
      {
        HibernateUtil.closeSession();
      }
    }
    return json;
  }

  public static String getDetalleMvimts(HttpServletRequest request)
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
        ArrayList arrayRemision = new ArrayList(0);
        arrayRemision.clear();
        int start = Integer.parseInt(Utilities.obtenParametro(request, "start").equals("") ? "0" : Utilities.obtenParametro(request, "start"));
        int limit = Integer.parseInt(Utilities.obtenParametro(request, "limit").equals("") ? "0" : Utilities.obtenParametro(request, "limit"));
        String idRemision = Utilities.obtenParametro(request, "idRemision").replaceAll("'", "");
        /*
        if (!idRemision.equals(""))
        {
          s.removeAttribute("idRemision");
          s.setAttribute("idRemision", idRemision);
          idRemision = s.getAttribute("idRemision").toString();
        }
        else
        {
          idRemision = s.getAttribute("idRemision").toString();
        }*/
        DaoHelper daoHelper = new DaoHelper();
        // Vector vecParams = new Vector();
        // vecParams.clear();
        // vecParams.add(Integer.valueOf(Integer.parseInt(idRemision)));
        arrayRemision = daoHelper.getResultTable(SQLFacturas.getDetMovimProducts(idRemision));
        
        if (arrayRemision != null)
        {
          
          if (!arrayRemision.isEmpty())
          {
              
            record = callback + "({total:" + arrayRemision.size() + ",records:[";
            
            for (int i = start; (i < start + limit) && (i < arrayRemision.size()); i++)
            {
              Hashtable hashAuxRemision = new Hashtable();
              hashAuxRemision.clear();
              hashAuxRemision = (Hashtable)arrayRemision.get(i);
              
              record = record + "{";
              record = record + "productoRemi:'" + (String)hashAuxRemision.get("DREPRODUCTO") + "',";
              record = record + "descripcionRemi:'" + (String)hashAuxRemision.get("PRDDESCRIPCION") + "',";
              record = record + "loteRemi:'" + (String)hashAuxRemision.get("DRELOTE") + "',";
              record = record + "fechaRemi:'" + (String)hashAuxRemision.get("DREFECFAC") + "',";
              record = record + "ubicacionRemi:'" + (String)hashAuxRemision.get("DREUBICACION") + "',";
              record = record + "cantidadRemi:'" + (String)hashAuxRemision.get("DRECANTIDAD") + "'";
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
  
  public String getMoverProducto(HttpServletRequest request)
  {
    String respuesta = "";
    Liusuarios usr = Utilities.ReactivarSession(request);
    if (usr != null)
    {
      EntityManager em = HibernateUtil.getEntityManager();
      if (!em.getTransaction().isActive()) {
        em.getTransaction().begin();
      }
      try
      { 
        //String idUsuario = Utilities.obtenParametro(request, "idUsuario");
        // int idUsuario = usr.getUsuclave();
        //Liusuarios usuarios = (Liusuarios)EventManager.getSingleList(Liusuarios.class, Integer.valueOf(Integer.parseInt(idUsuario)));
        //if (usuarios != null)
        if (usr != null)
        {
          //respuesta = "{success:true,data:{iduser:'" + usuarios.getUsuclave() + "'," + "nombre:'" + usuarios.getUsunombre() + "'," + "usuario:'" + usuarios.getUsulogin() + "'," + "contrasena:'" + (usuarios.getUsupasswordweb() == null ? "" : EncriptadoSimple.ClaveToString(usuarios.getUsupasswordweb())) + "'";
          //respuesta = respuesta + "}}";
            respuesta = "{success:true,data:{claveProd:" + Utilities.obtenParametro(request, "clave") + "," + "descripcionProd:" + Utilities.obtenParametro(request, "descripcionPrd") + "," + "almacenProd:" + Utilities.obtenParametro(request, "almacen") + "," + "disponibleProd:'" + Utilities.obtenParametro(request, "disponible") + "',";
            respuesta = respuesta + "cmbAccion:'" + "SAL" + "'," + "cantidadMov:'" + "" + "'," + "comentariosMov:'" + "" + "'";
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
  
  
  public String getMover(HttpServletRequest request)
  {
    String respuesta = "";
    Liusuarios usr = Utilities.ReactivarSession(request);
    if (usr != null)
    {
      EntityManager em = HibernateUtil.getEntityManager();
      if (!em.getTransaction().isActive()) {
        em.getTransaction().begin();
      }
      try
      {
        int idMov = 0;
        int idUsuario = usr.getUsuclave();
        String idProducto = Utilities.obtenParametro(request, "idProducto");
        String idAlmacen = Utilities.obtenParametro(request, "idAlmacen");
        Integer idCantidad = Integer.valueOf(Utilities.obtenParametro(request, "idCantidad"));
        // Integer idCantidad = 1;
        String idAccion = Utilities.obtenParametro(request, "idAccion");
        String idComentarios = Utilities.obtenParametro(request, "idComentarios");
        String idEstatus = "A";
        
        Liprdmover mov1 = new Liprdmover();
        mov1.setPmoproducto(idProducto);
        mov1.setPmoalmacen(idAlmacen);
        mov1.setPmocantidad(idCantidad);
        mov1.setPmoaccion(idAccion);
        mov1.setPmocoms(idComentarios);
        mov1.setPmoestatus(idEstatus);
        mov1.setPmousuario(idUsuario);
       
        em.merge(mov1);
        em.getTransaction().commit();
        
      }
      catch (Exception e)
      {
        e.printStackTrace();
        em.getTransaction().rollback();
      }
      finally
      {
        HibernateUtil.closeSession();
      }
    }
    return respuesta;
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
