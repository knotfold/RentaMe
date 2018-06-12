package com.example.pcwin.rentame;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
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

public class DetailsServicios extends AppCompatActivity {

    private RecyclerView recyclerViewServices;
    private RecyclerView.Adapter adapter;

    String idDepto;

    public List<ListItemsServicios> listItemsServicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_servicios);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {

            idDepto = bundle.getString("idDepto");

        }

        recyclerViewServices = (RecyclerView) findViewById(R.id.recycleViewServices);
        recyclerViewServices.setHasFixedSize(true);
        recyclerViewServices.setLayoutManager(new LinearLayoutManager(this));

        listItemsServicios = new ArrayList<>();

        loadRecyclerViewDataMDP();
    }

    public void loadRecyclerViewDataMDP() {

        /*URL_DATA = "http://10.0.2.2:81/dbproject/get_platillos.php?IdRestaurante=" + IdRestaurante;*/
        final String URL_DATA = "https://rentame.000webhostapp.com/get_servdepto.php?IDDepto=" + idDepto ;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("result");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                ListItemsServicios item = new ListItemsServicios(
                                        o.getString("Servicio"),
                                        o.getString("Descripcion")




                                );
                                listItemsServicios.add(item);
                            }
                            adapter = new MyAdapterServices(listItemsServicios, getApplicationContext());
                            recyclerViewServices.setAdapter(adapter);

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
