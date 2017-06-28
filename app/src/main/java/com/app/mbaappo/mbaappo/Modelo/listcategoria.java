package com.app.mbaappo.mbaappo.Modelo;

/**
 * Created by Antunez on 27/6/2017.
 */

public class listcategoria {
    private String nombre;
    private String photourl;

    public listcategoria(String nombre, String photourl) {
        this.nombre = nombre;
        this.photourl = photourl;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPhotourl() {
        return photourl;
    }
}
