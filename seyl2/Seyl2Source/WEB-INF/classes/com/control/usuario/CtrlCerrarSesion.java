package com.control.usuario;

import com.dao.util.HibernateUtil;
import com.util.GotoPage;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CtrlCerrarSesion
  extends HttpServlet
{
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = request.getSession(false);
    try
    {
      if (session.getAttribute("usuario") != null)
      {
        setBitacoraSalida(session);
        Enumeration enume = session.getAttributeNames();
        if (enume.hasMoreElements()) {
          session.removeAttribute((String)enume.nextElement());
        }
        session.invalidate();
      }
      else
      {
        session.invalidate();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      HibernateUtil.closeSessionFactory();
      System.gc();
      System.gc();
      System.gc();
      System.gc();
      GotoPage.gotoPage("/jsp_general/cerrar.jsp", request, response, getServletContext());
    }
  }

  /* Error */
  public void setBitacoraSalida(HttpSession session)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 19
    //   3: invokeinterface 4 2 0
    //   8: ifnull +58 -> 66
    //   11: invokestatic 20	com/dao/util/HibernateUtil:getEntityManager	()Ljavax/persistence/EntityManager;
    //   14: astore_2
    //   15: aload_1
    //   16: ldc 19
    //   18: invokeinterface 4 2 0
    //   23: checkcast 21	java/lang/Integer
    //   26: astore_3
    //   27: aload_2
    //   28: invokeinterface 22 1 0
    //   33: invokeinterface 23 1 0
    //   38: ifne +14 -> 52
    //   41: aload_2
    //   42: invokeinterface 22 1 0
    //   47: invokeinterface 24 1 0
    //   52: invokestatic 25	com/dao/util/HibernateUtil:closeSession	()V
    //   55: goto +11 -> 66
    //   58: astore 4
    //   60: invokestatic 25	com/dao/util/HibernateUtil:closeSession	()V
    //   63: aload 4
    //   65: athrow
    //   66: return
    // Line number table:
    //   Java source line #49	-> byte code offset #0
    //   Java source line #50	-> byte code offset #11
    //   Java source line #51	-> byte code offset #15
    //   Java source line #52	-> byte code offset #27
    //   Java source line #53	-> byte code offset #41
    //   Java source line #71	-> byte code offset #52
    //   Java source line #72	-> byte code offset #55
    //   Java source line #71	-> byte code offset #58
    //   Java source line #74	-> byte code offset #66
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	67	0	this	CtrlCerrarSesion
    //   0	67	1	session	HttpSession
    //   14	28	2	em	javax.persistence.EntityManager
    //   26	2	3	intIdAcceso	java.lang.Integer
    //   58	6	4	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   58	60	58	finally
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
