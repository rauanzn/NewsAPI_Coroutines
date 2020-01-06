package com.example.myapplication.mvp.views

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.view.ContextThemeWrapper
import com.example.myapplication.mvp.abstracts.BaseFragment
import com.example.myapplication.mvp.interfaces.contracts.DetailFragmentContract
import com.example.myapplication.mvp.models.Articles
import com.example.myapplication.mvp.presenters.DetailFragmentPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.detail_fragment.view.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R


class DetailFragment :BaseFragment(),DetailFragmentContract.View{
    lateinit var photo:ImageView
    lateinit var description:TextView
    lateinit var author_is:TextView
    lateinit var toolbar:androidx.appcompat.widget.Toolbar
    lateinit var presenterDetail:DetailFragmentContract.Presenter

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.detail_fragment,container,false)
        photo = view.photo
        description = view.detail_content
        author_is = view.author_is
        toolbar = view.toolbar
        setPresenter(DetailFragmentPresenter(this,arguments!!))
        presenterDetail.loadNews(context)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true);

    }
    override fun displayView(article: Articles) {
        Picasso.get().load(article.urlToImage).into(photo)
        description.setText(article.description)
        author_is.setText(article.author)
    }

    override fun setPresenter(presenter: DetailFragmentContract.Presenter) {
        presenterDetail = presenter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            Log.i("DetailFragment","clicked")
            activity?.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)

    }
}