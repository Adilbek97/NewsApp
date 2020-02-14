package com.example.newsapp.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.newsapp.data.db.NewsDataBase
import com.example.newsapp.MyApplication
import com.example.newsapp.data.network.Api
import com.example.newsapp.models.Article
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainRepository(private val retrofitService: Api){
    private val TAG = javaClass.simpleName
    private val dataBase = NewsDataBase.getDatabase(MyApplication.instance)
    val dao = dataBase.newsDao()

    @SuppressLint("CheckResult")
    fun getNewsFromServer(
        page: Int,
        onSucces: (article: List<Article>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        retrofitService.getObservableNews(page = page).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ news ->
                Log.d(TAG, "onSucces called")
                onSucces(news.articles)
                Log.d(TAG, "size is ${news.articles.size}")
                //save to database
                 saveToDb(news.articles)

            }, {
                Log.d(TAG, "onError called")
                if (page == 1) {

                    dao.getArticles()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            onSucces( it)
                            Log.d(TAG,"${it.size} items came from db")
                        }
                }else{
                    onError(it)
                }
            })
    }

    @SuppressLint("CheckResult")
    private fun saveToDb(articles: List<Article>) {
        for (article in articles) {
            Completable.fromAction {
                dao.insert(article)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    Log.d(TAG,"new saved")
                }
        }
    }

}