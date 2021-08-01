package com.example.fbuapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fbuapp.R;
import com.example.fbuapp.business.Business;
import com.example.fbuapp.business.BusinessAdapter;
import com.example.fbuapp.comments.CommentAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class BusinessActivity extends AppCompatActivity {

    List<Business> rankedBusinesses;
    List<Float> rankings;
    RecyclerView rvBusinesses;
    List<Business> allBusinesses;
    BusinessAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        rankedBusinesses = (List<Business>) Parcels.unwrap(getIntent().getParcelableExtra("rankedBusinesses"));
        rvBusinesses = findViewById(R.id.rvBusinesses);
        allBusinesses= new ArrayList<>();
        adapter = new BusinessAdapter(getApplicationContext(), allBusinesses);
        rvBusinesses.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvBusinesses.setLayoutManager(linearLayoutManager);
        allBusinesses.addAll(rankedBusinesses);
        adapter.notifyDataSetChanged();
    }
}