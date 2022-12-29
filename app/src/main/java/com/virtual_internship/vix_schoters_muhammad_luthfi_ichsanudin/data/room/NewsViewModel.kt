package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model.NewsResponse

class NewsViewModel : ViewModel() {

    private var newsLiveData: MutableLiveData<List<NewsResponse>>? = null

    fun getNews(category: String?): MutableLiveData<List<NewsResponse>>? {

        newsLiveData = category.let { NewsRepository().getNewsApiCall(it) }

        return newsLiveData
    }

    var newsData: LiveData<List<NewsResponse>>? = null

    fun insertNews(context: Context, news: NewsResponse) {
        NewsRepository.insertNews(context, news)
    }

    fun deleteNews(context: Context, news: NewsResponse) {
        NewsRepository.deleteNews(context, news)
    }

    fun getNewsFromDB(context: Context): LiveData<List<NewsResponse>>? {
        newsData = NewsRepository.getAllNews(context)
        return newsData
    }
}
