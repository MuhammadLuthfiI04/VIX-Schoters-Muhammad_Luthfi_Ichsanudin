package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.room

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.BuildConfig
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.activity.main.MainActivity
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model.news.NewsResponse
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.api.news.NewsApi
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model.news.News
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.api.news.ApiConfigNews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    companion object {
        private var newsDatabase: NewsDatabase? = null
        private fun initializeDB(context: Context): NewsDatabase {
            return NewsDatabase.getDatabaseClient(context)
        }

        fun insertNews(context: Context, news: NewsResponse) {
            newsDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                newsDatabase!!.newsDao().insertNews(news)
            }
        }

        fun deleteNews(context: Context, news: NewsResponse) {
            newsDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                newsDatabase!!.newsDao().deleteNews(news)
            }
        }

        fun getAllNews(context: Context): LiveData<List<NewsResponse>> {
            newsDatabase = initializeDB(context)
            return newsDatabase!!.newsDao().getNewsFromDatabase()
        }
    }

    fun getNewsApiCall(category: String?): MutableLiveData<List<NewsResponse>> {
        val newsList = MutableLiveData<List<NewsResponse>>()
        val call = ApiConfigNews.getInstance().create(NewsApi::class.java)
            .getNews("id", category, BuildConfig.API_KEY_NEWS)
        call.enqueue(object : Callback<News> {
            override fun onResponse(
                call: Call<News>,
                response: Response<News>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val tempNewsList = mutableListOf<NewsResponse>()
                        body.articles.forEach {
                            tempNewsList.add(
                                NewsResponse(
                                    it.title,
                                    it.urlToImage,
                                    it.description,
                                    it.url,
                                    it.source.name,
                                    it.publishedAt,
                                    it.content
                                )
                            )
                        }
                        newsList.value = tempNewsList
                    }

                } else {
                    val jsonObj: JSONObject?
                    try {
                        jsonObj = response.errorBody()?.string()?.let { JSONObject(it) }
                        if (jsonObj != null) {
                            MainActivity.apiRequestError = true
                            MainActivity.errorMessage = jsonObj.getString("message")
                            val tempNewsList = mutableListOf<NewsResponse>()
                            newsList.value = tempNewsList
                        }
                    } catch (e: JSONException) {
                        Log.d("JSONException", "" + e.message)
                    }
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                MainActivity.apiRequestError = true
                MainActivity.errorMessage = t.localizedMessage as String
                Log.d("error_message", "message" + t.localizedMessage)
            }
        })

        return newsList
    }

}

