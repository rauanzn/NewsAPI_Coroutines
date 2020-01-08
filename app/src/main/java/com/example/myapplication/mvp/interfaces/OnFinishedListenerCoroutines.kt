package com.example.myapplication.mvp.interfaces

import com.example.myapplication.mvp.models.News
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface OnFinishedListenerCoroutines {
    fun onFinished(news: Deferred<Response<News>>?)
}