package com.app.mbaappo.mbaappo.Modelo;

/**
 * Created by Antunez on 23/6/2017.
 */

public class lista_servicio_ofrecido {
    private String Nombre;
    private String Precio;
    public lista_servicio_ofrecido(String nombre, String precio) {
        this.Nombre = nombre;
        this.Precio = precio;
    }
    public lista_servicio_ofrecido(){}
    public String getNombre() {
        return Nombre;
    }

    public String getPrecio() {
        return Precio;
    }




}
