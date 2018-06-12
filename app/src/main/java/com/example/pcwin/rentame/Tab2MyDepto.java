package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;

public class Tab2MyDepto extends Fragment {
    public List<ListItemsImgDepto> listItemsImgDepto;
    /*RecycleView*/
    private RecyclerView recyclerViewImgDepto;
    /*RV Adapters*/
    private RecyclerView.Adapter adapterImgDepto;
    String idDepto;
    boolean logedIn;
    String hasDepto = "";
    TextView textNombre;
    TextView textUbicacion;
    TextView textNumPer;
    TextView textNumRoom;
    TextView textNumBath;
    TextView textCosto;
    Button buttonRenta;
    Button buttonServicios;
    Button buttonPDU;
    Button buttonPDE;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_2_my_depto,container,false);
        if (getArguments() != null) {
            logedIn = getArguments().getBoolean("logedState");
            hasDepto = getArguments().getString("idDepto");
        }





        textNombre = (TextView) rootView.findViewById(R.id.textNombreDepto);
        textUbicacion = (TextView) rootView.findViewById(R.id.textUbicacionDepto);
        textNumPer = (TextView) rootView.findViewById(R.id.textNumPer);
        textNumRoom = (TextView) rootView.findViewById(R.id.textNumRoom);
        textNumBath = (TextView) rootView.findViewById(R.id.textNumBath);
        textCosto = (TextView) rootView.findViewById(R.id.textCosto);
        buttonRenta = (Button) rootView.findViewById(R.id.buttonRentar);
        buttonServicios = (Button) rootView.findViewById(R.id.buttonServicios) ;
        buttonPDU = (Button) rootView.findViewById(R.id.buttonPDU);
        buttonPDE = (Button) rootView.findViewById(R.id.buttonPDE);




            buttonRenta.setVisibility(View.GONE);

            buttonServicios.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailsServicios.class);
                    Bundle extras = new Bundle();
                    extras.putString("idDepto",hasDepto);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });

            buttonPDU.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailsPoliticasDepto.class);
                    startActivity(intent);
                }
            });

            buttonPDE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailsPoliticasDepto.class);
                    startActivity(intent);
                }
            });


        recyclerViewImgDepto= (RecyclerView) rootView.findViewById(R.id.recycleViewImgDepto);
        recyclerViewImgDepto.setHasFixedSize(true);
        recyclerViewImgDepto.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        String URL_DATA = "https://rentame.000webhostapp.com/get_onlyfotos.php?IDDepto=" + hasDepto;
        listItemsImgDepto = new ArrayList<>();
        loadRecyclerViewDataImgDepto(URL_DATA);
        loadRecyclerViewDataDepto();


        /*
        recyclerViewAllDeptoImg.setHasFixedSize(true);
        recyclerViewAllDeptoImg.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItemsImgDeptos = new ArrayList<>();
        */



        return rootView;

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);







    }

    public void detailsPDU(View view)
    {
        startActivity(new Intent(getContext(),DetailsPoliticasDeUso.class));
    }

    public void detailsPDUD(View view)
    {
        startActivity(new Intent(getContext(),DetailsPoliticasDepto.class));
    }

    public void loadRecyclerViewDataDepto() {

        /*URL_DATA = "http://10.0.2.2:81/dbproject/get_platillos.php?IdRestaurante=" + IdRestaurante;*/
        final String URL_DATA = "https://rentame.000webhostapp.com/get_one_depto.php?IDDepto=" + hasDepto;

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
                                textNombre.setText(o.getString("Nombre"));
                                textNumRoom.setText("Habitaciones: " + o.getString("Habitaciones"));
                                textNumBath.setText("Baños: " + o.getString("Banos"));
                                textNumPer.setText("Capacidad: " + o.getString(  "Capacidad"));
                                textCosto.setText(o.getString("CostoRenta") + " mensuales");
                                String Ubi = o.getString("cogPostal") + " " + o.getString("NumINt") + " " + o.getString("NumExt") + " " + o.getString("Planta")
                                        + " " + o.getString("Calle")
                                        + " " + o.getString("Barrio") + " " + o.getString("Municipio") + " " + o.getString("Estado");
                                textUbicacion.setText(Ubi);


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

    public void loadRecyclerViewDataImgDepto(String url)
    {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("result");

                            for(int i = 0; i<array.length();i++)
                            {
                                JSONObject o = array.getJSONObject(i);
                                ListItemsImgDepto item = new ListItemsImgDepto(
                                        o.getString("Foto")



                                );
                                listItemsImgDepto.add(item);
                            }
                            adapterImgDepto = new MyAdapterImgDepto(listItemsImgDepto,getContext());
                            recyclerViewImgDepto.setAdapter(adapterImgDepto);

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
