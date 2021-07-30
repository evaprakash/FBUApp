package com.example.fbuapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fbuapp.R;
import com.example.fbuapp.business.Business;

import org.parceler.Parcels;

import java.util.List;

public class BusinessActivity extends AppCompatActivity {

    List<Business> rankedBusinesses;
    List<Float> rankings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        rankedBusinesses = (List<Business>) Parcels.unwrap(getIntent().getParcelableExtra("rankedBusinesses"));
        rankings = (List<Float>) Parcels.unwrap(getIntent().getParcelableExtra("rankingsList"));
    }
}