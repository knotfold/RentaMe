package com.example.pcwin.rentame;

public class ListItemsInmueble {

    private String IDDepto;
    private String Nombre;
    private String Habitaciones;
    private String Banos;
    private String Capacidad;
    private String CostoRenta;
    private String PoliticasUso;
    private String Ubicacion;
    private String Estado;
    private String ImgUrl;
    private String Direccion;

    public ListItemsInmueble(String IDDepto, String nombre, String costoRenta, String ubicacion, String estado, String imgUrl) {
        this.IDDepto = IDDepto;
        Nombre = nombre;
        CostoRenta = costoRenta;
        Ubicacion = ubicacion;
        Estado = estado;
        ImgUrl = imgUrl;
    }

    public ListItemsInmueble(String nombre, String habitaciones, String banos, String capacidad, String costoRenta, String ubicacion, String estado, String direccion) {
        Nombre = nombre;
        Habitaciones = habitaciones;
        Banos = banos;
        Capacidad = capacidad;
        CostoRenta = costoRenta;
        Ubicacion = ubicacion;
        Estado = estado;
        Direccion = direccion;
    }

    public String getIDDepto() {
        return IDDepto;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getHabitaciones() {
        return Habitaciones;
    }

    public String getBanos() {
        return Banos;
    }

    public String getCapacidad() {
        return Capacidad;
    }

    public String getCostoRenta() {
        return CostoRenta;
    }

    public String getPoliticasUso() {
        return PoliticasUso;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public String getEstado() {
        return Estado;
    }

    public String getImgUrl() {
        return ImgUrl;
    }
}
