package com.app.mbaappo.mbaappo.Modelo;

/**
 * Created by Antunez on 26/6/2017.
 */

public class estructura_buscador {
    private String key_servicio;

    public estructura_buscador(String key_servicio) {
        this.key_servicio = key_servicio;
    }
    public  estructura_buscador(){
    }

    public String getKey_servicio() {
        return key_servicio;
    }
}
