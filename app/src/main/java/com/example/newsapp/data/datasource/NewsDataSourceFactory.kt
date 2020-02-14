package com.example.newsapp.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.newsapp.data.network.Api
import com.example.newsapp.data.repository.MainRepository
import com.example.newsapp.models.Article


class NewsDataSourceFactory(
    private val repository: MainRepository,
    private val errorWhilePaging:(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, Article>)->Unit
): DataSource.Factory<Int, Article>() {

    val newsDataSourceLiveData = MutableLiveData<PageKeyedDataSource<Int, Article>>()
    override fun create(): DataSource<Int, Article> {
        val newsDataSource = NewsDataSource(repository,errorWhilePaging)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }

}