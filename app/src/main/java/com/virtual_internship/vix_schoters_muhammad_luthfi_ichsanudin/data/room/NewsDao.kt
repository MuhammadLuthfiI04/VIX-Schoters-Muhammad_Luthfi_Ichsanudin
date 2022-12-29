package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model.NewsResponse

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: NewsResponse)

    @Query("SELECT * FROM News_Table")
    fun getNewsFromDatabase(): LiveData<List<NewsResponse>>

    @Delete
    fun deleteNews(news: NewsResponse)
}
