package com.example.pcwin.rentame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TabMaintenance extends Fragment {

    Button buttonRegister;
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState){
        View rootView = inflater.inflate(R.layout.tab_maintenance, container, false);



        return rootView;

    }



}
