package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
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
    public String mail;

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
            Toast.makeText(this, "Las contraseñas coinciden " + mail, Toast.LENGTH_LONG).show();
        }
        Intent intent = getIntent();


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.home);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.ic_message);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.ic_user);





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
                    // set Fragmentclass Arguments
                    Tab1Home tab1 = new Tab1Home();
                    tab1.setArguments(bundle);
                    return tab1;


                case 1:
                    if(!logedIn)
                    {
                        Tab2LoginPlease tab2 = new Tab2LoginPlease();
                        return tab2;
                    }
                    else {
                        Tab2Message tab2 = new Tab2Message();
                        return tab2;
                    }


                case 2:
                    if(!logedIn)
                {
                    Tab3Login tab3 = new Tab3Login();
                    return tab3;
                }
                    else {
                    Tab3Config tab3 = new Tab3Config();
                    return tab3;
                }



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
                    return  "CONFIGURACION";

            }

            return null;
        }
    }
}
