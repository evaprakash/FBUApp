package com.example.fbuapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fbuapp.R;
import com.example.fbuapp.YelpService;

import org.json.JSONException;
import org.json.JSONObject;

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
    private EditText term;
    private EditText location;
    private EditText radius;
    private EditText category;
    private EditText price;
    private Button btnSearch;

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

        term = view.findViewById(R.id.term);
        location = view.findViewById(R.id.location);
        radius = view.findViewById(R.id.radius);
        category = view.findViewById(R.id.category);
        price = view.findViewById(R.id.price);
        btnSearch = view.findViewById(R.id.btnSearch);

        String termContent = term.getText().toString();
        String locationContent = location.getText().toString();
        //java.lang.NumberFormatException: For input string: ""
        //String radiusContent = term.getText().toString();
        String categoryContent = category.getText().toString();
        String priceContent = price.getText().toString();
        System.out.println("!!! " + locationContent + " " + categoryContent + " " + priceContent);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                YelpService yelpService = retrofit.create(YelpService.class);
                System.out.println("!!! " + locationContent + " " + categoryContent + " " + priceContent);
                Call<ResponseBody> call = yelpService.filteredSearch(termContent, locationContent, categoryContent, priceContent);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        int statusCode = response.code();
                        JSONObject content = null;
                        try {
                            content = new JSONObject(response.body().string());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, String.valueOf(content));
                        Log.i(TAG, String.valueOf(response) + " " + response.isSuccessful());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(TAG, "Error with request");
                    }
                });
            }
        });
    }
}