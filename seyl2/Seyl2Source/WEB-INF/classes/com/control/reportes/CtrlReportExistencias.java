package com.control.reportes;

import com.entity.Liempresas;
import com.entity.Liusuarios;
import com.util.CompilerReportDinQuery;
import com.util.GetNameReport;
import com.util.Utilities;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CtrlReportExistencias
  extends HttpServlet
{
  private String pathFile;
  private String diagonal;
  private String pathXml;

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    try
    {
      String query = "";
      HttpSession session = request.getSession(false);
      Liusuarios usr = Utilities.ReactivarSession(request);
      // Liempresas emp = Utilities.ObtenerEmpresa(usr.getUsuempresa().intValue());
      GetNameReport objNamereport = new GetNameReport();
      Map param = new HashMap();
      CompilerReportDinQuery compiler = new CompilerReportDinQuery();
      String idProducts = Utilities.obtenParametro(request, "idProducts").replaceAll("'", "");
      String idAlmacen = Utilities.obtenParametro(request, "idalmacen").replaceAll("'", "");
      String nameProducts = Utilities.obtenParametro(request, "nomProduct").replaceAll("'", "");
      String nameAlmacen = Utilities.obtenParametro(request, "nomAlmacen").replaceAll("'", "");
      String Famili = Utilities.obtenParametro(request, "idFamili").replaceAll("'", "");
      String Line = Utilities.obtenParametro(request, "idLine").replaceAll("'", "");
      String tipoProd = Utilities.obtenParametro(request, "idTipoProd").replaceAll("'", "");
      if (nameProducts.equals("")) {
        nameProducts = "";
      } else if (nameProducts.equals("null")) {
        nameProducts = "";
      }
      if (!nameProducts.equals("")) {
        param.put("NOMPRODUCTO", nameProducts);
      } else {
        param.put("NOMPRODUCTO", null);
      }
      if (nameAlmacen.equals("")) {
        nameAlmacen = "";
      } else if (nameAlmacen.equals("null")) {
        nameAlmacen = "";
      }
      if (!nameAlmacen.equals("")) {
        param.put("NOMALMACEN", nameAlmacen);
      } else {
        param.put("NOMALMACEN", null);
      }
      /*
      if (emp != null) {
        param.put("EMPRESA", emp.getEmpnombre());
      }
      */
      param.put("EMPRESA", usr.getUsuempresa().toString());
      if (!idProducts.equals("")) {
        idProducts = " and lp.prdclave like '" + idProducts + "%' ";
      }
      if (Famili.equals("")) {
        Famili = "";
      } else if (Famili.equals("null")) {
        Famili = "";
      }
      if (Line.equals("")) {
        Line = "";
      } else if (Line.equals("null")) {
        Line = "";
      }
      if (tipoProd.equals("")) {
        tipoProd = "";
      } else if (tipoProd.equals("null")) {
        tipoProd = "";
      }
      if (idAlmacen.equals("")) {
        idAlmacen = "";
      } else if (idAlmacen.equals("null")) {
        idAlmacen = "";
      }
      if (!idAlmacen.equals("")) {
        idAlmacen = " and li.invaloid='" + idAlmacen + "'";
      }
      if (!Famili.equals("")) {
        idProducts = idProducts + " and lp.prdlinid='" + Famili + "' ";
      }
 //     idProducts = idProducts + (!Famili.equals("") ? " and lp.prdfamilia='" + Famili + "' " : "");
 //     idProducts = idProducts + " and lp.prdfamilia='05' ";
      idProducts = idProducts + (!Line.equals("") ? " and lp.prdfamid='" + Line + "' " : "");
      idProducts = idProducts + (!tipoProd.equals("") ? " and lp.prdtprid='" + tipoProd + "' " : "");

      query = "select lp.prdclave, lp.prdnombre as prddescripcion, lp.prdlinid as prdlinea, ln.linnombre as lindescripcion, lp.prdfamid as prdfamilia, lf.famnombre as famdescripcion, lp.prdualid as prdunidad,";
      query = query + "li.invreal, li.invreservado, li.invaloid as invalmacen, lp.prdcosto, la.alonombre as almnombre,";
      query = query + "lp.prdpzascja as prdpzascaja, lp.prdestatus as priestatus, lt.tprnombre as tprdescripcion ";
      query = query + "from seproductos lp  ";
      query = query + "inner join seinventario li on li.invprdid=lp.prdid ";
      query = query + "inner join sealmlogicos la on la.aloid = li.invaloid ";
      query = query + "inner join sealmacenes laf on laf.almid = la.aloalmid ";
      query = query + "inner join sefamilias lf on lp.prdfamid=lf.famid and lp.prdempid=lf.famempid  ";
      query = query + "inner join selineas ln on lp.prdlinid=ln.linid and lp.prdempid=ln.linempid ";
      query = query + "inner join setipoproducto lt on lt.tprid=lp.prdtprid and lt.tprempid=lp.prdempid ";
      query = query + "where lp.prdempid= " + usr.getUsuempresa() + idAlmacen + " and li.invreal>0 " + idProducts;
      
      String nombreReport = "reporteExcistencias" + objNamereport.obtenerNombre() + ".xls";
      session.setAttribute("ex", nombreReport);

      this.pathFile = session.getServletContext().getRealPath("/");
      this.diagonal = this.pathFile.substring(this.pathFile.length() - 1, this.pathFile.length());
      this.pathFile = (this.pathFile + "ReportesCompilados" + this.diagonal + nombreReport);
      this.pathXml = session.getServletContext().getRealPath("/");
      param.put("DIR", this.pathXml + "ReportesXML" + this.diagonal + "Generales" + this.diagonal);
      this.pathXml = (this.pathXml + "ReportesXML" + this.diagonal + "Generales" + this.diagonal + "reportExistencia.jrxml");
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
