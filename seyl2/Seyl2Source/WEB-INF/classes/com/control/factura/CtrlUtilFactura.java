package com.control.factura;

public class CtrlUtilFactura
{
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

  public static String getOrdSqlBusExis(String Orderby, String dir)
  {
    String sqlOrden = "";
    if (Orderby.equals("clvProducto")) {
      sqlOrden = "order by lp.prdclave " + dir;
    } else if (Orderby.equals("decripcion")) {
      sqlOrden = "order by lp.prdnombre " + dir;
    } else if (Orderby.equals("linea")) {
      sqlOrden = "order by lp.prdlinid " + dir;
    } else if (Orderby.equals("familia")) {
      sqlOrden = "order by lp.prdfamid " + dir;
    } else if (Orderby.equals("uniAlmacenado")) {
      sqlOrden = "order by lp.prdualid " + dir;
    } else if (Orderby.equals("existReal")) {
      sqlOrden = "order by li.invreal " + dir;
    } else if (Orderby.equals("existReservada")) {
      sqlOrden = "order by li.invreservado " + dir;
    }
    return sqlOrden;
  }

  public static String getOrdSqlBusFac(String Orderby, String dir)
  {
    String sqlOrden = "";
    if (Orderby.equals("estatus")) {
      sqlOrden = " order by le.embestatus " + dir;
    } else if (Orderby.equals("factura")) {
      sqlOrden = " order by le.embfolio " + dir;
    } else if (Orderby.equals("referencia")) {
      sqlOrden = " order by le.embref1 " + dir;
    } else if (Orderby.equals("envio")) {
      sqlOrden = " order by le.embenvio " + dir;
    } else if (Orderby.equals("numCliente")) {
      sqlOrden = " order by lc.cliclave " + dir;
    } else if (Orderby.equals("nombCliente")) {
      sqlOrden = " order by lc.clinombre " + dir;
    } else if (Orderby.equals("destino")) {
      sqlOrden = " order by lp.pobnombre " + dir;
    } else if (Orderby.equals("numCajas")) {
      sqlOrden = " order by le.embcajas " + dir;
    } else if (Orderby.equals("importe")) {
      sqlOrden = " order by le.embvalor " + dir;
    } else if (Orderby.equals("fechFactura")) {
      sqlOrden = " order by le.embfecfac " + dir;
    } else if (Orderby.equals("fechIngreso")) {
      sqlOrden = " order by le.embfecrec " + dir;
    } else if (Orderby.equals("cita")) {
      sqlOrden = " order by le.embfeccita " + dir;
    } else if (Orderby.equals("fechEmbarq")) {
      sqlOrden = " order by le.embfecemb " + dir;
    } else if (Orderby.equals("fechEntreg")) {
      sqlOrden = " order by le.embfecent " + dir;
    } else if (Orderby.equals("comentarios")) {
      sqlOrden = " order by le.embcoms " + dir;
    } else {
      sqlOrden = " order by le.embfolio";
    }
    return sqlOrden;
  }
}
