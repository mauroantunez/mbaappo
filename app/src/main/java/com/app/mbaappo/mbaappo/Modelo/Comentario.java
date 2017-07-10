package com.app.mbaappo.mbaappo.Modelo;

/**
 * Created by Antunez on 9/7/2017.
 */

public class Comentario {
    private String keyPadre;
    private String elcomentario;
    private String nombre;
    private String fecha;
    private float rating;


    public Comentario(String keyPadre, String elcomentario, String nombre, String fecha) {
        this.keyPadre = keyPadre;
        this.elcomentario = elcomentario;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public Comentario() {
    }

    public float getRating() {
        return rating;
    }

    public String getKeyPadre() {
        return keyPadre;
    }

    public String getElcomentario() {
        return elcomentario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }
}
