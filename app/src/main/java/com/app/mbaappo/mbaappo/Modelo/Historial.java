package com.app.mbaappo.mbaappo.Modelo;

/**
 * Created by Antunez on 26/6/2017.
 */

public class Historial {
    private String key_servicio;
    private String key_usuario_contratado;
    private String key_padre;
    private String key_usuario_contrato;
    private String fecha = "";
    //private List<Friend> friends;

    public Historial(){

    }
    public Historial(String key_servicio, String key_usuario_contratado, String key_padre, String key_usuario_contrato,String fecha) {
        this.key_servicio = key_servicio;
        this.key_usuario_contratado = key_usuario_contratado;
        this.key_padre = key_padre;
        this.key_usuario_contrato = key_usuario_contrato;
        this.fecha = fecha;
    }
    public String getKey_usuario_contrato() {
        return key_usuario_contrato;
    }

    public String getFecha() {
        return fecha;
    }

    public String getKey_servicio() {
        return key_servicio;
    }

    public String getKey_usuario_contratado() {
        return key_usuario_contratado;
    }

    public String getKey_padre() {
        return key_padre;
    }
}

