package com.example.pcwin.rentame;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GraphPrediction extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_prediction);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 10000),
                new DataPoint(1, 5000),
                new DataPoint(2, 30000),
                new DataPoint(3, 2700),
                new DataPoint(4, 11600),
                new DataPoint(5,22600),
                new DataPoint(6,34700)
        });
        series.setTitle("Ganancias por mes, en el semestre");
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        graph.addSeries(series);
    }

    public void compareFecha()
    {

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
                                o.getString("costoRenta");

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
}
