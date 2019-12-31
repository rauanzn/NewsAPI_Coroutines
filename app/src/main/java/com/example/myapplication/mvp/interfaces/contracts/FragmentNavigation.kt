package com.example.myapplication.mvp.interfaces.contracts

import com.example.myapplication.mvp.abstracts.BaseFragment

interface FragmentNavigation {
    interface View{
        fun attachPresenter(presenter: Presenter)
    }
    interface Presenter{
        fun addFragment(fragment: BaseFragment)
    }
}