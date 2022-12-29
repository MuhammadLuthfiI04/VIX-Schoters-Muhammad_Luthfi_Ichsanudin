package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.api

import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/v2/top-headlines")
    fun getNews(
        @Query("country") country : String,
        @Query("category") category : String?,
        @Query("apiKey") key : String)
    : Call<News>
}