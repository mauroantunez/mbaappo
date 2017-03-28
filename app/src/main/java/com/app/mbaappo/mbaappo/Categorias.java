package com.app.mbaappo.mbaappo;

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
            new Categorias("Jaguar F-Type 2015", R.drawable.jaguar_f_type_2015),
            new Categorias("Mercedes AMG-GT", R.drawable.mercedes_benz_amg_gt),
            new Categorias("Mazda MX-5", R.drawable.mazda_mx5_2015),
            new Categorias("Porsche 911 GTS", R.drawable.porsche_911_gts),
            new Categorias("BMW Serie 6", R.drawable.bmw_serie6_cabrio_2015),
            new Categorias("Ford Mondeo", R.drawable.ford_mondeo),
            new Categorias("Volvo V60 Cross Country", R.drawable.volvo_v60_crosscountry),
            new Categorias("Jaguar XE", R.drawable.jaguar_xe),
            new Categorias("VW Golf R Variant", R.drawable.volkswagen_golf_r_variant_2015),
            new Categorias("Seat León ST Cupra", R.drawable.seat_leon_st_cupra),
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
