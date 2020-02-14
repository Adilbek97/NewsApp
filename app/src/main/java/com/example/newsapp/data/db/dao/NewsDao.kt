package com.example.newsapp.data.db.dao

import androidx.room.*
import com.example.newsapp.models.Article
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface NewsDao {

    @Insert
    fun insertNews(articles:List<Article>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article):Completable

    @Query("SELECT * FROM articles")
    fun getArticles(): Observable<List<Article>>

    @Query("DELETE FROM articles")
    fun deleteAll(): Single<Int>
}