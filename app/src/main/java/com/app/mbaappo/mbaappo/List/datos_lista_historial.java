package com.app.mbaappo.mbaappo.List;

import android.graphics.drawable.Drawable;

/**
 * Created by mauro on 23/5/2017.
 */

public class datos_lista_historial {
    protected Drawable foto_historial;
    protected String servicio_nombre;
    protected String servicio_usuario;
    protected String servicio_fecha;

    public datos_lista_historial(Drawable foto_historial, String servicio_nombre, String servicio_usuario, String servicio_fecha) {
        this.foto_historial = foto_historial;
        this.servicio_nombre = servicio_nombre;
        this.servicio_usuario = servicio_usuario;
        this.servicio_fecha = servicio_fecha;
    }

    public void setFoto_historial(Drawable foto_historial) {
        this.foto_historial = foto_historial;
    }

    public void setServicio_nombre(String servicio_nombre) {
        this.servicio_nombre = servicio_nombre;
    }

    public void setServicio_usuario(String servicio_usuario) {
        this.servicio_usuario = servicio_usuario;
    }

    public void setServicio_fecha(String servicio_fecha) {
        this.servicio_fecha = servicio_fecha;
    }

    public Drawable getFoto_historial() {

        return foto_historial;
    }

    public String getServicio_nombre() {
        return servicio_nombre;
    }

    public String getServicio_usuario() {
        return servicio_usuario;
    }

    public String getServicio_fecha() {
        return servicio_fecha;
    }
}
