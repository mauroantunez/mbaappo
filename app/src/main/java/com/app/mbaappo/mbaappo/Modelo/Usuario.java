package com.app.mbaappo.mbaappo.Modelo;

import java.util.HashMap;
import java.util.Map;

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

    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

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

    public Map<String,Object> toMapusu(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("nombre",nombre);
        result.put("apellido",apellido);
        result.put("email",email);
        result.put("telefono",telefono);
        result.put("direccion",direccion);
        result.put("profilePicLocation",profilePicLocation);
        return result;
    }
}
