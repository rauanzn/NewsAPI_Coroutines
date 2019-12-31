package com.example.myapplication.mvp.presenters

import com.example.myapplication.mvp.abstracts.BaseFragment
import com.example.myapplication.mvp.interfaces.contracts.FragmentNavigation
import com.example.myapplication.mvp.interfaces.contracts.MainActivityContract

class MainActivityPresenter(var view: MainActivityContract.View) : FragmentNavigation.Presenter, MainActivityContract.Presenter{
    override fun searchNews(query: String?) {

    }

    override fun addFragment(fragment: BaseFragment) {
        view.setFragment(fragment)
    }

}