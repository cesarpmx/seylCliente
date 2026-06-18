package com.control.reportes;

import com.control.factura.CtrlUtilFactura;
import com.dao.jdbc.DaoHelper;
import com.entity.Liempresas;
import com.entity.Liusuarios;
import com.sql.SQLFacturas;
import com.util.CompilerReportDinQuery;
import com.util.GetNameReport;
import com.util.Utilities;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReportFactEstatus
  extends HttpServlet
{
  private String nombre_origen;
  private String pathFile;
  private String diagonal;
  private String pathXml;

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html;charset=ISO-8859-1");
    try
    {
      // String query1 = "";
      String query2 = "";
      String query = "";
      HttpSession session = request.getSession(false);
      Liusuarios usr = Utilities.ReactivarSession(request);
      // Liempresas emp = Utilities.ObtenerEmpresa(usr.getUsuempresa().intValue());
      GetNameReport objNamereport = new GetNameReport();
      Map param = new HashMap();
      DaoHelper daoHelper = new DaoHelper();
      CompilerReportDinQuery compiler = new CompilerReportDinQuery();
      String fechaIni = Utilities.obtenParametro(request, "fInicio").replaceAll("'", "");
      String fechaFin = Utilities.obtenParametro(request, "fFin").replaceAll("'", "");
      String estatus = Utilities.obtenParametro(request, "status").replaceAll("'", "");
      String IdStatus = Utilities.obtenParametro(request, "idStatus").replaceAll("'", "");
      String idPorteo = Utilities.obtenParametro(request, "idPorteo").replaceAll("'", "");
      CtrlUtilFactura objUtil = new CtrlUtilFactura();
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

      if (!fechaIni.equals("")) {
        param.put("FECHAINI", fechaIni);
      }
      if (!fechaFin.equals("")) {
        param.put("FECHAFIN", fechaFin);
      }
      if (!estatus.equals("")) {
        param.put("ESTATUS", estatus);
      } else {
        param.put("ESTATUS", null);
      }
      /*
      if (emp != null) {
        param.put("EMPRESA", emp.getEmpnombre());
      }*/
      if (!IdStatus.equals("")) {
        query2 = objUtil.getSqlStatus(IdStatus);
      }
      if (idPorteo.equals("")) {
        idPorteo = "";
      } else if (idPorteo.equals("null")) {
        idPorteo = "";
      }
      query2 = query2 + (!idPorteo.equals("") ? " and le.embalmid='" + idPorteo + "' " : "");

      query = "select le.embestatus,lr.rectiporechazo,le.embfolio,le.embref1,le.embenvio,lc.cliclave,lc.clinombre,lp.pobnombre,";
      query = query + "le.embcajas,le.embvalor,le.embfecfac,le.embfecrec,le.embfeccita,le.embfecemb,le.embfecent,le.embcoms,";
      query = query + "ee.evereferencia, ee.eveproveedor, ee.evecosto, le.embdteid ";
      query = query + "from seembarques le, seclientrega lco, seclientes lc, sepoblaciones lp, serechazos lr, seevenembarques ee ";
      query = query + "where (lp.pobid=le.embpobid) and (lco.cenid = le.embcenid and lc.cliid=lco.cencliid)  and ";
      query = query + "(lr.recembid(+) = le.embid) and (trunc(le.embfecfac) between trunc(to_date('" + fechaIni;
      query = query + "', 'DD/MM/YYYY')) and trunc(to_date('" + fechaFin + "', 'DD/MM/YYYY'))) and le.embempid=" + usr.getUsuempresa();
      query = query + " and ee.eveembid(+) = le.embid and ee.evetevid(+)='TPF' and ee.eveproveedor(+) is not null ";
      query = query + query2 + "order by le.embfolio, le.embenvio";

      String nombreReport = "reporteFacturas" + objNamereport.obtenerNombre() + ".xls";
      session.setAttribute("ex", nombreReport);

      this.pathFile = session.getServletContext().getRealPath("/");
      this.diagonal = this.pathFile.substring(this.pathFile.length() - 1, this.pathFile.length());
      this.pathFile = (this.pathFile + "ReportesCompilados" + this.diagonal + nombreReport);
      this.pathXml = session.getServletContext().getRealPath("/");
      param.put("DIR", this.pathXml + "ReportesXML" + this.diagonal + "Generales" + this.diagonal);
      this.pathXml = (this.pathXml + "ReportesXML" + this.diagonal + "Generales" + this.diagonal + "reportFacturas.jrxml");
      compiler.setPath(this.pathXml);

      compiler.setUrlFile(this.pathFile);
      compiler.viewExcel(response, param, query, nombreReport);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.out.println(e.getMessage());
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
}
