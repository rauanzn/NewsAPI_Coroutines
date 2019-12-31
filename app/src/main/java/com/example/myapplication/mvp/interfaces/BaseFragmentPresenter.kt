package com.example.myapplication.mvp.interfaces

import android.text.method.TextKeyListener.clear
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable



interface BaseFragmentPresenter{
    fun onDestroy()
    fun onViewAttached()
    fun onViewDetached()
}