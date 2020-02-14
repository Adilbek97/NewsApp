package com.example.newsapp.di

import com.example.newsapp.data.network.Api
import com.example.newsapp.data.network.RetrofitService
import com.example.newsapp.data.repository.MainRepository
import com.example.newsapp.ui.main.MainContract
import com.example.newsapp.ui.main.MainPresenter
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    single { RetrofitService.builService(Api::class.java) }
    single { MainRepository(get()) }
//    single { (newsDao: NewsDao) ->NewsDataSourceFactory(get(),newsDao) }

    factory { (view: MainContract.View) ->
        MainPresenter(view, get())
    }

}