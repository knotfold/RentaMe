package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Tab3Login extends Fragment {

    EditText editTextCorreo;
    EditText editTextPass;
    Button button;
    String mail;
    String mail2;
    String pass;
    String pass2;
    String IdUsuario;
    String hasDepto;
    String user;
    boolean logedIn;

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState){
        View rootView = inflater.inflate(R.layout.tab_3_login_tab, container, false);
        editTextCorreo = (EditText) rootView.findViewById(R.id.editTextMailLogin);
        editTextPass = (EditText) rootView.findViewById(R.id.editTextPassLogin);
        button = (Button) rootView.findViewById(R.id.buttonIS);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                mail = editTextCorreo.getText().toString();
                pass = editTextPass.getText().toString();
                if(mail.equals("123") && pass.equals("123"))
                {
                    Intent intent = new Intent(getContext(), AdminInterface.class);
                    Bundle extras = new Bundle();
                    extras.putBoolean("logedState", logedIn = true);
                    extras.putString("mail", mail);
                    extras.putString("idDepto", hasDepto);
                    extras.putString("idUser", IdUsuario);
                    extras.putString("user", user);
                    intent.putExtras(extras);
                    Objects.requireNonNull(getContext()).startActivity(intent);
                    getActivity().finish();
                }
                else
                {
                    GetUserDetails();

                }



            }
        });




        return rootView;

    }



    public void GetUserDetails() {

        /*URL_DATA = "http://10.0.2.2:81/dbproject/get_platillos.php?IdRestaurante=" + IdRestaurante;*/
        final String URL_DATA = "https://rentame.000webhostapp.com/validarARR.php?email=" + mail;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("result");
                            if(String.valueOf(array).equals("[]")) {
                                Toast.makeText(getContext(), "los datos no son válidos", Toast.LENGTH_LONG).show();
                            }


                            else
                            {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                IdUsuario = o.getString("IDArrendatario");
                                user = o.getString("Nombre");
                                mail2 = o.getString("email");
                                pass2 = o.getString("contrasena");
                                hasDepto = o.getString("IDDepto");
                                if (!mail.equals(mail2)) {
                                    Toast.makeText(getContext(), "los datos no son válidos", Toast.LENGTH_LONG).show();
                                } else {


                                    if (pass.equals(pass2)) {

                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        Bundle extras = new Bundle();
                                        extras.putBoolean("logedState", logedIn = true);
                                        extras.putString("mail", mail);
                                        extras.putString("idDepto", hasDepto);
                                        extras.putString("idUser", IdUsuario);
                                        extras.putString("user", user);
                                        intent.putExtras(extras);
                                        Objects.requireNonNull(getContext()).startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        Toast.makeText(getContext(), "los datos no son válidos " , Toast.LENGTH_LONG).show();
                                    }
                                }
                            }


                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void getUserExtras() {

        /*URL_DATA = "http://10.0.2.2:81/dbproject/get_platillos.php?IdRestaurante=" + IdRestaurante;*/
        final String URL_DATA = "https://rentame.000webhostapp.com/get_user_login_datails.php?Email=pedrogv@mail.com";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("result");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                IdUsuario = (o.getString("IDArrendatario"));
                                hasDepto = (o.getString("IDDepto"));



                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

}
