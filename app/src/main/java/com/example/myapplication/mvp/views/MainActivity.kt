package com.example.myapplication.mvp.views

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.R
import com.example.myapplication.mvp.abstracts.BaseFragment
import com.example.myapplication.mvp.interfaces.contracts.FragmentNavigation
import com.example.myapplication.mvp.interfaces.contracts.MainActivityContract
import com.example.myapplication.mvp.presenters.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(),
    MainActivityContract.View {

    private lateinit var fragmentManager:FragmentManager
    private lateinit var fragmentTransaction:FragmentTransaction
    private lateinit var presenter: FragmentNavigation.Presenter
    private val newsFragment: BaseFragment = NewsListFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainActivityPresenter(this)
        newsFragment.attachPresenter(presenter)
        loadFragments()

    }
    private fun loadFragments(){
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container, newsFragment).addToBackStack(null)
            .commit()
    }
    override fun setFragment(fragment: BaseFragment) {
        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.search_menu,menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val menuItem = menu?.findItem(R.id.action_search)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search Latest News..."
        searchView.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.length>2){
                    (newsFragment as NewsListFragment).presenterNews.loadNews(null,query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (newsFragment as NewsListFragment).presenterNews.loadNews(null,newText)
                return false
            }

        })
        menuItem?.icon?.setVisible(false,false)
        return true
    }
}