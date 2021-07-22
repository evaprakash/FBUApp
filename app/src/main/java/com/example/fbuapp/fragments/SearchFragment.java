package com.example.fbuapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fbuapp.R;
import com.example.fbuapp.YelpService;

import java.io.IOException;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private final String BASE_URL = "https://api.yelp.com/v3/";
    private final String API_KEY = "LNaxxtZQkOaK1kgcOt5GWyR03XLK9f845_ERPH-OkWHT0uid2MGrwgbkwRjSljBwbiqrCIgQ_HmOe_9AmXbaEmZphE0iuub830kk4yuqn4HX7cQZ8JKJH_1T-N34YHYx";
    private final String AUTH_HEADER = "Bearer " + API_KEY;
    private final String TAG = "SearchFragment";

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        YelpService yelpService = retrofit.create(YelpService.class);
        Call<ResponseBody> call = yelpService.filteredSearch(AUTH_HEADER, "Starbucks", "California");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                ResponseBody user = response.body();
                Log.i(TAG, String.valueOf(response) + " " + response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Error with request");
            }
        });
    }
}