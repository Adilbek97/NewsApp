package com.example.newsapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.models.Article
import com.example.newsapp.showToast
import com.example.newsapp.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(),
    MainContract.View {
    val TAG = javaClass.simpleName
    private val presenter: MainPresenter by inject{ parametersOf(this)}
    private lateinit var newsListAdapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsListAdapter =
            NewsListAdapter( { url ->
                startActivity(Intent(this,DetailActivity::class.java).putExtra("url",url))
            }){
                hideErrorContainer()
                presenter.retry()
            }

        news_rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = newsListAdapter
        }

        presenter.itemPagedList.observe(this,Observer{
            Log.d(TAG," ${it.size} items came")
            newsListAdapter.submitList(it)
        })
    }

    fun showErrorContainer(){
        newsListAdapter.showErrorContainer()
    }
    fun hideErrorContainer(){
        newsListAdapter.hideErrorContainer()
    }

    override fun showProgress() {
        showToast("ok succesfully came")
    }

    override fun hideProgress() {
        showToast("on error")
    }

    override fun setDataToRecyclerView(newsList: List<Article>) {


    }

    override fun onResponseFailure(throwable: Throwable) {

    }
}
