package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterDepto extends RecyclerView.Adapter<MyAdapterDepto.ViewHolder> {
    private List<ListItemsInmueble> listItems;
    private Context context;
    private List<ListItemsImgDepto> listItemsImgDeptos;
    private boolean logedIn = false;

    public static String URL_DATA = "http://iglesiasensalida.000webhostapp.com/ssat/get_departamentov2.php";

    public MyAdapterDepto(List<ListItemsInmueble> listItems, Context context, boolean logedIn) {
        this.listItems = listItems;
        this.context = context;
        this.logedIn = logedIn;
    }


    @Override
    public MyAdapterDepto.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rv_inmueble, parent, false);
        return new ViewHolder(v);


    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyAdapterDepto.ViewHolder holder, int position) {
        final ListItemsInmueble listItemsInmueble = listItems.get(position);
        Picasso.with(context).load(listItemsInmueble.getImgUrl()).into(holder.imageViewImgRVDeptos);
        holder.textViewAllDeptoNombre.setText(listItemsInmueble.getNombre());
        holder.getTextViewAllDeptoPrecio.setText("$" + listItemsInmueble.getCostoRenta());
        holder.textViewAllDeptoUbicacion.setText(listItemsInmueble.getUbicacion() + ",");
        holder.textViewAllDeptoEstado.setText(listItemsInmueble.getEstado());
        holder.getTextViewAllDeptoID.setText(listItemsInmueble.getIDDepto());



        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Toast.makeText(context, "You clicked" + listItemIglesia.getNombreIglesia(), Toast.LENGTH_LONG).show();*/

                Intent intent = new Intent(v.getContext(), DetailsDepto.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_IdDepto", listItemsInmueble.getIDDepto());
                extras.putBoolean("logedState", logedIn);
                intent.putExtras(extras);
                v.getContext().startActivity(intent);


            }
        });



    }




    @Override
    public int getItemCount() {
        return listItems.size();
    }


        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView imageViewImgRVDeptos;
            public TextView textViewAllDeptoNombre;
            public TextView getTextViewAllDeptoPrecio;
            public TextView textViewAllDeptoUbicacion;
            public TextView textViewAllDeptoEstado;
            public TextView getTextViewAllDeptoID;
            public LinearLayout linearLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                imageViewImgRVDeptos = (ImageView) itemView.findViewById(R.id.ImgRVDeptos);
                textViewAllDeptoNombre = (TextView) itemView.findViewById(R.id.textViewAllDeptoNombre);
                getTextViewAllDeptoPrecio = (TextView) itemView.findViewById(R.id.textViewAllDeptoPrecio);
                textViewAllDeptoUbicacion = (TextView) itemView.findViewById(R.id.textViewAllDeptoUbication);
                textViewAllDeptoEstado = (TextView) itemView.findViewById(R.id.textViewAllDeptoEstado);
                getTextViewAllDeptoID = (TextView) itemView.findViewById(R.id.textViewAllDeptoID);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutAllDepto);


            }
        }
    }

