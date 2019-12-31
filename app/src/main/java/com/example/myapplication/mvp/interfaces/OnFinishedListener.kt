package com.example.myapplication.mvp.interfaces

import com.example.myapplication.mvp.models.News
import io.reactivex.Observable
import retrofit2.Response

interface OnFinishedListener{
    fun onFinished(news: Observable<News>?)
}