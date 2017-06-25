package com.app.mbaappo.mbaappo.Modelo;

/**
 * Created by Antunez on 23/6/2017.
 */

public class lista_servicio_ofrecido {
    private String Titulo;
    private String Precio;
    private String key;
    public lista_servicio_ofrecido(String nombre, String precio, String key) {
        this.Titulo = nombre;
        this.Precio = precio;
        this.key =key;

    }
    public lista_servicio_ofrecido(){}
    public String getTitulo() {
        return Titulo;
    }

    public String getKey() {
        return key;
    }

    public String getPrecio() {
        return Precio;
    }

}
