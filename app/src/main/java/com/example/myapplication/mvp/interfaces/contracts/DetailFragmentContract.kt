package com.example.myapplication.mvp.interfaces.contracts

import android.content.Context
import com.example.myapplication.mvp.interfaces.BaseFragmentPresenter
import com.example.myapplication.mvp.interfaces.BaseFragmentView
import com.example.myapplication.mvp.models.Articles
import com.example.myapplication.mvp.models.News

interface DetailFragmentContract{
    interface Presenter: BaseFragmentPresenter {
        fun loadNews(context: Context?)
    }
    interface View:
        BaseFragmentView<Presenter> {
        fun displayView(article:Articles)
    }
}