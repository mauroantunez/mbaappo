package com.app.mbaappo.mbaappo.Modelo;



/**
 * Created by Ivan on 19/3/2017.
 */

public class estructura_categoria {
    private String key_padre;


    public estructura_categoria() {
    }

    public estructura_categoria(String nombre) {
        this.key_padre = nombre;

    }

    public String getKey_padre() {
        return key_padre;
    }



}
