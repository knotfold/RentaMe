package com.example.pcwin.rentame;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.List;

public class FinalizarRenta extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterDepto;
    public List<ListItemsInmueble> listItems;
    String idUser;
    String idDepto;
    String nombreDepto;
    String date;
    String costoDepto;
    String idMDP;
    String noCuenta;
    String user;
    String mensaje;
    String dateFinal;
    TextView textNombreDepto;
    TextView textCostoDepto;
    TextView textDate;
    TextView textMDP;
    TextView textDateFinal;
    TextView textCostoFinal;
    int costoFinal;
    String URL_DATA = "https://rentame.000webhostapp.com/get_one_depto.php?IDDepto=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_renta);

        recyclerView = (RecyclerView) findViewById(R.id.rvDeptoInContract);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(FinalizarRenta.this));

        textNombreDepto = (TextView) findViewById(R.id.textNombreDepto);
        textCostoDepto = (TextView) findViewById(R.id.textCostoDepto);
        textDate = (TextView) findViewById(R.id.textIniRenta);
        textMDP = (TextView) findViewById(R.id.textMDP);
        textCostoFinal = (TextView) findViewById(R.id.textCostoDeptoFinal);
        textDateFinal =  (TextView) findViewById(R.id.textFinRenta);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {

            idUser = bundle.getString("idUser");
            date = bundle.getString("date");
            idDepto = bundle.getString("idDepto");
            nombreDepto = bundle.getString("nombreDepto");
            costoDepto = bundle.getString("costoDepto");
            idMDP =bundle.getString("idMDP");
            user = bundle.getString("user");
            noCuenta = bundle.getString("noTarjeta");
            dateFinal = bundle.getString("dateFinal");
            costoFinal = bundle.getInt("costoFinal");




        }
        textNombreDepto.setText(nombreDepto);
        textCostoDepto.setText(costoDepto);
        textDate.setText(date);
        textMDP.setText(noCuenta);
        textDateFinal.setText(dateFinal);
        textCostoFinal.setText(String.valueOf(costoFinal));
        recyclerView.setAdapter(adapterDepto);

        URL_DATA = "https://rentame.000webhostapp.com/get_one_depto.php?IDDepto=" + idDepto;

        listItems = new ArrayList<>();

        loadRecyclerViewData(URL_DATA);


    }

    public void finalizar(View view)
    {
        updateUser(idUser,idDepto);
        updateDepto(idDepto);
        addPagor();
        addContrato();
        String mensajeContrato = "Contrato con fecha de inicio en el día: " + date + " y con fecha final en el día "
                + dateFinal + ", con un pago mensual de $" + costoDepto + " y un pago total de $" + costoFinal + " de usar el inmueble: "
                + nombreDepto + " que se liquidara con los pagos que se realizen a la tarjeta " + noCuenta + " cada mes";
        String mensajePago = "El pago ah sido realizado por la cantidad de $" + costoDepto + " a la tarjeta " + noCuenta;
        addMensaje(mensajeContrato,"0");
        addMensaje(mensajePago,"1");
        Intent intent = new Intent(this,MainActivity.class);
        Bundle extras = new Bundle();
        extras.putString("idDepto",idDepto);
        extras.putString("user",user);
        extras.putString("idUser",idUser);
        boolean logedin;
        extras.putBoolean("logedState",  logedin = true);

        intent.putExtras(extras);
        startActivity(intent);
        this.finish();
        Toast.makeText(FinalizarRenta.this,"El proceso de renta ah sido concretado, para ver detalles de sus pagos y comporbantes, dirijase a la sección de mensajes", Toast.LENGTH_LONG).show();

    }

    public void pde(View view)
    {
        Intent intent = new Intent(this, DetailsPoliticasDepto.class);
        startActivity(intent);
    }

    private void updateUser(final String idUser, final String idDepto){



        class UpdateIDDepto extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_EMP_IDDepto,idDepto);
                hashMap.put(Config.KEY_EMP_IDArrendatario,idUser);


                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_USER_IDDEPTO,hashMap);

                return s;
            }
        }

        UpdateIDDepto ue = new UpdateIDDepto();
        ue.execute();
    }

    public void addMensaje (final String mensaje, final String tipo){





        class addMensaje extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onPostExecute(String s) {


                super.onPostExecute(s);




            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_EMP_MENSAJE,mensaje);
                params.put(Config.KEY_EMP_FECHA_MENSAJE,date);
                params.put(Config.KEY_EMP_TIPO_MENSAJE,tipo);
                params.put(Config.KEY_EMP_IDArrendatario, idUser);





                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_mensaje, params);
                return res;
            }
        }

        addMensaje ae = new addMensaje();
        ae.execute();




    }

    public void addContrato(){






        class addContrato extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onPostExecute(String s) {


                super.onPostExecute(s);




            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_EMP_FINICIO,date);
                params.put(Config.KEY_EMP_FFINAL,dateFinal);
                params.put(Config.KEY_EMP_PAGOM,costoDepto);
                params.put(Config.KEY_EMP_SumTotal,String.valueOf(costoFinal));
                params.put(Config.KEY_EMP_IDArrendatario, idUser);
                params.put(Config.KEY_EMP_IDDepto,idDepto);





                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_Contrato, params);
                return res;
            }
        }

        addContrato ae = new addContrato();
        ae.execute();




    }

    public void addPagor(){






        class addPagor extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onPostExecute(String s) {


                super.onPostExecute(s);




            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_EMP_IDDepto, idDepto);
                params.put(Config.KEY_EMP_IDArrendatario, idUser);
                params.put(Config.KEY_EMP_Monto,costoDepto);
                params.put(Config.KEY_EMP_Fecha_PagoR,date);
                params.put(Config.KEY_EMP_ID_AMP,idMDP);





                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_Pagor, params);
                return res;
            }
        }

        addPagor ae = new addPagor();
        ae.execute();




    }


    private void updateDepto(final String idDepto){



        class UpdateIDDepto extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_EMP_IDDepto,idDepto);


                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_DEPTO_DISPONIBILIDAD,hashMap);

                return s;
            }
        }

        UpdateIDDepto ue = new UpdateIDDepto();
        ue.execute();
    }

    public void loadRecyclerViewData(String url)
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
                                ListItemsInmueble item = new ListItemsInmueble(
                                        o.getString("IDDepto"),
                                        o.getString("Nombre"),
                                        o.getString("CostoRenta"),
                                        o.getString("Municipio"),
                                        o.getString("Estado"),
                                        o.getString("Foto")

                                );
                                listItems.add(item);
                            }
                            adapterDepto = new MyAdapterDepto(listItems,FinalizarRenta.this,idDepto);
                            recyclerView.setAdapter(adapterDepto);

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

        RequestQueue requestQueue = Volley.newRequestQueue(FinalizarRenta.this);
        requestQueue.add(stringRequest);

    }

}
