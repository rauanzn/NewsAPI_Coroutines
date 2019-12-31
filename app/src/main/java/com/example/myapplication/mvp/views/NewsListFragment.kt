package com.example.myapplication.mvp.views

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.mvp.interfaces.contracts.NewsListContract
import com.example.myapplication.R
import com.example.myapplication.mvp.abstracts.BaseFragment
import com.example.myapplication.mvp.adapters.NewsAdapter
import com.example.myapplication.mvp.interfaces.OnItemClickListener
import com.example.myapplication.mvp.interfaces.OnReachedEndListener
import com.example.myapplication.mvp.interfaces.contracts.FragmentNavigation
import com.example.myapplication.mvp.models.Articles
import com.example.myapplication.mvp.models.News
import com.example.myapplication.mvp.models.NewsModel
import com.example.myapplication.mvp.presenters.NewsListFragmentPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news_list.*
import kotlinx.android.synthetic.main.fragment_news_list.view.*

class NewsListFragment() : BaseFragment(), NewsListContract.View{
    lateinit var presenterNews: NewsListContract.Presenter
    private lateinit var adapter:NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var navPresenter:FragmentNavigation.Presenter
    private lateinit var refresh_layout:SwipeRefreshLayout
    private lateinit var nestedScrollView: NestedScrollView
    private var onItemClickListener = object :OnItemClickListener{
        override fun onItemClick(bundle:Bundle) {
            val detailFragment = DetailFragment()
            detailFragment.arguments = bundle
            detailFragment.attachPresenter(navPresenter)
            navPresenter.addFragment(detailFragment)
        }

    }

    override fun setDataToRecyclerView(news: News) {
        adapter.list.addAll(news.articles as ArrayList<Articles>)
    }

    override fun setPresenter(presenter: NewsListContract.Presenter) {
        this.presenterNews = presenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenterNews.onViewAttached()
        presenterNews.loadNews(context,null)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_list,container,false)
        setPresenter(NewsListFragmentPresenter(this,NewsModel()))
        navPresenter = super.navigationPresenter
        initRecycler(view)
        (activity as AppCompatActivity).setSupportActionBar(view.toolbar_main)
        view.refresh_layout.setOnRefreshListener {
            presenterNews.loadNews(context,null)
            view.refresh_layout.isRefreshing = false
        }

        nestedScrollView = view.nestedscrollview
        nestedScrollView.setOnScrollChangeListener(object :NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if(v?.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1)?.getMeasuredHeight()!! - v.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {
                        (presenterNews as OnReachedEndListener).onReachedEnd()
                    }
                }
            }

        })
        return view
    }
    private fun initRecycler(view: View){
        recyclerView = view.newslist_recyclerview
        adapter = NewsAdapter(ArrayList(),context!!,onItemClickListener,presenterNews as OnReachedEndListener)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
    override fun noInternetConnection() {
        Log.i("no internet","problem")
    }

    override fun onReloadSuccess() {
        Toast.makeText(context,"updated",Toast.LENGTH_SHORT).show()
        adapter.notifyDataSetChanged()
    }

    override fun onReloadError() {
        Toast.makeText(context,"oops error came over",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenterNews.onViewDetached()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterNews.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuInflater = inflater
        menuInflater.inflate(R.menu.search_menu,menu)
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menu.findItem(R.id.action_search)?.actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = "Search Latest News..."
        searchView.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.length>2){
                    presenterNews.loadNews(null,query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenterNews.loadNews(null,newText)
                return false
            }

        })
        menuItem?.icon?.setVisible(false,false)
    }
}