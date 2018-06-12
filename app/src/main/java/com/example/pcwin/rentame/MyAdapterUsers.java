package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapterUsers extends RecyclerView.Adapter<MyAdapterUsers.ViewHolder> {
    private List<ListItemsUsuarios> listItems;
    private Context context;

    private boolean logedIn = false;
    private String hasDepto = "";
    private String idUser;
    private String user;
    private boolean isAdmin = true;





    public MyAdapterUsers(List<ListItemsUsuarios> listItems, Context context, String hasDepto) {
        this.listItems = listItems;
        this.context = context;
        this.hasDepto = hasDepto;

    }


    @Override
    public MyAdapterUsers.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rv_users, parent, false);
        return new ViewHolder(v);


    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyAdapterUsers.ViewHolder holder, int position) {
        final ListItemsUsuarios listItemsUsuarios = listItems.get(position);
        holder.textNombre.setText(listItemsUsuarios.getNombre());
        holder.textAPaterno.setText(listItemsUsuarios.getApellidoP());
        holder.textAMaterno.setText(listItemsUsuarios.getApellidoM());
        holder.textMail.setText(listItemsUsuarios.getEmail());
        holder.textIdUser.setText(listItemsUsuarios.getIDArrendatario());



        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Toast.makeText(context, "You clicked" + listItemIglesia.getNombreIglesia(), Toast.LENGTH_LONG).show();*/

                Intent intent = new Intent(v.getContext(), DetailsUsers.class);
                Bundle extras = new Bundle();
                extras.putString("idUser", listItemsUsuarios.getIDArrendatario());
                extras.putBoolean("isAdmin", isAdmin);

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


            public TextView textNombre;
            public TextView textAPaterno;
            public TextView textAMaterno;
            public TextView textMail;
            public TextView textIdUser;
            public LinearLayout linearLayout;

            public ViewHolder(View itemView) {
                super(itemView);

                textNombre = (TextView) itemView.findViewById(R.id.textNombreUser);
                textAPaterno = (TextView) itemView.findViewById(R.id.textAPaterno);
                textAMaterno = (TextView) itemView.findViewById(R.id.textAMaterno);
                textMail = (TextView) itemView.findViewById(R.id.textMail);
                textIdUser = (TextView) itemView.findViewById(R.id.textViewIdUser);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutAllUsers);


            }
        }
    }

