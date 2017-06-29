package com.app.mbaappo.mbaappo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.mbaappo.mbaappo.FirebaseUI.FirebaseImageLoader;
import com.app.mbaappo.mbaappo.Modelo.estructura_servicio;
import com.app.mbaappo.mbaappo.R;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by mauro on 23/5/2017.
 */

public class adapter_servicios extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<estructura_servicio> items;
    private Context mView;
    public adapter_servicios( ArrayList<estructura_servicio> items) {

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
        estructura_servicio datos = items.get(position);
        estructura_servicio dir = items.get(position);
        ImageView foto = (ImageView) v.findViewById(R.id.foto_serv);
        StorageReference url = FirebaseStorage.getInstance().getReference().child(datos.getUrlfoto());
        Glide.with(mView)
                .using(new FirebaseImageLoader())
                .load(url)
                .bitmapTransform(new CropCircleTransformation(mView))
                .into(foto);
        TextView nombre_serv = (TextView) v.findViewById(R.id.nombre_servicio);
        nombre_serv.setText(datos.getTitulo());
        TextView precio_serv = (TextView) v.findViewById(R.id.servicio_descripcion);
        precio_serv.setText(datos.getPrecio());
        TextView nombre_usu = (TextView) v.findViewById(R.id.servicio_descripcion);
        nombre_usu.setText(datos.getNombreUsuario());
        RatingBar puntuacion = (RatingBar) v.findViewById(R.id.rating_servicio);
        puntuacion.setRating(datos.getRating());
        return v;
    }
}
