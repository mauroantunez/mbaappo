package com.app.mbaappo.mbaappo.Modelo;

import com.google.firebase.storage.StorageReference;

/**
 * Created by Antunez on 12/6/2017.
 */

public class estructura_servicio {
    private String titulo;
    private String uid;
    String precio;
    String NombreUsuario;
    String descripcion;
    float rating;
    String categoria;
    String urlfoto;
    String email;
    String key;
    public estructura_servicio(){

    }


    public estructura_servicio(String nombre_servicio, String uid_persona_ofrece, String precio, String nombre_persona_ofrece, String descripcion, float rating, String categoria,String urlfoto,String email, String key) {

        this.titulo = nombre_servicio;
        this.uid = uid_persona_ofrece;
        this.precio = precio;
        this.NombreUsuario = nombre_persona_ofrece;
        this.descripcion = descripcion;
        this.rating = rating;
        this.urlfoto = urlfoto;
        this.categoria = categoria;
        this.email = email;
        this.key = key;

    }

    public String getEmail() {
        return email;
    }

    public estructura_servicio(String nombre_servicio, String precio, String descripcion, float rating, String urlfoto){
        this.titulo = nombre_servicio;
        this.precio = precio;
        this.descripcion = descripcion;
        this.rating = rating;
        this.urlfoto = urlfoto;
    }

    public String getKey() {
        return key;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getUid() {
        return uid;
    }

    public String getPrecio() {
        return precio;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getRating() {
        return rating;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getUrlfoto() {
        return urlfoto;
    }
}
