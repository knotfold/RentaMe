package com.example.pcwin.rentame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class Tab1Home extends Fragment {
    private RecyclerView recyclerView;
    public RecyclerView.Adapter adapterDepto;

    private Button buttonSearch;
    Boolean logedIn = false;
    String hasDepto;
    String idUser;
    String user;
    String noBanos;
    String noHabitaciones;
    String capacidadDepto;

    private Button button;

    SwipeRefreshLayout mSwipeRefreshLayout;






     String URL_DATA = "https://rentame.000webhostapp.com/get_departamentov2.php";
     String URL_REVERT = "https://rentame.000webhostapp.com/get_departamentov2.php";

    final Handler handler = new Handler();
    final Runnable r = new Runnable() {
        public void run() {

            listItems.clear();
            adapterDepto.notifyDataSetChanged();
            loadRecyclerViewData(URL_REVERT);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };



    public List<ListItemsInmueble> listItems;
    public List<ListItemsImgDepto> listItemsImgDeptos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            logedIn = getArguments().getBoolean("logedState");
            hasDepto = getArguments().getString("idDepto");
            idUser = getArguments().getString("idUser");
            user = getArguments().getString("user");
            noBanos = getArguments().getString("noBanos");
            noHabitaciones = getArguments().getString("noHabitaciones");
            capacidadDepto = getArguments().getString("capacidadDepto");

            if(noBanos != null||noHabitaciones!=null||capacidadDepto!=null)
            {

                if(noBanos ==null)
                {
                    noBanos = "";
                }
                if(noHabitaciones==null)
                {
                    noHabitaciones = "";

                }
                if(capacidadDepto == null)
                {
                    capacidadDepto ="";
                }
                URL_DATA = "https://rentame.000webhostapp.com/busqueda.php?Banos=" + noBanos + "&Habitaciones=" + noHabitaciones+ "&Capacidad=" + capacidadDepto;

            }


        }





    listItems = new ArrayList<>();

        if(noBanos != null||noHabitaciones!=null||capacidadDepto!=null)
        {

            if(noBanos ==null)
            {
                noBanos = "";
            }
            if(noHabitaciones==null)
            {
                noHabitaciones = "";

            }
            if(capacidadDepto == null)
            {
                capacidadDepto ="";
            }
            URL_DATA = "https://rentame.000webhostapp.com/busqueda.php?Banos=" + noBanos + "&Habitaciones=" + noHabitaciones+ "&Capacidad=" + capacidadDepto;

        }

        loadRecyclerViewData(URL_DATA);






    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_1_home,container,false);




        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleViewAllDepto);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        buttonSearch = (Button) rootView.findViewById(R.id.buttonSearch);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(r, 1000);
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Busqueda.class);
                Bundle extras = new Bundle();
                extras.putString("idDepto", hasDepto);
                extras.putString("idUser", idUser);
                extras.putBoolean("logedState",logedIn);
                extras.putString("user",user);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
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
        recyclerView.setAdapter(adapterDepto);





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
                            adapterDepto = new MyAdapterDepto(listItems,getActivity(),logedIn,hasDepto, idUser, user);
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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

}
