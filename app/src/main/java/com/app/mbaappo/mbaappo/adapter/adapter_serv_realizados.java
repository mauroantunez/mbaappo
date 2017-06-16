package com.app.mbaappo.mbaappo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.mbaappo.mbaappo.R;
import com.app.mbaappo.mbaappo.UI.datos_serv_realizados;

import java.util.ArrayList;

/**
 * Created by mauro on 23/5/2017.
 */

public class adapter_serv_realizados extends BaseAdapter {
    protected Activity activity_realizados;
    protected ArrayList<datos_serv_realizados> items_realizados;

    public adapter_serv_realizados(Activity activity_realizados, ArrayList<datos_serv_realizados> items_realizados) {
        this.activity_realizados = activity_realizados;
        this.items_realizados = items_realizados;
    }

    @Override
    public int getCount() {

         int r = items_realizados.size();
        return r;
    }

    @Override
    public Object getItem(int position) {
        return items_realizados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(convertView == null){
            LayoutInflater inf_r = (LayoutInflater) activity_realizados.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf_r.inflate(R.layout.item_serv_realizados,null);
        }
        datos_serv_realizados datos_r = items_realizados.get(position);
        TextView nombre_serv = (TextView) v.findViewById(R.id.nombre_serv_reali);
        nombre_serv.setText(datos_r.getNombre_serv_reali());
        TextView precio_serv = (TextView) v.findViewById(R.id.precio_serv_reali);
        precio_serv.setText(datos_r.getPrecio_serv_reali());
        return v;
    }
}
