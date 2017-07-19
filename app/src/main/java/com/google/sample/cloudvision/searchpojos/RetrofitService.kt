package com.google.sample.cloudvision.searchpojos

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Android on 7/17/2017.
 */

interface RetrofitService {

    @GET("customsearch/v1")
    fun getStuff(@Query("key") key: String,
                 @Query("cx") id: String,
                 @Query("searchType") searchType: String,
                 @Query("q") image: String): Call<BaseImage>
}
