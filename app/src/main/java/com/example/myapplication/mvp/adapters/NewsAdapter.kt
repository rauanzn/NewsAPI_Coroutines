package com.example.myapplication.mvp.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.mvp.interfaces.OnItemClickListener
import com.example.myapplication.mvp.interfaces.OnReachedEndListener
import com.example.myapplication.mvp.models.Articles
import com.example.myapplication.mvp.models.News
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter(var news: ArrayList<Articles>,con:Context,onItemClickListener: OnItemClickListener,onReachedEndListener: OnReachedEndListener) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
    lateinit var newsList:List<News>
    lateinit var context: Context
    lateinit var onItemClickListener: OnItemClickListener
    lateinit var list:ArrayList<Articles>
    lateinit var onReachedEndListener: OnReachedEndListener
    lateinit var recyclerview:RecyclerView
    init {
        list = news
        context = con
        this.onItemClickListener = onItemClickListener
        this.onReachedEndListener = onReachedEndListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false)
        return NewsHolder(view,onItemClickListener)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.itemView.author.setText(list.get(position).author)
        holder.itemView.publishedAt.setText(list.get(position).publishedAt)
        holder.itemView.desc.setText(list.get(position).description)
        holder.itemView.title.setText(list.get(position).title)
        Picasso.get().load(list.get(position).urlToImage).into(holder.itemView.img)
        holder.itemView.load_photo.visibility = View.INVISIBLE
        holder.bind(list.get(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class NewsHolder(itemView: View,var onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        fun bind(articles: Articles){
            val bundle = Bundle()
            bundle.putParcelable("article",articles)
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(bundle)
            }
        }

    }
}