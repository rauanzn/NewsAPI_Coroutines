package com.example.myapplication.mvp.presenters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.myapplication.mvp.interfaces.contracts.NewsListContract
import com.example.myapplication.mvp.interfaces.OnFinishedListener
import com.example.myapplication.mvp.interfaces.OnReachedEndListener
import com.example.myapplication.mvp.models.News
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsListFragmentPresenter(var view: NewsListContract.View?,
                                var model: NewsListContract.Model)
    : NewsListContract.Presenter,OnFinishedListener,OnReachedEndListener{


    lateinit var compositeDisposable: CompositeDisposable

    override fun loadNews(context: Context?,keyword:String?) {
        if (keyword == null || keyword.length==0) {
            model.getNews(this)
        }
        else{

            model.getNewsByKeyword(this,keyword)
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
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

    override fun onReachedEnd() {
        model.getNews(this)
    }
}