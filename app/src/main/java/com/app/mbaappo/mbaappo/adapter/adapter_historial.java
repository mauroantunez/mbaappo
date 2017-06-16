package com.app.mbaappo.mbaappo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mbaappo.mbaappo.R;
import com.app.mbaappo.mbaappo.List.datos_lista_historial;

import java.util.ArrayList;

/**
 * Created by mauro on 23/5/2017.
 */

public class adapter_historial extends BaseAdapter {
    protected Activity activity_historial;
    protected ArrayList<datos_lista_historial> items_historial;

    public adapter_historial(Activity activity_historial, ArrayList<datos_lista_historial> items_historial) {
        this.activity_historial = activity_historial;
        this.items_historial = items_historial;
    }

    @Override
    public int getCount() {
        int b = items_historial.size();
        return b;
    }

    @Override
    public Object getItem(int position) {
        return items_historial.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(convertView == null){
            LayoutInflater inf_h = (LayoutInflater) activity_historial.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf_h.inflate(R.layout.item_historial,null);
        }
        datos_lista_historial datos_h = items_historial.get(position);
        ImageView foto = (ImageView) v.findViewById(R.id.historial_foto);
        foto.setImageDrawable(datos_h.getFoto_historial());
        TextView nombre_serv = (TextView) v.findViewById(R.id.servicio_historial);
        nombre_serv.setText(datos_h.getServicio_nombre());
        TextView usuario_serv = (TextView) v.findViewById(R.id.usuario_servicio);
        usuario_serv.setText(datos_h.getServicio_usuario());
        TextView fecha_serv = (TextView) v.findViewById(R.id.servicio_fecha);
        fecha_serv.setText(datos_h.getServicio_fecha());
        return v;
    }
}
