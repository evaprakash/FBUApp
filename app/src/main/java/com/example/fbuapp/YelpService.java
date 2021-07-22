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
    @GET("businesses/search")
    Call<ResponseBody> filteredSearch(@Header("Authorization") String authHeader, @Query("term") String term, @Query("location") String location);
}