package com.example.newsapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.gone
import com.example.newsapp.models.Article
import com.example.newsapp.visible
import kotlinx.android.synthetic.main.footer_item.view.*
import kotlinx.android.synthetic.main.news_item.view.*

class NewsListAdapter(val selectedListener:(url:String)->Unit,
                      val retry:()->Unit):
    PagedListAdapter<Article, RecyclerView.ViewHolder>(
        HospitalDC()
    ){
    lateinit var context: Context
    private val ITEM = 0
    private val FOOTER = 1
    private lateinit var itemView1:View

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        if (position == FOOTER){
            val inflater = LayoutInflater.from(parent.context)
            context = parent.context
            return FooterHolder(inflater.inflate(R.layout.footer_item, parent, false))
        }else{
            val inflater = LayoutInflater.from(parent.context)
            context = parent.context
            return ViewHolder(inflater.inflate(R.layout.news_item, parent, false))
        }

    }

   /* override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!,position)
    }*/
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if (holder is ViewHolder){
           holder.bind(getItem(position)!!,position)
       }
    }
    /*fun swapData(data : List<Article>){
        audioListFull.addAll(data)
        submitList(data.toMutableList())
    }*/

    inner class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(article: Article, position: Int){
            itemView.name_tv.text = article.title
            itemView.adress_tv.text = article.description
            itemView.data_tv.text = article.publishedAt

            Glide.with(context).load(article.urlToImage).into(itemView.image_view)
            itemView.setOnClickListener {
                selectedListener(article.url)
            }
        }
    }

    fun showErrorContainer(){
        itemView1.visible()
    }

    fun hideErrorContainer(){
        itemView1.gone()
    }

    override fun getItemCount(): Int {
        return super.getItemCount()+1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount-1) {
            FOOTER
        } else
            ITEM
    }

    inner class FooterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.retry_tv.setOnClickListener {retry()}
            itemView1 = itemView
        }
    }

    private class HospitalDC : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem == newItem
        }
    }

}