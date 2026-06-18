package com.sql;

public class SQLUsuarios
{
  public static String obtenUsuario()
  {
    return "Select u.usuclave From Liusuarios u Where u.usulogin = ?1 And u.usupasswordweb = ?2 And u.usuacceso = 1";
  }

  public static String obtenUsuarios()
  {
    return "Select u From Liusuarios u Where u.usulogin Like ?1 And u.usunombre Like ?2 And u.usupaterno Like ?3 And u.usumaterno Like ?4 ";
  }

  public static String obtenUsuariosPntCnt()
  {
    return "Select u From Usuario u Where u.uIdOrigen.oIdOrigen = ?1 And u.uAcceso = True";
  }

  public static String obtenUsuarioAgenda()
  {
    return "Select u From Usuario u Where u.uIdUsuario = ?1 And u.uHabilitado = True";
  }

  public static String obtenBitacoraAccesoo()
  {
    return "Select ab From AdminBitacoraAcceso ab Where ab.idUsuario = ?1 And ab.idFechaIngreso = ?2";
  }

  public static String obtenerNombreUsuario()
  {
    return "Select u From Usuario u Where u.uCorreoElectronico = ?1 And u.uHabilitado = True";
  }

  public static String obtenerContrasena()
  {
    return "Select u From Usuario u Where u.uUsuario = ?1 And u.uHabilitado = True";
  }

  public static String obtnerUsuairosActivos(String sqluser)
  {
    return "select usuclave,(usunombre|| ' ' || usupaterno|| ' ' || usumaterno) as nombre,usuestatus,usulogin,usupasswordweb from liusuarios where usuestatus='A'  " + sqluser;
  }
}
