package com.example.pcwin.rentame;

public class ListItemsMensajes {

  private String Mensaje;
  private String IDArrendatario;
  private String fecha;
  private String Tipo;
  private String idMensaje;
  private String idAdmin;

    public ListItemsMensajes(String idMensaje, String mensaje,String fecha,   String tipo, String IDArrendatario, String idAdmin) {
        Mensaje = mensaje;
        this.IDArrendatario = IDArrendatario;
        this.fecha = fecha;
        Tipo = tipo;
        this.idMensaje = idMensaje;
        this.idAdmin = idAdmin;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public String getIDArrendatario() {
        return IDArrendatario;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTipo() {
        return Tipo;
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public String getIdAdmin() {
        return idAdmin;
    }
}
