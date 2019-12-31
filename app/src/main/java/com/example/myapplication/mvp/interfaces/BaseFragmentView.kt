package com.example.myapplication.mvp.interfaces

interface BaseFragmentView<T:BaseFragmentPresenter> {
    fun setPresenter(presenter: T)
}