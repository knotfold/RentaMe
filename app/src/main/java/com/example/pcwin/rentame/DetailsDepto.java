package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Objects;

public class DetailsDepto extends AppCompatActivity {

    public List<ListItemsImgDepto> listItemsImgDepto;
    /*RecycleView*/
    private RecyclerView recyclerViewImgDepto;
    /*RV Adapters*/
    private RecyclerView.Adapter adapterImgDepto;
    String idDepto;
    boolean logedIn;
    String hasDepto = "";
    String idUser;
    String costoDepto;
    TextView textNombre;
    TextView textUbicacion;
    TextView textNumPer;
    TextView textNumRoom;
    TextView textNumBath;
    TextView textCosto;
    String disponible;
    String user;
    boolean isRenting;
    Button buttonRenta;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            idDepto = bundle.getString("EXTRA_IdDepto");
            logedIn = bundle.getBoolean("logedState");
            hasDepto = bundle.getString("idDepto");
            idUser = bundle.getString("idUser");
            user = bundle.getString("user");
        }




        setContentView(R.layout.activity_details_depto);
        textNombre = (TextView) findViewById(R.id.textNombreDepto);
        textUbicacion = (TextView) findViewById(R.id.textUbicacionDepto);
        textNumPer = (TextView) findViewById(R.id.textNumPer);
        textNumRoom = (TextView) findViewById(R.id.textNumRoom);
        textNumBath = (TextView) findViewById(R.id.textNumBath);
        textCosto = (TextView) findViewById(R.id.textCosto);
        buttonRenta = (Button) findViewById(R.id.buttonRentar);



        if(!logedIn || !hasDepto.equals("null"))
        {
            buttonRenta.setVisibility(View.GONE);
        }

        recyclerViewImgDepto= (RecyclerView) findViewById(R.id.recycleViewImgDepto);
        recyclerViewImgDepto.setHasFixedSize(true);
        recyclerViewImgDepto.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        String URL_DATA = "https://rentame.000webhostapp.com/get_onlyfotos.php?IDDepto=" + idDepto;
        listItemsImgDepto = new ArrayList<>();
        loadRecyclerViewDataImgDepto(URL_DATA);
        loadRecyclerViewDataDepto();





    }
    public void detailsPDU(View view)
    {
        startActivity(new Intent(this,DetailsPoliticasDeUso.class));
    }

    public void detailsPDUD(View view)
    {
        startActivity(new Intent(this,DetailsPoliticasDepto.class));
    }


    public void detailServices(View view)
    {
        Intent intent = new Intent(this, DetailsServicios.class);
        Bundle extras = new Bundle();
        extras.putString("idDepto",idDepto);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void Rentar(View view)
    {
        Intent intent = new Intent(this, Renta.class);
        Bundle extras = new Bundle();
        extras.putBoolean("isRenting", isRenting = true);
        extras.putString("idDepto",idDepto);
        extras.putString("idUser",idUser);
        extras.putString("costoDepto" , costoDepto);
        extras.putString("nombreDepto",textNombre.getText().toString());
        extras.putString("user",user);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void loadRecyclerViewDataDepto() {

        /*URL_DATA = "http://10.0.2.2:81/dbproject/get_platillos.php?IdRestaurante=" + IdRestaurante;*/
        final String URL_DATA = "https://rentame.000webhostapp.com/get_one_depto.php?IDDepto=" + idDepto;

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
                                costoDepto = o.getString("CostoRenta");
                                disponible = o.getString("Disponible");
                                if(disponible.equals("no"))
                                {
                                    buttonRenta.setText("No disponible");
                                    buttonRenta.setClickable(false);
                                }
                                textCosto.setText(costoDepto + " mensuales");
                                String Ubi = o.getString("Calle")
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
