package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.activity.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.api.github.ApiConfigGithub
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model.github.DetailUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUser>()

    fun setUserDetail(username: String?) {
        ApiConfigGithub.apiInstance
            .getDetailUser(username.toString())
            .enqueue(object : Callback<DetailUser> {
                override fun onResponse(
                    call: Call<DetailUser>,
                    response: Response<DetailUser>,
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                    Log.d("Error", t.message.toString())
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUser> {
        return user
    }
}