package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapterMDP extends RecyclerView.Adapter<MyAdapterMDP.ViewHolder> {
    private List<ListItemsMDP> listItems;
    private Context context;
    private List<ListItemsMDP> listItemsMDP;
    String idUser;
    String idDepto;
    String idamp;
    private String nombreDepto;
    String date;
    private String costoDepto;
    private boolean isRenting=false;
    String user;
    private String dateFinal;
    int costoFinal;


    public static String URL_DATA = "https://rentame.000webhostapp.com/get_departamentov2.php";

    public MyAdapterMDP(List<ListItemsMDP> listItems, Context context, String idUser, String idDepto, String nombreDepto, String date, String costoDepto, boolean isRenting, String user, String dateFinal, int costoFinal) {
        this.listItems = listItems;
        this.context = context;
        this.idUser = idUser;
        this.idDepto = idDepto;
        this.nombreDepto = nombreDepto;
        this.date = date;
        this.costoDepto = costoDepto;
        this.isRenting = isRenting;
        this.user = user;
        this.dateFinal = dateFinal;
        this.costoFinal = costoFinal;
    }


    @Override
    public MyAdapterMDP.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rv_mdp, parent, false);
        return new ViewHolder(v);


    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyAdapterMDP.ViewHolder holder, int position) {
        final ListItemsMDP listItemsMDP = listItems.get(position);
        holder.textNoTarjeta.setText(listItemsMDP.getNumeroCuenta());
        holder.textIdMDP.setText(listItemsMDP.getIDMetdo());



        holder.linearLayoutMDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Toast.makeText(context, "You clicked" + listItemIglesia.getNombreIglesia(), Toast.LENGTH_LONG).show();*/
                if(isRenting)
                {
                    Intent intent = new Intent(v.getContext(), FinalizarRenta.class);
                    Bundle extras = new Bundle();
                    extras.putString("idMDP",listItemsMDP.getIDMetdo());
                    extras.putString("noTarjeta" ,listItemsMDP.getNumeroCuenta());
                    extras.putString("idUser",idUser);
                    extras.putString("idDepto",idDepto);
                    extras.putString("nombreDepto",nombreDepto);
                    extras.putString("date",date);
                    extras.putString("costoDepto",costoDepto);
                    extras.putString("user",user);
                    extras.putString("dateFinal",dateFinal);
                    extras.putInt("costoFinal",costoFinal);

                    intent.putExtras(extras);
                    v.getContext().startActivity(intent);

                }




            }
        });



    }




    @Override
    public int getItemCount() {
        return listItems.size();
    }


        public class ViewHolder extends RecyclerView.ViewHolder {

            public  TextView textNoTarjeta;
            public TextView textIdMDP;
            public LinearLayout linearLayoutMDP;

            public ViewHolder(View itemView) {
                super(itemView);

                textNoTarjeta = (TextView) itemView.findViewById(R.id.textViewNumeroCuenta);
                textIdMDP = (TextView) itemView.findViewById(R.id.textViewIdMDP);
                linearLayoutMDP = (LinearLayout) itemView.findViewById(R.id.linearLayoutAllMDP);


            }
        }
    }

