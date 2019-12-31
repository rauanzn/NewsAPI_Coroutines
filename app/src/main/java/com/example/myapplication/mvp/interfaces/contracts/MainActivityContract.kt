package com.example.myapplication.mvp.interfaces.contracts

import androidx.fragment.app.Fragment
import com.example.myapplication.mvp.abstracts.BaseFragment

interface MainActivityContract {
    interface View {
        fun setFragment(fragment: BaseFragment)
    }

    interface Presenter {
        fun searchNews(query:String?)
    }
}