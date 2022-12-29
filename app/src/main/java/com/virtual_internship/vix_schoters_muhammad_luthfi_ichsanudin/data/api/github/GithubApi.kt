package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.api.github

import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model.github.DetailUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


private const val API_KEY_GITHUB = "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"

interface GithubApi {

    @GET("users/{username}")
    @Headers("Authorization: token $API_KEY_GITHUB")
    fun getDetailUser(
        @Path("username") username: String,
    ): Call<DetailUser>
}