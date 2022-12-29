package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model.news.NewsResponse
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.DATABASE_NAME

@Database(entities = [NewsResponse::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    companion object {

        @Volatile
        private var INSTANCE: NewsDatabase? = null
        fun getDatabaseClient(context: Context): NewsDatabase {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, NewsDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!
            }
        }
    }
}