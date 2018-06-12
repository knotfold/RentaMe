package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyAdapterServices extends RecyclerView.Adapter<MyAdapterServices.ViewHolder> {
    private List<ListItemsServicios> listItems;
    private Context context;
    private boolean logedIn = false;
    private String hasDepto = "";
    private String idUser;

    public static String URL_DATA = "https://rentame.000webhostapp.com/get_departamentov2.php";

    public MyAdapterServices(List<ListItemsServicios> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;

    }


    @Override
    public MyAdapterServices.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rv_servicios, parent, false);
        return new ViewHolder(v);


    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyAdapterServices.ViewHolder holder, int position) {
        final ListItemsServicios listItemsServicios = listItems.get(position);
        holder.textService.setText(listItemsServicios.getServicio());
        holder.textDescrip.setText(listItemsServicios.getDescrip());







    }




    @Override
    public int getItemCount() {
        return listItems.size();
    }


        public class ViewHolder extends RecyclerView.ViewHolder {

            public  TextView textService;
            public TextView textDescrip;


            public ViewHolder(View itemView) {
                super(itemView);

                textService = (TextView) itemView.findViewById(R.id.textViewSerivce);
                textDescrip = (TextView) itemView.findViewById(R.id.textViewDescript);



            }
        }
    }

