package com.example.newsapp.ui.main

import com.example.newsapp.models.Article
import com.example.newsapp.models.News

class MainContract {

    interface Presenter {
        fun onDestroy()

    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setDataToRecyclerView(newsArrayList: List<Article>)
        fun onResponseFailure(throwable: Throwable)
    }

}