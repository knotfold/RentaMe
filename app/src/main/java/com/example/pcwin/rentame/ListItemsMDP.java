package com.example.pcwin.rentame;

public class ListItemsMDP {

    private String IDMetdo;
    private String Dinero;
    private String NumeroCuenta;
    private String NomPropieCuenta;
    private String IDArrendatario;


    public ListItemsMDP(String IDMetdo,  String numeroCuenta) {
        this.IDMetdo = IDMetdo;
        NumeroCuenta = numeroCuenta;

    }

    public String getIDMetdo() {
        return IDMetdo;
    }

    public String getDinero() {
        return Dinero;
    }

    public String getNumeroCuenta() {
        return NumeroCuenta;
    }

    public String getNomPropieCuenta() {
        return NomPropieCuenta;
    }

    public String getIDArrendatario() {
        return IDArrendatario;
    }
}
