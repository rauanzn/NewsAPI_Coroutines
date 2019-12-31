package com.example.myapplication.mvp.presenters

import android.content.Context
import android.os.Bundle
import com.example.myapplication.mvp.interfaces.contracts.DetailFragmentContract
import com.example.myapplication.mvp.models.Articles

class DetailFragmentPresenter(var view:DetailFragmentContract.View?,
                              var model:Bundle):DetailFragmentContract.Presenter{
    override fun onDestroy() {

    }

    override fun onViewAttached() {

    }

    override fun onViewDetached() {
        view = null
    }

    override fun loadNews(context: Context?) {
        view?.displayView(model.getParcelable<Articles>("article")!!)
    }

}