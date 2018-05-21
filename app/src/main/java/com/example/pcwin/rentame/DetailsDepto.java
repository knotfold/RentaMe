package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

public class DetailsDepto extends AppCompatActivity {

    public List<ListItemsImgDepto> listItemsImgDepto;
    /*RecycleView*/
    private RecyclerView recyclerViewImgDepto;
    /*RV Adapters*/
    private RecyclerView.Adapter adapterImgDepto;
    String idDepto;
    boolean logedIn;
    TextView textNombre;
    TextView textUbicacion;
    TextView textNumPer;
    TextView textNumRoom;
    TextView textNumBath;
    TextView textCosto;
    Button buttonRenta;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        idDepto = extras.getString("EXTRA_IdDepto");
        logedIn = extras.getBoolean("logedState");

        setContentView(R.layout.activity_details_depto);
        textNombre = (TextView) findViewById(R.id.textNombreDepto);
        textUbicacion = (TextView) findViewById(R.id.textUbicacionDepto);
        textNumPer = (TextView) findViewById(R.id.textNumPer);
        textNumRoom = (TextView) findViewById(R.id.textNumRoom);
        textNumBath = (TextView) findViewById(R.id.textNumBath);
        textCosto = (TextView) findViewById(R.id.textCosto);
        buttonRenta = (Button) findViewById(R.id.buttonRentar);

        if(!logedIn)
        {
            buttonRenta.setVisibility(View.GONE);
        }

        recyclerViewImgDepto= (RecyclerView) findViewById(R.id.recycleViewImgDepto);
        recyclerViewImgDepto.setHasFixedSize(true);
        recyclerViewImgDepto.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        String URL_DATA = "http://iglesiasensalida.000webhostapp.com/ssat/get_onlyfotos.php?IDDepto=" + idDepto;
        listItemsImgDepto = new ArrayList<>();
        loadRecyclerViewDataImgDepto(URL_DATA);
        loadRecyclerViewDataDepto();



    }

    public void loadRecyclerViewDataDepto() {

        /*URL_DATA = "http://10.0.2.2:81/dbproject/get_platillos.php?IdRestaurante=" + IdRestaurante;*/
        final String URL_DATA = "http://iglesiasensalida.000webhostapp.com/ssat/get_one_depto.php?IDDepto=" + idDepto;

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
                                textNumRoom.setText(o.getString("Habitaciones"));
                                textNumBath.setText(o.getString("Banos"));
                                textNumPer.setText(o.getString("Capacidad"));
                                textCosto.setText(o.getString("CostoRenta"));
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                            adapterImgDepto = new MyAdapterImgDepto(listItemsImgDepto,getApplicationContext());
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

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}
