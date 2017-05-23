package com.app.mbaappo.mbaappo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mauro on 23/5/2017.
 */

public class adapter_servicios extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<datos_lista_servicios> items;

    public adapter_servicios(Activity activity, ArrayList<datos_lista_servicios> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        int a = items.size();
        return a;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_servicio,null);
        }
        datos_lista_servicios datos = items.get(position);
        datos_lista_servicios dir = items.get(position);
        ImageView foto = (ImageView) v.findViewById(R.id.foto_serv);
        foto.setImageDrawable(datos.getFoto());
        TextView nombre_serv = (TextView) v.findViewById(R.id.nombre_servicio);
        nombre_serv.setText(datos.getServicio_nombre());
        TextView precio_serv = (TextView) v.findViewById(R.id.precio_servicio);
        precio_serv.setText(datos.getPrecio_servicio());
        TextView nombre_usu = (TextView) v.findViewById(R.id.servicio_persona);
        nombre_usu.setText(datos.getNombre_usuario());
        RatingBar puntuacion = (RatingBar) v.findViewById(R.id.rating_servicio);
        puntuacion.setRating(datos.getPuntuacion());
        return v;
    }
}
