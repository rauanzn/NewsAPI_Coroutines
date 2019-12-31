package com.example.myapplication.mvp.abstracts

import androidx.fragment.app.Fragment
import com.example.myapplication.mvp.interfaces.contracts.FragmentNavigation


abstract class BaseFragment : Fragment(), FragmentNavigation.View {

    lateinit var navigationPresenter: FragmentNavigation.Presenter
    override fun attachPresenter(
        presenter: FragmentNavigation.Presenter
    ) {
        navigationPresenter = presenter
    }
}