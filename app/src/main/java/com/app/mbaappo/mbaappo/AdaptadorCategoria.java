package com.app.mbaappo.mbaappo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Ivan on 19/3/2017.
 *
 * Clase usada para poblar el gridView de la pantalla principal que muestra las categorias de
 * servicio
 *
 */

public class AdaptadorCategoria extends BaseAdapter {

    private Context context;

    public AdaptadorCategoria(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Categorias.ITEMS.length;
    }

    @Override
    public Categorias getItem(int position) {
        return Categorias.ITEMS[position];
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.categoria_item, parent, false);
        }

        ImageView imagenCategoria = (ImageView) view.findViewById(R.id.imagenCategoria);
        TextView nombreCategoria = (TextView) view.findViewById(R.id.nombreCategoria);

        final Categorias item = getItem(position);
        Glide.with(imagenCategoria.getContext())
                .load(item.getIdImagen())
                .into(imagenCategoria);

        nombreCategoria.setText(item.getNombre());

        return view;
    }
}
