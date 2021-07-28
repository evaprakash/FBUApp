package com.example.fbuapp.yelp;

import android.graphics.Movie;

import com.bumptech.glide.load.engine.Resource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class BusinessResponse {
    List<Business> resources;

    public BusinessResponse() {
        resources = new ArrayList<Business>();
    }

    public List<Business> getResources() {
        return resources;
    }

    public static BusinessResponse parseJSON(String jsonResponse) {
        Gson gson = new GsonBuilder().create();
        BusinessResponse businessResponse = gson.fromJson(jsonResponse, BusinessResponse.class);
        return businessResponse;
    }
}
