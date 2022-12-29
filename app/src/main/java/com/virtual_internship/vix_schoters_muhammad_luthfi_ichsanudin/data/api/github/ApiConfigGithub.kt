package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.api.github


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL_GITHUB = "https://api.github.com/"

class ApiConfigGithub {
    companion object{
        private val retrofitGithub: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_GITHUB)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiInstance: GithubApi = retrofitGithub.create(GithubApi::class.java)
    }
}