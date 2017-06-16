package com.app.mbaappo.mbaappo.Modelo;

import com.app.mbaappo.mbaappo.R;

/**
 * Created by Ivan on 19/3/2017.
 */

public class Categorias {
    private String nombre;
    private int idImagen;

    public Categorias() {
    }

    public Categorias(String nombre, int idImagen) {
        this.nombre = nombre;
        this.idImagen = idImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public int getId() {
        return nombre.hashCode();
    }

    //Datos de prueba, borrar luego al igual que las imagenes en la carpeta /res

    public static Categorias[] ITEMS = {
            new Categorias("Hogar", R.drawable.hogar),
            new Categorias("Entretenimiento", R.drawable.entretenimiento),
            new Categorias("Deporte", R.drawable.deporte),
            new Categorias("Reparaciones", R.drawable.reparaciones),
            new Categorias("Salud", R.drawable.salud),
            new Categorias("Profesores", R.drawable.profesores),
            new Categorias("Otros", R.drawable.otros),
    };

    public static Categorias getItem(int id) {
        for (Categorias item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }


}
