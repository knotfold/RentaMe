package com.example.pcwin.rentame;

public class ListItemsServicios {

    private String servicio;
    private String Descrip;

    public ListItemsServicios(String servicio, String descrip) {
        this.servicio = servicio;
        Descrip = descrip;
    }

    public String getServicio() {
        return servicio;
    }

    public String getDescrip() {
        return Descrip;
    }
}
