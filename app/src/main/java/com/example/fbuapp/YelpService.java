package com.example.fbuapp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface YelpService {
    //static final String API_KEY = "";
    @Headers({"Authorization: Bearer LNaxxtZQkOaK1kgcOt5GWyR03XLK9f845_ERPH-OkWHT0uid2MGrwgbkwRjSljBwbiqrCIgQ_HmOe_9AmXbaEmZphE0iuub830kk4yuqn4HX7cQZ8JKJH_1T-N34YHYx"})
    @GET("businesses/search")
    Call<ResponseBody> filteredSearch(@Query("term") String term, @Query("location") String location);
}