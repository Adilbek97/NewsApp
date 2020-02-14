package com.example.newsapp.data.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.newsapp.data.network.Api
import com.example.newsapp.data.repository.MainRepository
import com.example.newsapp.models.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsDataSource(
    private val repository: MainRepository,
    val errorWhilePaging:(params: LoadParams<Int>, callback: LoadCallback<Int, Article>)->Unit
    ): PageKeyedDataSource<Int, Article>() {
    val TAG = javaClass.simpleName
    private var current_page = 1
    private val pageSize = 5
    val newsList = ArrayList<Article>()

    override fun loadInitial( params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Article>) {
        Log.d(TAG, "called from loadInitial")
        repository.getNewsFromServer(current_page,{
            callback.onResult(it, null, current_page + 1)
        }){

        }
    }


    /* private fun saveToDb(news: News) {
         newsList.addAll(news.articles)
         newsDao.deleteAll()
         newsDao.insertNews(newsList)
     }*/

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
       /* apiService.getObservableNews(page = current_page).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({news ->
                val key = if (params.key < pageSize) params.key+1 else null
                callback.onResult(news.articles,key)
                Log.d(TAG,"called from loadAfter")
            },{

            })*/
        repository.getNewsFromServer(params.key,{
            val key = if (params.key < pageSize) params.key+1 else null
            callback.onResult(it,key)
            Log.d(TAG,"called from loadAfter")
        }){
            errorWhilePaging(params,callback)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
       /* apiService.getObservableNews(page = current_page).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({news ->

            },{

            })*/
        repository.getNewsFromServer(params.key,{
            val prevPage = if (params.key>1) current_page-1 else null
            callback.onResult(it,prevPage)
            Log.d(TAG,"called from loadBefore")
        }){

        }
    }
}