package com.sql;

public class EmailsSql
{
  public static String qryMensajeSust()
  {
    return "select * from emailmsg,emailmsgperfil where clave = ? and emailmsg.clvmsgperfil=emailmsgperfil.clvmsgperfil";
  }

  public static String qryVarSust()
  {
    return "select * from emailvar,emailvardisp where clvmensaje = ? and emailvar.clvvardisp = emailvardisp.clvvardisp";
  }

  public static String qryMensajeConfigCorreo()
  {
    return "Select vm from VistaMensaje vm Where vm.vmIdVistaMensaje = ?1";
  }
}
