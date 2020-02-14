package com.example.newsapp.ui.main

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.newsapp.data.datasource.NewsDataSourceFactory
import com.example.newsapp.data.network.Api
import com.example.newsapp.data.repository.MainRepository
import com.example.newsapp.gone
import com.example.newsapp.models.Article
import com.example.newsapp.models.News
import com.example.newsapp.showToast
import com.example.newsapp.visible
import kotlinx.android.synthetic.main.activity_main.*

class MainPresenter(val view: MainContract.View,
                    private val repository: MainRepository):
    MainContract.Presenter {
    private val TAG = javaClass.simpleName
    lateinit var params: PageKeyedDataSource.LoadParams<Int>
    lateinit var callback: PageKeyedDataSource.LoadCallback<Int, Article>
    private val newsDataSourceFactory = NewsDataSourceFactory(repository){params, callback ->
        this.params = params
        this.callback = callback
        (view as MainActivity).showToast("При загразке данных произошла ошибка.Проверьте Ваше подключение к сети.")
        view.showErrorContainer()
    }

    var itemPagedList:LiveData<PagedList<Article>>
    private var liveDataSource:LiveData<PageKeyedDataSource<Int, Article>>
    init {
        liveDataSource = newsDataSourceFactory.newsDataSourceLiveData
        val config = PagedList.Config.Builder()
            .setPageSize(5)
            .setEnablePlaceholders(false)
            .build()
        itemPagedList = LivePagedListBuilder(newsDataSourceFactory,config).build()
    }

    fun retry(){
        liveDataSource.value?.loadAfter(this.params,this.callback)
    }


    override fun onDestroy() {

    }

}