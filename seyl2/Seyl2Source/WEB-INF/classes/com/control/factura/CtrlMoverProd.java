package com.control.factura;

import com.dao.EventManager;
import com.dao.jdbc.DaoHelper;
import com.dao.util.HibernateUtil;
import com.entity.Liusuarios;
import com.sql.SQLUsuarios;
import com.util.EncriptadoSimple;
import com.util.Utilities;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CtrlMoverProd
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
        out.print(getMoverProd(request));
        break;
      case 2:
        out.print(getMoverProd(request));
        break;
      case 3:
        out.print(getMoverProd(request));
      }
    }
    finally
    {
      out.close();
    }
  }
/*
  public String usuarios(HttpServletRequest request)
  {
    String callback = Utilities.obtenParametro(request, "callback").replaceAll("'", "");
    String record = callback + "({total:0,records:[]});";
    String fechaIni = "";String fechaFin = "";String sqluser = "";String ordernBy = "";String dir = "";
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
        ArrayList arrayUsuarios = new ArrayList(0);
        arrayUsuarios.clear();
        DaoHelper daoHelper = new DaoHelper();
        String idUsuario = Utilities.obtenParametro(request, "idUsuario");
        if (!idUsuario.equals("")) {
          sqluser = " and usunombre like '" + idUsuario.replace("'", "") + "%' ";
        }
        dir = Utilities.obtenParametro(request, "dir");
        int start = Integer.parseInt(Utilities.obtenParametro(request, "start").equals("") ? "0" : Utilities.obtenParametro(request, "start"));
        int limit = Integer.parseInt(Utilities.obtenParametro(request, "limit").equals("") ? "0" : Utilities.obtenParametro(request, "limit"));
        arrayUsuarios = daoHelper.getResultTable(SQLUsuarios.obtnerUsuairosActivos(sqluser));
        if (arrayUsuarios != null)
        {
          if (!arrayUsuarios.isEmpty())
          {
            record = callback + "({total:" + arrayUsuarios.size() + ",records:[";
            for (int i = start; (i < start + limit) && (i < arrayUsuarios.size()); i++)
            {
              Hashtable hashAuxUsuarios = new Hashtable();
              hashAuxUsuarios.clear();
              hashAuxUsuarios = (Hashtable)arrayUsuarios.get(i);
              record = record + "{";
              record = record + "elegir:\"<u><a href=# onClick=getDetalleUsuario(" + hashAuxUsuarios.get("USUCLAVE") + ") style='color:#1CA4D7; cursor:pointer;'>Elegir</a></u> \" ,";
              record = record + "nombre:'" + (hashAuxUsuarios.get("NOMBRE").equals("") ? "NA" : hashAuxUsuarios.get("NOMBRE")) + "',";
              record = record + "estatus:'" + (hashAuxUsuarios.get("USUESTATUS").equals("") ? "NA" : hashAuxUsuarios.get("USUESTATUS")) + "',";
              record = record + "login:'" + (hashAuxUsuarios.get("USULOGIN").equals("") ? "NA" : hashAuxUsuarios.get("USULOGIN")) + "',";
              record = record + "contrasena:'" + (hashAuxUsuarios.get("USUPASSWORDWEB").equals("") ? "NA" : hashAuxUsuarios.get("USUPASSWORDWEB")) + "'";
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

  public String getDetalleUsuario(HttpServletRequest request)
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
        String idUsuario = Utilities.obtenParametro(request, "idUsuario");
        Liusuarios usuarios = (Liusuarios)EventManager.getSingleList(Liusuarios.class, Integer.valueOf(Integer.parseInt(idUsuario)));
        if (usuarios != null)
        {
          respuesta = "{success:true,data:{iduser:'" + usuarios.getUsuclave() + "'," + "nombre:'" + usuarios.getUsunombre() + "'," + "usuario:'" + usuarios.getUsulogin() + "'," + "contrasena:'" + (usuarios.getUsupasswordweb() == null ? "" : EncriptadoSimple.ClaveToString(usuarios.getUsupasswordweb())) + "'";




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

  public String getCambioPassword(HttpServletRequest request)
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
        String idUsuario = Utilities.obtenParametro(request, "idUser");
        String password = Utilities.obtenParametro(request, "password");
        Liusuarios usuarios = (Liusuarios)EventManager.getSingleList(Liusuarios.class, Integer.valueOf(Integer.parseInt(idUsuario)));
        if (usuarios != null)
        {
          usuarios.setUsupasswordweb(EncriptadoSimple.StringToClave(password));
          em.merge(usuarios);
          em.getTransaction().commit();
        }
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
  
  */
  
  public String getMoverProd(HttpServletRequest request)
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
        String claveProd = Utilities.obtenParametro(request, "clave");
        String descripcionProd = Utilities.obtenParametro(request, "descripcion");
        /*
        Liusuarios usuarios = (Liusuarios)EventManager.getSingleList(Liusuarios.class, Integer.valueOf(Integer.parseInt(idUsuario)));
        if (usuarios != null)
        {
          usuarios.setUsupasswordweb(EncriptadoSimple.StringToClave(password));
          em.merge(usuarios);
          em.getTransaction().commit();
        }*/
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
