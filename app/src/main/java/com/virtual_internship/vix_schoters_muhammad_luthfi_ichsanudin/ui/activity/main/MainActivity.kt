package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.activity.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.FragmentManager
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.room.NewsViewModel
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.BUSINESS
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.ENTERTAINMENT
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.GENERAL
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.HEALTH
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.HOME
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.SCIENCE
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.SPORTS
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.TECHNOLOGY
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.TOTAL_NEWS_TAB
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.R
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model.news.NewsResponse
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.activity.bookmarks.BookmarksActivity
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.activity.profile.ProfileActivity

class MainActivity : AppCompatActivity() {

    private val newsCategories = arrayOf(
        HOME, BUSINESS, ENTERTAINMENT,
        HEALTH, SCIENCE, SPORTS, TECHNOLOGY
    )

    private lateinit var viewModel: NewsViewModel
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var fragmentManager: FragmentManager
    private lateinit var shimmerLayout: ShimmerFrameLayout
    private var totalRequestCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
        shimmerLayout = findViewById(R.id.shimmer_layout)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]


        if (!isNetworkAvailable(applicationContext)) {
            shimmerLayout.visibility = View.GONE
            val showError: TextView = findViewById(R.id.display_error)
            showError.text = getString(R.string.warning)
            showError.visibility = View.VISIBLE
        }

        requestNews(GENERAL, generalNews)
        requestNews(BUSINESS, businessNews)
        requestNews(ENTERTAINMENT, entertainmentNews)
        requestNews(HEALTH, healthNews)
        requestNews(SCIENCE, scienceNews)
        requestNews(SPORTS, sportsNews)
        requestNews(TECHNOLOGY, techNews)

        fragmentManager = FragmentManager(supportFragmentManager, lifecycle)
        viewPager.adapter = fragmentManager
        viewPager.visibility = View.GONE

    }

    private fun requestNews(newsCategory: String, newsData: MutableList<NewsResponse>) {
        viewModel.getNews(category = newsCategory)?.observe(this) {
            newsData.addAll(it)
            totalRequestCount += 1

            if (newsCategory == GENERAL) {
                shimmerLayout.stopShimmer()
                shimmerLayout.hideShimmer()
                shimmerLayout.visibility = View.GONE
                setViewPager()
            }

            if (totalRequestCount == TOTAL_NEWS_TAB) {
                viewPager.offscreenPageLimit = 9
            }
        }
    }

    private fun setViewPager() {
        if (!apiRequestError) {
            viewPager.visibility = View.VISIBLE
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = newsCategories[position]
            }.attach()
        } else {
            val showError: TextView = findViewById(R.id.display_error)
            showError.text = errorMessage
            showError.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.profile_menu -> {
                intent = Intent(applicationContext, ProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.bookmarks_menu -> {
                intent = Intent(applicationContext, BookmarksActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
                return true
            }
        }
        return false
    }

    companion object {
        var generalNews: ArrayList<NewsResponse> = ArrayList()
        var businessNews: MutableList<NewsResponse> = mutableListOf()
        var entertainmentNews: MutableList<NewsResponse> = mutableListOf()
        var healthNews: MutableList<NewsResponse> = mutableListOf()
        var scienceNews: MutableList<NewsResponse> = mutableListOf()
        var sportsNews: MutableList<NewsResponse> = mutableListOf()
        var techNews: MutableList<NewsResponse> = mutableListOf()
        var apiRequestError = false
        var errorMessage = "error"
    }
}
