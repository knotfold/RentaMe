package com.example.pcwin.rentame;

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

public class Tab2Users extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterUser;
    private RecyclerView recyclerViewAllDeptoImg;
    private RecyclerView.Adapter adapterAllDeptoImg;
    Boolean logedIn = false;
    String hasDepto;
    String idUser;
    String user;
    boolean isAdmin = true;

    private Button buttonAddUser;




    public static String URL_DATA = "https://rentame.000webhostapp.com/get_all_users.php";



    public List<ListItemsUsuarios> listItems;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        listItems = new ArrayList<>();

        loadRecyclerViewData(URL_DATA);






    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_2_users,container,false);
        if (getArguments() != null) {
             logedIn = getArguments().getBoolean("logedState");
             hasDepto = getArguments().getString("idDepto");
             idUser = getArguments().getString("idUser");
             user = getArguments().getString("user");
        }


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleViewAllUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        buttonAddUser = (Button) rootView.findViewById(R.id.buttonAddUser);

        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Register.class);
                Bundle extras = new Bundle();
                extras.putBoolean("isAdmin", isAdmin );

                intent.putExtras(extras);
                v.getContext().startActivity(intent);
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
        recyclerView.setAdapter(adapterUser);





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
                                ListItemsUsuarios item = new ListItemsUsuarios(
                                        o.getString("IDArrendatario"),
                                        o.getString("Nombre"),
                                        o.getString("ApellidoP"),
                                        o.getString("ApellidoM"),
                                        o.getString("Email")



                                );
                                listItems.add(item);
                            }
                            adapterUser = new MyAdapterUsers(listItems,getActivity(),idUser);
                            recyclerView.setAdapter(adapterUser);

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
