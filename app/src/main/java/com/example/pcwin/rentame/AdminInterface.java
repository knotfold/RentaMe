package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.Objects;

public class AdminInterface extends AppCompatActivity {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public boolean logedIn = false;
    public String hasDepto;
    public String mail;
    public String IdUsuario;
    public String user;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_interface);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            logedIn = bundle.getBoolean("logedState");
            mail = bundle.getString("mail");
            hasDepto = bundle.getString("idDepto");
            IdUsuario = bundle.getString("idUser");
            user = bundle.getString("user");


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
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.ic_user);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.home);


    }

    public void Register(View view) {
        startActivity(new Intent(this, Register.class));
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

    Tab1Home tab1 = new Tab1Home();

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
            switch (position) {
                case 0:
                    Tab1Home tab1 = new Tab1Home();
                    return tab1;

                case 1:
                    Tab2Users tab2 = new Tab2Users();
                    return tab2;

                case 2:
                    Tab3ConfigAdmin tab3 = new Tab3ConfigAdmin();
                    return tab3;

                default:
                    return null;

            }
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:

                    return "tab1";
                case 1:
                    return "RESTAURANTES";
                case 2:
                    return "CONFIGURACION";

            }

            return null;
        }
    }
}

