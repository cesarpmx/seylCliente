package com.sql;

public class SQLFacturas
{
  public static String obtFactura(String idFactura)
  {
    return "select le.embfechafac,le.embfecpedido,le.embref,le.embenvio,le.embcliente,lc.clinombre,lp.pobclave,lp.pobnombre, le.embcajas,le.embkilos,le.embvalor,le.embcoms,le.embfolentrada,le.embfecharec,le.embguia,le.embubicacion,le.embfechaprog, le.embfeccanped,le.embfolembarque,le.embfecembarque,le.embfecentrega,le.embforigen,le.embrefentrega,le.embtiporechazo, le.embclave,le.embaccion,le.embfoldevdoc,le.embfecdevdoc,le.embfolio from Liembarques le,Liclientes lc,Lipoblaciones lp where le.embempresa.empclave=?1 and (lc.clinumero=le.embcliente and lc.cliempresa=le.embempresa)  and (lp.pobclave=le.embpoblacion) " + idFactura;
  }

  public static String obtenerEmpaqueEncabezado(String factura, String empresa)
  {
    return "SELECT preid, preempid  ||' '|| empnombre as empresa, cliclave ||' '|| clinombre as cliente,  prealmid ||' '|| almnombre as almacen, premalid ||' '|| malnombre as movimiento, preref1, preref2, to_char(prefecha, 'dd/mm/yyyy') as prefecha, replace(replace(precoms, chr(13)), chr(10)) precoms, to_char(embfecemb, 'DD/MM/YYYY') as embfecemb, to_char(embfecent, 'DD/MM/YYYY') as embfecent, embestatus as embestatus, replace(replace(embcoms, chr(13)), chr(10)) as embcoms, embref1 as embref1, rectiporechazo, to_char(evefecha, 'dd/mm/yyyy') as evefecha, evereferencia, to_char(evecosto, 'fm999,999,990.00') as evecosto, (eveproveedor || ' / ' || evecoms) as eveproveedor FROM sepresurtido, seempresas, seclientes, sealmacenes, semovalmacen, seembarques, serechazos, seevenembarques  WHERE embid = " + factura + " and (embpreid = preid) and (empid = preempid) and (cliid = precliid) and (almid = prealmid) and (malid = premalid) and (recembid(+)=embid) and eveembid(+)=embid and evetevid(+)='TPF' ";
  }

  public static String obtenerEmpaqueDetalle(String factura)
  {
    return "SELECT prdclave, prdnombre, dprcantidad, dpraloid FROM seembarques, sepresurtido, sedetpresurtido, seproductos where embid=" + factura + " AND preref1 = embfolio AND preempid = embempid AND dprpreid = preid AND prdid = dprprdid ORDER BY prdclave";
  }

  public static String BuscarFacturas(String fecha, String tipoCliente, String estatus, int idEmpresa, String orderBy)
  {
    return "select le.embid,le.embestatus,le.embfolio,le.embref1,le.embenvio,lc.cliclave,lc.clinombre,lp.pobid,lp.pobnombre,le.embcajas,to_char(le.embvalor,'$999,999,999.00') as embvalor, to_char(le.embfecfac,'dd/mm/yyyy')as embfecfac,to_char(le.embfecrec,'dd/mm/yyyy')as embfecrec,to_char(le.embfeccita,'dd/mm/yyyy HH24:MI') as embfeccita, to_char(le.embfecemb,'dd/mm/yyyy') as embfecemb,to_char(le.embfecent,'dd/mm/yyyy') as embfecent,replace(replace(le.embcoms, chr(13)), chr(10)) embcoms ,lr.rectiporechazo from seembarques le, seclientrega lco, seclientes lc, sepoblaciones lp, serechazos lr  where (lp.pobid=le.embpobid) and (lco.cenid=le.embcenid and lc.cliid=lco.cencliid) and (lr.recembid(+) = le.embid)" + fecha + " and le.embempid=" + idEmpresa + estatus + orderBy;
  }

  public static String BuscarFechCiclo(String nociclo)
  {
    return "select TO_CHAR(cicfechaini,'DD/MM/YYYY')as CICFECHAINI,TO_CHAR(cicfechafin,'DD/MM/YYYY')as CICFECHAFIN from liciclos where cicclave=" + nociclo;
  }

  public static String BuscarExistencias(String sqlLike, String empresa, String almacen, String sqlOrderBy)
  {
    return "select lp.prdid,lp.prdclave,lp.prdnombre,lp.prdlinid,ln.linnombre,lp.prdfamid,lf.famnombre,lp.prdualid,li.invreal,li.invreservado,li.invaloid,NVL(lp.prdcosto,0)as prdcosto, al.alonombre from seproductos lp  inner join seinventario li on li.invprdid=lp.prdid inner join sefamilias lf on lp.prdfamid=lf.famid and lp.prdempid=lf.famempid  inner join selineas ln on lp.prdlinid=ln.linid and lp.prdempid=ln.linempid inner join sealmlogicos al on al.aloid = li.invaloid where lp.prdempid= " + empresa + almacen + sqlLike + " " + sqlOrderBy;
  }

  public static String ObtenerFacturas(String sqlBusq, String idEmpresa)
  {
    return "select le.embid,le.embref1,le.embfolio,to_char(le.embfecfac,'dd/mm/yyyy')as embfecfac,le.embenvio from seembarques le where le.embempid=" + idEmpresa + sqlBusq;
  }

  public static String ContFacturas(String idFactura, String idEmpresa)
  {
    return "select count(le.embid) as nomFact from seembarques le where le.embempid=" + idEmpresa + idFactura;
  }

  public static String getSaldoMov(Integer empresa, Integer producto, String Almacen, String fecha)
  {
    return "SELECT NVL(SUM(DRECANTIDAD), 0) as saldo FROM sedetremisiones, seremisiones  where dreempid = " + empresa + " and dreprdid = '" + producto + "' and drealoid = '" + Almacen + "' and remid = dreremid and " + " trunc(remfecha) < trunc(to_date('" + fecha + "', 'DD/MM/YYYY'))";
  }

  public static String getConMovProd(String FechIn, String FechFi, Integer idProdct, String Almacen)
  {
    return "select ld.dreprdid as dreproducto, to_char(lr.remfecha,'dd/mm/yyyy') as drefecha, ld.dreremid as dreremision, lr.remreferencia, lm.malnombre as movdescripcion, ld.drecantidad from sedetremisiones ld, semovalmacen lm, seremisiones lr where ld.dreprdid='" + idProdct + "' " + "and ld.drealoid='" + Almacen + "' and lr.remid=ld.dreremid and lm.malid=lr.remmalid and trunc(lr.remfecha) between trunc(to_date('" + FechIn + "', 'DD/MM/YYYY')) and trunc(to_date('" + FechFi + "', 'DD/MM/YYYY')) order by lr.remid ";
  }

  public static String getProductosEmpresa(String Producto)
  {
    return "SELECT prdclave, prddescripcion FROM liproductos WHERE prdempresa = ?1 " + Producto + " ORDER BY prdclave";
  }

  public static String getDetMovimProducts(String Remision)
  {
    return "select lp.prdclave as dreproducto, lp.prdnombre as prddescripcion, lr.raclote as drelote, to_char(lr.racfeccaducidad,'dd/mm/yyyy') as drefecfac, lr.racubiid as dreubicacion, ld.drecantidad from sedetremisiones ld, seproductos lp, serack lr where ld.dreremid =" + Remision + " and lp.prdid = ld.dreprdid and lr.racid = ld.dreracid";
  }
}
