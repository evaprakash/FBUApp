package com.example.fbuapp.business;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface YelpService {
    @Headers({"Authorization: Bearer LNaxxtZQkOaK1kgcOt5GWyR03XLK9f845_ERPH-OkWHT0uid2MGrwgbkwRjSljBwbiqrCIgQ_HmOe_9AmXbaEmZphE0iuub830kk4yuqn4HX7cQZ8JKJH_1T-N34YHYx"})
    @GET("businesses/search")
    Call<ResponseBody> filteredSearch(@Query("term") String term, @Query("location") String location, @Query("category") String category, @Query("sort_by") String sortBy, @Query("limit") int limit);
}