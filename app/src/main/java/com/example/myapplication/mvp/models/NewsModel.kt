package com.example.myapplication.mvp.models

import android.annotation.SuppressLint
import android.util.Log
import com.example.myapplication.mvp.api.ApiClient
import com.example.myapplication.mvp.api.GSON
import com.example.myapplication.mvp.interfaces.contracts.NewsListContract
import com.example.myapplication.mvp.interfaces.OnFinishedListener
import com.example.myapplication.mvp.interfaces.OnFinishedListenerCoroutines

class NewsModel : NewsListContract.Model{
    lateinit var apiCient:ApiClient
    var PAGE:Int = 0
    init {
        apiCient = ApiClient()
    }
    @SuppressLint("CheckResult")
    override fun getNews(onFinishedListener: OnFinishedListener) {
        PAGE++
        val api = apiCient.getApiClient()?.create(GSON::class.java)
        val call =  api?.getNews("us",ApiClient.TOKEN,PAGE)
        onFinishedListener.onFinished(call)
    }

    override fun getNewsByKeyword(onFinished: OnFinishedListener, keyword: String?) {
        val api = apiCient.getApiClient()?.create(GSON::class.java)
        val call =  api?.getNewsSearch(keyword!!,"en","publishedAt",ApiClient.TOKEN)
        onFinished.onFinished(call)
    }
    @SuppressLint("CheckResult")
    override fun getNewsByCoroutines(onFinishedListener: OnFinishedListenerCoroutines) {
        PAGE++
        val api = apiCient.getApiClientByCoroutines()?.create(GSON::class.java)
        val call =  api?.getNewsByCoroutines("us",ApiClient.TOKEN,PAGE)
        onFinishedListener.onFinished(call)
    }

    override fun getNewsByKeywordByCoroutines(onFinished: OnFinishedListenerCoroutines, keyword: String?) {
        val api = apiCient.getApiClient()?.create(GSON::class.java)
        val call =  api?.getNewsSearchByCoroutines(keyword!!,"en","publishedAt",ApiClient.TOKEN)
        onFinished.onFinished(call)
    }
}