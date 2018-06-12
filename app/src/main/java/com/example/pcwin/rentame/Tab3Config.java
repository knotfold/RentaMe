package com.example.pcwin.rentame;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class Tab3Config extends Fragment {
    public Button buttonModificarPerfil;
    public Button buttonMDP;
    public Button buttonLogOut;
    public EditText editTextUser;
    public String user;
    public String mail;
    public String idDepto;
    public String idUser;
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState){
        View rootView = inflater.inflate(R.layout.tab_3_config, container, false);

        if (getArguments() != null) {
            user = getArguments().getString("user");
            mail = getArguments().getString("mail");
            idUser = getArguments().getString("idUser");
            idDepto = getArguments().getString("idDepto");
        }


        buttonModificarPerfil = (Button) rootView.findViewById(R.id.buttonModoificar);
        buttonMDP = (Button) rootView.findViewById(R.id.buttonMDP);
        buttonLogOut = (Button) rootView.findViewById(R.id.buttonLogOut);
        editTextUser = (EditText) rootView.findViewById(R.id.editTextUser);
        editTextUser.setText(user);

        buttonMDP.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), MetodosDePago.class);
                Bundle extras = new Bundle();
                extras.putString("idUser",idUser);
                extras.putString("user",user);
                extras.putString("idDepto",idDepto);
                intent.putExtras(extras);
                Objects.requireNonNull(getContext()).startActivity(intent);

            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MainActivity.class));
                getActivity().finish();
            }
        });

        buttonModificarPerfil.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailsUsers.class);
                Bundle extras = new Bundle();
                extras.putString("idUser",idUser);
                intent.putExtras(extras);
                Objects.requireNonNull(getContext()).startActivity(intent);
            }
        });





        return rootView;

    }

}
