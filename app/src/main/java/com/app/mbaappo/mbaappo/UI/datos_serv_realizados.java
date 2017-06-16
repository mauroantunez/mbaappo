package com.app.mbaappo.mbaappo.UI;

/**
 * Created by mauro on 23/5/2017.
 */

public class datos_serv_realizados {
    protected String nombre_serv_reali;
    protected String precio_serv_reali;
    public datos_serv_realizados(String nombre_serv_reali, String precio_serv_reali) {
        this.nombre_serv_reali = nombre_serv_reali;
        this.precio_serv_reali = precio_serv_reali;
    }

    public String getNombre_serv_reali() {
        return nombre_serv_reali;
    }

    public void setNombre_serv_reali(String nombre_serv_reali) {
        this.nombre_serv_reali = nombre_serv_reali;
    }

    public void setPrecio_serv_reali(String precio_serv_reali) {
        this.precio_serv_reali = precio_serv_reali;
    }

    public String getPrecio_serv_reali() {
        return precio_serv_reali;
    }
}
