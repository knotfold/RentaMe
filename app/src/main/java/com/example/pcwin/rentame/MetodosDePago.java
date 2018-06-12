package com.example.pcwin.rentame;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MetodosDePago extends AppCompatActivity {
    private RecyclerView recyclerViewMDP;
    private RecyclerView.Adapter adapter;
    String idUser;
    String idDepto;
    String dateFinal;
    String nombreDepto;
    String date;
    String costoDepto;
    Button buttonAddMDP;
    String user;
    int costoFinal;
    TextView textInicial;
    boolean isRenting = false;

    public List<ListItemsMDP> listItemsMDP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodos_de_pago);

        buttonAddMDP = (Button) findViewById(R.id.buttonAddMDP);
        buttonAddMDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MetodosDePago.this, AddMDP.class);
                Bundle extras = new Bundle();
                extras.putString("idUser", idUser);
                extras.putString("user", user);
                extras.putString("idDepto",idDepto);
                extras.putBoolean("isRenting",isRenting);
                extras.putString("date",date);
                extras.putString("dateFinal",dateFinal);
                extras.putString("nombreDepto",nombreDepto);
                extras.putString("costoDepto",costoDepto);
                extras.putInt("costoFinal",costoFinal);

                intent.putExtras(extras);
                MetodosDePago.this.startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {

            idUser = bundle.getString("idUser");
            date = bundle.getString("date");
            idDepto = bundle.getString("idDepto");
            nombreDepto = bundle.getString("nombreDepto");
            costoDepto = bundle.getString("costoDepto");
            isRenting = bundle.getBoolean("isRenting");
            user = bundle.getString("user");
            dateFinal = bundle.getString("dateFinal");
            costoFinal = bundle.getInt("costoFinal");



        }
        textInicial = (TextView) findViewById(R.id.textIni);

        if (isRenting)
        {
            textInicial.setText("Elija el método de pago para continuar con el contrato de renta");
        }

        recyclerViewMDP = (RecyclerView) findViewById(R.id.recycleViewMDP);
        recyclerViewMDP.setHasFixedSize(true);
        recyclerViewMDP.setLayoutManager(new LinearLayoutManager(this));

        listItemsMDP = new ArrayList<>();

        loadRecyclerViewDataMDP();
    }

    public void loadRecyclerViewDataMDP() {

        /*URL_DATA = "http://10.0.2.2:81/dbproject/get_platillos.php?IdRestaurante=" + IdRestaurante;*/
        final String URL_DATA = "https://rentame.000webhostapp.com/get_ampago.php?IDArrendatario=" + idUser ;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("result");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                ListItemsMDP item = new ListItemsMDP(
                                        o.getString("IDamp"),
                                        o.getString("NumeroCuenta")




                                );
                                listItemsMDP.add(item);
                            }
                            adapter = new MyAdapterMDP(listItemsMDP, getApplicationContext(),idUser, idDepto,nombreDepto,date,costoDepto,isRenting,user,dateFinal,costoFinal);
                            recyclerViewMDP.setAdapter(adapter);

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
}
