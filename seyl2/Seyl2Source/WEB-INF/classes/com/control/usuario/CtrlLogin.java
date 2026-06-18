package com.control.usuario;

import com.dao.EventManager;
import com.dao.util.HibernateUtil;
import com.entity.Liadminmodulo;
import com.entity.Liusuarios;
import com.sql.SQLControl;
import com.sql.SQLUsuarios;
import com.util.Utilities;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CtrlLogin
  extends HttpServlet
{
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html;charset=ISO-8859-1");
    PrintWriter out = response.getWriter();
    String bnd = Utilities.obtenParametro(request, "bnd");
    if (bnd.equals("1")) {
      out.print(obtenUsuarioValido(request, response));
    } else if (bnd.equals("2")) {
      out.print(obtenModulosUsuario((Liusuarios)request.getSession().getAttribute("usuario")));
    } else if (bnd.equals("3")) {
      out.print(obtenAccionesUsuario((Liusuarios)request.getSession().getAttribute("usuario")));
    }
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

  public String obtenUsuarioValido(HttpServletRequest request, HttpServletResponse response)
  {
    String strJson = "{success: false,msg: 'El usuario no existe o la información es incorrecta.'}";
    try
    {
      String strTema = Utilities.obtenParametro(request, "tema");

      HttpSession session = request.getSession(false);
      Liusuarios u = obtenUsuario(request);
      if (u != null)
      {
        session.setAttribute("modulos", obtenModulosUsuario(u));

        strJson = "{success:true, msg: 'El usuario es correcto',tema:'" + (strTema.equals("") ? "default" : strTema) + "'}";
      }
    }
    catch (Exception e)
    {
      strJson = "{success: false,msg: 'El usuario no existe o la información es incorrecta.'}";
      e.printStackTrace();
    }
    finally
    {
      HibernateUtil.closeSession();
    }
    return strJson;
  }

  private Liusuarios obtenUsuario(HttpServletRequest request)
  {
    Liusuarios usrU = null;
    try
    {
      String strUsername = Utilities.obtenParametro(request, "usuario");
      String strPassword = Utilities.obtenParametro(request, "contrasena");

      Vector vecUsr = new Vector();
      vecUsr.add(strUsername);
      vecUsr.add(strPassword);

      List lstUsr = EventManager.getArrayParameter(SQLUsuarios.obtenUsuario(), vecUsr);
      if ((lstUsr != null) && (!lstUsr.isEmpty())) {
        usrU = (Liusuarios)lstUsr.get(0);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      HibernateUtil.closeSession();
    }
    return usrU;
  }

  private String obtenModulosUsuario(Liusuarios u)
  {
    String strModulos = "[{xtype:'label', text:'No tiene módulos asignados',style:'color:#fea438;font-weight:bold;'}]";
    try
    {
      if (u != null)
      {
        Vector vecPrm = new Vector();
        vecPrm.add(u.getUsuclave());
        List lstModulos = EventManager.getArrayParameter(SQLControl.obtenModulos(), vecPrm);
        if (!lstModulos.isEmpty())
        {
          strModulos = "[{xtype:'label', text:'Módulos: ',style:'color:#fea438;font-weight:bold;'},";
          for (int i = 0; i < lstModulos.size(); i++)
          {
            Object[] obj = (Object[])(Object[])lstModulos.get(i);
            Liadminmodulo am = (Liadminmodulo)obj[0];
            strModulos = strModulos + "'-',{xtype:'button',text:'" + am.getAmnombremodulo() + "',enableToggle:'true',toggleGroup:'mdl',disabledClass:'x-btn-disabled-modulos'," + "     toggleHandler:function(btn,pressed){" + "         if(btn==this){" + "             if(pressed==true){" + "                 btn.disable();" + "                 setModuloAll(" + "                     'panelCentral'," + "                     new com.punto.pen.ContactCenter({" + "                         id:'pnlCC'," + "                         items:[" + "                             new com.punto.pen.TreePanel({id:'pnlTreeAcciones',url:contexto+'/Usuario',prm:{bnd:3},region:'west'})," + "                             {xtype:'panel',id:'pnlCenter',region:'center',layout:'fit'," + "                                 items:[" + "                                     new com.punto.pen.PanelBuscadorPaciente({" + "                                         id:'pnlBuscadorPaciente'" + "                                     })" + "                                 ]" + "                             }" + "                         ]" + "                     })" + "                 );" + "             }else{" + "                 btn.enable();" + "             }" + "         }" + "     }" + "},";
          }
          strModulos = strModulos.substring(0, strModulos.length() - 1) + "]";
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      strModulos = "[{xtype:'label', text:'No tiene módulos asignados',style:'color:#fea438;font-weight:bold;'}]";
    }
    finally
    {
      HibernateUtil.closeSession();
    }
    return strModulos;
  }

  private String obtenAccionesUsuario(Liusuarios u)
  {
    String strAcciones = "[{xtype:'label', text:'No tiene acciones asignadas',style:'color:#fea438;font-weight:bold;'}]";
    try
    {
      if (u != null)
      {
        Vector vecPrm = new Vector();
        vecPrm.add(u.getUsuclave());
        List lstModulos = EventManager.getArrayParameter(SQLControl.obtenAcciones(), vecPrm);
        if (!lstModulos.isEmpty())
        {
          strAcciones = "[{xtype:'label', text:'Módulos: ',style:'color:#fea438;font-weight:bold;'},";
          for (int i = 0; i < lstModulos.size(); i++)
          {
            Object[] obj = (Object[])(Object[])lstModulos.get(i);
            Liadminmodulo am = (Liadminmodulo)obj[0];
            strAcciones = strAcciones + "'-',{xtype:'button',text:'" + am.getAmnombremodulo() + "',enableToggle:'true',toggleGroup:'mdl',disabledClass:'x-btn-disabled-modulos'," + "     toggleHandler:function(btn,pressed){" + "         if(btn==this){" + "             if(pressed==true){" + "                 btn.disable();" + "                 btn.disable();" + "             }else{" + "                 btn.enable();" + "             }" + "         }" + "     }" + "},";
          }
          strAcciones = strAcciones.substring(0, strAcciones.length() - 1) + "]";
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      strAcciones = "[{xtype:'label', text:'No tiene acciones asignadas',style:'color:#fea438;font-weight:bold;'}]";
    }
    finally
    {
      HibernateUtil.closeSession();
    }
    return strAcciones;
  }
}
