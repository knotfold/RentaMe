package com.example.pcwin.rentame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    private RecyclerView.Adapter adapterDepto;
    private RecyclerView recyclerViewAllDeptoImg;
    private RecyclerView.Adapter adapterAllDeptoImg;
    Boolean logedIn;

    private Button button;




    public static String URL_DATA = "http://iglesiasensalida.000webhostapp.com/ssat/get_departamentov2.php";
    public static String URL_DATA_2 = "http://iglesiasensalida.000webhostapp.com/ssat/get_onlyfotos.php?IDDepto=";


    public List<ListItemsInmueble> listItems;
    public List<ListItemsImgDepto> listItemsImgDeptos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        listItems = new ArrayList<>();

        loadRecyclerViewData(URL_DATA);






    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_1_home,container,false);
        if (getArguments() != null) {
             logedIn = getArguments().getBoolean("logedState");
        }


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleViewAllDepto);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                            adapterDepto = new MyAdapterDepto(listItems,getActivity(),logedIn);
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
