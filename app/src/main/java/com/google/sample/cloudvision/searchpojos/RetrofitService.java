package com.google.sample.cloudvision.searchpojos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Android on 7/17/2017.
 */

public interface RetrofitService {

    @GET("customsearch/v1")
    Call<BaseImage> getStuff(@Query("key") String key,
                             @Query("cx") String id,
                             @Query("searchType") String searchType,
                             @Query("q") String image);
}
