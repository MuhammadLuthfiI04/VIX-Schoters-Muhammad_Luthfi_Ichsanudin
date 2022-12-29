package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.api.news

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfigNews {

    private const val BASE_URL = "https://newsapi.org/v2/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}