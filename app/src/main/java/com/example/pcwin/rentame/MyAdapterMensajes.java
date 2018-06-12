package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static com.example.pcwin.rentame.R.color.colorPrimaryDark;

public class MyAdapterMensajes extends RecyclerView.Adapter<MyAdapterMensajes.ViewHolder> {
    private List<ListItemsMensajes> listItems;
    private Context context;

    String idUser;
    String idDepto;
    String idamp;
    String tipoMensaje;
    private String nombreDepto;
    String date;
    private String costoDepto;
    private boolean isRenting=false;
    String user;
    private String dateFinal;
    int costoFinal;


    public static String URL_DATA = "https://rentame.000webhostapp.com/get_departamentov2.php";

    public MyAdapterMensajes(List<ListItemsMensajes> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;

    }


    @Override
    public MyAdapterMensajes.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rv_messages, parent, false);
        return new ViewHolder(v);


    }



    @SuppressLint({"SetTextI18n", "ResourceAsColor", "RtlHardcoded"})
    @Override
    public void onBindViewHolder(final MyAdapterMensajes.ViewHolder holder, int position) {
        final ListItemsMensajes listItemsMensajes = listItems.get(position);
        holder.textMensaje.setText(listItemsMensajes.getMensaje());
        holder.textFecha.setText(listItemsMensajes.getFecha());
        holder.textIdMensaje.setText(listItemsMensajes.getIdMensaje());
        tipoMensaje = listItemsMensajes.getTipo();
        if(tipoMensaje.equals("1") || tipoMensaje.equals("0") )
        {


           holder.textMensaje.setGravity(Gravity.LEFT);
            holder.textFecha.setGravity(Gravity.LEFT);
        }
        else
        {
            holder.textMensaje.setGravity(Gravity.RIGHT);
            holder.textFecha.setGravity(Gravity.RIGHT);
        }



        holder.linearLayoutMensajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Toast.makeText(context, "You clicked" + listItemIglesia.getNombreIglesia(), Toast.LENGTH_LONG).show();*/




            }
        });



    }




    @Override
    public int getItemCount() {
        return listItems.size();
    }


        public class ViewHolder extends RecyclerView.ViewHolder {

            public  TextView textMensaje;
            public TextView textIdMensaje;
            public TextView textTipoMensaje;
            public TextView textFecha;
            public LinearLayout linearLayoutMensajes;
            public CardView cardViewMensajes;

            public ViewHolder(View itemView) {
                super(itemView);

                textMensaje = (TextView) itemView.findViewById(R.id.textMensaje);
                textFecha = (TextView) itemView.findViewById(R.id.textViewFecha);
                textIdMensaje = (TextView) itemView.findViewById(R.id.textViewIdMensaje);
                linearLayoutMensajes = (LinearLayout) itemView.findViewById(R.id.linearLayoutAllMensajes);


            }
        }
    }

