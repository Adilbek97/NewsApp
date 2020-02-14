package com.example.newsapp.data.network

import com.example.newsapp.models.News
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("v2/everything")
    fun getNews(@Query("q") q:String = "android",
                @Query("from") from:String = "2019-04-00",
                @Query("sortBy") sortBy:String = "publishedAt",
                @Query("apiKey") apiKey:String = "26eddb253e7840f988aec61f2ece2907",
                @Query("page") page:Int):Call<News>
    @GET("v2/everything")
    fun getObservableNews(@Query("q") q:String = "android",
                @Query("from") from:String = "2019-04-00",
                @Query("sortBy") sortBy:String = "publishedAt",
                @Query("apiKey") apiKey:String = "26eddb253e7840f988aec61f2ece2907",
                @Query("page") page:Int):Observable<News>
}