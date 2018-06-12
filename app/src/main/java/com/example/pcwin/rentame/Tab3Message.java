package com.example.pcwin.rentame;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

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

public class Tab3Message extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterMensajes;
    EditText textMensaje;
    Button buttonSend;
    String idUser;
    String URL_DATA;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public List<ListItemsMensajes> listItems;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            idUser = getArguments().getString("idUser");

        }
        URL_DATA = "https://rentame.000webhostapp.com/get_mensajes.php?IDArrendatario=" + idUser;


        listItems = new ArrayList<>();

        loadRecyclerViewData(URL_DATA);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setAdapter(adapterMensajes);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_3_message, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleViewAllMensajes);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lManager = new LinearLayoutManager(getActivity());
        lManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(lManager);

        textMensaje = (EditText) rootView.findViewById(R.id.textMensaje);
        buttonSend = (Button) rootView.findViewById(R.id.buttonSend);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(r, 1000);
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSend.setEnabled(false);
                if (!textMensaje.getText().toString().equals("")) {
                    addMensaje(textMensaje.getText().toString(), "3");
                }


            }
        });


        return rootView;

    }

    final Handler handler = new Handler();
    final Runnable r = new Runnable() {
        public void run() {

            listItems.clear();
            adapterMensajes.notifyDataSetChanged();
            loadRecyclerViewData(URL_DATA);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };


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
                textMensaje.setText("");
                listItems.clear();
                adapterMensajes.notifyDataSetChanged();
                loadRecyclerViewData(URL_DATA);
                buttonSend.setEnabled(true);



            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_EMP_MENSAJE,mensaje);
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
                                ListItemsMensajes item = new ListItemsMensajes(
                                        o.getString("IDMensaje"),
                                        o.getString("Mensaje"),
                                        o.getString("fecha"),
                                        o.getString("Tipo"),
                                        o.getString("IDArrendatario"),
                                        o.getString("IDAdmon")


                                );
                                listItems.add(item);
                            }
                            adapterMensajes = new MyAdapterMensajes(listItems,getActivity());
                            recyclerView.setAdapter(adapterMensajes);

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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }



}
