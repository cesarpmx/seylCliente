package com.control.usuario;

import com.dao.EventManager;
import com.dao.util.HibernateUtil;
import com.entity.Liusuarios;
import com.sql.SQLUsuarios;
import com.util.EncriptadoSimple;
import com.util.Utilities;
import com.util.email.MailHelper;
import com.util.email.PropertiesLoader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CtrlRestablecerContrasena
  extends HttpServlet
{
  private static Properties props;
  EventManager objEventManager = new EventManager();
  EncriptadoSimple objEncriptado = new EncriptadoSimple();

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    String strJson = "{success: false,msg: 'La información es incorrecta verifique sus datos.'}";
    try
    {
      String valorRadio = "";String email = "";String usuario = "";
      valorRadio = Utilities.obtenParametro(request, "rd_CRM01_6");
      email = Utilities.obtenParametro(request, "email");
      usuario = Utilities.obtenParametro(request, "nameusuario");
      String wnd = Utilities.obtenParametro(request, "wnd");
      if (valorRadio.equals("Si")) {
        out.print(enviarContrasena(email, wnd));
      } else if (valorRadio.equals("No")) {
        out.print(enviarUsuario(usuario, wnd));
      }
    }
    finally
    {
      out.close();
      HibernateUtil.closeSession();
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

  public String enviarContrasena(String email, String idWnd)
  {
    String strJson = "{success: false,msg: 'La información es incorrecta verifique sus datos.'}";
    try
    {
      String nombreUsuario = "";String apellido = "";String correo = "";String alias = "";String contrasena = "";String Mfrom = "";String Msubject = "";
      Integer a = Integer.valueOf(3);
      MailHelper mhelper = new MailHelper(props);
      Hashtable hash = new Hashtable(0);
      hash.clear();
      Hashtable mensaje = new Hashtable(0);
      mensaje.clear();
      Vector params = new Vector(0);
      params.add(email);
      Vector parametro = new Vector(0);
      parametro.add(a);
      List usuarioAux = EventManager.getArrayParameter(SQLUsuarios.obtenerNombreUsuario(), params);
      Liusuarios usrU = (Liusuarios)usuarioAux.get(0);








      mensaje.put("to", correo);
      mensaje.put("mensaje", "<table><tr><td>Estimado " + nombreUsuario + " </td></tr><tr><td>De acuerdo a tu solicitud te hacemos llegar su nombre de usuario para acceder a <strong>Siempre a tu Lado.</strong></td></tr><tr><td><strong>nombre de usuario: " + alias + "</strong></td></tr><tr><td>&nbsp;</td></tr><tr><td>Nota: La información contenida en este mensaje así como  cualquier archivo dentro de él, es confidencial y queda restringida Ãºnicamente  para el uso de las personas o entidades a las cuales es dirigido.</td></tr></table>");
      mensaje.put("from", Mfrom);
      mensaje.put("subject", Msubject);

      Address from = new InternetAddress((String)mensaje.get("from"));
      Address to = new InternetAddress((String)mensaje.get("to"));
      hash.put("mensaje", mensaje.get("mensaje"));
      hash.put("subject", mensaje.get("subject"));
      hash.put("from", from);
      hash.put("to", to);
      if (mhelper.sendmail(hash)) {
        strJson = "{success: true,msg: 'Su solicitud a sido exitosa, se ha enviado un mensaje al siguiente correo " + correo + ".',wnd:'" + idWnd + "'}";
      }
    }
    catch (Exception ex)
    {
      strJson = "{success: false,msg: 'No tenemos registrado ese correo o es incorrecto, verifique el correo.'}";
    }
    return strJson;
  }

  public String enviarUsuario(String usuario, String idWnd)
  {
    String strJson = "{success: false,msg: 'La información es incorrecta verifique sus datos.'}";
    try
    {
      String nombreUsuario = "";String apellido = "";String correo = "";String alias = "";String contrasena = "";String Mfrom = "";String Msubject = "";
      int a = 4;
      MailHelper mhelper = new MailHelper(props);
      Hashtable hash = new Hashtable(0);
      hash.clear();
      Hashtable mensaje = new Hashtable(0);
      mensaje.clear();
      Vector params = new Vector(0);
      params.add(usuario);
      Vector paramt = new Vector(0);
      paramt.add(Integer.valueOf(a));
      List usuarioAux = EventManager.getArrayParameter(SQLUsuarios.obtenerContrasena(), params);









      mensaje.put("to", correo);
      mensaje.put("mensaje", "<table><tr><td>Estimado " + nombreUsuario + " </td></tr><tr><td>De acuerdo a tu solicitud te hacemos llegar su contraseÃ±a para acceder a <strong>Siempre a tu Lado.</strong></td></tr><tr><td><strong>su contraseÃ±a es la siguiente: " + contrasena + "</strong></td></tr><tr><td>&nbsp;</td></tr><tr><td>Nota: La información contenida en este mensaje así como  cualquier archivo dentro de él, es confidencial y queda restringida Ãºnicamente  para el uso de las personas o entidades a las cuales es dirigido.</td></tr></table>");
      mensaje.put("from", Mfrom);
      mensaje.put("subject", Msubject);
      Address from = new InternetAddress((String)mensaje.get("from"));
      Address to = new InternetAddress((String)mensaje.get("to"));
      hash.put("mensaje", mensaje.get("mensaje"));
      hash.put("subject", mensaje.get("subject"));
      hash.put("from", from);
      hash.put("to", to);
      if (mhelper.sendmail(hash)) {
        strJson = "{success: true,msg: 'Su solicitud a sido exitosa, se ha enviado un mensaje al siguiente correo " + correo + ".',wnd:'" + idWnd + "'}";
      }
    }
    catch (Exception ex)
    {
      strJson = "{success: false,msg: 'La información es incorrecta verifique sus datos.'}";
    }
    return strJson;
  }
}
