package com.example.myapplication.mvp.interfaces.contracts

import android.content.Context
import com.example.myapplication.mvp.interfaces.BaseFragmentPresenter
import com.example.myapplication.mvp.interfaces.BaseFragmentView
import com.example.myapplication.mvp.interfaces.OnFinishedListener
import com.example.myapplication.mvp.interfaces.OnFinishedListenerCoroutines
import com.example.myapplication.mvp.models.News

interface NewsListContract{
    interface Model{
        fun getNews(onFinished: OnFinishedListener)
        fun getNewsByKeyword(onFinished: OnFinishedListener,keyword: String?)
        fun getNewsByCoroutines(onFinishedListener: OnFinishedListenerCoroutines)
        fun getNewsByKeywordByCoroutines(onFinished: OnFinishedListenerCoroutines,keyword: String?)
    }
    interface Presenter: BaseFragmentPresenter {
        fun loadNews(context: Context?,keyword:String?)
    }
    interface View:
        BaseFragmentView<Presenter> {
        fun noInternetConnection()
        fun onReloadSuccess()
        fun onReloadError()
        fun setDataToRecyclerView(news: News)
    }
}