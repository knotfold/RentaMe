package com.example.pcwin.rentame;

public class ListItemsUsuarios {

  private String IDArrendatario;
  private String Nombre;
  private String ApellidoP;
  private String ApellidoM;
  private String Tcelular;
  private String TelCasa;
  private String TelOficina;
  private String FechNac;
  private String Email;
  private String contrasena;
  private String Sexo;
  private String Identificacion;
  private String IDDepto;

    public ListItemsUsuarios(String IDArrendatario, String nombre, String apellidoP, String apellidoM, String tcelular, String telCasa, String telOficina, String fechNac, String email, String contrasena, String sexo, String identificacion, String IDDepto) {
        this.IDArrendatario = IDArrendatario;
        Nombre = nombre;
        ApellidoP = apellidoP;
        ApellidoM = apellidoM;
        Tcelular = tcelular;
        TelCasa = telCasa;
        TelOficina = telOficina;
        FechNac = fechNac;
        Email = email;
        this.contrasena = contrasena;
        Sexo = sexo;
        Identificacion = identificacion;
        this.IDDepto = IDDepto;
    }

    public ListItemsUsuarios(String IDArrendatario, String nombre, String apellidoP, String apellidoM, String email) {
        this.IDArrendatario = IDArrendatario;
        Nombre = nombre;
        ApellidoP = apellidoP;
        ApellidoM = apellidoM;
        Email = email;
    }

    public String getIDArrendatario() {
        return IDArrendatario;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getApellidoP() {
        return ApellidoP;
    }

    public String getApellidoM() {
        return ApellidoM;
    }

    public String getTcelular() {
        return Tcelular;
    }

    public String getTelCasa() {
        return TelCasa;
    }

    public String getTelOficina() {
        return TelOficina;
    }

    public String getFechNac() {
        return FechNac;
    }

    public String getEmail() {
        return Email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getSexo() {
        return Sexo;
    }

    public String getIdentificacion() {
        return Identificacion;
    }

    public String getIDDepto() {
        return IDDepto;
    }
}
