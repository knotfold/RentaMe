package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public boolean logedIn = false;
    public String hasDepto;
    public String mail;
    public String IdUsuario;
    public String user;
    String noHabitaciones;
    String capacidadDepto;
    String noBanos;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {

                logedIn = bundle.getBoolean("logedState");
                mail = bundle.getString("mail");
                hasDepto = bundle.getString("idDepto");
                IdUsuario = bundle.getString("idUser");
                user = bundle.getString("user");
                noHabitaciones = bundle.getString("noHabitaciones");
                capacidadDepto = bundle.getString("capacidadDepto");
                noBanos = bundle.getString("noBanos");




        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.logo);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.home);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.ic_message);
        Objects.requireNonNull(tabLayout.getTabAt(3)).setIcon(R.drawable.ic_user);

    }

    public void Register(View view)
    {
        startActivity(new Intent(this,Register.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void busquedaRevertDepto()
    {
        noBanos ="";
        noHabitaciones = "";
        capacidadDepto = "";
    }


    final Handler handler = new Handler();
    final Runnable r = new Runnable() {
        public void run() {

            tab1.listItems.clear();
            tab1.adapterDepto.notifyDataSetChanged();
            tab1.loadRecyclerViewData(tab1.URL_REVERT);

        }
    };
    Tab1Home tab1;

    /**
     * A placeholder fragment containing a simple view.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @SuppressLint("Assert")
        @Override
        public Fragment getItem(int position) {
            switch(position)
            {
                case 0:

                    Bundle bundle = new Bundle();
                    bundle.putBoolean("logedState", logedIn);
                    bundle.putString("idDepto",hasDepto);
                    bundle.putString("idUser",IdUsuario);
                    bundle.putString("user",user);
                    bundle.putString("noBanos",noBanos);
                    bundle.putString("noHabitaciones",noHabitaciones);
                    bundle.putString("capacidadDepto",capacidadDepto);
                    // set Fragmentclass Arguments
                    tab1  = new Tab1Home();
                    tab1.setArguments(bundle);
                    return tab1;


                case 1:

                    busquedaRevertDepto();
                    if(!logedIn)
                    {
                        Tab2LoginPlease tab2 = new Tab2LoginPlease();

                        return tab2;
                    }
                    else {
                        if(hasDepto.equals("null"))
                        {
                            Bundle mydepto = new Bundle();
                            mydepto.putString("idDepto",hasDepto);
                            Tab2NoDepto tab2 = new Tab2NoDepto();
                            tab2.setArguments(mydepto);
                            return tab2;
                        }
                        else {
                            Bundle extras = new Bundle();
                            extras.putString("idDepto", hasDepto);
                            // set Fragmentclass Arguments
                            Tab2MyDepto tab2 = new Tab2MyDepto();
                            tab2.setArguments(extras);
                            return tab2;
                        }
                    }


                case 2:
                    handler.postDelayed(r, 1000);
                    if(!logedIn)
                {
                    Tab2LoginPlease tab3 = new Tab2LoginPlease();
                    return tab3;
                }
                    else {
                        if (hasDepto!=null)
                        {
                           Bundle extras = new Bundle();
                            extras.putString("idUser", IdUsuario);
                            Tab3Message tab3 = new Tab3Message();
                            tab3.setArguments(extras);
                            return tab3;
                        }
                        else {
                            TabMaintenance tab3 = new TabMaintenance();
                            return tab3;
                        }
                }

                case 3:
                    busquedaRevertDepto();
                    if(!logedIn)
                    {


                        // set Fragmentclass Arguments
                        Tab3Login tab4 = new Tab3Login();

                        return tab4;
                    }
                    else {
                        Bundle login = new Bundle();
                        login.putString("user",user);
                        login.putString("idUser",IdUsuario);
                        login.putString("mail",mail);
                        login.putString("idDepto",hasDepto);
                        Tab3Config tab4 = new Tab3Config();
                        tab4.setArguments(login);
                        return tab4;
                    }



                default:
                    return null;

            }
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:

                    return "tab1";
                case 1:
                    return "RESTAURANTES";
                case 2:
                    return  "CONFIGURACION";
                case 3:
                    return  "CONFIGURACION";
            }

            return null;
        }
    }



}
