package com.app.mbaappo.mbaappo.Modelo;

/**
 * Created by Antunez on 1/6/2017.
 */

public class Usuario {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String profilePicLocation;

    public Usuario(){}

    public Usuario(String nombre, String apellido, String email, String telefono, String direccion, String ubicacionfoto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.profilePicLocation = ubicacionfoto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getProfilePicLocation() {
        return profilePicLocation;
    }
}