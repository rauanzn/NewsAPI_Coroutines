package com.example.myapplication.mvp.api

import com.example.myapplication.mvp.models.News
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface GSON {
    @GET("top-headlines")
    fun getNews(@Query("country")country:String,
                @Query("apiKey")apiKey:String,
                @Query("page")page:Int): Observable<News>

    @GET("everything")
    fun getNewsSearch(
        @Query("q")keyword:String,
        @Query("language")language:String,
        @Query("sortBy")sortBy:String,
        @Query("apiKey")apiKey:String
        ):Observable<News>
}