package com.example.fbuapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.fbuapp.R;
import com.example.fbuapp.Ranking;
import com.example.fbuapp.activities.BusinessActivity;
import com.example.fbuapp.business.Business;
import com.example.fbuapp.business.BusinessResponse;
import com.example.fbuapp.business.YelpService;
import com.parse.Parse;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import www.sanju.motiontoast.MotionToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private final String BASE_URL = "https://api.yelp.com/v3/";
    private final String TAG = "SearchFragment";
    private final String SORT_BY = "review_count";
    public final String BACKUP_LOCATION = "Menlo Park";
    public static final String KEY_LOCATION = "location";
    private final int LIMIT = 50;
    private EditText term;
    private EditText location;
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

        Spinner category_spinner = (Spinner) view.findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> category_adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.category_array, android.R.layout.simple_spinner_item);
        category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(category_adapter);
        category_spinner.setOnItemSelectedListener(this);


        Spinner price_spinner = (Spinner) view.findViewById(R.id.price_spinner);
        ArrayAdapter<CharSequence> price_adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.price_array, android.R.layout.simple_spinner_item);
        price_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        price_spinner.setAdapter(price_adapter);
        price_spinner.setOnItemSelectedListener(this);

        Spinner transportation_spinner = (Spinner) view.findViewById(R.id.transportation_spinner);
        ArrayAdapter<CharSequence> transportation_adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.transportation_array, android.R.layout.simple_spinner_item);
        transportation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transportation_spinner.setAdapter(transportation_adapter);
        transportation_spinner.setOnItemSelectedListener(this);

        btnSearch = view.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                YelpService yelpService = retrofit.create(YelpService.class);
                String termContent = term.getText().toString().toLowerCase();
                String rawLocationContent = location.getText().toString();
                String transportationContent = transportation_spinner.getSelectedItem().toString().toLowerCase();
                String rawCategoryContent = category_spinner.getSelectedItem().toString();
                String categoryContent;
                if (rawCategoryContent.equals("Counseling and Mental Health")) {
                    categoryContent = "c_and_mh";
                } else {
                    categoryContent = rawCategoryContent.replaceAll("\\s", "").toLowerCase();
                }

                String rawPriceContent = price_spinner.getSelectedItem().toString().toLowerCase();
                String priceContent;
                if (rawPriceContent.equals("expensive")) {
                    priceContent="4";
                } else if (rawPriceContent.equals("medium")) {
                    priceContent = "2, 3";
                } else {
                    priceContent="1";
                }

                String locationContent;
                if (rawLocationContent.equals("")) {
                    String defaultLocationContent = ParseUser.getCurrentUser().getString(KEY_LOCATION);
                    if (defaultLocationContent.equals("")) {
                        locationContent = BACKUP_LOCATION.toLowerCase();
                    } else {
                        locationContent = defaultLocationContent.toLowerCase();
                    }
                } else {
                    locationContent = rawLocationContent.toLowerCase();
                }

                Call<ResponseBody> call = yelpService.filteredSearch(termContent, locationContent, categoryContent, SORT_BY, LIMIT);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        int statusCode = response.code();
                        JSONObject jsonResponse = null;
                        try {
                            if (response.body() != null) {
                                jsonResponse = new JSONObject(response.body().string());
                                BusinessResponse businessResponse = BusinessResponse.parseJSON(jsonResponse.toString());
                                Ranking ranking = new Ranking(businessResponse.getResources(), priceContent, transportationContent);
                                List<Business> rankedBusinesses = ranking.rank();
                                seeResults(rankedBusinesses);
                            } else {
                                MotionToast.Companion.createColorToast(getActivity(),
                                        "Error",
                                        "Error with search request.",
                                        MotionToast.TOAST_ERROR,
                                        MotionToast.GRAVITY_BOTTOM,
                                        MotionToast.SHORT_DURATION,
                                        ResourcesCompat.getFont(getContext(),R.font.helvetica_regular));
                                Log.e(TAG, "Error with search request!!");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, String.valueOf(jsonResponse));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(TAG, "Error with request");
                        MotionToast.Companion.createColorToast(getActivity(),
                                "Error",
                                "Error with search request.",
                                MotionToast.TOAST_ERROR,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.SHORT_DURATION,
                                ResourcesCompat.getFont(getContext(),R.font.helvetica_regular));
                    }
                });
            }
        });
    }

    private void seeResults(List<Business> rankedBusinesses) {
        Intent intent = new Intent(getContext(), BusinessActivity.class);
        intent.putExtra("rankedBusinesses", Parcels.wrap(rankedBusinesses));
        getContext().startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}