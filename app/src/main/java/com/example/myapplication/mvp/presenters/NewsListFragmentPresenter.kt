package com.example.myapplication.mvp.presenters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.myapplication.mvp.interfaces.contracts.NewsListContract
import com.example.myapplication.mvp.interfaces.OnFinishedListener
import com.example.myapplication.mvp.interfaces.OnFinishedListenerCoroutines
import com.example.myapplication.mvp.interfaces.OnReachedEndListener
import com.example.myapplication.mvp.models.News
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import retrofit2.Response

class NewsListFragmentPresenter(var view: NewsListContract.View?,
                                var model: NewsListContract.Model)
    : NewsListContract.Presenter,OnFinishedListener,OnReachedEndListener,OnFinishedListenerCoroutines{
    lateinit var compositeDisposable: CompositeDisposable
    private var myJob: Job? = null

    override fun loadNews(context: Context?,keyword:String?) {
        if (keyword == null || keyword.length==0) {
            model.getNewsByCoroutines(this)
//Rx part            model.getNews(this)
        }
        else{
            model.getNewsByKeywordByCoroutines(this,keyword)
//Rx part            model.getNewsByKeyword(this,keyword)
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        myJob?.cancel()
    }

    override fun onViewAttached() {
        compositeDisposable = CompositeDisposable()
    }
    override fun onViewDetached() {
        view = null
        compositeDisposable.dispose()
    }
    @SuppressLint("CheckResult")
    override fun onFinished(news: Observable<News>?) {
        Log.i("newslistfragment","yoy")
        news?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                Log.i("search",it.articles.toString())

            view?.setDataToRecyclerView(it)
            view?.onReloadSuccess()
    },{
                view?.onReloadError()
            })
    }
    override fun onFinished(news: Deferred<Response<News>>?) {
        myJob = CoroutineScope(Dispatchers.Main).launch {
            val result = asyncFetchingData(news).await()
            if (result != null) {
                when (result.code()) {
                    201, 200 -> {
                        Log.i("NewsListFragmentPresent", "assigned")
                        view?.setDataToRecyclerView(result.body()!!)
                        view?.onReloadSuccess()
                    }
                }
            }
        }
    }
    fun asyncFetchingData(news: Deferred<Response<News>>?) = GlobalScope.async(Dispatchers.IO+myJob!!){
            try {
                news?.await()
            }
            catch (e:Throwable){
                view?.onReloadError()
                null
            }
        }
    override fun onReachedEnd() {
        model.getNews(this)
    }
}