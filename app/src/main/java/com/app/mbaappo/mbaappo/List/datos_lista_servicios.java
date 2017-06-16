package com.app.mbaappo.mbaappo.List;

import android.graphics.drawable.Drawable;
import android.widget.RatingBar;

import com.google.firebase.storage.StorageReference;

/**
 * Created by mauro on 22/5/2017.
 */

public class datos_lista_servicios{
    protected StorageReference foto;
    protected String servicio_nombre;
    protected String precio_servicio;
    protected String nombre_usuario;
    protected Float puntuacion;

    public datos_lista_servicios(StorageReference foto,String servicio_nombre,String precio_servicio,String nombre_usuario,Float puntuacion){
        this.foto = foto;
        this.servicio_nombre = servicio_nombre;
        this.precio_servicio = precio_servicio;
        this.nombre_usuario = nombre_usuario;
        this.puntuacion = puntuacion;
    }

    public void setFoto(StorageReference foto) {
        this.foto = foto;
    }

    public void setServicio_nombre(String servicio_nombre) {
        this.servicio_nombre = servicio_nombre;
    }

    public void setPrecio_servicio(String precio_servicio) {
        this.precio_servicio = precio_servicio;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public void setPuntuacion(Float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public StorageReference getFoto() {
        return foto;
    }

    public String getServicio_nombre() {
        return servicio_nombre;
    }

    public String getPrecio_servicio() {
        return precio_servicio;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public Float getPuntuacion() {
        return puntuacion;
    }
}
