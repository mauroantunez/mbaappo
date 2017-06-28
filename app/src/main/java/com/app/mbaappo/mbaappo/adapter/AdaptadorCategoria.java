package com.app.mbaappo.mbaappo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mbaappo.mbaappo.Modelo.Categorias;
import com.app.mbaappo.mbaappo.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Ivan on 19/3/2017.
 *
 * Clase usada para poblar el gridView de la pantalla principal que muestra las categorias de
 * servicio
 *
 */

public class AdaptadorCategoria extends RecyclerView.Adapter<AdaptadorCategoria.CategoriasViewHolder>
        implements View.OnClickListener{

    private List<Categorias> items;
    private View.OnClickListener listener;

    public AdaptadorCategoria(List<Categorias> items){

        this.items = items;
    }


    @Override
    public CategoriasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_item, parent, false);
        v.setOnClickListener(this);
        return new CategoriasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoriasViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(items.get(position).getIdImagen())
                //.centerCrop()
                .into(holder.imagen);
        holder.texto.setText(items.get(position).getNombre());


    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null)
            listener.onClick(v);
    }


    public static class CategoriasViewHolder extends RecyclerView.ViewHolder{

        public ImageView imagen;
        public TextView texto;

        public CategoriasViewHolder(View itemView) {
            super(itemView);
            imagen = (ImageView) itemView.findViewById(R.id.imagenCategoria);
            texto = (TextView) itemView.findViewById(R.id.nombreCategoria);
            itemView.setTag(itemView);
        }

    }
}