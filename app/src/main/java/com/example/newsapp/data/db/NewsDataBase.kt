package com.example.newsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.db.dao.NewsDao
import com.example.newsapp.models.Article
import com.example.newsapp.models.Source

@Database(entities = [Article::class, Source::class],version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDataBase : RoomDatabase(){

    abstract fun newsDao():NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDataBase? = null

        fun getDatabase(context: Context): NewsDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDataBase::class.java,
                    "news_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}